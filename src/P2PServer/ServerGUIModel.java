/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package P2PServer;

import DatabaseManager.Directory;
import DatabaseManager.Entry;
import TCPInteractionPrototype.ServerAction;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sun.applet.Main;

/**
 *
 * @author Owen
 */
public class ServerGUIModel extends Application {
    private final int directoryPort = 9000;
    
    @Override
    public void start(Stage stage){
        try{
            ObservableList<Entry> entries = FXCollections.observableList(new ArrayList<Entry>());
            Directory directory = new Directory(entries);
            ServerGUIController controller = new ServerGUIController(entries);
            stage.setScene(new Scene(controller));
            stage.setTitle("P2P File Exchange Directory");
            stage.show();
            ServerAction server = new ServerAction(directoryPort, directory);
            new Thread(server).start();
        }
        catch (Exception ex){
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args){
        launch(args);
    }
}
