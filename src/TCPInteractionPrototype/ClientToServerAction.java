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
 *
 * @author Owen
 */
public class ClientToServerAction extends TCPAction{
    public ClientToServerAction(InetAddress ip, int port) {
        this.ip = ip;
        this.port = port;
    }
    
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
