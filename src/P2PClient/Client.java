/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package P2PClient;

import DatabaseManager.Directory;
import MessageManipulator.MessageWriter;
import TCPInteractionPrototype.ClientToClientAction;
import TCPInteractionPrototype.ClientToServerAction;
import TCPInteractionPrototype.ServerAction;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The main class for the client. mostly for testing at the moment. will probably replace with an applet or something at some point
 * @author Owen
 */
public class Client {
    private final static String PATH = "H:\\documents\\My Documents";
    private final static String FILENAME = "test.txt";
    /**
     * main class for the client. most hard coded values are for testing and stuff atm so its pretty automated
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int directoryPort = 9000; //temp for testing
        int servPort = 2009;
        int command = 1;
        Directory localDirectory = new Directory(PATH);
        
        InetAddress ip = null;
        try {
            ip = InetAddress.getLocalHost(); // still temp for testing
        } catch (UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("couldnt get host ip");
        }
        ClientToServerAction client = new ClientToServerAction(ip,directoryPort, localDirectory);
        ServerAction server = new ServerAction(servPort, localDirectory);
        
        System.out.println("path: " + PATH);
        new Thread(client).start();
        System.out.println("client started");
        new Thread(server).start();
        System.out.println("server started");
        MessageWriter writer = new MessageWriter();
        String message = writer.composeInformAndUpdate(ip, PATH);
        System.out.println("message to send: " + message);
        client.changeMessage(message);
        localDirectory.printDirectory();
        ClientToClientAction request = new ClientToClientAction(ip, 2007, PATH, FILENAME);
        String message2 = writer.composeFileRequest(FILENAME);
        request.changeMessage(message2);
        new Thread(request).start();
    }
}
