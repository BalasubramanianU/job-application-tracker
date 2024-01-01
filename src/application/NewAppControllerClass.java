package application;

import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class NewAppControllerClass {

    @FXML
    private Button saveBtn;

    @FXML
    private TextField posField;

    @FXML
    private TextField compField;

    @FXML
    private DatePicker dateField;

    @FXML
    private RadioButton appRadio;

    @FXML
    private RadioButton interRadio;

    @FXML
    private RadioButton accRadio;

    @FXML
    private RadioButton rejctRadio;

    @FXML
    private TextArea descField;

    private JobAppManager jobAppManager;
    private String currentJobKey;

    public NewAppControllerClass(JobAppManager jobAppManager) {
        this.jobAppManager = jobAppManager;
    }

    @FXML
    private void handleAddApplication() {
        JobApp jobApp = createJobAppFromFields();
        String key = generateUniqueKey();
        jobAppManager.addJob(key, jobApp);
        clearFields();
        displayAllJobs();
    }

    private JobApp createJobAppFromFields() {
        JobApp jobApp = new JobApp();
        jobApp.setPosition(posField.getText());
        jobApp.setCompany(compField.getText());
        jobApp.setDateapplied(java.sql.Date.valueOf(dateField.getValue()));
        jobApp.setJobdesc(descField.getText());

        if (appRadio.isSelected()) {
            jobApp.setStatus("Applied");
        } else if (interRadio.isSelected()) {
            jobApp.setStatus("Interviewing");
        } else if (accRadio.isSelected()) {
            jobApp.setStatus("Accepted");
        } else if (rejctRadio.isSelected()) {
            jobApp.setStatus("Rejected");
        }

        return jobApp;
    }

    private String generateUniqueKey() {
        return "job" + (jobAppManager.getAllJobs().size() + 1);
    }

    private void clearFields() {
        posField.clear();
        compField.clear();
        dateField.setValue(null);
        appRadio.setSelected(false);
        interRadio.setSelected(false);
        accRadio.setSelected(false);
        rejctRadio.setSelected(false);
        descField.clear();
    }

    private void displayAllJobs() {
        Map<String, JobApp> allJobs = jobAppManager.getAllJobs();
        System.out.println("All Jobs: " + allJobs);
    }
}
