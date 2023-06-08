package app.taskplanner.viewmodel.noteviewmodel;

import app.taskplanner.model.DataModel;
import app.taskplanner.model.notes.Note;
import app.taskplanner.model.notes.SimpleNoteBody;
import app.taskplanner.model.notes.SimpleTask;
import app.taskplanner.model.notes.Task;
import app.taskplanner.service.ChangeModelService;
import app.taskplanner.service.NotificationService;
import app.taskplanner.viewmodel.*;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;


public class NoteViewModel implements ViewModel {

    private DataModel dataModel;
    private SingleNoteHandler singleNoteHandler;
    private ChangeModelService changeModelService;
    private NotificationService notificationService;

    private Note currentNote;

    private final StringProperty noteContent = new SimpleStringProperty();
    private final StringProperty noteTitle = new SimpleStringProperty();


    private final ObservableList<SimpleTask> tasks = FXCollections.observableArrayList();

    public ObservableList<SimpleTask> getTasks() {
        return tasks;
    }

    public Property<String> noteContentProperty() {
        return noteContent;
    }
    public Property<String> noteTitleProperty() {
        return noteTitle;
    }


    public void setupNote(Note currentNote) {
        this.currentNote = currentNote;
        noteContent.setValue(currentNote.getNoteBody().getContent());
        noteTitle.setValue(currentNote.getMetadata().getTitle());
        List<SimpleTask> loadTasks = currentNote.getNoteBody().getTasks();
        for (SimpleTask st : loadTasks){
            SimpleTask simpleTask = st;
            tasks.add(simpleTask);
        }
        //tasks.addAll(loadTasks);
        //tasks.add(new SimpleTask("indiialla",true));
    }

    public void save() {
        currentNote.getNoteBody().setContent(noteContent.get());
        currentNote.getMetadata().setTitle(noteTitle.get());
        //currentNote.getNoteBody().setTasks(tasks);
        //currentNote.getNoteBody().getTasks().add(new SimpleTask("taszczek", true));

        changeModelService.saveNote(currentNote);
        notificationService.notifyViewModels(getKey());
    }

    public void close() {
        notificationService.removeViewModel(this);
        singleNoteHandler.closeNote(currentNote);
    }

    public int getKey() {
        return currentNote.getMetadata().getKey();
    }

    public void refresh() {
        int key = currentNote.getMetadata().getKey();
        currentNote.setMetadata(dataModel.getMetadata(key));
        try {
            currentNote.setNoteBody(dataModel.getBody(key));
        } catch (IOException e) {
            System.out.println("Note refreshing failed");
        }
        noteTitle.setValue(currentNote.getMetadata().getTitle());
        noteContent.setValue(currentNote.getNoteBody().getContent());
    }
    public void init(SingleNoteHandler singleNoteHandler, DataModel dataModel, ChangeModelService cms, NotificationService ns) {
        this.dataModel = dataModel;
        this.singleNoteHandler = singleNoteHandler;
        this.changeModelService = cms;
        this.notificationService = ns;
    }
}