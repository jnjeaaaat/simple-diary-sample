package com.example.demo.src.todoList;

import com.example.demo.src.todoList.model.TodoList;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoListRepository extends JpaRepository<TodoList, Long> {
    @Query("select t from todoList as t where t.userId = :userId")
    Optional<TodoList> findAllByUserId();
}
