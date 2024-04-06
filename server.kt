import socket
import threading
import json
from datetime import datetime

# Server settings
HOST = '0.0.0.0'
PORT = 12345

def handle_client(conn, addr):
    print(f"Connected by {addr}")
    with conn:
        while True:
            data = conn.recv(1024)
            if not data:
                break
            # Convert bytes data to dictionary
            cell_data = json.loads(data.decode())
            print(f"Received data from {addr}: {cell_data}")
            # Save to database or file here (not implemented)
            conn.sendall(b"Data received")
    print(f"Connection with {addr} closed")

def start_server():
    server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server.bind((HOST, PORT))
    server.listen()
    print(f"Server listening on {HOST}:{PORT}")

    try:
        while True:
            conn, addr = server.accept()
            thread = threading.Thread(target=handle_client, args=(conn, addr))
            thread.start()
            print(f"Active connections: {threading.activeCount() - 1}")
    except KeyboardInterrupt:
        print("Server is shutting down...")
        server.close()

if __name__ == '__main__':
    start_server()
