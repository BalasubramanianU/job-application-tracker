package application;

import java.time.LocalDate;
import java.util.Set;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleGroup;

public class ViewAppController {
    @FXML
    private ListView<String> listView;

    @FXML
    private Pane jobDetailArea;

    @FXML
    private TextField positionName;

    @FXML
    private TextField companyName;

    @FXML
    private TextArea jobDescription;

    @FXML
    private RadioButton applied;

    @FXML
    private RadioButton interviewing;

    @FXML
    private RadioButton accepted;

    @FXML
    private RadioButton rejected;

    @FXML
    private DatePicker dateApplied;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button dontSaveButton;

    @FXML
    private Button saveButton;

    ToggleGroup toggleGroup = new ToggleGroup();

    JobAppManager jobAppManager = new JobAppManager();

    String currentSelectedItem;

    @FXML
    public void initialize() {
        jobDetailArea.setVisible(false);

        Set<String> keys = jobAppManager.getAllJobs().keySet();
        for (String key : keys) {
            listView.getItems().add(key);
        }

        applied.setToggleGroup(toggleGroup);
        interviewing.setToggleGroup(toggleGroup);
        rejected.setToggleGroup(toggleGroup);
        accepted.setToggleGroup(toggleGroup);

        listView.setCellFactory(param -> new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    JobApp jobDetails = jobAppManager.getAllJobs().get(item);

                    if (jobDetails != null) {
                        setText(jobDetails.getPosition() + ", " + jobDetails.getCompany());
                    }
                }
            }
        });

        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                currentSelectedItem = newValue;
                showJobDetails(newValue);
            }
        });
    }

    private void showJobDetails(String selectedItem) {
        saveButton.setVisible(false);
        dontSaveButton.setVisible(false);
        dateApplied.setDisable(true);
        positionName.setEditable(false);
        companyName.setEditable(false);
        jobDescription.setEditable(false);
        jobDetailArea.setVisible(true);
        JobApp jobDetail = getJobDetails(selectedItem);
        positionName.setText(jobDetail.getPosition());
        companyName.setText(jobDetail.getCompany());
        jobDescription.setText(jobDetail.getJobdesc());
        String[] dateComponents = jobDetail.getDateapplied().toString().split("-");
        dateApplied.setValue(LocalDate.of(Integer.parseInt(dateComponents[0]), Integer.parseInt(dateComponents[1]), Integer.parseInt(dateComponents[2])));
        switch (jobDetail.getStatus()) {
            case "Applied":
                applied.setSelected(true);
                break;
            case "Interviewing":
                interviewing.setSelected(true);
                break;
            case "Rejected":
                rejected.setSelected(true);
                break;
            case "Accepted":
                accepted.setSelected(true);
                break;
        }
        toggleGroup.getToggles().forEach(toggle -> {
            if (!((RadioButton) toggle).isSelected()) {
                ((RadioButton) toggle).setDisable(true);
            }
        });
    }

    private JobApp getJobDetails(String item) {
        return jobAppManager.getAllJobs().get(item);
    }

    @FXML
    private void onEdit() {
        positionName.setEditable(true);
        companyName.setEditable(true);
        jobDescription.setEditable(true);
        saveButton.setVisible(true);
        dontSaveButton.setVisible(true);
        toggleGroup.getToggles().forEach(toggle -> {
            ((RadioButton) toggle).setDisable(false);
        });
        dateApplied.setDisable(false);
    }

    @FXML
    private void onDelete() {
        jobAppManager.deleteJob(currentSelectedItem);
        listView.getItems().remove(currentSelectedItem);
        listView.getSelectionModel().clearSelection();
        jobDetailArea.setVisible(false);
    }

    @FXML
    private void onSave() {
        JobApp jobApp = new JobApp();
        jobApp.setPosition(positionName.getText());
        jobApp.setCompany(companyName.getText());
        jobApp.setDateapplied(java.sql.Date.valueOf(dateApplied.getValue()));
        jobApp.setJobdesc(jobDescription.getText());
        toggleGroup.getToggles().forEach(toggle -> {
            if (((RadioButton) toggle).isSelected()) {
                jobApp.setStatus(((RadioButton) toggle).getText().toString());
            }
        });

        jobAppManager.addJob(currentSelectedItem, jobApp);
        showJobDetails(currentSelectedItem);
    }

    @FXML
    private void onDontSave() {
        showJobDetails(currentSelectedItem);
    }
}
