/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.autonavi.data.test.fxui;

import javafx.beans.property.SimpleStringProperty;
import com.autonavi.data.test.TaskPackage;

/**
 *
 * @author xiangbin.yang
 */
public class TaskPackageViewModel {

    public TaskPackageViewModel(TaskPackage taskPackage) {
        this.taskPackage = taskPackage;
        setTaskId(taskPackage.getTaskId());
        setTaskName(taskPackage.getTaskName());
    }
    
    
    private SimpleStringProperty taskId;
    
    /**
     * Get task id
     * 
     * @return 
     */
    public String getTaskId(){
        return taskId.get();
    }
    
    /**
     * Set task id
     * 
     * @param taskId 
     */
    public void setTaskId(String taskId){
        this.taskId.set(taskId);
    }
    
    private SimpleStringProperty taskName;
    
    /**
     * Get task name
     * 
     * @return 
     */
    public String getTaskName(){
        return taskName.get();
    }
    
    /**
     * Set task name
     * 
     * @param taskName 
     */
    public void setTaskName(String taskName){
        this.taskName.set(taskName);
    }
    
    private TaskPackage taskPackage;
    
    /**
     * Get task package
     * 
     * @return 
     */
    public TaskPackage getTaskPackage(){
        return taskPackage;
    }
    
    /**
     * Set task package
     * 
     * @param taskPackage 
     */
    public void setTaskPackage(TaskPackage taskPackage){
        this.taskPackage = taskPackage;
    }
}
