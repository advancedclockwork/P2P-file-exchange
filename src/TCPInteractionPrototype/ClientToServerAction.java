/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TCPInteractionPrototype;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * A TCP thread to facilitate communication to and from the client and the server
 * @author Owen
 */
public class ClientToServerAction extends TCPAction{

    /**
     * Constructor to open a new ClientToServerAction object. may want to make this a singleton
     * @param ip of the server
     * @param port to use when opening input and output streams
     */
    public ClientToServerAction(InetAddress ip, int port) {
        this.ip = ip;
        this.port = port;
    }
    
    /**
     * Overridden run from Thread object. opens input and output streams and waits for outgoing or incoming streams to not be null, then sends outgoing or passes incoming to their respective places
     */
    public void run(){
        Socket clientSocket = openOutSocket(port,ip);
        InputStream inFromServer = startInputStream(clientSocket);
        OutputStream outToServer = startOutputStream(clientSocket);
        while(true){
            if(outToServer != null && message != null){
                System.out.println(message.length());
                sendMessage(outToServer, message);
                message = null;
            }
            if(inFromServer != null){
                byte[] data = getIncomingData(inFromServer);
                String message = new String(data);
                System.out.println(message);
            }
        }
    }
}
