package de.hfu.pms.events;

import de.hfu.pms.shared.dto.DocumentInformationDTO;

import java.util.Collection;

public class RequestDocumentsEvent {
    private Collection<DocumentInformationDTO> documents;

    public RequestDocumentsEvent(Collection<DocumentInformationDTO> documents){
        this.documents = documents;
    }

    public Collection<DocumentInformationDTO> getDocuments(){
        return documents;
    }
}
