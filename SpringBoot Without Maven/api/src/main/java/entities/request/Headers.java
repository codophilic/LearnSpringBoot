package entities.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Headers {

	@JsonProperty
    @NotNull(message = "Unique ID cannot be null")
    private Long unique_id;

	@JsonProperty
    @NotNull(message = "Secure cannot be null")
    private Secure secure;

}
