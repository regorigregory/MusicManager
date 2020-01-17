/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.gui3;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import ku.piii2019.bl3.MediaItem;
import javafx.scene.control.TableView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.input.DataFormat;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableRow;
import javafx.scene.input.DragEvent;
import javafx.collections.ObservableList;

/**
 *
 * @author Regory Gregory a.k.a. Gergo Endresz
 */

public class DragEventController {

    TableView[] tableViews;

    public static DataFormat DATA_TYPE = new DataFormat(MediaItem.class.getName());

    public DragEventController(TableView[] tv) {
        this.tableViews = tv;
        this.initialize();
    }

    public void initialize() {

        for (TableView tv : tableViews) {
            MultipleSelectionModel lsm = tv.getSelectionModel();
            lsm.setSelectionMode(SelectionMode.MULTIPLE);
            tv.setOnDragDetected(event -> {
                Object[] elementsToDrag = FXCollections.observableArrayList(tv.getSelectionModel().getSelectedIndices()).toArray();
                Dragboard db = tv.startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();
                content.put(DATA_TYPE, elementsToDrag);
                db.setContent(content);
                event.consume();
            }
            );
            
            tv.setOnDragOver(event -> {
                if (event.getDragboard().hasContent(DATA_TYPE)) {
                    Object[] contents = (Object[]) event.getDragboard().getContent(DATA_TYPE);
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                event.consume();
            });

            tv.setRowFactory(notUsedReference -> {
                TableRow row = new TableRow();

                row.setOnDragDropped(event -> {
                    TableView originalParent = (TableView) event.getGestureSource();
                    dragDropEventBody(originalParent, tv, event, row);

                });
                return row;
            });
            //if the table is empty, there is no row, therefore to the best of my knowledge, it is necessary.
            tv.setOnDragDropped(event -> {
                TableView source = (TableView) event.getGestureSource();
                if (tv.getItems().size() == 0) {
                    dragDropEventBody(source, tv, event, null);
                }
            });
        }
    }

    public void dragDropEventBody(TableView source, TableView target, DragEvent event, TableRow passedTableRow) {
        Dragboard db = event.getDragboard();
        boolean completed = false;
        ObservableList<MediaItem> elements = source.getItems();

       
        try {
            if (db.hasContent(DATA_TYPE)) {

                //tv.getItems().add();
                Object[] contents = (Object[]) db.getContent(DATA_TYPE);

                List<Integer> selectedIndices = Arrays.asList(contents).stream().map(x -> Integer.parseInt(x.toString())).collect(Collectors.toList());
                
                List<MediaItem> removedElements = selectedIndices.stream().map(i -> elements.get(i.intValue())).collect(Collectors.toList());
                elements.removeAll(removedElements);
                
                int maxIndex = selectedIndices.stream().min(Integer::compareTo).get();
                int passedIndex = (passedTableRow != null) ? passedTableRow.getIndex() : 0; 
                int index = passedIndex;
                if(maxIndex<passedIndex){
                    index = index-removedElements.size();
                    if(index<0){
                        index=0;
                    }
                }
                
                int targetRowCount = target.getItems().size();
                
                //This has to be done as the getGestureSource() returns the last table row thew user hovered above
                //Therefore it inserts it not to the
                //int smallestIndex = selectedIndices.min(Integer::compareTo).get();
                
              
                
                index = (index+1 > targetRowCount) ? targetRowCount : index+1;
                
                target.getItems().addAll(index, removedElements);

                target.getSelectionModel().clearSelection();

                removedElements.stream().forEach(el -> target.getSelectionModel().select(el));
                //selectedIndices.forEach(x->sm.select(x.intValue()));
                completed = true;
            }
            
            event.setDropCompleted(completed);
            event.consume();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}