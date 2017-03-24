/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TCPInteractionPrototype;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Owen
 */
public class ServerThread extends TCPAction {
    private Socket serverSocket;
    
    public ServerThread(Socket serverSocket, String message){
        this.serverSocket = serverSocket;
        this.message = message;
    }
    
    public void run(){
    OutputStream outToClient = startOutputStream(serverSocket);
    InputStream inFromClient = startInputStream(serverSocket);
    long time = System.currentTimeMillis();
        try {
            outToClient.write(("HTTP/1.1 200 OK\n\nWorkerRunnable: " + message + " - " + time + "").getBytes());
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        try{
        outToClient.close();
        inFromClient.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    /*
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
    */
}
