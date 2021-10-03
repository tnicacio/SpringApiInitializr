package com.tnicacio.springapiinitializr.user.controller;

import com.tnicacio.springapiinitializr.dto.UserInputDTO;
import com.tnicacio.springapiinitializr.dto.UserOutputDTO;
import com.tnicacio.springapiinitializr.dto.UserUpdateDTO;
import com.tnicacio.springapiinitializr.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<UserOutputDTO>> findAll(Pageable pageable) {
        Page<UserOutputDTO> list = service.findAllPaged(pageable);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserOutputDTO> findById(@PathVariable Long id) {
        UserOutputDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<UserOutputDTO> insert(@RequestBody @Valid UserInputDTO dto) {
        UserOutputDTO newDto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newDto.getId()).toUri();
        return ResponseEntity.created(uri).body(newDto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserOutputDTO> update(@PathVariable Long id, @RequestBody @Valid UserUpdateDTO dto) {
        UserOutputDTO newDto = service.update(id, dto);
        return ResponseEntity.ok().body(newDto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

} 
