package com.example.demo.src.todoList;

import com.example.demo.src.todoList.model.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoListRepository extends JpaRepository<TodoList, Long> {
}
