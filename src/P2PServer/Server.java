/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package P2PServer;

import TCPInteractionPrototype.ServerAction;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Owen
 */
public class Server {
    public static void main(String[] args){
        int directoryPort = 49000; //temp for testing
        int tcpPort = 2009;
        InetAddress ip = printIP(); // still temp for testing
        ServerAction server = new ServerAction(ip,tcpPort);
        server.start();
    }
    
    private static InetAddress printIP(){
        InetAddress ip = null;
        try {
            ip = InetAddress.getLocalHost();
            System.out.println(ip);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("there was a problem printing your ip");
        }
        return ip;
    }
}
