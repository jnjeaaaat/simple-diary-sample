package com.example.demo.src.todoList;

import com.example.demo.config.BaseException;
import com.example.demo.src.todoList.model.PostTodoListReq;
import com.example.demo.src.todoList.model.PostTodoListRes;
import com.example.demo.src.todoList.model.TodoList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
@Transactional
public class TodoListService {

    @Autowired
    private TodoListRepository todoListRepository;

    public TodoList save(PostTodoListReq postTodoListReq) throws BaseException {
        try {
            TodoList todoListRes =
                    todoListRepository.save(TodoList.builder()
                            .userId(postTodoListReq.getUserId())
                            .todoContents(postTodoListReq.getTodoContents())
                            .priority(postTodoListReq.getPriority())
                            .todoDate(postTodoListReq.getTodoDate())
                            .budget(postTodoListReq.getBudget())
                            .isFinished(false)
                            .build());

            return todoListRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
