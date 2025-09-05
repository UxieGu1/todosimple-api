package dev.gui.todosimple.service;

import dev.gui.todosimple.entity.User;
import dev.gui.todosimple.repository.TaskRepository;
import dev.gui.todosimple.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findById(Long id){
        Optional<User> user = this.userRepository.findById(id);
        return user.orElseThrow(() -> new RuntimeException("Usuário com id: " + id + " não encontrado"));
    }

    public User create(User user){
        user.setId(null);
        return this.userRepository.save(user);
    }

    @Transactional
    public User update(User user){
        User newUser = findById(user.getId());
        newUser.setPassword(user.getPassword());
        return this.userRepository.save(newUser);
    }

    public void delete(Long id){
        findById(id);
        try{
            this.userRepository.deleteById(id);
        }catch (Exception e){
            throw new RuntimeException("Não foi possível excluir pois há entidades relacionadas!");
        }
    }
}
