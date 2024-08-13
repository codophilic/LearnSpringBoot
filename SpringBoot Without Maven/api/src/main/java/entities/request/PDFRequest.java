package entities.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.Valid;
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
public class PDFRequest {

	@JsonProperty
    @NotNull(message = "Headers cannot be null")
    private Headers headers;

	@JsonProperty
    @NotNull(message = "Body cannot be null")
    private Body body;

}

