package com.example.demo.src.todoList;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.todoList.model.PostTodoListReq;
import com.example.demo.src.todoList.model.TodoList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/app/todos")
public class TodoListController {

    @Autowired
    private final TodoListService todoListService;

    public TodoListController (TodoListService todoListService) {
        this.todoListService = todoListService;
    }

    @PostMapping("")
    public BaseResponse<String> save(@RequestBody PostTodoListReq postTodoListReq) {
        try {
            TodoList todoList = todoListService.save(postTodoListReq);
            return new BaseResponse<>(SUCCESS, todoList.getTodoContents());
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
