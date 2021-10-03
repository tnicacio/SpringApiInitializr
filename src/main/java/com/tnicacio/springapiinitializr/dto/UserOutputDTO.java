package com.tnicacio.springapiinitializr.dto;

import com.tnicacio.springapiinitializr.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserOutputDTO extends UserDTO {

    private Long id;

    public UserOutputDTO() {
        super();
    }

    public UserOutputDTO(User entity) {
        super(entity);
        id = entity.getId();
    }

}
