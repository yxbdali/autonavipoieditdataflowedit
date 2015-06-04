/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.autonavi.data.test.fxui;

import com.autonavi.data.test.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;

/**
 *
 * @author xiangbin.yang
 */
public class Utils {
    public static<T> ObservableList convertArrayList2ObservableList(ArrayList<T> arrayList) {
        return FXCollections.observableArrayList(arrayList);
    }
    
    /**
     * convert pda data snapshot list to view model
     * 
     * @param pdaDataSnapshotList
     * @return 
     */
    public static ObservableList<PDADataSnapshotViewModel> convertPDADataSnapshotList2ViewModel(PDADataSnapshotList pdaDataSnapshotList){
        ArrayList<PDADataSnapshotViewModel> pdaDataSnapshotViewList = new ArrayList<>();
        for (PDADataSnapshot pdaDataSnapshot : pdaDataSnapshotList) {
            pdaDataSnapshotViewList.add(new PDADataSnapshotViewModel(pdaDataSnapshot));
        }
        
        ObservableList<PDADataSnapshotViewModel> pdaDataSnapshotListViewModel = FXCollections.observableArrayList(pdaDataSnapshotViewList);
        
        return  pdaDataSnapshotListViewModel;
    }
}
