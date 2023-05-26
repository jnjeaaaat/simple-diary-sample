package com.example.demo.src.todoList.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@NoArgsConstructor
@Entity(name = "todoList")
public class TodoList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todoId")
    private Long todoId;

    @Column(length = 200, nullable = false, name = "todoContents")
    private String todoContents;

    @Column(name = "priority")
    private int priority;

    @Column(name = "todoDate")
    private String todoDate;

    @Column(name = "budget", nullable = true)
    @ColumnDefault("0")
    private int budget;

    @Column(name = "isFinished", nullable = true)
    @ColumnDefault("false")
    private Boolean isFinished;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Builder
    public TodoList(String todoContents, int priority, String todoDate, int budget, Boolean isFinished) {
        this.todoContents = todoContents;
        this.priority = priority;
        this.todoDate = todoDate;
        this.budget = budget;
        this.isFinished = isFinished;
//        this.createdAt = createdAt;
//        this.updatedAt = updatedAt;
    }
}
