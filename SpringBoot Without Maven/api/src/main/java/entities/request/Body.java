package entities.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Map;

public class Body {

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotNull(message = "Fills cannot be null")
    private Map<String, Map<String, Object>> fills;

    // Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Map<String, Object>> getFills() {
        return fills;
    }

    public void setFills(Map<String, Map<String, Object>> fills) {
        this.fills = fills;
    }
}
