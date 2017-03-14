/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MessageManipulator;
import java.io.File;

/**
 *
 * @author owen
 */
public class FolderReader extends MessageData{
    private File storage;
    
    /**
     * creates a FolderReader object targeting the given file path
     * @param path 
     */
    public FolderReader(String path){
        storage = new File(path);
    }
    
    /**
     * reads all files in the selected directory and return them as a string separated with messageDivider and entryDivider final variables contained in MessageData
     * @return string of folders with custom dividers contained in MessageData
     */
    public String getContents()  // make this recursive later to read contents of directories inside of selected directory
    {
        String response = "";
        File[] listOfFiles = storage.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                response += listOfFiles[i].getName() + messageDivider + listOfFiles[i].length() + entryDivider;
            }
        }
        return response;
    }
}