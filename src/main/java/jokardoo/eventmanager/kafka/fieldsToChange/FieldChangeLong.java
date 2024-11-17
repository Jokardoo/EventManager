package jokardoo.eventmanager.kafka.fieldsToChange;

import lombok.Data;

@Data
public class FieldChangeLong {
    private Long oldValue;
    private Long newValue;

    public boolean hasChanges() {
        return !oldValue.equals(newValue);
    }
}
