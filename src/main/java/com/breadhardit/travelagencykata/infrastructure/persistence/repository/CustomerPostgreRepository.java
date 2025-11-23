package com.breadhardit.travelagencykata.infrastructure.persistence.repository;

import com.breadhardit.travelagencykata.infrastructure.persistence.entity.CustomerEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class CustomerPostgreRepository implements CustomersJPARepository{
    CustomerEntity getByPassportNumber(String passportNumber){
        return null;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends CustomerEntity> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends CustomerEntity> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<CustomerEntity> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<String> strings) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public CustomerEntity getOne(String s) {
        return null;
    }

    @Override
    public CustomerEntity getById(String s) {
        return null;
    }

    @Override
    public CustomerEntity getReferenceById(String s) {
        return null;
    }

    @Override
    public <S extends CustomerEntity> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends CustomerEntity> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends CustomerEntity> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends CustomerEntity> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends CustomerEntity> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends CustomerEntity> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends CustomerEntity, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends CustomerEntity> S save(S entity) {
        return null;
    }

    @Override
    public <S extends CustomerEntity> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<CustomerEntity> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public List<CustomerEntity> findAll() {
        return List.of();
    }

    @Override
    public List<CustomerEntity> findAllById(Iterable<String> strings) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public void delete(CustomerEntity entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends CustomerEntity> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<CustomerEntity> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<CustomerEntity> findAll(Pageable pageable) {
        return null;
    }
}
