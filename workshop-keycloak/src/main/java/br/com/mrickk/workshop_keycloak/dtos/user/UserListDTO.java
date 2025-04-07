package br.com.mrickk.workshop_keycloak.dtos.user;

import br.com.mrickk.workshop_keycloak.models.User;

import java.time.LocalDate;

public record UserListDTO(Long id,
                          String name,
                          LocalDate birthday,
                          Integer age) {

    public UserListDTO(User user) {
        this(user.getId(),
                user.getName(),
                user.getBirthday(),
                user.getAge());
    }
}
