package de.hfu.pms.events;

public class CreateDocStudentPropertyEvent<T> {

    private final T property;

    public CreateDocStudentPropertyEvent(final T property) {
        this.property = property;
    }

    public final T getProperty() {
        return property;
    }
}
