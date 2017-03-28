/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatabaseManager;

import java.net.InetAddress;

/**
 * entry object containing filename, ip of host and size of file
 * @author o
 */
public class Entry {
    
    private final String name;
    private final InetAddress ip;
    private final String userName;
    private final int size;
    private final String sizeOfFile;
    
    public Entry(String name, InetAddress ip, int size)
    {
        this.name = name;
        this.ip = ip;
        this.size = size;
        userName = ip.getHostName();
        sizeOfFile = Integer.toString(size);
    }
    public String getName(){
        return name;
    }
    public int getSize(){
        return size;
    }
    public InetAddress getIp(){
        return ip;
    }
    public String getUserName(){
        return userName;
    }
    public String getSizeOfFile(){
        return sizeOfFile;
    }
}
