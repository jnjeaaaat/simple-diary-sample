package com.example.demo.src.todoList;

import com.example.demo.src.todoList.model.TodoList;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class TodoListRepositoryImpl implements TodoListRepository{

    private EntityManager entityManager;

    @Override
    public Optional<TodoList> findAllByUserId() {
        return Optional.empty();
    }

    @Override
    public List<TodoList> findAll() {
        return null;
    }

    @Override
    public List<TodoList> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<TodoList> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<TodoList> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(TodoList entity) {

    }

    @Override
    public void deleteAll(Iterable<? extends TodoList> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends TodoList> S save(S entity) {
        return null;
    }

    @Override
    public <S extends TodoList> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<TodoList> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends TodoList> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<TodoList> entities) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public TodoList getOne(Long aLong) {
        return null;
    }

    @Override
    public <S extends TodoList> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends TodoList> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends TodoList> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends TodoList> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends TodoList> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends TodoList> boolean exists(Example<S> example) {
        return false;
    }
}
