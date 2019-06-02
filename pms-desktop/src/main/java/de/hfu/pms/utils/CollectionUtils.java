package de.hfu.pms.utils;

import java.util.Collection;
import java.util.LinkedList;

/**
 * This utility class contains some helper methods when working with collections.
 */
public class CollectionUtils {

    /**
     * The callback interface can be used within the {@link CollectionUtils} class.
     * @param <T> the type of the items to validate / check
     */
    public interface Callback<T> {

        /**
         * Used in various methods to decide whether two items have something in common or not.
         * @param original the original item who has been passed in
         * @param collectionItem one collection item to compare the original to
         * @return
         */
        boolean check(T original, T collectionItem);

    }

    /**
     * Use this method to remove all items which do not pass the specified callback.
     * E.g. This can be useful when all items with a certain attribute value shall be removed (works as filter).
     * @param item the item that can be used within the callback
     * @param collection the collection to iterate through
     * @param callback the callback function which decides if an item shall be removed or not
     * @param <T> the type of the items
     */
    public static <T> void removeFromList(T item, Collection<T> collection, Callback<T> callback) {
        Collection<T> itemsToRemove = new LinkedList<>();
        for (T t : collection) {
            if (callback.check(item, t)) {
                itemsToRemove.add(t);
            }
        }
        collection.removeAll(itemsToRemove);
    }

}
