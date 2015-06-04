/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.autonavi.data.test.fxui;

import com.autonavi.data.test.DbDataQueryBase;
import com.autonavi.data.test.POIDataItem;
import com.autonavi.data.test.POIDataItemBase;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import java.io.StringWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import com.autonavi.data.test.DbDataFlowQueryConfigItem;
//import com.autonavi.data.test.
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;

/**
 *
 * @author xiangbin.yang
 */
public class JavaFXDlgUtils {

    /**
     * Get alert dialog
     * 
     * @param alertType
     * @param head
     * @param message
     * @return 
     */
    public static Alert getAlertDialog(Alert.AlertType alertType, String head, String message) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(head);
        alert.setContentText(message);

        return alert;
    }
    
    /**
     * Get confirmation dialog
     * 
     * @param head
     * @param content
     * @return 
     */
    public static Alert getConfirmeDialog(String head, String content){
        Alert alert = getAlertDialog(AlertType.CONFIRMATION, head, content);
        
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
        
        return alert;
    }

    public static TextInputDialog getTextInputDialog(String title, String head, String content) {
        TextInputDialog textInputDialog = new TextInputDialog();
        textInputDialog.setTitle(title);
        textInputDialog.setContentText(content);
        textInputDialog.setHeaderText(head);

        return textInputDialog;
    }
    
    

    public static <T> ChoiceDialog getChoiceDialog(String title, String head, String content, ArrayList<T> choices) {
        ChoiceDialog choiceDialog = new ChoiceDialog(choices.get(0), choices);
        choiceDialog.setTitle(title);
        choiceDialog.setHeaderText(head);
        choiceDialog.setContentText(content);

        return choiceDialog;
    }

    public static Alert getExceptionDialog(String title, String head, String content, Exception e) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(head);
        alert.setContentText(content);

        Exception ex = e;

        // Create expandable Exception.
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("The exception stacktrace was:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        // Set expandable Exception into the dialog pane.
        alert.getDialogPane().setExpandableContent(expContent);

        return alert;
    }

    public static Dialog getTaskPackageDataDialog(String title, String head, ArrayList<POIDataItem> poiDataItemList,
            ArrayList<POIDataItemBase> dbDataItemList, DbDataFlowQueryConfigItem dbDataFlowQueryConfigItem) {
        Dialog dialog = new Dialog();
        dialog.setResizable(true);
        dialog.setTitle(title);
        dialog.setHeaderText(head);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

      
        TabPane tabPane = new TabPane();
        tabPane.setPrefSize(500, 300);
        
        Tab poiTab = generateTabItem("MDB_POI_EDIT");
        tabPane.getTabs().add(poiTab);
        TableView poiDataTableView = (TableView)((GridPane)poiTab.getContent()).getChildren().get(0);
        
        TableColumn<POIDataItem, String> poiGuidColumn = new TableColumn<POIDataItem, String>("POI");
        poiGuidColumn.setCellValueFactory(new PropertyValueFactory("poiIdProperty"));
        
        poiDataTableView.getColumns().add(poiGuidColumn);
        
        poiDataTableView.setItems(FXCollections.observableArrayList(poiDataItemList));
        
        //GridPane.setVgrow(tabPane, Priority.ALWAYS);
        //GridPane.setHgrow(tabPane, Priority.ALWAYS);
        
        
        dialog.getDialogPane().setContent(tabPane);

        return dialog;
    }
    
    private static Tab generateTabItem(String tabName){
        Tab poiTab = new Tab(tabName);
        poiTab.setClosable(false);
        
        TableView tableView = new TableView();
       
        GridPane gridPane = new GridPane();
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setVgrow(Priority.ALWAYS);
        rowConstraints.setFillHeight(true);
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setHgrow(Priority.ALWAYS);
        columnConstraints.setFillWidth(true);
        gridPane.getColumnConstraints().add(columnConstraints);
        gridPane.getRowConstraints().add(rowConstraints);
        gridPane.setPadding(new Insets(10));
        poiTab.setContent(gridPane);
        gridPane.add(tableView, 0, 0);
        
        return poiTab;
    }
}
