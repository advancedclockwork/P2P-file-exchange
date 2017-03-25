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
 * Methods for the ClientAction and ServerAction threads. some dont belong in the prototype, but will sort hat out later
 * @author Owen
 */
public abstract class TCPAction implements Runnable{

    /**
     * ip of the client or host
     */
    protected InetAddress ip;

    /**
     * port selected to create communication Socket
     */
    protected int port;

    /**
     * message to be sent if not null
     */
    protected String message = null;

    /**
     * response message for Server to be sent if 1
     */
    protected int messageSent = 0;

    /**
     * used to separate the amount of data the receiver should expect from the message itself
     */
    protected String messageDivider = "{*~!";
    /**
     * status of connection
     */
    protected boolean isRunning = true;
    
    byte[] fileData = null;
    
    
    /**
     * adds the size of the message to the message, converts it to a byteStream and sends it while handling errors
     * @param outToServer is the output stream on which the data will be sent
     * @param message is the message to be sent
     */
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
    
    protected void sendFile(OutputStream outToClient, byte[] fileData){
        try {
            outToClient.write(fileData, 0, fileData.length);
        } catch (IOException ex) {
            Logger.getLogger(TCPAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Starts the output stream while handling errors
     * @param socket is the socket to be used for the stream
     * @return the new output stream
     */
    protected OutputStream startOutputStream(Socket socket){
        OutputStream stream = null;
        try {
            stream = socket.getOutputStream();
        } catch (IOException ex) {
            Logger.getLogger(ServerAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stream;
    }
    
    /**
     * opens a new input stream while handling errors
     * @param socket is the socket on which to start the stream
     * @return the new input stream
     */
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
    
    /**
     * Starts the serverSocket while handling errors
     * @param port is the port you wish to use to open the socket
     * @return the new ServerSocket
     */
    protected ServerSocket openServSocket(int port){
        ServerSocket welcomeSocket = null;
        try {   
            welcomeSocket = new ServerSocket(port);
        } catch (IOException ex) {
            Logger.getLogger(ServerAction.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("had trouble opening the server socket on port " + port);
        }
        return welcomeSocket;
    }
    
    /**
     * opens a new Socket using a ServerSocket while handling errors
     * @param socket is the server socket used to create our socket
     * @return the newly made socket
     */
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
    
    /**
     * opens a Socket using a port and an ip while handling errors
     * @param port is the port the socket will use
     * @param ip is the ip of the server
     * @return the newly created socket
     */
    protected Socket openOutSocket(int port, InetAddress ip){
        Socket outSocket = null;
        try {
            outSocket = new Socket(ip,port);
        } catch (IOException ex) {
            Logger.getLogger(ClientToServerAction.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("problem connecting to server socket");
        }
        return outSocket;
    }
    
    /**
     * gets a bitstream from the inputStream and returns it while handling errors
     * @param inFromClient is the input stream that is no longer null
     * @return the stream of data received from the sender
     */
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
    
    /**
     * Seperates the size of the message from the start of the message so that the entire message can be anticipated and accomodated
     * @param inFromClient is the input stream
     * @return an integer the represents the size of the message to come in bytes
     */
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
    
    /**
     * allows for other classes with access to an Action object to change the message from null so that the Action class knows to send it
     * @param message which is the message to be sent
     */
    public void changeMessage(String message){
        this.message = message;
    }
    
    public void changeFile(byte[] fileData){
        this.fileData = fileData;
    }
    
    /**
     * allows for other classes to access what the recieved message is
     * @return the current message
     */
    public String getMessage(){
        return message;
    }
}
