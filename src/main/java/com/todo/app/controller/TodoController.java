package com.todo.app.controller;

import com.todo.app.Others.TodoResponse;
import com.todo.app.models.Item;
import com.todo.app.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@RequestMapping("/todo")
@RestController
public class TodoController {

    @Autowired
    ItemService itemService;

    @PostMapping("/item")
    public ResponseEntity<TodoResponse> save(@RequestBody Item item) {
        item.setCreatedDate(Date.from(Instant.now()));
        itemService.save(item);
        return new ResponseEntity<>(new TodoResponse("Item created", item.getId()), HttpStatus.CREATED);
    }

    @DeleteMapping("/item/{itemId}")
    public ResponseEntity<TodoResponse> delete(@PathVariable("itemId") Long id) {
        itemService.delete(id);
        return new ResponseEntity<>(new TodoResponse("Item deleted", id), HttpStatus.OK);
    }

    @PutMapping("/item")
    public ResponseEntity<TodoResponse> update(@RequestBody Item item) {
        if(item.getId() == null || item.getItem() == null
                || item.getItem().isEmpty() || item.getState() == null) {
            return new ResponseEntity<>(new TodoResponse("Invalid item", null), HttpStatus.BAD_REQUEST);
        }
        itemService.update(item);
        return new ResponseEntity<>(new TodoResponse("Item updated", item.getId()), HttpStatus.OK);
    }

    @GetMapping("/item/all")
    public ResponseEntity<List<Item>> getAllItems() {
        return new ResponseEntity<>(itemService.getAllItems().get(), HttpStatus.OK);
    }
}
