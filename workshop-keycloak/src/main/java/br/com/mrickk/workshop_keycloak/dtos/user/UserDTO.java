package br.com.mrickk.workshop_keycloak.dtos.user;

import br.com.mrickk.workshop_keycloak.models.User;

import java.time.LocalDate;

public record UserDTO(String name,
                      LocalDate birthday,
                      Integer age) {

    public UserDTO(User user) {
        this(user.getName(), user.getBirthday(), user.getAge());
    }

    public User mapToUser() {
        User user = new User();
        user.setName(name);
        user.setBirthday(birthday);
        user.setAge(age);
        return user;
    }

}
