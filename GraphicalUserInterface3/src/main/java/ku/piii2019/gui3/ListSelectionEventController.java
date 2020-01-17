/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.gui3;


import javafx.scene.control.TableView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SelectionMode;

/**
 *
 * @author Regory Gregory
 */
public class ListSelectionEventController {
    TableView[] tableViews;
     
    public ListSelectionEventController(TableView[] tv){
        this.tableViews = tv;
        this.initialize();
    }
    public void initialize(){
        
        for(TableView tv : tableViews){
            MultipleSelectionModel lsm =  tv.getSelectionModel();
            lsm.setSelectionMode(SelectionMode.MULTIPLE);
            //lsm.selectedItemProperty().addListener(new CustomListSelectionListener(tv));       
        }
        
    }
}
