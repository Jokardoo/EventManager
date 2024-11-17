package jokardoo.eventmanager.kafka.fieldsToChange;

import jokardoo.eventmanager.domain.event.EventStatus;
import lombok.Data;

@Data
public class FieldChangeEventStatus {

    private EventStatus oldValue;
    private EventStatus newValue;

    public boolean hasChanges() {
        return !oldValue.equals(newValue);
    }
}
