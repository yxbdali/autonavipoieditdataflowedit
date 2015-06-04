/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.autonavi.data.test.fxui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author xiangbin.yang
 */
public class FXMLChooseTaskTypeDlgDocumentController implements Initializable {
    @FXML
    private ComboBox TaskStageComboBox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ObservableList<String> taskStageList = FXCollections.observableArrayList("EDIT", "INNER CHECK");
        //TaskStageComboBox = new ComboBox();
        TaskStageComboBox.setItems(taskStageList);
        //TaskStageComboBox.getItems().addAll("EDIT", "INNER CHECK");
        
    }
    
    @FXML
    private void OKButtonAction(ActionEvent event){   
        JOptionPane.showMessageDialog(null, TaskStageComboBox.getValue().toString(), "Info", JOptionPane.ERROR_MESSAGE);
    }
    
}
