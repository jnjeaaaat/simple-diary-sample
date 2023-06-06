package com.example.demo.src.todoList.repository;

import com.example.demo.src.todoList.TodoListSpecific;
import com.example.demo.src.todoList.model.TodoList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@Transactional
public class TodoListRepositoryImpl implements TodoListCustomRepository {

    @Autowired
    EntityManager entityManager;


    @Override
    public List<TodoList> findTodoListCustom() {
        System.out.println("===== TodoList Custom Repository ===="); // 확인하기 위해서 print
        List<TodoList> result = entityManager.createQuery("SELECT t FROM TodoList AS t ORDER BY t.updatedAt DESC", TodoList.class).getResultList();
        return result;
    }
}
