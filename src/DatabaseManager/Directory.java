/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatabaseManager;

import MessageManipulator.MessageWriter;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *  directory holding all entrys
 *  i should make this a singleton later and make it a proper database, but this works for now
 * @author owen
 */
public class Directory {
    private final ArrayList<Entry> entries = new ArrayList();
    private String path;
    
    /**
     * constructor for Server
     */
    public Directory(){ 
    }
    
    /**
     * constructor for local server
     * @param path 
     */
    public Directory(String path){
        this.path = path;
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
        MessageWriter writer = new MessageWriter();
        String response = "";
        for(int i =0; i<entries.size(); i++)
        {
            Entry current = entries.get(i);
            response += writer.composeInformAndUpdateResponse(current.getName(), current.getSize(), current.getIp());
        }
        return response;
    }
    
    public void printDirectory(){
        for(int i = 0; i<entries.size(); i++){
            Entry current = entries.get(i);
            System.out.println(current);
        }
    }
    
    public String getPath(){
        return path;
    }
}
