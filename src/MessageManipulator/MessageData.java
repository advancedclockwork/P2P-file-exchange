/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MessageManipulator;

/**
 * holds values for unique string separators for regex parsing.
 * message format "command + commandDivider + ip + ipDivider + [filename + entryDivider + fileSize + messageDivider]
 * @author Owen
 */
public abstract class MessageData {

    /**
     * marks the division of one entry to another
     */
    protected final String messageDivider = "&@&^";

    /**
     * marks the division of the filename and the file's size
     */
    protected final String entryDivider= "%$#@";

    /**
     * marks the division of the command sent to the server and the rest of the message
     */
    protected final String commandDivider = "^%$#";

    /**
     * marks the division of the ip included in the message and the rest of the message
     */
    protected final String ipDivider = "%_#@";
}
