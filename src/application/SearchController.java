package application;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class SearchController {

    @FXML
    private TextField posSearchField;

    @FXML
    private TextField compSearchField;

    @FXML
    private DatePicker dateSearchField;

    @FXML
    private RadioButton acceptSearchRadio;

    @FXML
    private RadioButton rejectSearchRadio;

    @FXML
    private RadioButton appliedSearchRadio;

    @FXML
    private RadioButton interSearchRadio;

    @FXML
    private ListView<String> posSearchList;

    @FXML
    private ListView<String> compSearchList;

    @FXML
    private ListView<String> dateSearchList;

    @FXML
    private ListView<String> statSearchList;

    @FXML
    private Button searchBtn;

    private final JobAppManager jobAppManager = new JobAppManager();

    @FXML
    public void initialize() {
        List<JobApp> initialResults = jobAppManager.getAllJobs().values().stream().collect(Collectors.toList());
        updateSearchResults(initialResults);
    }

    @FXML
    public void handleSearch() {
        String position = posSearchField.getText().toLowerCase();
        String company = compSearchField.getText().toLowerCase();
        String status = getStatusSelection();
        String dateString = getDateSelection();

        List<JobApp> searchResults = jobAppManager.getAllJobs().values().stream()
                .filter(job -> matchesPosition(job, position))
                .filter(job -> matchesCompany(job, company))
                .filter(job -> matchesStatus(job, status))
                .filter(job -> matchesDate(job, dateString))
                .collect(Collectors.toList());

        updateSearchResults(searchResults);
    }

    private boolean matchesPosition(JobApp job, String position) {
        return position.isEmpty() || job.getPosition().toLowerCase().contains(position);
    }

    private boolean matchesCompany(JobApp job, String company) {
        return company.isEmpty() || job.getCompany().toLowerCase().contains(company);
    }

    private boolean matchesStatus(JobApp job, String status) {
        return status.isEmpty() || job.getStatus().toLowerCase().equals(status);
    }

    private boolean matchesDate(JobApp job, String dateString) {
        if (dateString.isEmpty()) {
            return true;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        String jobDate = dateFormat.format(job.getDateapplied());
        return jobDate.equals(dateString);
    }

    private String getStatusSelection() {
        if (acceptSearchRadio.isSelected()) {
            return "accepted";
        } else if (rejectSearchRadio.isSelected()) {
            return "rejected";
        } else if (appliedSearchRadio.isSelected()) {
            return "applied";
        } else if (interSearchRadio.isSelected()) {
            return "interviewing";
        }
        return "";
    }

    private String getDateSelection() {
        if (dateSearchField.getValue() != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
            return dateFormat.format(java.sql.Date.valueOf(dateSearchField.getValue()));
        }
        return "";
    }

    private void updateSearchResults(List<JobApp> results) {
        posSearchList.setItems(FXCollections.observableArrayList(results.stream()
                .map(job -> job.getPosition())
                .collect(Collectors.toList())));
        compSearchList.setItems(FXCollections.observableArrayList(results.stream()
                .map(job -> job.getCompany())
                .collect(Collectors.toList())));
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        dateSearchList.setItems(FXCollections.observableArrayList(results.stream()
                .map(job -> dateFormat.format(job.getDateapplied()))
                .collect(Collectors.toList())));
        statSearchList.setItems(FXCollections.observableArrayList(results.stream()
                .map(job -> job.getStatus())
                .collect(Collectors.toList())));
    }
}
