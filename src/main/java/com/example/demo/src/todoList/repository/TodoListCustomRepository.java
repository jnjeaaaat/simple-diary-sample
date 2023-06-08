package com.example.demo.src.todoList.repository;

import com.example.demo.src.todoList.TodoListSpecific;
import com.example.demo.src.todoList.model.TodoList;
import com.example.demo.src.todoList.model.TodoListGetRes;

import java.util.List;
import java.util.Optional;

public interface TodoListCustomRepository<T> {
    List<TodoList> findTodoListCustom();
    void updateTodoContents(Long todoId, String todoContents);
    void updatePriority(Long todoId, int priority);
    void updateTodoDate(Long todoId, String todoDate);
    void updateBudget(Long todoId, int budget);
}
