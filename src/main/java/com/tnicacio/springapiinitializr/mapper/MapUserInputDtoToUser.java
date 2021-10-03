package com.tnicacio.springapiinitializr.mapper;

import com.tnicacio.springapiinitializr.dto.UserInputDTO;
import com.tnicacio.springapiinitializr.role.repository.RoleRepository;
import com.tnicacio.springapiinitializr.user.User;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Setter(onMethod_ = @Autowired)
public class MapUserInputDtoToUser implements Mapper<UserInputDTO, User> {

    private RoleRepository roleRepository;

    @Override
    public User mapNonNullFromTo(UserInputDTO dto, User entity) {
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());

        entity.getRoles().clear();
        dto.getRoles().stream()
                .map(roleDto -> roleRepository.getById(roleDto.getId()))
                .forEach(role -> entity.getRoles().add(role));
        return entity;
    }

}
