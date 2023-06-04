package com.example.demo.src.todoList.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dom4j.swing.XMLTableColumnDefinition;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@NoArgsConstructor
@Entity(name = "todoList")
@Table
public class TodoList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long todoId;

    private Long userId;

    private String todoContents;

    private int priority;

    private String todoDate;

    private int budget;

    private Boolean isFinished;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Builder
    public TodoList(Long userId, String todoContents, int priority, String todoDate, int budget, Boolean isFinished) {
        this.userId = userId;
        this.todoContents = todoContents;
        this.priority = priority;
        this.todoDate = todoDate;
        this.budget = budget;
        this.isFinished = isFinished;
//        this.createdAt = createdAt;
//        this.updatedAt = updatedAt;
    }
}
