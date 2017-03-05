/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package P2PClient;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Owen
 */
public class Client {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
                int directoryPort = 49000; //temp for testing
        int tcpPort = 2009;
        
        InetAddress ip = null;
        try {
            ip = InetAddress.getLocalHost(); // still temp for testing
        } catch (UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("couldnt get host ip");
        }
        String message = "where in the world is carmen sandiego";
        ClientAction client = new ClientAction(ip,tcpPort,message);
        client.start();
        
    }
    
}
