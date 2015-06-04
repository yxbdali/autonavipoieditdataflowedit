/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.autonavi.data.test.fxui;

import java.io.File;
import java.util.Optional;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.WindowEvent;

/**
 *
 * @author xiangbin.yang
 */
public class AutonaviDataFlowSnapshotUI extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        //getClass().
        String cssFile = System.getProperty("user.dir") + File.separator + "light.css";
        cssFile = AutonaviDataFlowSnapshotUI.class.getResource("light.css").toExternalForm();
        scene.getStylesheets().add(cssFile);
        
        stage.setScene(scene);
        stage.setTitle("Data Flow Snapshot");
        //stage.setResizable(false);
        
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>(){
            @Override
            public void handle(WindowEvent event){
                Alert confirmeAlert = JavaFXDlgUtils.getConfirmeDialog("Confirme", "Do you want to save Data flow packages?");

                Optional<ButtonType> result = confirmeAlert.showAndWait();
                if (result.get() == ButtonType.YES){
                    try {
                        FXMLDocumentController.getInstance().saveDataFlows();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    
                }
            }
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
