/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TCPInteractionPrototype;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * A TCP thread representing the server end of the client server relationship
 * @author Owen
 */
public class ServerAction extends TCPAction {
    
    /**
     * Constructor for creating new ServerAction object. may have to make this a factory to facilitate multiple users at one time
     * @param ip is the servers ip, but may remove because im not sure its ever used
     * @param port is the port number to be used for the ServerSocket initialization
     */
    public ServerAction(InetAddress ip, int port){    
        this.port = port;
        this.ip = ip;
    }
    
    /**
     * Starts the ServerSocket, in turn starting the Socket to communicate with the client. when a message is recieved, a reply is sent, and the message is sent to the client. if a response is requested, it is sent when ready
     */
    @Override
    public void run(){
        int reply = 0;
        ServerSocket welcomeSocket = openServSocket(port);
        Socket serverSocket = openCommSocket(welcomeSocket);
        OutputStream outToClient = startOutputStream(serverSocket);
        InputStream inFromClient = startInputStream(serverSocket);
        while(true){
            if(inFromClient != null){
                byte[] data = getIncomingData(inFromClient);
                String dataToProcess = new String(data);
                System.out.println("Message:"+dataToProcess);
                
                reply = 1;
            }
            if(outToClient != null && reply == 1){
                sendMessage(outToClient,"message recieved");
                reply = 0;
            }
            if(message != null){
                sendMessage(outToClient,message);
            }
        }
    }
}
