package dev.gui.todosimple.controller;


import dev.gui.todosimple.entity.User;
import dev.gui.todosimple.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id){
        try{
            User userById = this.userService.findById(id);
            return new ResponseEntity<>(userById, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<User>> findAll(){
        try{
            List<User> users = this.userService.findAll();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/create")
    @Validated(User.CreateUser.class)
    public ResponseEntity<User> create(@RequestBody @Valid User user){
        try{
            User userCreated = this.userService.create(user);
            return new ResponseEntity<>(userCreated, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    @Validated({User.UpdateUser.class})
    public ResponseEntity<User> update(@RequestBody @Valid User userUpdated, @PathVariable Long id){
        try{
            userUpdated.setId(id);
            this.userService.update(userUpdated);
            return new ResponseEntity<>(userUpdated, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id){
        try{
            this.userService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
