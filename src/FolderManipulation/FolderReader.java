/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FolderManipulation;
import java.io.File;

/**
 *
 * @author owen
 */
public class FolderReader {
    private File storage;
    private String path;
    
    public FolderReader(String path){
        this.path = path;
        storage = new File(path);
    }
    
    public String getContents()
    {
        String response = "";
        File[] listOfFiles = storage.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {

            if (listOfFiles[i].isFile()) {
                response += listOfFiles[i].getName() + " " + listOfFiles[i].length() + "\r\n";
            }
        }
        return response;
    }
    
    public String getPath()
    {
        return path;
    }
    
}