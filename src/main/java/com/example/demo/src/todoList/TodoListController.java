package com.example.demo.src.todoList;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.todoList.model.TodoListSave;
import com.example.demo.src.todoList.model.TodoListSaveRes;
import com.example.demo.src.todoList.model.TodoList;
import com.example.demo.src.todoList.repository.TodoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/app/todos")
public class TodoListController {

    @Autowired
    private final TodoListService todoListService;
    @Autowired
    private final TodoListProvider todoListProvider;
    @Autowired
    private final TodoListRepository todoListRepository;

    public TodoListController (TodoListService todoListService, TodoListProvider todoListProvider, TodoListRepository todoListRepository) {
        this.todoListService = todoListService;
        this.todoListProvider = todoListProvider;
        this.todoListRepository = todoListRepository;
    }

    @PostMapping("")
    public BaseResponse<TodoListSaveRes> writeTodo(@RequestBody TodoListSave todoListSave) {
        try {
            TodoListSaveRes postTodoListRes = new TodoListSaveRes(todoListService.writeTodo(todoListSave).getTodoContents());
            return new BaseResponse<>(SUCCESS_NEW_TODO, postTodoListRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @GetMapping("/users/{userId}")
    public BaseResponse<List<TodoListSpecific>> getTodoListByUserId(@PathVariable("userId") Long userId) {
        try {
            List<TodoListSpecific> todoLists = todoListProvider.getTodoListByUserId(userId);
            return new BaseResponse<>(SUCCESS_TODO_BY_USER, todoLists);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @GetMapping("/{todoId}")
    public BaseResponse<Optional<TodoListSpecific>> getTodoListById(@PathVariable("todoId") Long todoId) {
        try {
            Optional<TodoListSpecific> todoList = todoListProvider.getTodoListById(todoId);
            return new BaseResponse<>(SUCCESS_TODO_ONE, todoList);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @GetMapping("/users/search/{userId}")
    public BaseResponse<List<TodoListSpecific>> getTodoListByKeyWord(@PathVariable("userId") Long userId,
                                                                     @RequestParam String keyWord) {
        try {
            List<TodoListSpecific> todoLists = todoListProvider.getTodoListByKeyWord(userId, keyWord);
            return new BaseResponse<>(SUCCESS_TODO_BY_KEY_WORD, todoLists);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @GetMapping("")
    public BaseResponse<List<TodoList>> getAllTodoList() {
        return new BaseResponse<>(todoListRepository.findTodoListCustom());
    }
}
