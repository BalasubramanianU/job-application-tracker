package application;

import java.util.Map;

public class JobAppManager implements StoreData {
    private static Map<String, JobApp> jobMap = StoreData.loadFromFile();

    // Create operation
    public void addJob(String key, JobApp job) {
        jobMap.put(key, job);
        StoreData.saveToFile(jobMap);
    }

    // Read operation
    public JobApp getJob(String key) {
        return jobMap.get(key);
    }

    // Update operation
    public void updateJob(String key, JobApp updatedJob) {
        if (jobMap.containsKey(key)) {
            JobApp existingJob = jobMap.get(key);

            // Update only the non-null fields
            if (updatedJob.getPosition() != null) {
                existingJob.setPosition(updatedJob.getPosition());
            }
            if (updatedJob.getCompany() != null) {
                existingJob.setCompany(updatedJob.getCompany());
            }
            if (updatedJob.getDateapplied() != null) {
                existingJob.setDateapplied(updatedJob.getDateapplied());
            }
            if (updatedJob.getStatus() != null) {
                existingJob.setStatus(updatedJob.getStatus());
            }
            if (updatedJob.getJobdesc() != null) {
                existingJob.setJobdesc(updatedJob.getJobdesc());
            }

            jobMap.put(key, existingJob);
            StoreData.saveToFile(jobMap);
        } else {
            System.out.println("Job with key " + key + " does not exist.");
        }
    }

    // Delete operation
    public void deleteJob(String key) {
        jobMap.remove(key);
        StoreData.saveToFile(jobMap);
    }
    
    // Get all jobs
    public Map<String, JobApp> getAllJobs() {
        return jobMap;
    }  
}
    

