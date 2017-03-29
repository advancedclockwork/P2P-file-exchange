/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TCPInteractionPrototype;

import DatabaseManager.Directory;
import MessageManipulator.MessageInterpreter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A TCP thread to facilitate communication to and from the client and the server
 * @author Owen
 */
public class ClientToServerAction extends TCPAction{
    private final Directory localDirectory;
    /**
     * Constructor to open a new ClientToServerAction object. may want to make this a singleton
     * @param ip of the server
     * @param port to use when opening input and output streams
     * @param localDirectory local directory where entries are stored
     */
    public ClientToServerAction(InetAddress ip, int port, Directory localDirectory) {
        this.ip = ip;
        this.port = port;
        this.localDirectory = localDirectory;
    }
    
    /**
     * Overridden run from Thread object. opens input and output streams and waits for outgoing or incoming streams to not be null, then sends outgoing or passes incoming to their respective places
     */
    public void run(){
        Socket clientSocket = openOutSocket(port,ip);
        InputStream inFromServer = startInputStream(clientSocket);
        OutputStream outToServer = startOutputStream(clientSocket);
        while(isRunning){
            if(outToServer != null && message != null){
                sendMessage(outToServer, message);
                message = null;
            }
            if(inFromServer != null){
                byte[] data = getIncomingData(inFromServer);
                String incomingMessage = new String(data);
                MessageInterpreter interpreter = new MessageInterpreter(localDirectory);
                interpreter.addToLocalDirectory(incomingMessage);
                try {
                    inFromServer.close();
                    outToServer.close();
                    clientSocket.close();
                } catch (IOException ex) {
                    Logger.getLogger(ClientToServerAction.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("exchange with server completed");
                isRunning = false;
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}
