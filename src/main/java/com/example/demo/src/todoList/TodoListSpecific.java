package com.example.demo.src.todoList;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

// 원하는 데이터만 조회
public interface TodoListSpecific {
    Long getTodoId();
    Long getUserId();
    String getTodoContents();
    int getPriority();
    String getTodoDate();
    int getBudget();
    LocalDateTime getUpdatedAt();
}
