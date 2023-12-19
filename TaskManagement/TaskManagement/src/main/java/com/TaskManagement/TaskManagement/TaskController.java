package com.TaskManagement.TaskManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/getAllTasks")
    public List<TaskEntity> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/getTaskById")
    public ResponseEntity<?> getTaskById(@RequestParam("id") Long id) {
        try {
            Optional<TaskEntity> task = taskService.getTaskById(id);
            return ResponseEntity.ok(task.orElse(null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving task: " + e.getMessage());
        }
    }

    @PostMapping("/addTask")
    public ResponseEntity<?> addTask(@RequestBody TaskEntity task) {
        try {
            TaskEntity addedTask = taskService.addTask(task);
            return ResponseEntity.status(HttpStatus.CREATED).body(addedTask);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding task: " + e.getMessage());
        }
    }

    @PutMapping("/updateTask")
    public ResponseEntity<?> updateTask(@RequestParam Long id, @RequestBody TaskEntity updatedTask) {
        try {
            TaskEntity updated = taskService.updateTask(id, updatedTask);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating task: " + e.getMessage());
        }
    }

    @DeleteMapping("/deleteTask")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        try {
            boolean deleted = taskService.deleteTask(id);
            return ResponseEntity.ok(deleted);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting task: " + e.getMessage());
        }
    }

    // Exception handler for generic exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
    }
}