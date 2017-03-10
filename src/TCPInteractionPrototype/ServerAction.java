/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TCPInteractionPrototype;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Owen
 */
public class ServerAction extends TCPAction {
    
    public ServerAction(InetAddress ip, int port){    
        this.port = port;
        this.ip = ip;
    }
    
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
