import tkinter as tk
from tkinter import ttk
from matplotlib.backends.backend_tkagg import FigureCanvasTkAgg
from matplotlib.figure import Figure
from matplotlib.animation import FuncAnimation
import socket
import threading
import sys
import json
import numpy as np

# Global variables for real-time plotting and joint angles
x_data, y_data = [], []
joint_angles = [0] * 7  # Assuming 7 joints for the KUKA iiwa
recorded_positions = []
recording = False

# Robot connection details
ROBOT_HOST = '10.100.0.2'
ROBOT_PORT = 30000

class RobotUI:
    def __init__(self, root):
        self.root = root
        self.root.title("KUKA Robot Control & Monitoring")

        # Real-time plotting frame
        self.plot_frame = ttk.Frame(self.root)
        self.plot_frame.pack(fill=tk.BOTH, expand=True, padx=10, pady=10)

        self.fig = Figure(figsize=(5, 4), dpi=100)
        self.ax = self.fig.add_subplot(111)
        self.ax.set_title("Real-time Robot Data")
        self.ax.set_xlabel("Time (s)")
        self.ax.set_ylabel("Z Force (N)")
        self.line, = self.ax.plot([], [], lw=2)

        self.canvas = FigureCanvasTkAgg(self.fig, master=self.plot_frame)
        self.canvas.get_tk_widget().pack(fill=tk.BOTH, expand=True)

        # Start/Stop plotting button
        self.plot_button = ttk.Button(self.root, text="Start Plotting", command=self.toggle_plotting)
        self.plot_button.pack(pady=5)

        # Joint angles display
        self.angles_frame = ttk.Frame(self.root)
        self.angles_frame.pack(fill=tk.BOTH, expand=True, padx=10, pady=10)

        ttk.Label(self.angles_frame, text="Current Joint Angles (degrees):", font=("Arial", 12)).pack(anchor=tk.W)

        # Create labels for each joint angle
        self.angle_labels = []
        for i in range(7):
            label = ttk.Label(self.angles_frame, text=f"Joint {i + 1}: 0.00°", font=("Arial", 10))
            label.pack(anchor=tk.W)
            self.angle_labels.append(label)

        # External forces display
        self.forces_frame = ttk.Frame(self.root)
        self.forces_frame.pack(fill=tk.BOTH, expand=True, padx=10, pady=10)

        ttk.Label(self.forces_frame, text="External Forces (N):", font=("Arial", 12)).pack(anchor=tk.W)

        # Create labels for X, Y, Z forces
        self.force_labels = {
            "X": ttk.Label(self.forces_frame, text="Force X: 0.00 N", font=("Arial", 10)),
            "Y": ttk.Label(self.forces_frame, text="Force Y: 0.00 N", font=("Arial", 10)),
            "Z": ttk.Label(self.forces_frame, text="Force Z: 0.00 N", font=("Arial", 10)),
        }
        for key in self.force_labels:
            self.force_labels[key].pack(anchor=tk.W)

        # Control panel for sending commands
        self.control_frame = ttk.Frame(self.root)
        self.control_frame.pack(fill=tk.BOTH, expand=True, padx=10, pady=10)

        ttk.Label(self.control_frame, text="Control Panel", font=("Arial", 14)).pack(anchor=tk.W, pady=5)

        # Joint movement inputs
        self.joint_inputs = []
        joint_label = ttk.Label(self.control_frame, text="Joint Angles (degrees):")
        joint_label.pack(anchor=tk.W)
        joint_inputs_frame = ttk.Frame(self.control_frame)
        joint_inputs_frame.pack(anchor=tk.W)
        for i in range(7):
            entry = ttk.Entry(joint_inputs_frame, width=8)
            entry.insert(0, "0")
            entry.grid(row=0, column=i, padx=5, pady=5)
            self.joint_inputs.append(entry)

        self.send_joints_button = ttk.Button(self.control_frame, text="Send Joint Angles", command=self.send_joint_angles)
        self.send_joints_button.pack(anchor=tk.W, pady=5)

        # Cartesian movement inputs
        cartesian_label = ttk.Label(self.control_frame, text="XYZ Coordinates (mm):")
        cartesian_label.pack(anchor=tk.W)
        cartesian_inputs_frame = ttk.Frame(self.control_frame)
        cartesian_inputs_frame.pack(anchor=tk.W)
        self.x_entry = ttk.Entry(cartesian_inputs_frame, width=8)
        self.x_entry.insert(0, "0")
        self.x_entry.grid(row=0, column=0, padx=5, pady=5)
        self.y_entry = ttk.Entry(cartesian_inputs_frame, width=8)
        self.y_entry.insert(0, "0")
        self.y_entry.grid(row=0, column=1, padx=5, pady=5)
        self.z_entry = ttk.Entry(cartesian_inputs_frame, width=8)
        self.z_entry.insert(0, "0")
        self.z_entry.grid(row=0, column=2, padx=5, pady=5)

        self.send_xyz_button = ttk.Button(self.control_frame, text="Send XYZ", command=self.send_xyz)
        self.send_xyz_button.pack(anchor=tk.W, pady=5)

        # Record and replay buttons
        self.record_button = ttk.Button(self.control_frame, text="Start Recording", command=self.toggle_recording)
        self.record_button.pack(anchor=tk.W, pady=5)

        self.replay_button = ttk.Button(self.control_frame, text="Replay Recorded Positions", command=self.replay_positions)
        self.replay_button.pack(anchor=tk.W, pady=5)

        # Socket connection
        self.client_socket = None
        self.running = True
        self.connect_to_robot()

        # Close socket on exit
        self.root.protocol("WM_DELETE_WINDOW", self.disconnect)

    def connect_to_robot(self):
        self.client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        try:
            self.client_socket.connect((ROBOT_HOST, ROBOT_PORT))
            threading.Thread(target=self.listen_to_robot, daemon=True).start()
            print(f"Connected to robot at {ROBOT_HOST}:{ROBOT_PORT}")
        except socket.error as e:
            print(f"Failed to connect to robot: {e}")

    def disconnect(self):
        print("Disconnecting...")
        self.running = False
        if self.client_socket:
            try:
                self.client_socket.shutdown(socket.SHUT_RDWR)
                self.client_socket.close()
            except Exception as e:
                print(f"Error closing socket: {e}")
        self.root.destroy()
        sys.exit(0)

    def send_joint_angles(self):
        try:
            angles = [float(entry.get()) for entry in self.joint_inputs]
            data = {
                "type": "MOVE_JOINTS",
                "angles": angles
            }
            command = json.dumps(data) + "\n"
            self.client_socket.sendall(command.encode())
            print(f"Sent command: {command}")
        except ValueError:
            print("Invalid joint angle input. Please enter valid numbers.")

    def send_xyz(self):
        try:
            x = float(self.x_entry.get())
            y = float(self.y_entry.get())
            z = float(self.z_entry.get())
            data = {
                "type": "MOVE_XYZ",
                "coordinates": [x, y, z]
            }
            command = json.dumps(data) + "\n"
            self.client_socket.sendall(command.encode())
            print(f"Sent command: {command}")
        except ValueError:
            print("Invalid XYZ input. Please enter valid numbers.")

    def update_forces(self, x, y, z):
        self.force_labels["X"].config(text=f"Force X: {x:.2f} N")
        self.force_labels["Y"].config(text=f"Force Y: {y:.2f} N")
        self.force_labels["Z"].config(text=f"Force Z: {z:.2f} N")

    def listen_to_robot(self):
        global x_data, y_data, joint_angles, recorded_positions, recording
        try:
            while self.running:
                data = self.client_socket.recv(1024).decode().strip()
                if not data:
                    print("Connection closed by robot.")
                    break

                if "Joint Angles" in data:
                    angles_str = data.split("Joint Angles:")[1].split("\n")[0].strip().strip("[]").split(",")
                    joint_angles = [float(angle.strip()) for angle in angles_str]
                    self.update_joint_angles()
                    if recording:
                        recorded_positions.append(joint_angles[:])

                if "External Forces" in data:
                    try:
                        forces_start = data.find("F=[") + 3
                        forces_end = data.find("]", forces_start)
                        forces_str = data[forces_start:forces_end]
                        forces_values = forces_str.split(";")

                        x_force = float(forces_values[0].strip())
                        y_force = float(forces_values[1].strip())
                        z_force = float(forces_values[2].strip())

                        x_data.append(len(x_data) / 50)
                        y_data.append(z_force)

                        self.update_forces(x_force, y_force, z_force)

                    except (IndexError, ValueError) as e:
                        print(f"\nError parsing forces: {e}")

        except Exception as e:
            print(f"Error receiving data: {e}")

    def update_joint_angles(self):
        global joint_angles
        for i, angle in enumerate(joint_angles):
            self.angle_labels[i].config(text=f"Joint {i + 1}: {np.rad2deg(angle):.2f}°")

    def toggle_plotting(self):
        if self.plot_button.cget("text") == "Start Plotting":
            self.animation = FuncAnimation(self.fig, self.update_plot, interval=50, cache_frame_data=False)
            self.plot_button.config(text="Stop Plotting")
        else:
            self.animation.event_source.stop()
            self.plot_button.config(text="Start Plotting")

    def update_plot(self, _):
        self.line.set_data(x_data, y_data)
        self.ax.relim()
        self.ax.autoscale_view()
        self.canvas.draw()
        return self.line,

    def toggle_recording(self):
        global recording, recorded_positions
        if self.record_button.cget("text") == "Start Recording":
            recording = True
            recorded_positions = []  # Clear previous recordings
            self.record_button.config(text="Stop Recording")
        else:
            recording = False
            self.record_button.config(text="Start Recording")
            print("Recording stopped. Recorded positions:", recorded_positions)

    def replay_positions(self):
        global recorded_positions
        if not recorded_positions:
            print("No positions recorded.")
            return

        # Threshold for angle difference (in degrees)
        threshold = 5.0

        # Filter out positions that are too close to each other
        filtered_positions = [recorded_positions[0]]  # Always keep the first position
        for current_position in recorded_positions[1:]:
            previous_position = filtered_positions[-1]
            if any(abs(np.rad2deg(c) - np.rad2deg(p)) > threshold for c, p in zip(current_position, previous_position)):
                filtered_positions.append(current_position)

        print(f"Filtered positions: {len(filtered_positions)} out of {len(recorded_positions)}")

        for positions in filtered_positions:
            data = {
                "type": "MOVE_JOINTS",
                "angles": [np.rad2deg(angle) for angle in positions]
            }
            command = json.dumps(data) + "\n"
            self.client_socket.sendall(command.encode())
            # print(f"Replaying position: {command}")

if __name__ == "__main__":
    root = tk.Tk()
    app = RobotUI(root)
    root.mainloop()
