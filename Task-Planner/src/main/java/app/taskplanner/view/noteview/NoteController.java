package app.taskplanner.view.noteview;

import app.taskplanner.model.notes.SimpleTask;
import app.taskplanner.view.ViewController;
import app.taskplanner.view.ViewFunctions;
import app.taskplanner.viewmodel.ViewModel;
import app.taskplanner.viewmodel.noteviewmodel.NoteViewModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static javafx.collections.FXCollections.observableArrayList;

public class NoteController implements ViewController {

    private NoteViewModel noteVM;

    boolean opened = false;

    @FXML
    public MenuBar menuBar;

    @FXML
    private TextArea noteContent;

    @FXML
    private TextField noteTitle;

    @FXML
    public Button save;

    @FXML
    private ListView<SimpleTask> taskList;

    @FXML
    public TextField taskName;
    @FXML

    private SplitPane taskPane;

    @FXML
    private DatePicker datePicker;


    private ObservableList<SimpleTask> taskItems;

    @Override
    public void init(ViewModel noteVM) {
        this.noteVM = (NoteViewModel) noteVM;
        noteContent.textProperty().bindBidirectional(((NoteViewModel) noteVM).noteContentProperty());
        noteTitle.textProperty().bindBidirectional(((NoteViewModel) noteVM).noteTitleProperty());
        datePicker.valueProperty().bindBidirectional(((NoteViewModel) noteVM).noteDateProperty());
        taskItems = ((NoteViewModel) noteVM).getTasks();
        taskList.setItems(taskItems);
        taskList.setCellFactory(param -> new TaskCell());
    }

    @FXML
    void listOtherNotes(ActionEvent event) {

    }

    @FXML
    void setOnDetected(MouseEvent event) {
        Dragboard dragboard = taskList.startDragAndDrop(TransferMode.MOVE);
        ClipboardContent content = new ClipboardContent();
        int selectedId = taskList.getSelectionModel().getSelectedIndex();
//        content.put(task, taskList.getItems().get(selectedId).getTaskTitle());
        dragboard.setContent(content);
        event.consume();
    }

    @FXML
    void setOnOver(DragEvent event) {
//        if (event.getGestureSource() != taskList && event.getDragboard().hasContent(task)) {
//            event.acceptTransferModes(TransferMode.MOVE);
//        }
//        event.consume();
    }

    @FXML
    void setOnDropped(DragEvent event) {
//        Dragboard dragboard = event.getDragboard();
//        boolean done = false;
//        if (dragboard.hasContent(task)) {
//            int selectedId = taskList.getSelectionModel().getSelectedIndex();
//            NoteTask noteTask = (NoteTask) dragboard.getContent(task);
//            taskList.getItems().remove(noteTask);
//            noteTask.setStatus(true);
//            taskList.getItems().remove(noteTask);
//            noteTask.setStatus(false);
//            taskList.getItems().add(selectedId, noteTask);
//            done = true;
//        }
//        event.setDropCompleted(done);
//        event.consume();
    }

    @FXML
    void openTaskPage(ActionEvent event) {
        if (!opened) {
            taskPane.setMinWidth(160);
            taskPane.setPrefWidth(160);
            //taskList.setCellFactory(CheckBoxListCell.forListView(NoteTask));
            noteVM.resizeX(-160);
            opened = true;
        } else {
            noteVM.resizeX(+160);
            opened = false;
        }
    }

    @FXML
    void saveNote() {
        setDateColor(datePicker.getValue());
        noteVM.save();
    }

    private void setDateColor(LocalDate date) {
        this.menuBar.setStyle("-fx-background-color: " + ViewFunctions.color(ViewFunctions.days(date)));
    }

    @FXML
    void closeNote(ActionEvent event) {
        noteVM.close();
    }

    @FXML
    void saveAndClose(ActionEvent event) {
        saveNote();
        noteVM.close();
    }

    @FXML
    void addTask(ActionEvent event) {
        String newTaskName = taskName.getText();
        SimpleTask task = new SimpleTask(newTaskName, false);
        taskItems.add(task);
        taskName.clear();
    }

}
