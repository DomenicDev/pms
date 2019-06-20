package de.hfu.pms.events;

public class RequestSearchDoctoralStudentEvent {

    private String searchText;

    public RequestSearchDoctoralStudentEvent(String searchText) {
        this.searchText = searchText;
    }

    public String getSearchText() {
        return searchText;
    }
}
