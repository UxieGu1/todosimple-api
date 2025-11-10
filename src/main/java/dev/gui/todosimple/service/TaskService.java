package dev.gui.todosimple.service;

import dev.gui.todosimple.entity.User;
import dev.gui.todosimple.repository.TaskRepository;
//import dev.gui.todosimple.security.UserSpringSecurity;
import dev.gui.todosimple.service.exceptions.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import dev.gui.todosimple.entity.Task;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    public Task findById(Long id){
        Optional<Task> task = this.taskRepository.findById(id);
        return task.orElseThrow(() -> new ObjectNotFoundException(
                "Usuário não encontrado! id: " + id + "Tipo: " + Task.class.getName()));
    }

    public List<Task> findAllByUserId(Long userId) {
        List<Task> tasks = this.taskRepository.findByUser_Id(userId);
        return tasks;
    }

    @Transactional
    public Task create(Task task){
        User user = this.userService.findById(task.getUser().getId());
        task.setId(null);
        task.setUser(user);
        return this.taskRepository.save(task);
    }

    @Transactional
    public Task update(Task task){
        Task newTask = findById(task.getId());
        newTask.setDescription(task.getDescription());
        return this.taskRepository.save(newTask);
    }

    @Transactional
    public void delete(Long id){
        findById(id);
        try{
            this.taskRepository.deleteById(id);
        }catch (Exception e){
            throw new RuntimeException("Não foi possível excluir pois há entidades relacionadas!");
        }
    }

//    private Boolean userHasTask(UserSpringSecurity userSpringSecurity, Task task) {
//        return task.getUser().getId().equals(userSpringSecurity.getId());
//    }
}