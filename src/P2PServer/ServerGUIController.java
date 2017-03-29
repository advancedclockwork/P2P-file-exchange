/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package P2PServer;

import DatabaseManager.Entry;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 *
 * @author Owen
 */
public class ServerGUIController extends VBox {
    @FXML
    private ScrollPane entryScrollPlane;

    @FXML
    private TableView<Entry> entryTable;

    @FXML
    private TableColumn<Entry, String> entryNameColumn;

    @FXML
    private TableColumn<Entry, Integer> entrySizeColumn;
    

    @FXML
    private TableColumn<Entry, InetAddress> entryUserColumn;
    
    @FXML
    private ScrollPane userScrollPlane;

    @FXML
    private ListView<String> userList;

    @FXML
    private Pane namePlate;
    
    @FXML
    private void initialize(){
        String hostName = "";
        try {
            InetAddress host = InetAddress.getLocalHost();
            if(host.getHostName()!= null){
                hostName = host.getHostAddress();
            }
        } catch (UnknownHostException ex) {
            Logger.getLogger(ServerGUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
        currentIPLabel.setText(hostName);
    };
    
    @FXML
    private Label currentIPLabel;
    
    ObservableList<Entry> entries;
    
    public ServerGUIController(ObservableList<Entry> entries){
        this.entries = entries; 
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ServerGUIView.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(ServerGUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
        entryNameColumn.setCellValueFactory(new PropertyValueFactory<Entry, String>("name"));
        entrySizeColumn.setCellValueFactory(new PropertyValueFactory<Entry, Integer>("size"));
        entryUserColumn.setCellValueFactory(new PropertyValueFactory<Entry, InetAddress>("ip"));
        entries.addListener(new ListChangeListener<Entry>(){
            @Override
            public void onChanged(ListChangeListener.Change c){
                entryTable.getItems().setAll(entries);
            }
        });
        
    }
    
    public static class User{
        private String userName;
        
        public User(String name){
            this.userName = name;
        }
        
        public String getUserName(){
            return userName;
        }
        
        public void setUserName(String name){
            name = userName;
        }
    }
}
