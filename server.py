from flask import Flask
import socket

 # creating the flask application named app
app = Flask(__name__) # __name__ refers to the current module, it will be main and flask will use this line to detect the root path of the app

@app.route('/') # flask decorator that associates the function index() with the url '/'. When a client accesses the root URL of the app flask will invoke the index() function
def index(): # function to handle requests
    return 'Socket server is running!' # string response to client when they access root url

@app.route('/start_server')
def start_server():
    # start socket server
    host = '127.0.0.1' # define the server's ip address to listen on
    port = 12345
    
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM) # create tcp/ip socket using ipv4
    server_socket.bind((host, port)) # bind socket to specified host and port
    server_socket.listen(1) # listen for incoming connections; 1 is maximum queued connections

    print(f"Server listening on {host}:{port}...")

    while True: # infinite loop to continuously accept connections
        client_socket, addr = server_socket.accept() 
        print(f"Connection established with {addr}")
        
        # handle client's requests here
        # can retrieve data here
        data = client_socket.recv(1024).decode('utf-8')
        print(f"Received from client: {data}")
        
        # send a response back to the client
        client_socket.send("Hello from server!".encode('utf-8'))
        
        client_socket.close()  # Cclose the connection with the client

    return 'Socket server started!'

if __name__ == '__main__':
    app.run(debug=True)
