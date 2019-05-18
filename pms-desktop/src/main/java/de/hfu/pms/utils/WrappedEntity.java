package de.hfu.pms.utils;

/**
 * The WrappedEntity class does simply wrap a specified object plus a representation string.<br/>
 *
 * This class can be used in gui elements (e.g. ComboBoxes) to provide a clean
 * presentation of the specified data to the end-user.<br/>
 *
 * The <code>toString()</code> method will return the specified representation string,
 * which makes it suitable for directly being used in gui controller.
 *
 * @param <T> the type of the wrapped entity
 */
public class WrappedEntity<T> {

    private String representation;
    private T entity;

    /**
     * Creates a new WrappedEntity with the specified representation and entity.
     * @param representation the customized presentation of the entity
     * @param entity the actual entity being wrapped
     */
    public WrappedEntity(String representation, T entity) {
        if (representation == null) {
            throw new IllegalArgumentException("The representation string must not be null");
        }
        if (entity == null) {
            throw new IllegalArgumentException("The specified entity must not be null");
        }
        this.representation = representation;
        this.entity = entity;
    }

    public String getRepresentation() {
        return representation;
    }

    public T getEntity() {
        return entity;
    }

    @Override
    public String toString() {
        return representation;
    }
}
