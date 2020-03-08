package com.todo.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.todo.app.models.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
