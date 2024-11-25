package jokardoo.eventmanager.kafka.fieldsToChange;

import lombok.Data;

@Data
public class FieldChange<T> {

    private T oldValue;
    private T newValue;

    public boolean hasChanges() {
        return !oldValue.equals(newValue);
    }
}
