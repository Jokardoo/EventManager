package jokardoo.eventmanager.dto.location;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class EventLocationDto {

    private int id;

    @NotBlank(message = "Location name should be not empty!")
    private String name;

    @NotBlank(message = "Address should be not empty!")
    private String address;

    @Min(value = 5, message = "Capacity should be bigger than 5!")
    @Positive
    @NotNull
    private int capacity;

    @Nullable
    private String description;

    public EventLocationDto() {

    }
}
