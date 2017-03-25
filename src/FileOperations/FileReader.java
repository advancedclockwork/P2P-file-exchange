/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileOperations;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Owen
 */
public class FileReader extends FileOperator{
        public FileReader(String name, String path){
        this.fileName = name;
        this.path = path;
    }
    
    public byte[] readFile(){
        File myFile = new File(path + "\\" + fileName);
        if (myFile.exists())
        {
        FileInputStream myFileStream = null;
            try {
                myFileStream = new FileInputStream(myFile);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FileReader.class.getName()).log(Level.SEVERE, null, ex);
            }
            fileData = new byte[(int) myFile.length()];
            try {
                myFileStream.read(fileData);
            } catch (IOException ex) {
                Logger.getLogger(FileReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else{
            System.out.println("file does not exist");
        }
        return fileData;
    }
}
