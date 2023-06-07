package com.example.demo.src.todoList.repository;

import com.example.demo.src.todoList.TodoListSpecific;
import com.example.demo.src.todoList.model.TodoList;
import com.example.demo.src.todoList.model.TodoListGetRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional
public class TodoListRepositoryImpl implements TodoListCustomRepository {

    @Autowired
    EntityManager entityManager;

    @Override
    public List<TodoList> findTodoListCustom() {
        System.out.println("===== TodoList Custom Repository ===="); // 확인하기 위해서 print
//        TypedQuery<TodoListSpecific> query = entityManager.createQuery("SELECT t.todoId, t.userId, t.todoContents, t.priority, t.todoDate, t.budget, t.updatedAt FROM TodoList AS t WHERE t.isFinished = :isFinished ORDER BY t.updatedAt DESC ", TodoListSpecific.class);
        TypedQuery<TodoList> query =
                entityManager.createQuery(
                        "SELECT new com.example.demo.src.todoList.model.TodoList(t.todoId, t.userId, t.todoContents, t.priority, t.todoDate, t.budget, t.updatedAt) " +
                                "FROM TodoList AS t " +
                                "WHERE t.isFinished = :isFinished " +
                                "ORDER BY t.updatedAt DESC ", TodoList.class);

        List<TodoList> result = query.setParameter("isFinished", false).getResultList();

        return result;
    }
}
