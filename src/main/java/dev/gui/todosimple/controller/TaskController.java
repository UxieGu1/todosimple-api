package dev.gui.todosimple.controller;

import dev.gui.todosimple.entity.Task;
import dev.gui.todosimple.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Task> findById(@PathVariable Long id){
        try{
            Task taskById = this.taskService.findById(id);
            return new ResponseEntity<>(taskById, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findAllById/{id}")
    public ResponseEntity<List<Task>> findAllById(@PathVariable Long id){
        try{
            List<Task> tasks = this.taskService.findAllByUserId(id);
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Task> create(@RequestBody Task task){
        try{
            Task taskCreated = this.taskService.create(task);
            return new ResponseEntity<>(taskCreated, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Task> update(@RequestBody Task taskUpdated, @PathVariable Long id){
        try{
            taskUpdated.setId(id);
            this.taskService.update(taskUpdated);
            return new ResponseEntity<>(taskUpdated, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Task> delete(@PathVariable Long id){
        try{
            this.taskService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

}
