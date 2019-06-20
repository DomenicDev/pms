package de.hfu.pms.events;

import de.hfu.pms.shared.dto.PreviewDoctoralStudentDTO;

import java.util.Collection;

public class ResponseSearchRequestEvent {

    private Collection<PreviewDoctoralStudentDTO> previews;

    public ResponseSearchRequestEvent(Collection<PreviewDoctoralStudentDTO> previews) {
        this.previews = previews;
    }

    public Collection<PreviewDoctoralStudentDTO> getPreviews() {
        return previews;
    }
}
