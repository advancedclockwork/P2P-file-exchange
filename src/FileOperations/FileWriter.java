/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileOperations;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Owen
 */
public class FileWriter extends FileOperator {
    public FileWriter(String name, String path, byte[] data){
        this.fileName = name;
        this.path = path;
        this.fileData = data;
    }
    
    public void writeFile(){
        File myFile = new File(path + "\\" + fileName);
        FileOutputStream newFile = null;
        try {
            newFile = new FileOutputStream(myFile);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            newFile.write(fileData);
        } catch (IOException ex) {
            Logger.getLogger(FileWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            newFile.close();
        } catch (IOException ex) {
            Logger.getLogger(FileWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
