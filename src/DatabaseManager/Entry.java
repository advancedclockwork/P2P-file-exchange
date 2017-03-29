/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatabaseManager;

import java.math.BigInteger;
import java.net.InetAddress;

/**
 * entry object containing filename, ip of host and size of file
 * @author o
 */
public class Entry {
    
    private final String name;
    private final InetAddress ip;
    private final BigInteger size;
    
    public Entry(String name, InetAddress ip, BigInteger size)
    {
        this.name = name;
        this.ip = ip;
        this.size = size;
    }
    public String getName(){
        return name;
    }
    public BigInteger getSize(){
        return size;
    }
    public InetAddress getIp(){
        return ip;
    }
}
