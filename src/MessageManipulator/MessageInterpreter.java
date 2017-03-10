/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MessageManipulator;


import DatabaseManager.Directory;
import TCPInteractionPrototype.ServerAction;
import java.net.InetAddress;
import static java.net.InetAddress.getByName;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Owen
 */
public class MessageInterpreter extends MessageData{
    private final Directory directory;
    public MessageInterpreter(Directory directory){
        this.directory = directory;
    }
    public void execute(String message, ServerAction server){
        String result = "";
        String[] commandSplit = message.split(commandDivider);
        int command = Integer.parseInt(commandSplit[0]);
        switch (command){
            case 1: 
                addUserToDirectory(commandSplit[1]);
                result = directory.query();
                System.out.println(result);
                break;
            case 2:
                result = directory.query();
                server.changeMessage(result);
                break;
            case 3:
                //result[] = di
                //break;
            default:
                break;
        }
                
    } 
    
    private void addUserToDirectory(String message)
    {
        //System.out.println("on its way to directory");
        String[] ipSplit = message.split(ipDivider);
        String[] ipString = ipSplit[0].split("/");
        InetAddress ip = null;
        try {
            ip = getByName(ipString[1]);
        } catch (UnknownHostException ex) {
            Logger.getLogger(MessageInterpreter.class.getName()).log(Level.SEVERE, null, ex);
        }
        String[] entries = ipSplit[1].split(messageDivider);
        for(int i = 1; i<entries.length; i++)
        {
            String[] entry = entries[i].split(entryDivider);
            String name = entry[0];
            int size = Integer.parseInt(entry[1]);
            directory.addToDirectory(name, size, ip);
        }
    }
}
