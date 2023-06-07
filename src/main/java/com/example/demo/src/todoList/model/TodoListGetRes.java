package com.example.demo.src.todoList.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TodoListGetRes {
    private Long todoId;
    private Long userId;
    private String todoContents;
    private int priority;
    private String todoDate;
    private int budget;
    private LocalDateTime updatedAt;
}
