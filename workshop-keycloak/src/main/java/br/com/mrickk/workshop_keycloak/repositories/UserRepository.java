package br.com.mrickk.workshop_keycloak.repositories;

import br.com.mrickk.workshop_keycloak.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByNameContainingIgnoreCase(String name);

}
