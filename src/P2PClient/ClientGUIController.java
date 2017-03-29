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
import java.io.File;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

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
    private TextField enterFilePath;
    
    @FXML
    private Text browseFiles;
    
    @FXML
    private void enterFilePath(ActionEvent event) {
        localDirectory.changePath(enterFilePath.getText());
        System.out.println(localDirectory.getPath());
    }
    
    @FXML
    void selectFileView(MouseEvent event) {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Select Folder To Share");
        Stage stage = new Stage();
        final File selectedDirectory = chooser.showDialog(stage);
        if (selectedDirectory != null) {
            String path = selectedDirectory.getAbsolutePath();
            localDirectory.changePath(path);
            enterFilePath.setText(path);
            System.out.println(localDirectory.getPath());
        }
    }
    
    @FXML 
    private void informAndUpdate(ActionEvent event){
        if(localDirectory.getPath() != null){
            ClientToServerAction action = new ClientToServerAction(serverIp, serverPort, localDirectory);
            new Thread(action).start();
            MessageWriter writer = new MessageWriter();
            String message = writer.composeInformAndUpdate(serverIp, localDirectory.getPath());
            action.changeMessage(message);
        }
    }
    
    @FXML 
    private void downloadRequest(ActionEvent event){
        Entry focus = downloadableFilesTable.getSelectionModel().getSelectedItem();
        InetAddress clientIP = focus.getIp();
        String fileName = focus.getName();
        ClientToClientAction request = new ClientToClientAction(clientIP, requestPort, localDirectory.getPath(), fileName );
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
