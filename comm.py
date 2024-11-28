import tkinter as tk
from tkinter import ttk
from matplotlib.backends.backend_tkagg import FigureCanvasTkAgg
from matplotlib.figure import Figure
from matplotlib.animation import FuncAnimation
import socket
import threading
import sys
from numpy import rad2deg, deg2rad
import json

# Global variables for real-time plotting and joint angles
x_data, y_data = [], []
joint_angles = [0] * 7  # Assuming 7 joints for the KUKA iiwa

# Robot connection details
ROBOT_HOST = '172.31.1.147'
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
        for i in range(7):  # Assuming 7 joints for the KUKA iiwa
            label = ttk.Label(self.angles_frame, text=f"Joint {i + 1}: 0.00°", font=("Arial", 10))
            label.pack(anchor=tk.W)
            self.angle_labels.append(label)

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

        # Socket connection
        self.client_socket = None
        self.running = True
        self.connect_to_robot()

        # Close socket on exit
        self.root.protocol("WM_DELETE_WINDOW", self.disconnect)

    def connect_to_robot(self):
        """Establish socket connection with the robot."""
        self.client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        try:
            self.client_socket.connect((ROBOT_HOST, ROBOT_PORT))
            threading.Thread(target=self.listen_to_robot, daemon=True).start()
            print(f"Connected to robot at {ROBOT_HOST}:{ROBOT_PORT}")
        except socket.error as e:
            print(f"Failed to connect to robot: {e}")

    def disconnect(self):
        """Properly disconnect from the robot."""
        print("Disconnecting...")
        self.running = False  # Stop the listener thread
        if self.client_socket:
            try:
                self.client_socket.shutdown(socket.SHUT_RDWR)  # Gracefully close the connection
                self.client_socket.close()
            except Exception as e:
                print(f"Error closing socket: {e}")
        self.root.destroy()
        sys.exit(0)

    def send_joint_angles(self):
        """Send joint angles to the robot as a JSON array."""
        try:
            angles = [float(entry.get()) for entry in self.joint_inputs]
            data = {
                "type": "MOVE_JOINTS",
                "angles": angles
            }
            command = json.dumps(data) + "\n"  # Serialize to JSON and add newline
            self.client_socket.sendall(command.encode())
            print(f"Sent command: {command}")
        except ValueError:
            print("Invalid joint angle input. Please enter valid numbers.")


    def send_xyz(self):
        """Send XYZ coordinates to the robot as a JSON array."""
        try:
            x = float(self.x_entry.get())
            y = float(self.y_entry.get())
            z = float(self.z_entry.get())
            data = {
                "type": "MOVE_XYZ",
                "coordinates": [x, y, z]
            }
            command = json.dumps(data) + "\n"  # Serialize to JSON and add newline
            self.client_socket.sendall(command.encode())
            print(f"Sent command: {command}")
        except ValueError:
            print("Invalid XYZ input. Please enter valid numbers.")


    def listen_to_robot(self):
        """Receive data from the robot in a separate thread."""
        global x_data, y_data, joint_angles
        try:
            while self.running:
                data = self.client_socket.recv(1024).decode().strip()
                if not data:
                    print("Connection closed by robot.")
                    break
                print(f"Raw data received: {data}")  # Uncommented

                # Parse joint angles
                if "Joint Angles" in data:
                    angles_str = data.split("Joint Angles:")[1].split("\n")[0].strip().strip("[]").split(",")
                    joint_angles = [float(angle.strip()) for angle in angles_str]
                    self.update_joint_angles()

                # Parse Z force for plotting
                if "External Forces" in data:
                    try:
                        forces_start = data.find("F=[") + 3  # Start after "F=["
                        forces_end = data.find("]", forces_start)  # Find closing bracket
                        forces_str = data[forces_start:forces_end]  # Extract "X, Y, Z"
                        forces_values = forces_str.split(",")  # Split into components

                        x_force = float(forces_values[0].strip())
                        y_force = float(forces_values[1].strip())
                        z_force = float(forces_values[2].strip())

                        x_data.append(len(x_data)/50)
                        y_data.append(z_force)

                        print(f"Parsed Forces - X: {x_force}, Y: {y_force}, Z: {z_force}")  # New print statement

                    except (IndexError, ValueError) as e:
                        print(f"Error parsing forces: {e}")

        except Exception as e:
            print(f"Error receiving data: {e}")

    def update_joint_angles(self):
        """Update the displayed joint angles and print them to the console."""
        global joint_angles
        for i, angle in enumerate(joint_angles):
            self.angle_labels[i].config(text=f"Joint {i + 1}: {rad2deg(angle):.2f}°")

        # print("Current Joint Angles (degrees):", ", ".join(f"{rad2deg(angle):.2f}" for angle in joint_angles))

    def toggle_plotting(self):
        """Start or stop real-time plotting."""
        if self.plot_button.cget("text") == "Start Plotting":
            self.animation = FuncAnimation(self.fig, self.update_plot, interval=50, cache_frame_data=False)
            self.plot_button.config(text="Stop Plotting")
        else:
            self.animation.event_source.stop()
            self.plot_button.config(text="Start Plotting")

    def update_plot(self, _):
        """Update the real-time plot."""
        self.line.set_data(x_data, y_data)
        self.ax.relim()
        self.ax.autoscale_view()
        self.canvas.draw()
        return self.line,


if __name__ == "__main__":
    root = tk.Tk()
    app = RobotUI(root)
    root.mainloop()
