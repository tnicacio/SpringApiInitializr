package com.tnicacio.springapiinitializr.dto;

import com.tnicacio.springapiinitializr.user.User;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Set<RoleDTO> roles = new HashSet<>();

    @NotBlank(message = "Required field")

    private String firstName;

    private String lastName;

    @Email(message = "Please insert a valid email")
    private String email;

    public UserDTO(User entity) {
        firstName = entity.getFirstName();
        lastName = entity.getLastName();
        email = entity.getEmail();
        entity.getRoles().forEach(role -> this.roles.add(new RoleDTO(role)));
    }

}
