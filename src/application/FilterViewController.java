package application;

import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class FilterViewController {

    @FXML
    private ListView<String> appliedListView;

    @FXML
    private ListView<String> rejectedListView;

    @FXML
    private ListView<String> inProcessListView;

    @FXML
    private ListView<String> acceptedListView;

    private JobAppManager jobAppManager;

    public FilterViewController(JobAppManager jobAppManager) {
        this.jobAppManager = jobAppManager;
    }

    public void initialize() {
        Map<String, JobApp> allJobs = jobAppManager.getAllJobs();

        populateListViewByStatus(allJobs, "Applied", appliedListView);
        populateListViewByStatus(allJobs, "Rejected", rejectedListView);
        populateListViewByStatus(allJobs, "Interviewing", inProcessListView);
        populateListViewByStatus(allJobs, "Accepted", acceptedListView);
    }

    private void populateListViewByStatus(Map<String, JobApp> jobs, String status, ListView<String> listView) {
        ObservableList<String> data = FXCollections.observableArrayList();

        jobs.forEach((key, jobDetails) -> {
            if (status.equals(jobDetails.getStatus())) {
                data.add(jobDetails.getPosition() + ", " + jobDetails.getCompany());
            }
        });

        listView.setItems(data);
    }
}
