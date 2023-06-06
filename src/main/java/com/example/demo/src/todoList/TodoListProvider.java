package com.example.demo.src.todoList;

import com.example.demo.config.BaseException;
import com.example.demo.src.todoList.model.TodoList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
@Transactional
public class TodoListProvider {
    private final TodoListRepository todoListRepository;

    @Autowired
    public TodoListProvider (TodoListRepository todoListRepository) {
        this.todoListRepository = todoListRepository;
    }

    public List<TodoListSpecific> getTodoListByUserId(Long userId) throws BaseException {
        try {
            List<TodoListSpecific> todoLists = todoListRepository.findAllByUserIdAndIsFinishedOrderByUpdatedAtDesc(TodoListSpecific.class, userId, false);
            return todoLists;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
