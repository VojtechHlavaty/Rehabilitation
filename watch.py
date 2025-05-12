import socket
import json
import numpy as np
import time

# Configuration
HOST = '10.100.0.173'  # Replace with the robot IP
PORT = 30001
SAVE_FILE = 'robot_data_log.npz'

def main():
    data_list = []

    try:
        with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
            print(f"Connecting to robot at {HOST}:{PORT}...")
            s.connect((HOST, PORT))
            print("Connected. Listening for data...")

            while True:
                data = s.recv(4096)
                if not data:
                    break

                try:
                    message = data.decode('utf-8').strip()
                    print(message)
                    print(time.time())
                    for line in message.splitlines():
                        json_data = json.loads(line)


                        timestamp = json_data.get('timestamp', 0)
                        joints = json_data.get('joint_positions', [0]*7)
                        coords = json_data.get('coordinates', [0, 0, 0])
                        orient = json_data.get('orientation', [0, 0, 0])
                        force_z = json_data.get('force_z', 0)

                        # Concatenate all relevant data into a single row
                        row = [timestamp] + joints + coords + orient + [force_z]
                        data_list.append(row)

                        print(f"Logged: {row}")

                except json.JSONDecodeError:
                    print("Received invalid JSON.")

    except KeyboardInterrupt:
        print("Interrupted by user. Saving data...")
    except Exception as e:
        print(f"Error: {e}")

    # Save as NumPy file
    if data_list:
        np_data = np.array(data_list)
        np.savez(SAVE_FILE, data=np_data)
        print(f"Data saved to {SAVE_FILE} with shape {np_data.shape}")
    else:
        print("No data to save.")

if __name__ == '__main__':
    main()
