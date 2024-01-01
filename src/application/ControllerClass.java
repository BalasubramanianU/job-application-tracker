package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class ControllerClass {

    @FXML
    private Button addAppBtn;

    @FXML
    private Pane appPane;

    @FXML
    private Button viewAppBtn;

    @FXML
    private Button applicationStatus;

    private final JobAppManager jobAppManager = new JobAppManager();

    @FXML
    public void initialize() {
        loadFXML("home.fxml", new HomeController());
    }

    @FXML
    private void viewAllApp() {
        loadFXML("viewApp.fxml", new ViewAppController());
    }

    @FXML
    private void addNewApp() {
        loadFXML("newApp.fxml", new NewAppControllerClass(jobAppManager));
    }

    @FXML
    private void homePane() {
        loadFXML("home.fxml", new HomeController());
    }

    @FXML
    private void loadApplicationStatus() {
        loadFXML("FilterView.fxml", new FilterViewController(jobAppManager));
    }

    @FXML
    private void searchPane() {
        loadFXML("search.fxml", new SearchController());
    }

    @FXML
    private void resumeHelper() {
        loadFXML("resumehelper.fxml", new ResumeController());
    }

    private void loadFXML(String fxmlFile, Object controller) {
        try {
//        	load the fxml dynamically
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            loader.setController(controller);
            Node node = loader.load();
            appPane.getChildren().clear();
            appPane.getChildren().add(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
