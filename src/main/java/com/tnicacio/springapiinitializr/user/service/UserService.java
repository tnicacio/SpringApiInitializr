package com.tnicacio.springapiinitializr.user.service;

import com.tnicacio.springapiinitializr.dto.UserInputDTO;
import com.tnicacio.springapiinitializr.dto.UserOutputDTO;
import com.tnicacio.springapiinitializr.dto.UserUpdateDTO;
import com.tnicacio.springapiinitializr.exception.serviceexception.DatabaseException;
import com.tnicacio.springapiinitializr.exception.serviceexception.ResourceNotFoundException;
import com.tnicacio.springapiinitializr.role.repository.RoleRepository;
import com.tnicacio.springapiinitializr.user.User;
import com.tnicacio.springapiinitializr.user.repository.UserRepository;
import com.tnicacio.springapiinitializr.converter.Converter;
import com.tnicacio.springapiinitializr.mapper.Mapper;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@Setter(onMethod_ = @Autowired)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserService implements UserDetailsService {

    static final Logger logger = LoggerFactory.getLogger(UserService.class);

    BCryptPasswordEncoder passwordEncoder;
    UserRepository repository;
    RoleRepository roleRepository;
    Converter<UserInputDTO, User> convertUserInputDtoToUser;
    Mapper<UserInputDTO, User> mapUserInputDtoToUser;

    @Transactional(readOnly = true)
    public Page<UserOutputDTO> findAllPaged(Pageable pageable) {
        Page<User> list = repository.findAll(pageable);
        return list.map(UserOutputDTO::new);
    }

    @Transactional(readOnly = true)
    public UserOutputDTO findById(Long id) {
        Optional<User> obj = repository.findById(id);
        User entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new UserOutputDTO(entity);
    }

    @Transactional
    public UserOutputDTO insert(UserInputDTO dto) {
        User entity = convertUserInputDtoToUser.convertNonNull(dto);
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        entity = repository.save(entity);
        return new UserOutputDTO(entity);
    }

    @Transactional
    public UserOutputDTO update(Long id, UserUpdateDTO dto) {
        try {
            User entity = repository.getById(id);
            entity = mapUserInputDtoToUser.mapNonNullFromTo(dto, entity);
            entity = repository.save(entity);
            return new UserOutputDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByEmail(username);
        if (user == null) {
            logger.error("User not found: {} ", username);
            throw new UsernameNotFoundException("Email not found");
        }
        logger.info("User found: {}", username);
        return user;
    }

}
