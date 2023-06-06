package com.example.demo.src.todoList.repository;

import com.example.demo.src.todoList.TodoListSpecific;
import com.example.demo.src.todoList.model.TodoList;

import java.util.List;
import java.util.Optional;

public interface TodoListCustomRepository<T> {
    List<TodoList> findTodoListCustom();
}
