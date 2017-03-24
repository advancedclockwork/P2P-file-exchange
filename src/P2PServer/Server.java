/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package P2PServer;

import DatabaseManager.Directory;
import MessageManipulator.MessageInterpreter;
import TCPInteractionPrototype.ServerAction;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * main class for the server side. most everything is for testing and will probably be replaced with an applet
 * @author Owen
 */
public class Server {
    public static void main(String[] args){
        int directoryPort = 9000; //temp for testing
        printIP(); // still temp for testing
        ServerAction server = new ServerAction(directoryPort);
        new Thread(server).start();
        
        try {
            Thread.sleep(20 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Stopping Server");
        server.stop();
        //server.run();
        //Directory directory = new Directory();
        //MessageInterpreter interpreter = new MessageInterpreter(directory);
        //while(server != null){
        //    if(server.getMessage() != null)
        //        interpreter.execute(server.getMessage(), server);
        //}
    }
    /**
     * prints the ip of the machine the server is running on for easy input when testing
     * @return 
     */
    private static InetAddress printIP(){
        InetAddress ip = null;
        try {
            ip = InetAddress.getLocalHost();
            System.out.println(ip.toString());
        } catch (UnknownHostException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("there was a problem printing your ip");
        }
        return ip;
    }
}
