/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.autonavi.data.test.fxui;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javax.swing.JOptionPane;
import java.io.File;
import com.autonavi.data.test.*;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TableColumn;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.Clipboard;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.util.StringConverter;

/**
 *
 * @author xiangbin.yang
 */
public class FXMLDocumentController implements Initializable {

    private final String workingDir = System.getProperty("user.dir");
    private final Path dataFlowPackageDir = Paths.get(workingDir + File.separator + "DataFlowPackages");
    private final String dataQueriesDir = workingDir + File.separator + "DataQueries";

    private String startTaskGuid;
    private ArrayList<String> taskStageList;

    private DataFlowPackageViewModel dataFlowPackageViewModel;
    private ObservableList<DataFlowPackageViewModel> dataFlowPackageViewModelList = FXCollections.observableArrayList();

    private DataFlowPackageInfoBundleList dataFlowPackageInfoBundleList = new DataFlowPackageInfoBundleList();
    private DataFlowPackageInfoBundle selecteDataFlowPackageInfoBundle;

    private ObservableList<POIDataFlowPackage> poiDataFlowPackageList = FXCollections.observableArrayList();

    private DbDataFlowQueryConfigItem dbDataFlowQueryConfigItem;
    private List<DbDataFlowQueryConfigItem> dbDataFlowQueryConfigItemList;

    private static FXMLDocumentController instance;

    private DbConfigList dbConfigList;

    public static FXMLDocumentController getInstance() {
        return instance;
    }

    public FXMLDocumentController() throws IOException {
        instance = this;
        taskStageList = new ArrayList<>();
        taskStageList.add("内业生产——201");
        taskStageList.add("内检——202");
        taskStageList.add("资检——203");

        if (!Files.exists(dataFlowPackageDir)) {
            Files.createDirectory(dataFlowPackageDir);
        }
        //dbDataFlowQueryConfigItem = DbDataFlowQueryConfigItem.xstreamDeserialize(workingDir + File.separator + "DataFlowQuery.xml");
    }

    private ObservableList<PDADataSnapshotViewModel> pdaDataSnapshotViewModelList;

    public ObservableList<PDADataSnapshotViewModel> getPDADataSnapshotViewModelList() {
        if (pdaDataSnapshotViewModelList == null) {
            pdaDataSnapshotViewModelList = FXCollections.observableArrayList();
            DataFlowTableView.setItems(pdaDataSnapshotViewModelList);
        }
        return pdaDataSnapshotViewModelList;
    }

    public void setPDADataSnapshotViewModelList(ObservableList<PDADataSnapshotViewModel> pdaDataSnapshotViewModelList) {
        this.pdaDataSnapshotViewModelList = pdaDataSnapshotViewModelList;
        DataFlowTableView.setItems(this.pdaDataSnapshotViewModelList);
    }

    @FXML
    private BorderPane MainBorderPane;

    @FXML
    private Label label;

    @FXML
    private TableView DataFlowTableView;

    @FXML
    private TableView DataFlowSnapshotTableView;

    @FXML
    private TableView TaskDataTableView;

    @FXML
    private TableView POIDataTableView;

    @FXML
    private TextField guidTextField;

    @FXML
    private Button CreateDataFlowPackageButton;

    @FXML
    private TextField DataPackageNameTextField;

    @FXML
    private TableColumn TaskIdColumn;

    @FXML
    private TableColumn TaskCommentColumn;

    @FXML
    private ContextMenu CopyTaskIdContextMenu;

    @FXML
    private ComboBox ProductLineComboBox;

    @FXML
    private ComboBox DatabaseComboBox;

    @FXML
    private CheckBox SaveAchieveCheckbox;

    /**
     * Save data flow event handler
     *
     * @param event
     */
    @FXML
    private void saveDataFlowButtonAction(ActionEvent event) throws SQLException, IOException {
        //JOptionPane.showMessageDialog(null, "Save data flow button clicked!", "Info", JOptionPane.INFORMATION_MESSAGE);
        /*
         String guid = guidTextField.getText();
         if (guid.equalsIgnoreCase("")){
         JOptionPane.showMessageDialog(null, "GUID can't be empty!", "Error", JOptionPane.ERROR_MESSAGE);
         return;
         }
         PDADataSnapshot pdaDataSnapshot = new PDADataSnapshot();
         pdaDataSnapshot.getPDADataSnapshotData(guid);
        
         PDADataSnapshotViewModel pdaDataSnapshotViewModel = new PDADataSnapshotViewModel(pdaDataSnapshot);
         getPDADataSnapshotViewModelList().add(pdaDataSnapshotViewModel);
         */

        /*
         final Stage chooseTaskDlgStage = new Stage();
         chooseTaskDlgStage.initModality(Modality.APPLICATION_MODAL);
         chooseTaskDlgStage.setResizable(false);
         Parent root = FXMLLoader.load(getClass().getResource("FXMLChooseTaskTypeDlgDocument.fxml"));
        
         Scene scene = new Scene(root);
         chooseTaskDlgStage.setScene(scene);
         chooseTaskDlgStage.show();
         */
        Object selectedObject = DataFlowSnapshotTableView.getSelectionModel().getSelectedItem();
        if (selectedObject == null) {
            JOptionPane.showMessageDialog(null, "Please select a data flow first!");
            return;
        }

        String taskId = null;

        TextInputDialog textInputDialog = JavaFXDlgUtils.getTextInputDialog("Input task guid", "Please input task id", null);
        Optional<String> result = textInputDialog.showAndWait();
        if (result.isPresent()) {
            taskId = result.get();
        } else {
            return;
        }
        try {
            saveTaskDataSnapshot(taskId);
        } catch (Exception e) {
            Alert exceptionDialog = JavaFXDlgUtils.getExceptionDialog("Exception", "Exception", "There was a exception throw", e);
            exceptionDialog.showAndWait();
            return;
        }

        /*
         ChoiceDialog taskStageChoiceDialog = JavaFXDlgUtils.getChoiceDialog("Chose task stage", "Please choose task stage", null, taskStageList);
         Optional<String> result = taskStageChoiceDialog.showAndWait();
         String taskStage = "";
         if (result.isPresent()){
         taskStage = result.get();
         }
         else{
         return;
         }
         */
        //TaskPackage taskPackage = new TaskPackage(startTaskGuid);
        //TaskPackageViewModel taskPackageViewModel = new TaskPackageViewModel(taskPackage);
        //dataFlowPackageViewModel.add(taskPackageViewModel);
        //test();
    }

    /**
     * Open file menu event handler
     *
     * @param event
     */
    @FXML
    private void openFileMenuAction(ActionEvent event) throws ClassNotFoundException, IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Data Snapshot");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            System.out.println("open file");
            String filePath = file.getPath();
            PDADataSnapshotList pdaDataSnapshotList = PDADataSnapshotList.deserialze(filePath);
            ObservableList<PDADataSnapshotViewModel> _PDADataSnapshotViewModels = getPDADataSnapshotViewModelList();
            _PDADataSnapshotViewModels = Utils.convertPDADataSnapshotList2ViewModel(pdaDataSnapshotList);
            //JOptionPane.showMessageDialog(null, "File opened!");
            DataFlowTableView.setItems(_PDADataSnapshotViewModels);
        }
    }

    @FXML
    private void CreateDataFlowPackageButtonAction(ActionEvent event) throws IOException {
        String dataFlowPackageName = DataPackageNameTextField.getText();
        if (dataFlowPackageName.equalsIgnoreCase("")) {
            Alert alert = JavaFXDlgUtils.getAlertDialog(Alert.AlertType.ERROR, null, "Data flow package name can't be empty!");
            alert.showAndWait();
            return;
        }

        if (isDataFlowPackageNameExisted(dataFlowPackageName)) {
            Alert alert = JavaFXDlgUtils.getAlertDialog(Alert.AlertType.ERROR, null, "Data flow package name has existed, please change another one.");
            alert.showAndWait();
            return;
        }

        // Create a new POI data flow package
        String poiDataFlowPackageFilePath = workingDir + File.separator + "DataFlowPackages" + File.separator + dataFlowPackageName + ".dat";
        Path path = Paths.get(poiDataFlowPackageFilePath);
        if (Files.exists(path)) {
            Alert alert = JavaFXDlgUtils.getAlertDialog(Alert.AlertType.ERROR, "Error", "The data flow package name has existed!\nPlease change another one!");
            alert.showAndWait();
            return;
        }
        POIDataFlowPackage poiDataFlowPackage = new POIDataFlowPackage(dataFlowPackageName);
        poiDataFlowPackage.setDataFlowPackageType(dbDataFlowQueryConfigItem.getDataFlowType());
        poiDataFlowPackageList.add(poiDataFlowPackage);

        DataFlowPackageInfoBundle dataFlowPackageInfoBundle = new DataFlowPackageInfoBundle(poiDataFlowPackage, poiDataFlowPackageFilePath);
        dataFlowPackageInfoBundleList.add(dataFlowPackageInfoBundle);

    }

    @FXML
    private void SaveDataFlowPackageMenuAction(ActionEvent event) throws IOException {
        saveDataFlows();
    }

    @FXML
    private void ExitMenuAction(ActionEvent event) {

    }

    @FXML
    private void RollbackButtonAction(ActionEvent event) {
        Object selectedObj = TaskDataTableView.getSelectionModel().getSelectedItem();

        if (selectedObj == null) {
            Alert alert = JavaFXDlgUtils.getAlertDialog(Alert.AlertType.WARNING, null, "Please select a task first!");
            alert.showAndWait();
            return;
        }

        POITaskPackage poiTaskPackage = (POITaskPackage) selectedObj;

        try {
            DbConfigItem dbConfigItem = findDbConfigItem(dbConfigList, poiTaskPackage.getDbConfigName());
            String connection = composeConnectionString(dbConfigItem);
            RollbackHelper.rollback(dbDataFlowQueryConfigItem, poiTaskPackage, connection, dbConfigItem.getUser(), dbConfigItem.getPassword());
            Alert messageAlert = JavaFXDlgUtils.getAlertDialog(Alert.AlertType.INFORMATION, "Success",
                    String.format("The task '%s' has been rollback", poiTaskPackage.getTaskId()));
            messageAlert.showAndWait();
        } catch (Exception e) {
            Alert alert = JavaFXDlgUtils.getExceptionDialog("Exception", "Exception was throwed", "The details is", e);
            alert.showAndWait();
        }

    }

    @FXML
    private void CopyTaskIdContextMenuItemAction(ActionEvent event) {
        Object selectedObj = TaskDataTableView.getSelectionModel().getSelectedItem();
        if (selectedObj != null) {
            POITaskPackage poiTaskPackage = (POITaskPackage) selectedObj;
            copyToClipboard(poiTaskPackage.getTaskId());
        }
    }

    @FXML
    private void CopyTaskIdButtonAction(ActionEvent event) {
        Object selectedObj = TaskDataTableView.getSelectionModel().getSelectedItem();
        if (selectedObj != null) {
            POITaskPackage poiTaskPackage = (POITaskPackage) selectedObj;
            copyToClipboard(poiTaskPackage.getTaskId());
        }
    }

    @FXML
    private void DeleteTaskPackageDataButtonAction(ActionEvent event) {
        Object selectedObj = TaskDataTableView.getSelectionModel().getSelectedItem();
        if (selectedObj != null) {
            POITaskPackage poiTaskPackage = (POITaskPackage) selectedObj;
            POIDataFlowPackage poiDataFlowPackage = selecteDataFlowPackageInfoBundle.getPOIDataFlowPackage();
            poiDataFlowPackage.Delete(poiTaskPackage);
        }
    }

    @FXML
    private void DeleteTaskContextMenuItemAction(ActionEvent event) {
        Object selectedObj = TaskDataTableView.getSelectionModel().getSelectedItem();
        if (selectedObj != null) {
            POITaskPackage poiTaskPackage = (POITaskPackage) selectedObj;
            POIDataFlowPackage poiDataFlowPackage = selecteDataFlowPackageInfoBundle.getPOIDataFlowPackage();
            poiDataFlowPackage.Delete(poiTaskPackage);
        }
    }

    @FXML
    private void DeleteDataFlowDataButtonAction(ActionEvent event) throws IOException {
        Alert confirmAlert = JavaFXDlgUtils.getAlertDialog(Alert.AlertType.CONFIRMATION, null, "Do you want to delete the selected data flow Package");
        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.get() == ButtonType.CANCEL) {
            return;
        }

        Object seletedObj = DataFlowSnapshotTableView.getSelectionModel().getSelectedItem();
        if (seletedObj != null) {
            POIDataFlowPackage poiDataFlowPackage = (POIDataFlowPackage) seletedObj;
            poiDataFlowPackageList.remove(poiDataFlowPackage);
            TaskDataTableView.setItems(null);

            DataFlowPackageInfoBundle dataFlowPackageInfoBundle = dataFlowPackageInfoBundleList.findDataFlowPackageInfoBundle(poiDataFlowPackage);
            if (dataFlowPackageInfoBundle != null) {
                dataFlowPackageInfoBundleList.remove(dataFlowPackageInfoBundle);
                String dataFlowPackageFilePath = dataFlowPackageInfoBundle.getPOIDataFlowPackageFilePath();
                Path path = Paths.get(dataFlowPackageFilePath);
                if (Files.exists(path)) {
                    Files.delete(path);
                }
            }
        }
    }

    @FXML
    private void ViewDataButtonAction(ActionEvent event) {
        Object object = TaskDataTableView.getSelectionModel().getSelectedItem();
        if (object != null) {
            POITaskPackage poiTaskPackage = (POITaskPackage) object;
            ArrayList<POIDataItem> poiDataItemList = poiTaskPackage.getPOIDataList();
            ArrayList<POIDataItemBase> dbDataItemList = poiTaskPackage.getDbDataList();
            Dialog dialog = JavaFXDlgUtils.getTaskPackageDataDialog("Task data", "Task Data", poiDataItemList, dbDataItemList, dbDataFlowQueryConfigItem);
            dialog.show();
        }
    }

    /**
     * Initialize
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        try {
            initializeDataQueries();
            initializeDataFlowPackages();
        } catch (Exception e) {
            e.printStackTrace();
        }

        DataFlowSnapshotTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                POIDataFlowPackage selectedDataFlowPackage = (POIDataFlowPackage) newValue;

                selecteDataFlowPackageInfoBundle = dataFlowPackageInfoBundleList.findDataFlowPackageInfoBundle(selectedDataFlowPackage);
                if (selectedDataFlowPackage != null) {
                    ObservableList<POITaskPackage> dataList = selectedDataFlowPackage.getTaskPackageListProperty();
                    TaskDataTableView.setItems(dataList);
                    TaskDataTableView.getSelectionModel().clearSelection();

                    String productLine = selectedDataFlowPackage.getDataFlowPackageType();
                    for (DbDataFlowQueryConfigItem item : dbDataFlowQueryConfigItemList) {
                        if (item.getDataFlowType().equalsIgnoreCase(productLine)) {
                            ProductLineComboBox.getSelectionModel().select(item);
                        }
                    }
                }
            }
        });

        initializeTableActions();
        try {
            initializeDatabaseComboBox();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*
         TaskDataTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
         @Override
         public void changed(ObservableValue observable, Object oldValue, Object newValue) {
         POITaskPackage selectedPOITaskPackage = (POITaskPackage) newValue;
                
         if (selectedPOITaskPackage != null) {
         ObservableList<POIDataItem> dataList = selectedPOITaskPackage.getPOIDataListProperty();
         POIDataTableView.setItems(dataList);
         }
                        
         System.out.println(selectedPOITaskPackage.getTaskId());
         }
         });
         */

        TaskDataTableView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                //MouseButton mouseButton = e.getButton();
                //if(e.getButton() == MouseButton.PRIMARY){
                //    CopyTaskIdContextMenu.show(TaskDataTableView, e.getScreenX(), e.getScreenY());
                //}
            }
        });

        TaskDataTableView.setContextMenu(CopyTaskIdContextMenu);
    }

    private void initializeDataFlowPackages() throws IOException {
        Path dataFlowPackagePath = Paths.get(workingDir + File.separator + "DataFlowPackages");
        System.out.println(dataFlowPackagePath);
        try (DirectoryStream<Path> streams = Files.newDirectoryStream(dataFlowPackagePath, "*.dat")) {
            poiDataFlowPackageList = FXCollections.observableArrayList();
            //dataFlowPackageViewModelList = FXCollections.observableArrayList();
            for (Path entry : streams) {
                DataFlowPackageViewModel dataFlowPackageViewModel = null;
                try {
                    String poiDataFlowPackageFile = entry.toString();
                    POIDataFlowPackage poiDataFlowPackage = POIDataFlowPackage.deserialize(poiDataFlowPackageFile);
                    poiDataFlowPackageList.add(poiDataFlowPackage);
                    //dataFlowPackageViewModel = DataFlowPackageViewModel.Deserialize(entry.toString());
                    //dataFlowPackageViewModelList.add(dataFlowPackageViewModel);
                    DataFlowPackageInfoBundle dataFlowPackageInfoBundle = new DataFlowPackageInfoBundle(poiDataFlowPackage, poiDataFlowPackageFile);
                    dataFlowPackageInfoBundleList.add(dataFlowPackageInfoBundle);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            DataFlowSnapshotTableView.setItems(poiDataFlowPackageList);
        }
    }

    /**
     * Save task data snapshot with given task id
     * 
     */
    private void saveTaskDataSnapshot(String taskId) throws SQLException, Exception {
        DbConfigItem dbConfigItem = (DbConfigItem) DatabaseComboBox.getSelectionModel().getSelectedItem();
        String connectionString = composeConnectionString(dbConfigItem);
        //boolean isSaveAchieve = SaveAchieveCheckbox.isSelected();
        DbTaskQueryConfigItem queryConfigItem = dbDataFlowQueryConfigItem.getTaskDataQueryConfigList().get(0);
        /*
         if (!isSaveAchieve){
         List<DbTableQueryConfigItem> achieveDbTableQueryConfigItems = new ArrayList<>();
         for(DbTableQueryConfigItem tableQueryConfigItem : queryConfigItem.getDbTableQueryConfigList()){
         if(tableQueryConfigItem.getTableName().equalsIgnoreCase("mdb_poi") || 
         tableQueryConfigItem.getTableName().equalsIgnoreCase("mdb_poi_chargepile")){
         achieveDbTableQueryConfigItems.add(tableQueryConfigItem);
         }
         }
         for (DbTableQueryConfigItem achieveDbTableQueryConfigItem : achieveDbTableQueryConfigItems) {
         queryConfigItem.getDbTableQueryConfigList().remove(achieveDbTableQueryConfigItem);
         }
         }
         */

        DbQueryHelper queryHelper = new DbQueryHelper(queryConfigItem, taskId, dbConfigItem.getConfigName());
        POITaskPackage poiTaskPackage = queryHelper.query(connectionString, dbConfigItem.getUser(), dbConfigItem.getPassword());

        if (poiTaskPackage.getTaskName() == null || poiTaskPackage.getTaskName().equalsIgnoreCase("")) {
            throw new Exception(String.format("%s\n%s\n\n", "No task can be queried from given task id " + taskId,
                    "Please check your selected product line and database are correct"));
        }
        if (selecteDataFlowPackageInfoBundle != null) {
            POIDataFlowPackage selecteDataFlowPackage = selecteDataFlowPackageInfoBundle.getPOIDataFlowPackage();
            selecteDataFlowPackage.Add(poiTaskPackage);
        }
    }

    /**
     * Copy to clipboard
     * 
     * @param content 
     */
    private void copyToClipboard(String content) {
        ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString(content);
        Clipboard.getSystemClipboard().setContent(clipboardContent);
    }

    private void initializeDataQueries() throws IOException {
        dbDataFlowQueryConfigItemList = new ArrayList<>();
        Path path = Paths.get(dataQueriesDir);
        if (Files.exists(path)) {
            try (DirectoryStream<Path> streams = Files.newDirectoryStream(path)) {
                for (Path stream : streams) {
                    DbDataFlowQueryConfigItem item = DbDataFlowQueryConfigItem.xstreamDeserialize(stream.toString());
                    dbDataFlowQueryConfigItemList.add(item);
                }
            }
        } else {
            Files.createDirectory(path);
        }

        ProductLineComboBox.setItems(FXCollections.observableArrayList(dbDataFlowQueryConfigItemList));
        ProductLineComboBox.setConverter(new StringConverter() {

            @Override
            public String toString(Object object) {
                DbDataFlowQueryConfigItem _item = (DbDataFlowQueryConfigItem) object;
                return _item.getDataFlowType();
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public Object fromString(String string) {
                for (DbDataFlowQueryConfigItem item : dbDataFlowQueryConfigItemList) {
                    if (item.getDataFlowType().equalsIgnoreCase(string)) {
                        return item;
                    }
                }
                return null;
            }
        });

        ProductLineComboBox.getSelectionModel().selectFirst();
        dbDataFlowQueryConfigItem = dbDataFlowQueryConfigItemList.get(0);
        ProductLineComboBox.valueProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                dbDataFlowQueryConfigItem = (DbDataFlowQueryConfigItem) newValue;
                System.out.println("Selected " + dbDataFlowQueryConfigItem.getDataFlowType());
            }
        });
    }

    private boolean isDataFlowPackageNameExisted(String dataFlowPackageName) {
        for (DataFlowPackageInfoBundle item : dataFlowPackageInfoBundleList) {
            if (item.getPOIDataFlowPackage().getPackageName().equalsIgnoreCase(dataFlowPackageName)) {
                return true;
            }
        }
        return false;
    }

    public void saveDataFlows() throws IOException {
        for (DataFlowPackageInfoBundle dataFlowPackageInfoBundle : dataFlowPackageInfoBundleList) {
            POIDataFlowPackage poiDataFlowPackage = dataFlowPackageInfoBundle.getPOIDataFlowPackage();
            String file = dataFlowPackageInfoBundle.getPOIDataFlowPackageFilePath();

            poiDataFlowPackage.serialize(file);
        }
    }

    private void initializeTableActions() {
        TaskCommentColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        //TaskCommentColumn.setCellFactory(null);
        TaskCommentColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<POITaskPackage, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<POITaskPackage, String> t) {
                String newComments = t.getNewValue();
                t.getTableView().getSelectionModel().getSelectedItem().setCommentsVal(newComments);
            }
        });
        /*
         TaskCommentColumn.setCellFactory(new Callback<TableColumn<POITaskPackage, String>, TableCell<POITaskPackage, String>>(){
         @Override
         public TableCell<POITaskPackage, String> call(TableColumn<POITaskPackage, String> column) {
         TextFieldTableCell<POITaskPackage, String> tableCell = new TextFieldTableCell<POITaskPackage, String>(){
         @Override
         public void updateItem(String item, boolean empty) {
         if(item!=null){
         setTooltip(new Tooltip(item));
         setText(item);                                
         }
         }
         };
                
         tableCell.setEditable(true);
         return tableCell;
         }
         });
         */
    }

    private void initializeDatabaseComboBox() throws IOException {
        String dbConfigListFile = workingDir + File.separator + "DbConfigList.xml";
        dbConfigList = DbConfigList.deserialize(dbConfigListFile);
        DatabaseComboBox.setItems(FXCollections.observableArrayList(dbConfigList.getDbConfigItemList()));
        DatabaseComboBox.getSelectionModel().selectFirst();
        DatabaseComboBox.setConverter(new StringConverter() {

            @Override
            public String toString(Object object) {
                DbConfigItem dbConfigItem = (DbConfigItem) object;
                return dbConfigItem.getConfigName();
            }

            @Override
            public Object fromString(String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }

    private String composeConnectionString(DbConfigItem dbConfigItem) {
        String server = dbConfigItem.getServer();
        String port = dbConfigItem.getPort();
        String sid = dbConfigItem.getSid();

        return String.format("jdbc:oracle:thin:@%s:%s:%s", server, port, sid);
    }

    private DbConfigItem findDbConfigItem(DbConfigList dbConfigList, String dbConfigName) {
        for (DbConfigItem dbConfigItem : dbConfigList.getDbConfigItemList()) {
            if (dbConfigItem.getConfigName().equalsIgnoreCase(dbConfigName)) {
                return dbConfigItem;
            }
        }
        return null;
    }

    private void test() throws SQLException, IOException {
        DbDataFlowQueryConfigItem dbDataFlowQueryConfigItem = DbDataFlowQueryConfigItem.deserialize(workingDir + File.separator + "DataFlowQuery.xml");
        DbQueryHelper queryHelper = new DbQueryHelper(dbDataFlowQueryConfigItem.getTaskDataQueryConfigList().get(0), "E459E17EF3B04E2DA248E9F6C1588006");
        POITaskPackage poiTaskPackage = queryHelper.query();

        if (selecteDataFlowPackageInfoBundle != null) {
            POIDataFlowPackage selecteDataFlowPackage = selecteDataFlowPackageInfoBundle.getPOIDataFlowPackage();
            selecteDataFlowPackage.Add(poiTaskPackage);
        }
    }
}
