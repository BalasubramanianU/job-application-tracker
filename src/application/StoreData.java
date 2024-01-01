package application;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public interface StoreData{
    public static final Map<String, JobApp> jobAppMap = new HashMap<>();
    public static final String FILE_PATH = "jobAppData.csv";

	public static void saveToFile(Map<String, JobApp> jobAppMap) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            Iterator<Map.Entry<String, JobApp>> iterator = jobAppMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, JobApp> entry = iterator.next();
                String key = entry.getKey();
                JobApp jobApp = entry.getValue();
                String csvString = key + "," + objToCsvStr(jobApp);
                writer.println(csvString);
            }
        } catch (IOException e) {
            System.out.print(e);
            e.printStackTrace();
        }
    }

    public static String objToCsvStr(JobApp jobApp) {
        String result = "";
        result = result + jobApp.getPosition() + ",";
        result = result + jobApp.getCompany() + ",";
        result = result + jobApp.getDateapplied() + ",";
        result = result + jobApp.getStatus() + ",";
        result = result + jobApp.getJobdesc();
        return result;
    }
    
    public static Map<String, JobApp> loadFromFile() {
        try (Scanner scanner = new Scanner(new FileReader(FILE_PATH))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",", 6);
                if (parts.length == 6) {
                    String key = parts[0];
                    JobApp jobApp = new JobApp();
                    jobApp.setPosition(parts[1]);
                    jobApp.setCompany(parts[2]);
                    try {
                        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(parts[3]);
                        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                        jobApp.setDateapplied(sqlDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    jobApp.setStatus(parts[4]);
                    jobApp.setJobdesc(parts[5]);
                    jobAppMap.put(key, jobApp);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jobAppMap;
    }
}
