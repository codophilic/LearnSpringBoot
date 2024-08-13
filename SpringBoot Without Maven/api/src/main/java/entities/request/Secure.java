package entities.request;


import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
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
public class Secure {

	@JsonProperty
	@NotEmpty
    @NotBlank(message = "SecurityKey cannot be blank")
    @Min(value = 3)
    @Pattern(regexp = "^[A-Za-z0-9]{10,}$", message = "SecurityKey must follow the pattern")
    private String securityKey;

}
