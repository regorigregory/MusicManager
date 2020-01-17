package ku.piii2019.gui3;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.LinkedHashSet;
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
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;
import ku.piii2019.bl3.FileService;
import ku.piii2019.gui3.PopUps.SearchParams;

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

        tableView1.setEditable(true);
        tableView2.setEditable(true);
        tableView3.setEditable(true);

        this.getDirections = new DirectionTeller(new TableView[]{tableView1, tableView2, tableView3});
        this.selectionController = new ListSelectionEventController(new TableView[]{tableView1, tableView2, tableView3});
        this.dragEventController = new DragEventController(new TableView[]{tableView1, tableView2, tableView3});
    }

    @FXML
    private void openInIntoSelected() {
        TableView referenceToTheTable = getTableInFocus();
        if (referenceToTheTable == null) {
            referenceToTheTable = tableView1;
        }
        open(referenceToTheTable, null);

    }

    @FXML
    private void openAIntoSelected() {
        TableView referenceToTheTable = getTableInFocus();
        if (referenceToTheTable == null) {
            referenceToTheTable = tableView1;
        }
        open(referenceToTheTable, collectionRootA);
    }

    @FXML
    private void openBIntoSelected() {
        TableView referenceToTheTable = getTableInFocus();
        if (referenceToTheTable == null) {
            referenceToTheTable = tableView1;
        }
        open(referenceToTheTable, collectionRootB);
    }

    @FXML
    public void exportM3USelectedTable() {
        TableView referenceToTheTable = getTableInFocus();
        if (referenceToTheTable == null) {
            referenceToTheTable = tableView1;
        }

        ObservableList<MediaItem> tableData
                = referenceToTheTable.getItems();
        Set<MediaItem> tableDataSet = new LinkedHashSet(tableData);
        if (tableDataSet.size() == 0) {
            String[] args = new String[]{"Table " + referenceToTheTable.getId() + " is empty", "Table " + referenceToTheTable.getId() + " is empty", "Please add items to table " + referenceToTheTable.getId() + " before trying to save an M3U List"};
            PopUps.alertUser(args);
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

        String destinationFolder = file.getParent();
        String fileName = file.getName();

        MediaFileService.getInstance().saveM3UFile(tableDataSet, fileName, destinationFolder, false);

    }

    @FXML
    private TableView toClipboard() {
        TableView source = getTableInFocus();
        if (source == null) {
            String[] args = new String[]{"No table in focus.", "Please select a table.", "Before cutting from a table, please, select one."};
            PopUps.alertUser(args);
            return source;
        }
        this.clipboard = FXCollections.observableArrayList(source.getSelectionModel().getSelectedItems());
        return source;
    }

    @FXML
    private void cutToClipboard(ActionEvent e) {
        TableView source = this.toClipboard();
        if (source == null) {
            return;
        }
        source.getItems().removeAll(FXCollections.observableArrayList(this.clipboard));
    }

    @FXML
    private void pasteFromClipboardToIndex() {
        TableView source = getTableInFocus();
        if (source == null) {
            String[] args = new String[]{"No table in focus.", "Please select a table.", "Before cutting from a table, please, select one."};
            PopUps.alertUser(args);
            return;
        }

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
        } else {
            source.setItems(this.clipboard);
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
        Set<MediaItem> missingElements = df.getMissingItems(new LinkedHashSet(myRealList), new LinkedHashSet(yourRealList));

        ObservableList<MediaItem> result = FXCollections.observableArrayList(missingElements);

        yourList.setItems(result);

    }
    @FXML
    private void searchByGenreOrArtist(){
        SearchParams results = PopUps.showSearchDialog();
        if(results!=null){
            System.out.println(results.getSearchPhrase());
            System.out.println(results.getArtistOrGenre());

            ObservableList<MediaItem> elements = tableView1.getItems();
            
            elements.addAll(tableView2.getItems());
            System.out.println(elements.size());
            Set<MediaItem> convertedElements = new HashSet(elements);
            System.out.println(convertedElements.size());

            Set<MediaItem> found =new HashSet<>();
            
            if(results.getArtistOrGenre().equals("artist")){
                System.out.println("Search by artist processed");
                found = new SimpleSearch().findByArtists(new String[]{results.getSearchPhrase()}, convertedElements);
            } else if(results.getArtistOrGenre().equals("genre")){
                   System.out.println("Search by genre processed");

                found = new SimpleSearch().findByGenres(new String[]{results.getSearchPhrase()}, convertedElements);
            }
            System.out.println("Found elements"+found.size());
            tableView3.getItems().addAll(FXCollections.observableArrayList(found));
        }

    }
    @FXML
    private void saveAsM3U(ActionEvent event) {

        TableView table = getDirections.urinaryAction(event);

        ObservableList<MediaItem> tableData
                = table.getItems();
        Set<MediaItem> tableDataSet = new LinkedHashSet(tableData);
        if (tableDataSet.size() == 0) {
            String[] args = new String[]{"Table " + table.getId() + " is empty", "Table " + table.getId() + " is empty", "Please add items to table " + table.getId() + " before trying to save an M3U List"};
            PopUps.alertUser(args);
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

        String destinationFolder = file.getParent();
        String fileName = file.getName();

        MediaFileService.getInstance().saveM3UFile(tableDataSet, fileName, destinationFolder, false);

    }

    @FXML
    private void saveAsM3UandCopyWithRelativePaths(ActionEvent event) {

        TableView source = getTableInFocus();
        if (source == null) {
            String[] args = new String[]{"No table in focus.", "Please select a table.", "Before cutting from a table, please, select one."};
            PopUps.alertUser(args);
            return;
        }
        ObservableList<MediaItem> tableData
                = source.getItems();
        Set<MediaItem> tableDataSet = new LinkedHashSet(tableData);
        if (tableDataSet.size() == 0) {
            String[] args = new String[]{"Table 3 is empty", "Table 3 is empty", "Please add items to table 3 before trying to save an M3U List"};
            PopUps.alertUser(args);
            return;
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new ExtensionFilter("M3U files", "*.m3u"));
        fileChooser.setTitle("Select save directory and m3u filename.");

        Window stage = MainApp.getPrimaryStage();

        File file = fileChooser.showSaveDialog(stage);

        try {
            file.delete();
            file.createNewFile();

        } catch (IOException ioex) {
            CustomLogging.logIt(ioex);
        }

        String destinateionFolder = file.getParent();
        String fileName = file.getName();
        MediaFileService.getInstance().saveM3UFile(tableDataSet, fileName, destinateionFolder, true);

        MediaFileService.getInstance().copyMediaFilesWithoutDirectoryStructure(tableDataSet, destinateionFolder, null);
    }

    @FXML
    private void refileSelectedTracks() {
        ObservableList<MediaItem> tableSelection1 = FXCollections.observableArrayList(tableView1.getSelectionModel().getSelectedItems());
        ObservableList<MediaItem> tableSelection2 = FXCollections.observableArrayList(tableView2.getSelectionModel().getSelectedItems());
        ObservableList<MediaItem> tableSelection3 = FXCollections.observableArrayList(tableView3.getSelectionModel().getSelectedItems());
        tableSelection1.addAll(tableSelection2);
        tableSelection2.addAll(tableSelection3);

        DirectoryChooser fileChooser = new DirectoryChooser();
        fileChooser.setTitle("Select save directory and m3u filename.");

        Window stage = MainApp.getPrimaryStage();

        File directory = fileChooser.showDialog(stage);
        for (MediaItem m : tableSelection1) {
            MediaFileService.getInstance().refileAndCopyMediaItem(directory.getAbsolutePath(), m);
        }

    }
    @FXML
    private void goodbye(){
        System.exit(0);
    }
    @FXML 
    private void showAbout(){
        String[] args = new String[]{"Programming III assignment.", "Programming III assignment.", "Created by Gergo Endresz (k1721863). "};
        PopUps.alertUser(args);
    }
    public TableView getTableInFocus() {
        TableView focusedTable = null;
        if (MainApp.getPrimaryStage().getScene().focusOwnerProperty().get() instanceof TableView) {
            focusedTable = (TableView) MainApp.getPrimaryStage().getScene().focusOwnerProperty().get();
        }
        return focusedTable;
    }
    
    @FXML
    private void openIn(ActionEvent event) {
        TableView referenceToTheTable = getDirections.urinaryAction(event);
        open(referenceToTheTable, null);
        System.out.println(getTableInFocus().toString());
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

    private void open(TableView table, String collectionRoot) {
        if (collectionRoot == null) {
            DirectoryChooser dirChooser = new DirectoryChooser();
            dirChooser.setTitle("Open Media Folder for Table " + getDirections.getTableNumber(table));
            File path = null;
            try {
                path = dirChooser.showDialog(null).getAbsoluteFile();
            } catch (Exception ex) {
                //CustomLogging.getInstance().logIt();
            }
            if (path == null) {
                return;
            }
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
        MediaFileService fileService = MediaFileService.getInstance();
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

}
