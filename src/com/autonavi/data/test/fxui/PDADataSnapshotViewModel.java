/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.autonavi.data.test.fxui;

import com.autonavi.data.test.*;
import java.util.Date;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author xiangbin.yang
 */
public class PDADataSnapshotViewModel {
    private PDADataSnapshot pdaDataSnapshot;
    
    /**
     * Get PDA Data snapshot
     * 
     * @return 
     */
    public PDADataSnapshot getPDADataSnapshot(){
        return pdaDataSnapshot;
    }
    
    /**
     * Set PDA data snapshot
     * 
     * @param pdaDataSnapshot 
     */
    public void setPDADataSnapshot(PDADataSnapshot pdaDataSnapshot){
        this.pdaDataSnapshot = pdaDataSnapshot;
    }
    
    private final SimpleStringProperty guid;
    
    /**
     * Get guid
     * 
     * @return 
     */
    public String getGuid(){
        return guid.get();
    }
    
    /**
     * Set guid with value
     * 
     * @param value 
     */
    public void setGuid(String value){
        guid.set(value);
    }
    
    private final SimpleStringProperty name;
    
    /**
     * Get name
     * 
     * @return 
     */
    public String getName(){
        return name.get();
    }
    
    /**
     * Set name
     * 
     * @param value 
     */
    public void setName(String value){
        name.set(value);
    }
    
    private SimpleObjectProperty saveDate;
    
    /**
     * Get save date
     * 
     * @return 
     */
    public Date getSaveDate(){
        return (Date)saveDate.get();
    }
    
    /**
     * Set save date
     * 
     * @param value 
     */
    public void setSaveDate(Date value){
        saveDate.set(value);
    }

    /**
     * Constructor
     * 
     * @param pdaDataSnapshot 
     */
    public PDADataSnapshotViewModel(PDADataSnapshot pdaDataSnapshot) {
        this.pdaDataSnapshot = pdaDataSnapshot;
        this.guid = new SimpleStringProperty(this.pdaDataSnapshot.getGuid());
        this.name = new SimpleStringProperty((String)this.pdaDataSnapshot.getPDAPOISnapshot().getDataHashMap().get("NAME"));
        this.saveDate = new SimpleObjectProperty(this.pdaDataSnapshot.getPDAPOISnapshot().getSaveDate());
    }  
}
