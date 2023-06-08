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
    EntityManager em;

    @Override
    public List<TodoList> findTodoListCustom() {
        System.out.println("===== TodoList Custom Repository ===="); // 확인하기 위해서 print
//        TypedQuery<TodoListSpecific> query = entityManager.createQuery("SELECT t.todoId, t.userId, t.todoContents, t.priority, t.todoDate, t.budget, t.updatedAt FROM TodoList AS t WHERE t.isFinished = :isFinished ORDER BY t.updatedAt DESC ", TodoListSpecific.class);
        TypedQuery<TodoList> query =
                em.createQuery(
                        "SELECT new com.example.demo.src.todoList.model.TodoList(t.todoId, t.userId, t.todoContents, t.priority, t.todoDate, t.budget, t.updatedAt) " +
                                "FROM TodoList AS t " +
                                "WHERE t.isFinished = :isFinished " +
                                "ORDER BY t.updatedAt DESC ", TodoList.class);

        List<TodoList> result = query.setParameter("isFinished", false).getResultList();

        return result;
    }

    @Override
    public void updateTodoContents(Long todoId, String todoContents) {
        em.createQuery("UPDATE TodoList t SET t.todoContents = :todoContents where t.todoId = :todoId")
                .setParameter("todoContents", todoContents)
                .setParameter("todoId", todoId)
                .executeUpdate();

        em.clear();
    }

    @Override
    public void updatePriority(Long todoId, int priority) {
        em.createQuery("UPDATE TodoList t SET t.priority = :priority where t.todoId = :todoId")
                .setParameter("priority", priority)
                .setParameter("todoId", todoId)
                .executeUpdate();

        em.clear();
    }

    @Override
    public void updateTodoDate(Long todoId, String todoDate) {
        em.createQuery("UPDATE TodoList t SET t.todoDate = :todoDate where t.todoId = :todoId")
                .setParameter("todoDate", todoDate)
                .setParameter("todoId", todoId)
                .executeUpdate();

        em.clear();
    }

    @Override
    public void updateBudget(Long todoId, int budget) {
        em.createQuery("UPDATE TodoList t SET t.budget = :budget where t.todoId = :todoId")
                .setParameter("budget", budget)
                .setParameter("todoId", todoId)
                .executeUpdate();

        em.clear();
    }
}
