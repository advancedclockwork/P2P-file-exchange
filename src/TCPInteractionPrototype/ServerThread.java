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
import java.net.Socket;

/**
 *
 * @author Owen
 */
public class ServerThread extends TCPAction {
    private final Socket serverSocket;
    private int reply = 0;
    private Directory directory;
    public ServerThread(Socket serverSocket, Directory directory){
        this.serverSocket = serverSocket;
        this.directory = directory;
    }
    
    public void run(){
        while(isRunning == true){
            OutputStream outToClient = startOutputStream(serverSocket);
            InputStream inFromClient = startInputStream(serverSocket);
            MessageInterpreter interpreter = new MessageInterpreter(directory);
            if(inFromClient != null){
                byte[] data = getIncomingData(inFromClient);
                String dataToProcess = new String(data);
                interpreter.execute(dataToProcess, this, serverSocket.getInetAddress());
                System.out.println("Message from client: " + dataToProcess);
            }
            if(message != null){
                sendMessage(outToClient,message);
                System.out.println("query Processed: " + message);
                try{
                    outToClient.close();
                    inFromClient.close();
                    serverSocket.close();
                    System.out.println("thread closed");
                    isRunning = false;
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
