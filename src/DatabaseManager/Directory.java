/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatabaseManager;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *  directory holding all entrys
 * @author owen
 */
public class Directory {
    ArrayList<Entry> entries = new ArrayList();
    
    
    public Directory(){ 
    }
    
    
    public void addToDirectory(String fileName, int fileSize, InetAddress host){
        Entry entry = new Entry(fileName, host, fileSize);
        entries.add(entry);
    }
    
    /**
     * gets a ip and removes all entries with that ip
     * @param host 
     */
    public void removeHost(InetAddress host)
    {
        for(int i=0; i<entries.size(); i++)
        {
            if(Arrays.equals(entries.get(i).getIp().getAddress(),host.getAddress()) == true)
            {
                entries.remove(i);
                i = i-1;
            }
        }
    }
    
    /**
     * search for an entry with the same filename and size in the directory
     * @param fileName
     * @param size
     * @return 
     */
    public String query(String fileName, int size)
    {
        String response;
        ArrayList<Entry> queries = new ArrayList();
        for(int i=0; i<entries.size(); i++)
        {
            if(fileName.equals(entries.get(i).getName()) && size == entries.get(i).getSize())
            {
                queries.add(entries.get(i));
            }
        }
        if(queries.size()>0)
        {
            response = getAllData(queries);
        }
        else
        {
            response = "404 NOTFOUND";
        }
        return response;
    }
    
    /**
     * return all entries in directory
     * @return 
     */
    public String query()
    {
        String response = getAllData(entries);
        return response;
    }
    
    /**
     * takes data and composes it into a message to be sent by receiver
     * @param entries
     * @return 
     */
    public String getAllData(ArrayList<Entry> entries)
    {
        String response = "\r\n";
        for(int i =0; i<entries.size(); i++)
        {
            Entry current = entries.get(i);
            response += current.getName() + " " + current.getSize() + " " + current.getIp();
            response += "\r\n";
        }
        return response;
    }
}
