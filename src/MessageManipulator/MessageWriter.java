/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MessageManipulator;

import DatabaseManager.Entry;
import java.net.InetAddress;
import java.util.ArrayList;

/**
 *
 * @author Owen
 */
public class MessageWriter extends MessagerData{
    
    public String composeMessage(ArrayList<Entry> entries, int command, InetAddress ip)
    {
        String response = command + commandDivider + ip.toString() + ipDivider;
        for(int i =0; i<entries.size(); i++)
        {
            Entry current = entries.get(i);
            response += current.getName() + entryDivider + current.getSize() + messageDivider;
        }
        return response;
    }
}
