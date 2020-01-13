/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.gui3;

import javafx.scene.control.MenuItem;
import javafx.event.ActionEvent;
import javafx.scene.control.TableView;
import javafx.css.Styleable;
import java.util.Arrays;

/**
 *
 * @author Regory Gregory
 */
public class DirectionTeller {
    TableView[] tableViews;
    String actionSeparatorCharacter;
    
    public DirectionTeller( TableView[] tableViews){
        this.tableViews = tableViews;
        this.actionSeparatorCharacter = ":";
    }
    
    public DirectionTeller( TableView[] tableViews, String actionSeparatorCharacter){
        this.tableViews = tableViews;
        this.actionSeparatorCharacter = actionSeparatorCharacter;

    }
    
    public TableView urinaryAction(ActionEvent e){
         Styleable m = (Styleable) e.getSource();
         String id = m.getId();
         return this.returnSource(id);
    }
    public TableView[] binaryAction(ActionEvent e){
         Styleable m = (Styleable) e.getSource();
         String id = m.getId();
         String[] addresses = processBinaryAction(id);
         TableView[] sourceAndTarget = new TableView[]{this.returnSource(addresses[0]), this.returnTarget(addresses[1])};
         return sourceAndTarget;
    }
    
    public TableView returnTarget(String id)
    {
        int index = Integer.valueOf(id)-1;
        return this.tableViews[index];
    }
    
    public TableView returnSource(String id)
    {
       int index = Integer.valueOf(id)-1;
       return this.tableViews[index];
    
    }
    
    public String[] processBinaryAction(String id){
        String[] addresses = id.split(this.actionSeparatorCharacter);
        
        return addresses;
    }
    
    public TableView getTableView(int id)
    {
        return this.tableViews[id];
    }
    public int getTableNumber(TableView t){
        return Arrays.asList(tableViews).indexOf(t)+1;
    }
   
}
