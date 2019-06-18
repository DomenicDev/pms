package de.hfu.pms.events;

import de.hfu.pms.controller.DashboardController;

/**
 * Used to show the specified screen on the dashboard controller.
 */
public class SwitchMainScreenEvent {

    private DashboardController.MainScreen screen;

    public SwitchMainScreenEvent(DashboardController.MainScreen screen) {
        this.screen = screen;
    }

    public DashboardController.MainScreen getScreen() {
        return screen;
    }

}
