package app.taskplanner.view.listview;
import app.taskplanner.model.notes.NoteMetadata;
import app.taskplanner.view.ViewFunctions;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


public class NoteListCell extends ListCell<NoteMetadata> {
    private final VBox contentBox;
    private final Label titleLabel;
    private final Label dateLabel;

    public NoteListCell() {
        contentBox = new VBox();
        titleLabel = new Label();
        dateLabel = new Label();

        contentBox.getChildren().addAll(titleLabel, dateLabel);
        contentBox.setAlignment(Pos.CENTER_LEFT);
        contentBox.setSpacing(5);
    }

    @Override
    protected void updateItem(NoteMetadata item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setGraphic(null);
        } else {
            titleLabel.setText(item.getTitle());
            dateLabel.setText(ViewFunctions.daysDisplay(item.getDate()));

            setGraphic(contentBox);

            setStyle("-fx-background-color: " + ViewFunctions.color(ViewFunctions.days(item.getDate())));
        }
    }


}





