package de.hfu.pms.utils;

import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;


/**
 * This util class contains some helper methods for common problems when working with JavaFx
 */
public class JavaFxUtils {

    /**
     * Sets all anchor pane constraints for the specified node with the specified value.
     * @param node the node to apply the anchor pane constraints to
     * @param valueForAll the value to apply all constraints for
     */
    public static void setAllAnchorPaneConstraints(Node node, double valueForAll) {
        setAllAnchorPaneConstraints(node, valueForAll, valueForAll, valueForAll, valueForAll);
    }

    /**
     * Helper method to set the anchor pane constraints for the specified node.
     * @param node the node object to apply the constraints to
     * @param left the offset from the left of the anchor pane
     * @param right the offset from the right of the anchor pane
     * @param top the offset from the top of the anchor pane
     * @param bottom the offset from the bottom of the anchor pane
     */
    public static void setAllAnchorPaneConstraints(Node node, double left, double right, double top, double bottom) {
        AnchorPane.setLeftAnchor(node, left);
        AnchorPane.setRightAnchor(node, right);
        AnchorPane.setTopAnchor(node, top);
        AnchorPane.setBottomAnchor(node, bottom);
    }

    /**
     * Helper method to remove all selected items from the specified view
     * and will return true if there were any items that have been removed, false otherwise.
     * @param tableView the view to remove the selected items from
     * @param <T> the type of the list items
     * @return true if there were items removed, false otherwise
     */
    public static <T> boolean removeSelectedItems(TableView<T> tableView) {
        if (tableView == null) {
            throw new IllegalArgumentException("null not allowed as argument");
        }

        if (tableView.getSelectionModel().isEmpty()) {
            // nothing to delete
            return false;
        }

        // remove all selected items from the table view
        tableView.getItems().removeAll(tableView.getSelectionModel().getSelectedItems());
        return true;
    }
}
