/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MessageManipulator;
import java.net.InetAddress;

/**
 * composes a message that can be interpreted by the server
 * @author Owen
 */
public class MessageWriter implements MessageData{
    
    /**
     * adds command and the clients ip to the message to be sent, then starts a fileReader object and adds that to the message as well, and returns the new raw message
     * @param command is the int of the command you wish to send
     * @param ip is the clients ip
     * @param path is the file path which the client wishes to share
     * @return the new raw message for the server
     */
    public String composeMessage(int command, InetAddress ip, String path)
    {
        String response = command + commandDivider + ip.toString() + ipDivider;
        FolderReader reader = new FolderReader(path);
        response += reader.getContents();
        return response;
    }
}
