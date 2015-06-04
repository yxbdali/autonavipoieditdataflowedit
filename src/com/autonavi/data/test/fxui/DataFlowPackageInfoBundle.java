/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.autonavi.data.test.fxui;

import com.autonavi.data.test.POIDataFlowPackage;

/**
 *
 * @author xiangbin.yang
 */
public class DataFlowPackageInfoBundle {
    
    /**
     * Default constructor
     * 
     */
    public DataFlowPackageInfoBundle() {
    }
    
    
    /**
     * Constructor with poi data flow package and it's file path
     * 
     * @param poiDataFlowPackage
     * @param poiDataFlowPackageFilePath 
     */
    public DataFlowPackageInfoBundle(POIDataFlowPackage poiDataFlowPackage, String poiDataFlowPackageFilePath) {
        this.poiDataFlowPackage = poiDataFlowPackage;
        this.poiDataFlowPackageFilePath = poiDataFlowPackageFilePath;
    }
    
    
    private POIDataFlowPackage poiDataFlowPackage;
    
    /**
     * Get POI Data Flow package
     * 
     * @return 
     */
    public POIDataFlowPackage getPOIDataFlowPackage(){
        return poiDataFlowPackage;
    }
    
    /**
     * Set POI Data Flow package
     * 
     * @param poiDataFlowPackage 
     */
    public void setPOIDataFlowPackage(POIDataFlowPackage poiDataFlowPackage){
        this.poiDataFlowPackage = poiDataFlowPackage;
    }
    
    private String poiDataFlowPackageFilePath;
    
    /**
     * Get POI data flow package file path
     * 
     * @return 
     */
    public String getPOIDataFlowPackageFilePath(){
        return poiDataFlowPackageFilePath;
    }
    
    /**
     * Set POI data flow package file path
     * 
     * @param poiDataFlowPackageFilePath 
     */
    public void setPOIDataFlowPackageFilePath(String poiDataFlowPackageFilePath){
        this.poiDataFlowPackageFilePath = poiDataFlowPackageFilePath;
    }
}
