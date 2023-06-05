package com.example.demo.src.todoList;

import com.example.demo.config.BaseException;
import com.example.demo.src.todoList.model.TodoListSave;
import com.example.demo.src.todoList.model.TodoList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
@Transactional
public class TodoListService {
    private final TodoListRepository todoListRepository;
    
    @Autowired
    public TodoListService (TodoListRepository todoListRepository) {
        this.todoListRepository = todoListRepository;
    }

    public TodoList writeTodo(TodoListSave todoListSave) throws BaseException {
        try {

            TodoList todoList =
                     TodoList.builder()
                             .userId(todoListSave.getUserId())
                             .todoContents(todoListSave.getTodoContents())
                             .priority(todoListSave.getPriority())
                             .todoDate(todoListSave.getTodoDate())
                             .budget(todoListSave.getBudget())
                             .isFinished(false)
                             .build();

            return todoListRepository.save(todoList);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

//    EntityManager entityManager = null;
//    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//
//    CriteriaQuery<TodoList> criteriaQuery = criteriaBuilder.createQuery(TodoList.class);
//    Root<TodoList> root = criteriaQuery.from(TodoList.class);
//    Predicate predicates = criteriaBuilder.equal(root.get("userId"), 1);
//            criteriaQuery.where(predicates);
//    TypedQuery<TodoList> todoListQuery = entityManager.createQuery(criteriaQuery);
//    List<TodoList> todoListRes = todoListQuery.getResultList();
//            for (TodoList a : todoListRes) {
//        System.out.println(a.getTodoContents());
//    }
}
