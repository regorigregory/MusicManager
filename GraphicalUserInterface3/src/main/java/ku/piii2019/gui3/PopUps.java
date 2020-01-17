/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.gui3;

import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

/**
 *
 * @author regor
 */
public class PopUps {

    public static void alertUser(String[] args) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setHeaderText(args[0]);
        alert.setTitle(args[1]);
        alert.setContentText(args[2]);
        alert.showAndWait();

    }

    public static SearchParams showSearchDialog() {
        Dialog dialog = new Dialog();
        dialog.setTitle("Dialog Test");
        dialog.setHeaderText("Please specify a searchphrase to insert matched elements into table 3.");

        DialogPane dp = dialog.getDialogPane();
        TextField textField = new TextField("Search phrase");
        String[] options = new String[]{"artist", "genre"};
        ComboBox<String> dropDown = new ComboBox(FXCollections.observableArrayList(options));
        dropDown.getSelectionModel().selectFirst();
        
        

        ButtonType buttonOK = new ButtonType("Search and insert", ButtonBar.ButtonData.YES); 
        dp.getButtonTypes().add(buttonOK);
        ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.NO);
        dp.getButtonTypes().add(cancel);
        
        dp.setContent(new VBox(8, textField, dropDown));
        Callback<ButtonType, SearchParams> resultConverter = (ButtonType bt) -> {
            System.out.println(bt.getButtonData());
            if (bt.getButtonData() == ButtonBar.ButtonData.YES) {
                return new SearchParams(textField.getText(),
                        dropDown.getValue());
            }
            return null;
        };
        dialog.setResultConverter(resultConverter);
        
        
        Optional<SearchParams> results = dialog.showAndWait();
        if(results.isPresent()){
            return results.get();
        }
      return null;
    }

    public static class SearchParams {

        String artistOrGenre;
        String searchPhrase;

        SearchParams(String ag, String needle) {
            artistOrGenre = ag;
            searchPhrase = needle;
        }
    }
}
