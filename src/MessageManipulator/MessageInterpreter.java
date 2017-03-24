/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MessageManipulator;


import DatabaseManager.Directory;
import TCPInteractionPrototype.ServerThread;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Class that reads incoming messages and translates them into entry objects to be stored in a directory object
 * @author Owen
 */
public class MessageInterpreter implements MessageData{
    private final Directory directory;

    /**
     * MessageInterpriter constructor with a directory object passed to it from the main class
     * @param directory is the directory object passed from the main class to store entries
     */
    public MessageInterpreter(Directory directory){
        this.directory = directory;
    }

    /**
     * recieves the raw message, strips the command off of it and uses the command to determine what to do with the rest of the message.
     * options: 
     * 1. inform and update(update the current directory of files to share with files provided by client in message
     * 2. request file info(client wishes to have a file transferred to it and needs a list of ips of other clients that have the file
     * 3. remove user(erase all entries in directory sharing the clients ip
     * @param message is the raw message from client
     * @param server is the Server action thread the message was recieved from so a return message can be sent
     */
    public void execute(String message, ServerThread server, InetAddress ip){
        String result = "";
        String[] commandSplit = message.split(commandDivider);
        MessageWriter writer = new MessageWriter();
        System.out.println("interpriter recieved data. command split: " + commandSplit[1]);
        //String command = Integer.parseInt(commandSplit[0]);
        switch (commandSplit[0]){
            case "1": 
                addToDirectory(commandSplit[1], ip);
                result = directory.query();
                //writer.composeServerResponse(result);
                server.changeMessage(result);
                System.out.println(result);
                break;
            case "2":
                result = directory.query();
                server.changeMessage(result);
                break;
            case "3":
                //result = directory.query(commandSplit[1],)
                //break;
            default:
                break;
        }
                
    } 
     /**
      * adds entire user to the database by taking the message, splitting the ip off of it, splitting it into entries, and finally splitting those entries into filename and size. it then inserts each entry into the directory
      * @param message is the raw message minus the stripped command
      */
    private void addToDirectory(String message, InetAddress ip)
    {
        String[] entries = message.split(entryDivider);
        for(int i = 0; i < entries.length; i++)
        {
            String[] entry = entries[i].split(messageDivider);
            String name = entry[0];
            int size = Integer.parseInt(entry[1]);
            System.out.println("entry " + i + " name: " + name + " size: " + size + " ip: " + ip.toString());
            directory.addToDirectory(name, size, ip);
        }
    }
    
    /**
     * enters entries from message from server into a local database
     * @param message message from server to be interpreted and entered into local directory
     */
    public void addToLocalDirectory(String message){
        String[] entries = message.split(entryDivider);
        for(int i = 0; i < entries.length; i++)
        {
            String[] entry = entries[i].split(messageDivider);
            System.out.println("entry:" + entry[0]);
            String name = entry[0];
            int size = Integer.parseInt(entry[1]);
            InetAddress ip = null;
            try {
                ip = InetAddress.getByName(entry[2].replaceAll("/", ""));
            } catch (UnknownHostException ex) {
                Logger.getLogger(MessageInterpreter.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("IP address is invalid");
            }
            System.out.println("entry " + i + " name: " + name + " size: " + size + " ip: " + ip.toString());
            directory.addToDirectory(name, size, ip);
        }
    }
}
