package smart.sage.riseapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Table(name = "contact")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "first_name", nullable = false)
    @NotBlank
    @NotNull
    @Size(min = 1, max = 16)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @NotBlank
    @NotNull
    @Size(min = 1, max = 16)
    private String lastName;

    @Column(name = "phone_number", nullable = false)
    @NotBlank
    @NotNull
    @Size(min = 10, max = 10)
    private String phoneNumber;

    @Column(name = "address", nullable = false)
    @NotBlank
    @NotNull
    @Size(min = 1, max = 64)
    private String address;
}