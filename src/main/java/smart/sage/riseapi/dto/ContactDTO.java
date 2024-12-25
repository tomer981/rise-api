package smart.sage.riseapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record ContactDTO(@JsonProperty("first_name") @NotNull(message = "first_name is required") String firstName,
                         @JsonProperty("last_name") @NotNull(message = "last_name is required") String lastName,
                         @JsonProperty("phone_number") @NotNull(message = "phone_number is required") String phoneNumber,
                         @JsonProperty("address") @NotNull(message = "address is required") String address) {
}
