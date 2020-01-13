package ku.piii2019.gui3;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.collections.ModifiableObservableListBase;
import javafx.scene.control.TableView;
import javafx.stage.DirectoryChooser;
import ku.piii2019.bl3.*;
import java.lang.UnsupportedOperationException;
import javafx.beans.property.SimpleListProperty;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;
import ku.piii2019.bl3.FileService;
import ku.piii2019.bl3.FileServiceImpl;

public class FXMLController implements Initializable {

    @FXML
    private Label label;
    @FXML
    private TableView<MediaItem> tableView1;
    @FXML
    private TableView<MediaItem> tableView2;
    @FXML
    private TableView<MediaItem> tableView3;

    private ListSelectionEventController selectionController;
    private DirectionTeller getDirections;
    private DragEventController dragEventController;
    private ObservableList<MediaItem> clipboard;

    String collectionRootAB = "test_folders" + File.separator
            + "original_filenames";
    String collectionRootA = collectionRootAB + File.separator
            + "collection-A";
    String collectionRootB = collectionRootAB + File.separator
            + "collection-B";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<MediaItemColumnInfo> columns = MediaItemTableViewFactory.makeColumnInfoList();
        MediaItemTableViewFactory.makeTable(tableView1, columns);
        MediaItemTableViewFactory.makeTable(tableView2, columns);
        MediaItemTableViewFactory.makeTable(tableView3, columns);

        this.getDirections = new DirectionTeller(new TableView[]{tableView1, tableView2, tableView3});
        this.selectionController = new ListSelectionEventController(new TableView[]{tableView1, tableView2, tableView3});
        this.dragEventController = new DragEventController(new TableView[]{tableView1, tableView2, tableView3});
    }

    @FXML
    private void toClipboard(ActionEvent e) {
        TableView source = getDirections.urinaryAction(e);
        this.clipboard = FXCollections.observableArrayList(source.getSelectionModel().getSelectedItems());

    }

    @FXML
    private void cutToClipboard(ActionEvent e) {
        TableView source = getDirections.urinaryAction(e);
        this.toClipboard(e);
        source.getItems().removeAll(FXCollections.observableArrayList(this.clipboard));
    }

    @FXML
    private void fromClipboardToIndex(ActionEvent e) {
        TableView source = getDirections.urinaryAction(e);

        ObservableList<Integer> selectedIndices = source.getSelectionModel().getSelectedIndices();

        Integer highestIndex = null;
        int noSelected = selectedIndices.size();

        if (selectedIndices.size() > 1) {
            highestIndex = selectedIndices.stream().max(Integer::compareTo).get();
        } else if (noSelected == 1) {
            highestIndex = selectedIndices.get(0);
        }

        if (highestIndex != null) {
            ModifiableObservableListBase<MediaItem> currentList = (ModifiableObservableListBase<MediaItem>) FXCollections.observableArrayList(source.getItems());

            currentList.addAll(highestIndex + 1, this.clipboard);
            source.setItems(currentList);
        }
    }

    @FXML
    private void fromClipboard(ActionEvent e) {
        if (this.clipboard != null) {
            TableView destination = getDirections.urinaryAction(e);

            ObservableList<MediaItem> currentElements = FXCollections.observableArrayList(destination.getItems());
            ObservableList<MediaItem> newElements = FXCollections.observableArrayList(this.clipboard);
            newElements.addAll(currentElements);
            destination.setItems(newElements);

        }

    }

    @FXML
    private void insertOneToAnother(ActionEvent e) {

        TableView[] addresses = getDirections.binaryAction(e);
        insert(addresses[0], addresses[1]);

    }

    @FXML
    private void showMissing(ActionEvent e) {

        TableView[] itenerary = getDirections.binaryAction(e);

        TableView<MediaItem> myList = itenerary[1];
        TableView<MediaItem> yourList = itenerary[0];

        List<MediaItem> myRealList = myList.getItems();
        List<MediaItem> yourRealList = yourList.getItems();

        DuplicateFinder df = new DuplicateFindFromID3();
        Set<MediaItem> missingElements = df.getMissingItems(new HashSet(myRealList), new HashSet(yourRealList));

        ObservableList<MediaItem> result = FXCollections.observableArrayList(missingElements);

        yourList.setItems(result);

    }

    @FXML
    private void openIn(ActionEvent event) {
        TableView referenceToTheTable = getDirections.urinaryAction(event);
        open(referenceToTheTable, null);
    }

    @FXML
    private void openA(ActionEvent event) {
        TableView referenceToTheTable = getDirections.urinaryAction(event);

        open(referenceToTheTable, collectionRootA);
    }

    @FXML
    private void openB(ActionEvent event) {
        TableView referenceToTheTable = getDirections.urinaryAction(event);
        open(referenceToTheTable, collectionRootB);
    }

    @FXML
    private void openAB(ActionEvent event) {
        TableView referenceToTheTable = getDirections.urinaryAction(event);
        open(referenceToTheTable, collectionRootAB);
    }

    private void insert(TableView src, TableView dst) {
        List<MediaItem> itemsToComeFirst = src.getItems();
        List<MediaItem> itemsToComeLast = dst.getItems();

        ObservableList<MediaItem> newList
                = FXCollections.observableArrayList(itemsToComeFirst);
        newList.addAll(itemsToComeLast);
        dst.setItems(newList);
    }

    @FXML
    private void swap(ActionEvent event) {

        ObservableList<MediaItem> table1Data
                = tableView1.getItems();
        ObservableList<MediaItem> table2Data
                = tableView2.getItems();
        tableView1.setItems(table2Data);
        tableView2.setItems(table1Data);

    }

    private void open(TableView table, String collectionRoot) {
        if (collectionRoot == null) {
            DirectoryChooser dirChooser = new DirectoryChooser();
            dirChooser.setTitle("Open Media Folder for Table " + getDirections.getTableNumber(table));
            File path = dirChooser.showDialog(null).getAbsoluteFile();
            collectionRoot = path.toString();
        } else {
            String cwd = System.getProperty("user.dir");
            System.out.println(cwd);
            collectionRoot = Paths.get(cwd,
                    "..",
                    collectionRoot).toString();
        }
        TableView<MediaItem> referenceToEitherTable = table;

        addContents(referenceToEitherTable, collectionRoot);
    }

    private void addContents(TableView<MediaItem> referenceToEitherTable, String collectionRoot) {
        FileService fileService = FileServiceImpl.getInstance();
        Set<MediaItem> collectionA = fileService.getAllMediaItems(collectionRoot.toString());

        MediaInfoSource myInfoSource = MediaInfoSourceFromID3.getInstance();
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

    @FXML
    private void clearTable(ActionEvent event) {
        TableView table = getDirections.urinaryAction(event);
        //table.getItems().removeAll();
        throw new UnsupportedOperationException("This has not been implemented yet!");

    }

    @FXML
    private void saveAsM3U(ActionEvent event) {
        TableView table = getDirections.urinaryAction(event);
        ObservableList<MediaItem> tableData
                = table.getItems();
        Set<MediaItem> tableDataSet = new HashSet(tableData);
        if (tableDataSet.size() == 0) {
            String[] args = new String[]{"Table 3 is empty", "Table 3 is empty", "Please add items to table 3 before trying to save an M3U List"};
            ErrorPopups.alertUser(args);
            return;
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new ExtensionFilter("M3U files", "*.m3u"));
        fileChooser.setTitle("Select save location and filename");

        Window stage = MainApp.getPrimaryStage();

        File file = fileChooser.showSaveDialog(stage);

        try {
            file.delete();
            file.createNewFile();

        } catch (IOException ioex) {
            CustomLogging.logIt(ioex);
        }

        System.out.println(file.getPath());
        String destinateionFolder = file.getParent();
        String fileName = file.getName();
        System.out.println(destinateionFolder);
        System.out.println(fileName);
        FileServiceImpl.getInstance().saveM3UFile(tableDataSet, fileName, destinateionFolder);

    }

}
