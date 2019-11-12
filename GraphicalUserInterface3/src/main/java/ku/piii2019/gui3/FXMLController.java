package ku.piii2019.gui3;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import ku.piii2019.bl3.*;

public class FXMLController implements Initializable {

    
    @FXML
    private Label label;
    @FXML
    private TableView<MediaItem> tableView1;
    @FXML
    private TableView<MediaItem> tableView2;
    @FXML
    private TableView<MediaItem> tableView3;

    String collectionRootAB = "test_folders" + File.separator
            + "original_filenames";
    String collectionRootA = collectionRootAB + File.separator
            + "collection-A";
    String collectionRootB = collectionRootAB + File.separator
            + "collection-B";

    @FXML
    private void handleButton1Action(ActionEvent event) {
        System.out.println("You clicked me!");
//        label.setText("Hello World!");
    }
    @FXML
    private void delete2(ActionEvent event) {
        System.out.println("You clicked me!");
        List<MediaItem> itemsToDelete = tableView2.getSelectionModel().getSelectedItems();
        tableView2.getItems().removeAll(itemsToDelete);
        
    }

    @FXML
    private void handleButton2Action(ActionEvent event) {
        System.out.println("You clicked me!");
//        label.setText("Hello World!");
    }
    
    
    @FXML
    private void handleKeyPressed2(KeyEvent event) {
        System.out.println("You clicked me!");
        if(event.isControlDown())
        {
            if(event.getCode()==KeyCode.X)
            {
                doCut();
                System.out.println("cut");
            }
            
        }
}

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<MediaItemColumnInfo> columns = MediaItemTableViewFactory.makeColumnInfoList();
        MediaItemTableViewFactory.makeTable(tableView1, columns);
        MediaItemTableViewFactory.makeTable(tableView2, columns);
        MediaItemTableViewFactory.makeTable(tableView3, columns);
        
        
        // you won't need any of this selection or drag code for the Nov 20th 
        // mock test, but I thought I'd keep it in for convenience
        addDragListener(tableView1);
        addDragListener(tableView2);
        addDragListener(tableView3);
        tableView1.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableView1.getSelectionModel().setCellSelectionEnabled(false);
        addSelectListener(tableView2);
        tableView1.setEditable(true);
    }

    @FXML
    private void openABIn2(ActionEvent event) {
        open(2, collectionRootAB);
    }
    @FXML
    private void insertTable1In2(ActionEvent event) {

        ObservableList<MediaItem> table1Data
                = tableView1.getItems();
        ObservableList<MediaItem> table2Data
                = tableView2.getItems();
        table2Data.addAll(0, table1Data);

    }
    @FXML
    private void insertTable2In1(ActionEvent event) {

        ObservableList<MediaItem> table1Data
                = tableView1.getItems();
        ObservableList<MediaItem> table2Data
                = tableView2.getItems();
        table1Data.addAll(0, table2Data);

    }

    @FXML
    private void swap(ActionEvent event) {

        
        Scene scene = tableView1.getScene();
        TableView<MediaItem> tableInFocus = null;
        if (scene.focusOwnerProperty().get() instanceof TableView) {
            tableInFocus = (TableView) scene.focusOwnerProperty().get();
            MediaItem m = tableInFocus.getSelectionModel().getSelectedItem();
            System.out.println("m " + m.getAlbum());
        }
    }

    @FXML
    private void showMissingItems(ActionEvent event) {
        Set<MediaItem> table1Data = new HashSet(tableView1.getItems());
        Set<MediaItem> table2Data = new HashSet(tableView2.getItems());

        DuplicateFinder d = new DuplicateFindFromID3();
        Set<MediaItem> missingItems = d.getMissingItems(table1Data, table2Data);

        ObservableList<MediaItem> dataForTableViewAndModel
                = FXCollections.observableArrayList(missingItems);
        tableView3.setItems(dataForTableViewAndModel);


    }

    @FXML
    private void openIn2(ActionEvent event) {
        open(2, null);
    }

    @FXML
    private void openAIn2(ActionEvent event) {
        open(2, collectionRootA);
    }

    @FXML
    private void openBIn2(ActionEvent event) {
        open(2, collectionRootB);
    }

    @FXML
    private void openABIn1(ActionEvent event) {
        open(1, collectionRootAB);
    }
    @FXML
    private void cut(ActionEvent event) {
        
        doCut();
 
    }

    @FXML
    private void openIn1(ActionEvent event) {
        open(1, null);
    }

    @FXML
    private void openAIn1(ActionEvent event) {
        open(1, collectionRootA);
    }

    @FXML
    private void openBIn1(ActionEvent event) {
        open(1, collectionRootB);
    }

    @FXML
    private void openABIn3(ActionEvent event) {
        open(3, collectionRootAB);
    }

    @FXML
    private void openIn3(ActionEvent event) {
        open(3, null);
    }

    @FXML
    private void openAIn3(ActionEvent event) {
        open(3, collectionRootA);
    }

    @FXML
    private void openBIn3(ActionEvent event) {
        open(3, collectionRootB);
    }

    private void open(int tableNumber, String collectionRoot) {
        if (collectionRoot == null) {
            DirectoryChooser dirChooser = new DirectoryChooser();
            dirChooser.setTitle("Open Media Folder for Table " + tableNumber);
            File path = dirChooser.showDialog(null).getAbsoluteFile();
            collectionRoot = path.toString();
        } else {
            String cwd = System.getProperty("user.dir");
            System.out.println(cwd);
            collectionRoot = Paths.get(cwd,
                    "..",
                    collectionRoot).toString();
        }
        TableView<MediaItem> referenceToEitherTable = null;
        if (tableNumber == 1) {
            referenceToEitherTable = tableView1;
        } else if (tableNumber == 2) {
            referenceToEitherTable = tableView2;
        } else if (tableNumber == 3) {
            referenceToEitherTable = tableView3;
        }

        addContents(referenceToEitherTable, collectionRoot);
    }

    private void addContents(TableView<MediaItem> referenceToEitherTable, 
                             String collectionRoot) {
        FileService fileService = new FileServiceImpl();
        Set<MediaItem> collectionA = fileService.getAllMediaItems(collectionRoot.toString());

        MediaInfoSource myInfoSource = new MediaInfoSourceFromID3();
        for (MediaItem item : collectionA) {
            try {
                myInfoSource.addMediaInfo(item);
            } catch (Exception e) {

            }
        }

        List<MediaItem> currentItems = referenceToEitherTable.getItems();
        collectionA.addAll(currentItems);

        ObservableList<MediaItem> dataForTableViewAndModel
                = FXCollections.observableArrayList(collectionA);
        referenceToEitherTable.setItems(dataForTableViewAndModel);
    }

    
    // I've kept this code in, t
    private static final DataFormat SERIALIZED_MIME_TYPE = new DataFormat("application/x-java-serialized-object");
    
        // you won't need any of this selection or drag code for the Nov 20th 
        // mock test, but I thought I'd keep it in for convenience
    private void addDragListener(TableView<MediaItem> tableView) {
        tableView.setRowFactory(tv -> {
            TableRow<MediaItem> row = new TableRow<>();

            row.setOnDragDetected(event -> {
                if (! row.isEmpty()) {
                    Integer index = row.getIndex();
                    Dragboard db = row.startDragAndDrop(TransferMode.MOVE);
                    db.setDragView(row.snapshot(null, null));
                    ClipboardContent cc = new ClipboardContent();
                    cc.put(SERIALIZED_MIME_TYPE, index);
                    db.setContent(cc);
                    event.consume();
                }
            });

            row.setOnDragOver(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasContent(SERIALIZED_MIME_TYPE)) {
                    if (row.getIndex() != ((Integer)db.getContent(SERIALIZED_MIME_TYPE)).intValue()) {
                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                        event.consume();
                    }
                }
            });

            row.setOnDragDropped(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasContent(SERIALIZED_MIME_TYPE)) {
                    int draggedIndex = (Integer) db.getContent(SERIALIZED_MIME_TYPE);
                    MediaItem draggedMediaItem = tableView.getItems().remove(draggedIndex);
                    int dropIndex ; 
                    if (row.isEmpty()) {
                        dropIndex = tableView.getItems().size() ;
                    } else {
                        dropIndex = row.getIndex();
                    }
                    tableView.getItems().add(dropIndex, draggedMediaItem);
                    event.setDropCompleted(true);
                    tableView.getSelectionModel().select(dropIndex);
                    event.consume();
                }
            });
            return row ;
        });
    }
        // you won't need any of this selection or drag code for the Nov 20th 
        // mock test, but I thought I'd keep it in for convenience

    private void addSelectListener(TableView<MediaItem> tableView) {
       
        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, 
                                Object oldValue, 
                                Object newValue) {
                if(tableView.getSelectionModel().getSelectedItem() != null) {    
            
                    MediaItem m =  tableView.getSelectionModel().getSelectedItem();
                    ObservableList selectedCells = tableView.getSelectionModel().getSelectedCells();
                    TablePosition tablePosition = (TablePosition) selectedCells.get(0);
                    Object val = tablePosition.getTableColumn().getCellData(newValue);
                    System.out.println("Selected Value" + val);
                    System.out.println("Selected item " + m.getAlbum());
                }
            }
        });
    }

    private TableView getTableInFocus() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void doCut() {
       TableView tableInFocus = getTableInFocus();
        // do my cut thing here
    }
}
