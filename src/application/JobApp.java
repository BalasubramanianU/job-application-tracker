package application;

import java.util.Date;

public class JobApp {
	private String position;
	private String company;
	private Date dateapplied;
	private String status;
	private String jobdesc;
	
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public Date getDateapplied() {
		return dateapplied;
	}
	public void setDateapplied(Date dateapplied) {
		this.dateapplied = dateapplied;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getJobdesc() {
		return jobdesc;
	}
	public void setJobdesc(String jobdesc) {
		this.jobdesc = jobdesc;
	}
	
	@Override
	public String toString() {
	    return "JobApp{" +
	            "position='" + position + '\'' +
	            ", company='" + company + '\'' +
	            ", dateapplied=" + dateapplied +
	            ", status='" + status + '\'' +
	            ", jobdesc='" + jobdesc + '\'' +
	            '}';
	}

	
}
