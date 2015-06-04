/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.autonavi.data.test.fxui;

import com.autonavi.data.test.POIDataFlowPackage;
import java.util.ArrayList;

/**
 *
 * @author xiangbin.yang
 */
public class DataFlowPackageInfoBundleList extends ArrayList<DataFlowPackageInfoBundle>{
    
    /**
     * Find Data flow package info bundle from given POI data package object
     * 
     * @param poiDataFlowPackge
     * @return 
     */
    public DataFlowPackageInfoBundle findDataFlowPackageInfoBundle(POIDataFlowPackage poiDataFlowPackge){
        DataFlowPackageInfoBundle dataFlowPackageInfoBundle = null;
        
        for (DataFlowPackageInfoBundle _dataFlowPackageInfoBundle : this){
            if (_dataFlowPackageInfoBundle.getPOIDataFlowPackage().equals(poiDataFlowPackge)){
                dataFlowPackageInfoBundle = _dataFlowPackageInfoBundle;
                break;
            }
        }
        
        return dataFlowPackageInfoBundle;
    }
}
