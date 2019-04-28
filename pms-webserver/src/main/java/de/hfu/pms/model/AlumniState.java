package de.hfu.pms.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AlumniState {

    @Column
    private String jobTitle;

    @Column
    private String employer;

    @Column
    private boolean agreementNews;

    @Column
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
}
