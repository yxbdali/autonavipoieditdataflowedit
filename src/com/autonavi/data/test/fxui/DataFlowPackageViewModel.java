/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.autonavi.data.test.fxui;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import com.autonavi.data.test.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author xiangbin.yang
 */
public class DataFlowPackageViewModel {
    /**
     * Default constructor
     * 
     */
    public DataFlowPackageViewModel(){
        dataPackageName = new SimpleStringProperty();
        createDate = new SimpleStringProperty();
        dataFlowPackage = new DataFlowPackage();
    }
    
    public DataFlowPackageViewModel(String packageName){
        this();
        setDataPackageName(packageName);
    }

    public DataFlowPackageViewModel(DataFlowPackage dataFlowPackage) {
        this.dataFlowPackage = dataFlowPackage;
        
        
        String packageName = dataFlowPackage.getPackageName();
        Date createDate = dataFlowPackage.getCreateDate();
        String sCreateDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createDate);
        
        this.dataPackageName = new SimpleStringProperty(packageName);
        this.createDate = new SimpleStringProperty(sCreateDate);
    }
    
    
    private final SimpleStringProperty dataPackageName;
    
    /**
     * Get data package naem
     * 
     * @return 
     */
    public String getDataPackageName(){
        return dataPackageName.get();
    }
    
    /**
     * Set data package name
     * 
     * @param dataPackageName 
     */
    public void setDataPackageName(String dataPackageName){
        this.dataPackageName.set(dataPackageName);
    }
    
    private final SimpleStringProperty createDate;
    
    /**
     * Get date flow package create time
     * 
     * @return 
     */
    public String getCreateDate(){
        return createDate.get();
    }
    
    /**
     * Set date flow package create time
     * 
     * @param createDate 
     */
    public void setCreateDate(String createDate){
        this.createDate.set(createDate);
    }
    
    private DataFlowPackage dataFlowPackage;
    
    /**
     * Get data flow package
     * 
     * @return 
     */
    public DataFlowPackage getDataFlowPackage(){
        return dataFlowPackage;
    }
    
    /**
     * Set data flow package
     * 
     * @param dataFlowPackage 
     */
    public void setDataFlowPackage(DataFlowPackage dataFlowPackage){
        this.dataFlowPackage = dataFlowPackage;
    }
    
    private ObservableList<TaskPackageViewModel> taskPackageViewModels;
    
    /**
     * Get task view models
     * 
     * @return 
     */
    public ObservableList<TaskPackageViewModel> getTaskPackageViewModels(){
        if (taskPackageViewModels == null){
            taskPackageViewModels = FXCollections.observableArrayList();
        }
        return taskPackageViewModels;
    }
    
    /**
     * Set task view models
     * 
     * @param taskPackageViewModels 
     */
    public void setTaskPackageViewModels(ObservableList<TaskPackageViewModel> taskPackageViewModels){
        this.taskPackageViewModels = taskPackageViewModels;
    }
    
    /**
     * Add task view model item
     * 
     * @param taskPackageViewModel 
     */
    public void add(TaskPackageViewModel taskPackageViewModel){
        getTaskPackageViewModels().add(taskPackageViewModel);
    }
    
    public static DataFlowPackageViewModel Deserialize(String dataPackageFile) throws ClassNotFoundException, IOException {
        DataFlowPackage dataFlowPackage = DataFlowPackage.deserialize(dataPackageFile);
        
        DataFlowPackageViewModel dataFlowPackageViewModel = new DataFlowPackageViewModel(dataFlowPackage);
        
        return  dataFlowPackageViewModel;
    }
    
    public void Serialize(String file) throws IOException {
        dataFlowPackage.serialize(file);
    }
}
