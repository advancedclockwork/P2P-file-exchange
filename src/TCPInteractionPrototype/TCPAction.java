/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TCPInteractionPrototype;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Owen
 */
public abstract class TCPAction extends Thread{
    protected InetAddress ip;
    protected int port;
    protected String message = null;
    protected int messageSent = 0;
    protected String messageDivider = "{*~!";
    
    protected void sendMessage(OutputStream outToServer, String message){
        String addedMessage = Integer.toString(message.length())+ messageDivider + message;
        byte[] toWrite = addedMessage.getBytes();
        try {
            outToServer.write(toWrite);
        } catch (IOException ex) {
            Logger.getLogger(ClientToServerAction.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("error writing to output stream");
        }
    }
    
    protected OutputStream startOutputStream(Socket socket){
        OutputStream stream = null;
        try {
            stream = socket.getOutputStream();
        } catch (IOException ex) {
            Logger.getLogger(ServerAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stream;
    }
    
    protected InputStream startInputStream(Socket socket){
        InputStream stream = null;
        try {
            stream = new DataInputStream(socket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(ServerAction.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("problem establishing input stream");
        }
        return stream;
    }
    
    protected ServerSocket openServSocket(int port){
        ServerSocket welcomeSocket = null;
        try {   
            welcomeSocket = new ServerSocket(port);
        } catch (IOException ex) {
            Logger.getLogger(ServerAction.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("had trouble opening the server socket");
        }
        return welcomeSocket;
    }
    
    protected Socket openCommSocket(ServerSocket socket){
        Socket serverSocket = null;
        try {
            serverSocket = socket.accept();
        } catch (UnknownHostException ex) {
            Logger.getLogger(ServerAction.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("server socket had a problem establishing hostname");
        } catch (IOException ex) {
            Logger.getLogger(ServerAction.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("server socket had an IO exception");
        }
        return serverSocket;
    }
    
    protected Socket openOutSocket(int port, InetAddress ip){
        Socket outSocket = null;
        try {
            outSocket = new Socket(ip,port);
        } catch (IOException ex) {
            Logger.getLogger(ClientToServerAction.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("problem opening outgoing socket");
        }
        return outSocket;
    }
    
    protected byte[] getIncomingData(InputStream inFromClient){
        int size = getIncomingSize(inFromClient);
        byte[] data = new byte[size];
        try {
            inFromClient.read(data);
        } catch (IOException ex) {
            Logger.getLogger(ServerAction.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("had trouble printing input to buffer");
        }
        return data;
    }
    
    protected int getIncomingSize(InputStream inFromClient){
        String dataRecieved = "";
        int size = 0;
        try {
            while(!dataRecieved.contains(messageDivider)){
                dataRecieved += (char)inFromClient.read();
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        dataRecieved = dataRecieved.replace(messageDivider,"");
        size = Integer.parseInt(dataRecieved);
        return size;
    }
    
    public void changeMessage(String message){
        this.message = message;
    }
}
