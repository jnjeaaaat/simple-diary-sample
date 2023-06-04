package com.example.demo.src.todoList;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.todoList.model.PostTodoListReq;
import com.example.demo.src.todoList.model.PostTodoListRes;
import com.example.demo.src.todoList.model.TodoList;
import org.springframework.web.bind.annotation.*;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/app/todos")
public class TodoListController {

    private final TodoListService todoListService;

    public TodoListController (TodoListService todoListService) {
        this.todoListService = todoListService;
    }

    @PostMapping("")
    public BaseResponse<String> save(@RequestBody PostTodoListReq postTodoListReq) {
        try {
            System.out.println(postTodoListReq.getTodoContents());
            TodoList todoListRes = todoListService.save(postTodoListReq);
            return new BaseResponse<>(SUCCESS, todoListRes.getTodoContents());
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
