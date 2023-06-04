package com.example.demo.src.todoList.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostTodoListReq {
    private Long userId;
    private String todoContents;
    private int priority;
    private String todoDate;
    private int budget;
}
