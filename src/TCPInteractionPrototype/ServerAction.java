/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TCPInteractionPrototype;

import DatabaseManager.Directory;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * A TCP thread representing the server end of the client server relationship
 * @author Owen
 */
public class ServerAction extends TCPAction {
    private ServerSocket welcomeSocket = null;
    private final Directory directory;
    /**
     * Constructor for creating new ServerAction object. may have to make this a factory to facilitate multiple users at one time
     * @param port is the port number to be used for the ServerSocket initialization
     */
    public ServerAction(int port, Directory directory){    
        this.port = port;
        this.directory = directory;
    }
    
    /**
     * Starts the ServerSocket, in turn starting the Socket to communicate with the client. when a message is recieved, a reply is sent, and the message is sent to the client. if a response is requested, it is sent when ready
     */
    @Override
    public void run(){
        welcomeSocket = openServSocket(port);
        while(isRunning){
            Socket serverSocket = openCommSocket(welcomeSocket);
            new Thread(new ServerThread(serverSocket, directory)).start();
        }
    }
    
    public synchronized void stop(){
        this.isRunning = false;
        try {
            this.welcomeSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }
}
