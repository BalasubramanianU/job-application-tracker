package application;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class HomeController {

    @FXML
    private Text title1Txt;

    @FXML
    private Text company1Txt;

    @FXML
    private Text applied1Txt;

    @FXML
    private Text title2Txt;

    @FXML
    private Text company2Txt;

    @FXML
    private Text applied2Txt;

    @FXML
    private Text title3Txt;

    @FXML
    private Text company3Txt;

    @FXML
    private Text applied3Txt;

    @FXML
    private Text tdatteTxt;

    private final JobAppManager jobAppManager = new JobAppManager();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");

    @FXML
    public void initialize() {
        setCurrentDate();
        setLastJobApps();
    }

    private void setCurrentDate() {
        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Format the date using a specific pattern
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = currentDate.format(formatter);

        // Set the formatted date in the tdatteTxt field
        tdatteTxt.setText(formattedDate);
    }

    private void setLastJobApps() {
        List<JobApp> last3Jobs = jobAppManager.getAllJobs().values()
                .stream()
                .sorted(Comparator.comparing(JobApp::getDateapplied).reversed())
                .limit(3)
                .collect(Collectors.toList());

        setJobText(title1Txt, company1Txt, applied1Txt, last3Jobs, 0);
        setJobText(title2Txt, company2Txt, applied2Txt, last3Jobs, 1);
        setJobText(title3Txt, company3Txt, applied3Txt, last3Jobs, 2);
    }

    private void setJobText(Text titleTxt, Text companyTxt, Text appliedTxt, List<JobApp> jobs, int index) {
        if (index < jobs.size()) {
            JobApp job = jobs.get(index);
            titleTxt.setText(job.getPosition());
            companyTxt.setText(job.getCompany());
            appliedTxt.setText("Applied On " + dateFormat.format(job.getDateapplied()));
        }
    }
}
