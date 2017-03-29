/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatabaseManager;

import MessageManipulator.MessageWriter;
import java.math.BigInteger;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *  directory holding all entries
 *  i should make this a singleton later and make it a proper database, but this works for now
 * @author owen
 */
public class Directory {
    ObservableList<Entry> entries;
    private String path = null;
    
    /**
     * constructor for Server
     * @param entries
     */
    public Directory(ObservableList<Entry> entries){ 
        this.entries = entries;
    }
    
    
    public void addToDirectory(String fileName, BigInteger fileSize, InetAddress host){
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
    public String query(String fileName, BigInteger size)
    {
        String response;
        ObservableList<Entry> queries = FXCollections.observableList(new ArrayList<Entry>());
        for(int i=0; i<entries.size(); i++)
        {
            if(fileName.equals(entries.get(i).getName()))
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
    public String getAllData(ObservableList<Entry> entries)
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
    
    public void changePath(String path){
        this.path = path;
    }
    
    public boolean contains(Entry checkAgainst){
        for (int i = 0; i < entries.size(); i++){
            Entry entry = entries.get(i);
            if (entry.getName().equals(checkAgainst.getName()) && entry.getSize() == checkAgainst.getSize() && entry.getIp().equals(checkAgainst.getIp())){
                return true;
            }
        }
        return false;
    }
}
