package com.example.demo.src.todoList;

import com.example.demo.src.todoList.model.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoListRepository extends JpaRepository<TodoList, Long> {
    <T> List<T> findAllByUserIdAndIsFinishedOrderByUpdatedAtDesc(Class<T> type, Long userId, Boolean isFinished);
}
