package com.example.demo.src.todoList;

import com.example.demo.config.BaseException;
import com.example.demo.src.todoList.model.TodoList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
@Transactional
public class TodoListProvider {
    private final TodoListRepository todoListRepository;

    @Autowired
    public TodoListProvider (TodoListRepository todoListRepository) {
        this.todoListRepository = todoListRepository;
    }

    /**
     * userId로 특정 유저의 할일 목록 다 가져오기
     */
    public List<TodoListSpecific> getTodoListByUserId(Long userId) throws BaseException {
        try {
            List<TodoListSpecific> todoLists = todoListRepository.findAllByUserIdAndIsFinishedOrderByUpdatedAtDesc(TodoListSpecific.class, userId, false);
            return todoLists;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /**
     * todoId로 특정 할일 가져오기
     */
    public Optional<TodoListSpecific> getTodoListById(Long todoId) throws BaseException {
        try {
            Optional<TodoListSpecific> todoList = todoListRepository.findByTodoIdAndIsFinished(TodoListSpecific.class, todoId, false);
            return todoList;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /**
     * userId로 특정 유저의 할일 목록 다 가져오기
     */
    public List<TodoListSpecific> getTodoListByKeyWord(Long userId, String todoContents) throws BaseException {
        try {
            List<TodoListSpecific> todoLists = todoListRepository.findAllByAndUserIdAndIsFinishedAndTodoContentsContainingOrderByUpdatedAtDesc(TodoListSpecific.class, userId, false, todoContents);
            return todoLists;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
