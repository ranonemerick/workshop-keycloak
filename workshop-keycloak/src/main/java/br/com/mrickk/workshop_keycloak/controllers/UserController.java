package br.com.mrickk.workshop_keycloak.controllers;

import br.com.mrickk.workshop_keycloak.dtos.user.UserDTO;
import br.com.mrickk.workshop_keycloak.dtos.user.UserListDTO;
import br.com.mrickk.workshop_keycloak.models.User;
import br.com.mrickk.workshop_keycloak.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid UserDTO userDto, UriComponentsBuilder builder) {
        try {
            var user = userDto.mapToUser();
            userService.save(user);
            var uri = builder.path("/users/{id}").buildAndExpand(user.getId()).toUri();
            return ResponseEntity.created(uri).body(user);
        }
        catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<?>> findAll() {
        var users = userService.findAll();
        var response = users.stream()
                .map(UserListDTO::new)
                .toList();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            var user = userService.findById(id);
            return ResponseEntity.ok().body(new UserListDTO(user));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/name")
    public ResponseEntity<List<?>> findByName(@RequestParam String name) {
        try {
            var users =  userService.findByName(name)
                    .stream()
                    .map(UserListDTO::new)
                    .toList();
            return ResponseEntity.ok().body(users);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody User data) {
        try {
            var user = userService.update(id, data);
            return ResponseEntity.ok().body(new UserDTO(user));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            userService.delete(id);
            return ResponseEntity.noContent().build();
        }catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

}
