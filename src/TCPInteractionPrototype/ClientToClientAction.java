/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TCPInteractionPrototype;

import FileOperations.FileWriter;
import java.io.ByteArrayOutputStream;
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
public class ClientToClientAction extends TCPAction{
    private final String path;
    private final String fileName;
    /**
     * Constructor to open a new ClientToServerAction object. may want to make this a singleton
     * @param ip of the server
     * @param port to use when opening input and output streams
     * @param path
     * @param fileName
     */
    public ClientToClientAction(InetAddress ip, int port, String path, String fileName){
        this.ip = ip;
        this.port = port;
        this.path = path;
        this.fileName = fileName;
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
                byte[] data = null;
                try {
                    data = readFileIn(inFromServer);
                } catch (IOException ex) {
                    Logger.getLogger(ClientToClientAction.class.getName()).log(Level.SEVERE, null, ex);
                }
                FileWriter writer = new FileWriter(fileName, path, data);
                writer.writeFile();
                try {
                    inFromServer.close();
                    outToServer.close();
                    clientSocket.close();
                } catch (IOException ex) {
                    Logger.getLogger(ClientToServerAction.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("exchange with client completed");
                isRunning = false;
            }
        }
    }
    private byte[] readFileIn(InputStream inFromServer) throws IOException{
        ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
        try {
            byte[] aByte = new byte[1];
            int bytesRead = inFromServer.read(aByte, 0, aByte.length);
            do{
                dataStream.write(aByte);
                bytesRead = inFromServer.read(aByte);
            } while (bytesRead != -1);
            byte[] data = dataStream.toByteArray();
            dataStream.flush();
            dataStream.close();
            return data;
        } catch (IOException ex) {
            Logger.getLogger(ClientToClientAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
