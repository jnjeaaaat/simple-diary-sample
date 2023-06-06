package com.example.demo.src.todoList;

import com.example.demo.src.todoList.model.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoListRepository extends JpaRepository<TodoList, Long> {
    /** updatedAt으로 내림차순 정렬 userId로 todoList가져오기 */
    <T> List<T> findAllByUserIdAndIsFinishedOrderByUpdatedAtDesc(Class<T> type, Long userId, Boolean isFinished);

    /** todoId로 특정 할일 가져오기 */
    <T> Optional<T> findByTodoIdAndIsFinished(Class<T> type, Long todoId, Boolean isFinished);
}
