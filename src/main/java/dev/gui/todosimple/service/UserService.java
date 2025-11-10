package dev.gui.todosimple.service;

import dev.gui.todosimple.entity.User;
import dev.gui.todosimple.entity.enums.ProfileEnum;
import dev.gui.todosimple.repository.UserRepository;
import dev.gui.todosimple.service.exceptions.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    public User findById(Long id){
        Optional<User> user = this.userRepository.findById(id);
        return user.orElseThrow(() -> new ObjectNotFoundException(
                "Usuário não encontrado! id: " + id + "Tipo: " + User.class.getName()));
    }

    public List<User> findAll(){
        return this.userRepository.findAll();
    }

    @Transactional
    public User create(User user){
        user.setId(null);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.addProfile(ProfileEnum.USER);
        return this.userRepository.save(user);
    }

    @Transactional
    public User update(User user){
        User newUser = findById(user.getId());
        newUser.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
        return this.userRepository.save(newUser);
    }

    @Transactional
    public void delete(Long id){
        findById(id);
        try{
            this.userRepository.deleteById(id);
        }catch (Exception e){
            throw new RuntimeException("Não foi possível excluir pois há entidades relacionadas!");
        }
    }

//    public static UserSpringSecurity authenticated() {
//        try {
//            return (UserSpringSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//    public User fromDTO(@Valid UserCreateDTO obj) {
//        User user = new User();
//        user.setUsername(obj.getUsername());
//        user.setPassword(obj.getPassword());
//        return user;
//    }
//
//    public User fromDTO(@Valid UserUpdateDTO obj) {
//        User user = new User();
//        user.setId(obj.getId());
//        user.setPassword(obj.getPassword());
//        return user;
//    }
}
