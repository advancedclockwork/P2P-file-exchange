/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatabaseManager;

/**
 * entry object containing filename, ip of host and size of file
 * @author o
 */
public class Entry {
    
    private String name;
    private byte[] ip = new byte[4];
    private int size;
    
    public Entry(String name, byte[] ip, int size)
    {
        this.name = name;
        this.ip = ip;
        this.size = size;
    }
    public String getName(){
        return name;
    }
    public int getSize(){
        return size;
    }
    public byte[] getIp(){
        return ip;
    }
}
