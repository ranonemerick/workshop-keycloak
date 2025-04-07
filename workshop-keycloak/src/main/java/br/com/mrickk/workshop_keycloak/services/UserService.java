package br.com.mrickk.workshop_keycloak.services;

import br.com.mrickk.workshop_keycloak.models.User;
import br.com.mrickk.workshop_keycloak.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public List<User> findByName(String name) {
        return userRepository.findByNameContainingIgnoreCase(name);
    }

    @Transactional
    public User update(Long id, User data) {
        var user  = userRepository.getReferenceById(id);
        if(data.getName() != null) user.setName(data.getName());
        if(data.getBirthday() != null) user.setBirthday(data.getBirthday());
        if(data.getAge() != null) user.setAge(data.getAge());

        return userRepository.save(user);
    }

    @Transactional
    public void delete(Long id) {
        var user = userRepository.getReferenceById(id);
        userRepository.deleteById(user.getId());
    }

}
