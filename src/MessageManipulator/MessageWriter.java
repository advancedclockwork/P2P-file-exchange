/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MessageManipulator;
import java.net.InetAddress;

/**
 *
 * @author Owen
 */
public class MessageWriter extends MessageData{
    
    public String composeMessage(int command, InetAddress ip, String path)
    {
        String response = command + commandDivider + ip.toString() + ipDivider;
        FolderReader reader = new FolderReader(path);
        response += reader.getContents();
        return response;
    }
}
