package com.example.demo.src.todoList.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TodoListSave {

    private Long userId;
    private String todoContents;
    private int priority;
    private String todoDate;
    private int budget;

}
