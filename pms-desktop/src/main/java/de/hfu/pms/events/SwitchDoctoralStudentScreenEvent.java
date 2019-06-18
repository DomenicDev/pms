package de.hfu.pms.events;

import de.hfu.pms.controller.DoctoralStudentMainContentController;

public class SwitchDoctoralStudentScreenEvent {

    private DoctoralStudentMainContentController.DoctoralStudentScreen screen;

    public SwitchDoctoralStudentScreenEvent(DoctoralStudentMainContentController.DoctoralStudentScreen screen) {
        this.screen = screen;
    }

    public DoctoralStudentMainContentController.DoctoralStudentScreen getScreen() {
        return screen;
    }
}
