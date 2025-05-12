import socket
import json
from typing import List

class SimpleRobotConnection:
    def __init__(self, host: str = '10.100.0.173', port: int = 30000):
        self.socket = socket.create_connection((host, port))
        print(f"Connected to robot at {host}:{port}")

    def send_command(self, command_type: str, values: List[float]):
        # The only keys needed are "type" and "value"
        cmd = json.dumps({"type": command_type, "value": values}) + "\n"
        self.socket.sendall(cmd.encode())
        print(f"Sent: {cmd.strip()}")

    def close(self):
        self.socket.close()
        print("Disconnected from robot")

def main():
    robot = SimpleRobotConnection()
    try:
        # robot.send_command("MOVE_Q", [0, 30, 0, -60, 0, 90, 0])
        robot.send_command("MOVE_Q", [0, 0, 0, -90, 0, 90, 0])
        #robot.send_command("MOVE_XYZ", [670.0, 0.0, 21.0, -179.0, 0.0, 177.0])
    finally:
        robot.close()

if __name__ == "__main__":
    main()
