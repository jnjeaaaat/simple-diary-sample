package com.example.demo.src.todoList;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.todoList.model.PostTodoListReq;
import com.example.demo.src.todoList.model.PostTodoListRes;
import com.example.demo.src.todoList.model.TodoList;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/app/todos")
public class TodoListController {

    private final TodoListService todoListService;

    public TodoListController (TodoListService todoListService) {
        this.todoListService = todoListService;
    }

    @PostMapping("")
    public BaseResponse<String> save(PostTodoListReq postTodoListReq) {
        try {
            System.out.println(postTodoListReq.getTodoContents());
            TodoList todoList = todoListService.save(postTodoListReq);
            return new BaseResponse<>(SUCCESS, todoList.getTodoContents());
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
