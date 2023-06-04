package com.example.demo.model.todoList;

import com.example.demo.src.test.JpaTestRepository;
import com.example.demo.src.test.model.JpaTest;
import com.example.demo.src.todoList.TodoListRepository;
import com.example.demo.src.todoList.model.TodoList;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TodoListRepositoryTest {

    @Autowired
    TodoListRepository todoListRepository;

    @After(value = "execution(* com.test.controller.TestController.*(..))")
    public void cleanup() {
        todoListRepository.deleteAll();
    }

    @Test
    public void 할일저장_불러오기() {
        // given
        String todoContents = "JPA, test 코드 익숙해지기";
        int priority = 10;
        String todoDate = "2023-05-27";
//        int budget = 500;
//        Boolean isFinished = false;

//        todoListRepository.save(TodoList.builder()
//                .todoContents(todoContents)
//                .priority(priority)
//                .todoDate(todoDate)
////                .budget(budget)
////                .isFinished(isFinished)
//                .build());

        // when
        Optional<TodoList> todoList = todoListRepository.findAllByUserId();

        assertThat(todoList);
        // then
//        TodoList todoList = todoLists.get(0);
//        assertThat(todoList.getTodoContents()).isEqualTo(todoContents);
//        assertThat(todoList.getPriority()).isEqualTo(10);
//        assertThat(todoList.getTodoDate()).isEqualTo(todoDate);
//        assertThat(todoList.getBudget()).isEqualTo(budget);
//        assertThat(todoList.getIsFinished()).isEqualTo(isFinished);
    }


}
