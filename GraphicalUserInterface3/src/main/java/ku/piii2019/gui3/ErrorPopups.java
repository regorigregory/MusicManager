/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.gui3;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 *
 * @author regor
 */
public class ErrorPopups {
    public static void alertUser(String[] args){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setHeaderText(args[0]);
        alert.setTitle(args[1]);
        alert.setContentText(args[2]);
        alert.showAndWait();
        
    }
}
