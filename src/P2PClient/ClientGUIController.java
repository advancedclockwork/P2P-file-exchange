/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package P2PClient;

import DatabaseManager.Directory;
import DatabaseManager.Entry;
import MessageManipulator.MessageWriter;
import TCPInteractionPrototype.ClientToClientAction;
import TCPInteractionPrototype.ClientToServerAction;
import java.io.IOException;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

/**
 *
 * @author Owen
 */
public class ClientGUIController extends VBox{
    
    @FXML
    private TableView<Entry> downloadableFilesTable;

    @FXML
    private TableColumn<Entry, String> fileNameColumn;

    @FXML
    private TableColumn<Entry, Integer> fileSizeColumn;

    @FXML
    private TableColumn<Entry, InetAddress> fileSourceColumn;

    @FXML
    private TableColumn<?, ?> downloadProgressColumn;

    @FXML
    private Button informAndUpdateButton;

    @FXML
    private Button downloadSelectedFileButton;

    @FXML
    private Button exitButton;
    
    @FXML 
    private void informAndUpdate(ActionEvent event){
        ClientToServerAction action = new ClientToServerAction(serverIp, serverPort, localDirectory);
        new Thread(action).start();
        MessageWriter writer = new MessageWriter();
        String message = writer.composeInformAndUpdate(serverIp, PATH);
        action.changeMessage(message);
    }
    
    @FXML 
    private void downloadRequest(ActionEvent event){
        Entry focus = downloadableFilesTable.getSelectionModel().getSelectedItem();
        InetAddress clientIP = focus.getIp();
        String fileName = focus.getName();
        ClientToClientAction request = new ClientToClientAction(clientIP, requestPort, PATH, fileName );
        MessageWriter writer = new MessageWriter();
        String message = writer.composeFileRequest(fileName);
        request.changeMessage(message);
        new Thread(request).start();
    }
    
    @FXML 
    private void exit(ActionEvent event){
        
    }
    
    private final int serverPort;
    private final InetAddress serverIp;
    private final Directory localDirectory;
    private final ObservableList<Entry> entries;
    private final static String PATH = "H:\\documents\\My Documents";
    private final int requestPort = 9007;
    
    
    public ClientGUIController(ObservableList<Entry> entries, int serverPort, InetAddress serverIp, Directory localDirectory){
        this.entries = entries; 
        this.serverPort = serverPort;
        this.serverIp = serverIp;
        this.localDirectory = localDirectory;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ClientGUIView.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(ClientGUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
        fileNameColumn.setCellValueFactory(new PropertyValueFactory<Entry, String>("name"));
        fileSizeColumn.setCellValueFactory(new PropertyValueFactory<Entry, Integer>("size"));
        fileSourceColumn.setCellValueFactory(new PropertyValueFactory<Entry, InetAddress>("ip"));
        
        entries.addListener(new ListChangeListener<Entry>(){
            @Override
            public void onChanged(ListChangeListener.Change c){
                downloadableFilesTable.getItems().setAll(entries);
            }
        });
    }
}
