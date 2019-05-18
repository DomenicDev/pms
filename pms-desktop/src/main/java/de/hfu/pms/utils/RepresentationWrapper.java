package de.hfu.pms.utils;

import de.hfu.pms.shared.enums.FamilyStatus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

/**
 * This utility class provides helper methods to make use of the WrappedEntity class that is used for
 * data representation in the graphical user interface.
 */
public class RepresentationWrapper {

    /**
     * Used to get the actual string representation that are shown in the gui.
     */
    private static ResourceBundle bundle = GuiLoader.getResourceBundle();

    /**
     * @return a collection with all family status values and their string representation.
     */
    public static Collection<WrappedEntity<FamilyStatus>> getWrappedFamilyStatus() {
        Collection<WrappedEntity<FamilyStatus>> collection = new ArrayList<>(FamilyStatus.values().length);
        collection.add(new WrappedEntity<>(bundle.getString("enum.familyStatus.single"), FamilyStatus.Single));
        collection.add(new WrappedEntity<>(bundle.getString("enum.familyStatus.married"), FamilyStatus.Married));
        collection.add(new WrappedEntity<>(bundle.getString("enum.familyStatus.no_info"), FamilyStatus.NoInformation));
        return collection;
    }

}
