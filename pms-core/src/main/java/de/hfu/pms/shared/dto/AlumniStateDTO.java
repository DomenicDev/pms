package de.hfu.pms.shared.dto;

public class AlumniStateDTO {

    private String jobTitle;
    private String employer;
    private boolean agreementNews;
    private boolean agreementEvaluation;

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public boolean isAgreementNews() {
        return agreementNews;
    }

    public void setAgreementNews(boolean agreementNews) {
        this.agreementNews = agreementNews;
    }

    public boolean isAgreementEvaluation() {
        return agreementEvaluation;
    }

    public void setAgreementEvaluation(boolean agreementEvaluation) {
        this.agreementEvaluation = agreementEvaluation;
    }

    @Override
    public String toString() {
        return "AlumniStateDTO{" +
                "jobTitle='" + jobTitle + '\'' +
                ", employer='" + employer + '\'' +
                ", agreementNews=" + agreementNews +
                ", agreementEvaluation=" + agreementEvaluation +
                '}';
    }
}
