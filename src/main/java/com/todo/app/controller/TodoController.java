package com.todo.app.controller;

import com.todo.app.others.TodoResponse;
import com.todo.app.models.Item;
import com.todo.app.models.User;
import com.todo.app.security.services.UserService;
import com.todo.app.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@RestController
public class TodoController {

    @Autowired
    ItemService itemService;

    @Autowired
    UserService userService;

    @PostMapping("/item")
    public ResponseEntity<TodoResponse> save(@RequestBody Item item) {
        item.setCreatedDate(Date.from(Instant.now()));
        itemService.save(item);
        User user = getUser();
        List<Item> userItems = user.getItems();
        userItems.add(item);
        user.setItems(userItems);
        userService.save(user);
        return new ResponseEntity<>(new TodoResponse("Item created", item.getId()), HttpStatus.CREATED);
    }

    @DeleteMapping("/item/{itemId}")
    public ResponseEntity<TodoResponse> delete(@PathVariable("itemId") Long id) {
        Item item = getUserItem(id);
        if(item == null) {
            throw new RuntimeException("User does not have this item with id: " + id);
        }
        User user = getUser();
        List<Item> userItems = user.getItems();
        userItems.remove(item);
        user.setItems(userItems);
        userService.save(user);
        return new ResponseEntity<>(new TodoResponse("Item deleted", id), HttpStatus.OK);
    }

    @PutMapping("/item")
    public ResponseEntity<TodoResponse> update(@RequestBody Item item) {
        if(item.getId() == null || item.getItem() == null
                || item.getItem().isEmpty() || item.getState() == null) {
            return new ResponseEntity<>(new TodoResponse("Invalid item", null), HttpStatus.BAD_REQUEST);
        }
        // Check if the user has this item in his todo list
        Item dbItem = getUserItem(item.getId());
        if(dbItem == null) throw new RuntimeException("Item does not exist with id: " + item.getId());
        itemService.update(item);
        return new ResponseEntity<>(new TodoResponse("Item updated", item.getId()), HttpStatus.OK);
    }

    @GetMapping("/item/all")
    public ResponseEntity<List<Item>> getAllItems() {
        return new ResponseEntity<>(getUser().getItems(), HttpStatus.OK);
    }

    @GetMapping("/item/all-items")
    public ResponseEntity<List<Item>> getAllItemsInSystem() {
        return new ResponseEntity<>(itemService.getAllItems().get(), HttpStatus.OK);
    }

    private User getUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserByUsername(username).orElse(null);
        if(user == null) {
            throw new RuntimeException("User does not exist with username: " + username);
        }
        return user;
    }

    private Item getUserItem(Long itemId) {
        List<Item> items = getUser().getItems();
        return items.stream().filter(
                savedItem -> savedItem.getId().equals(itemId)
        ).findAny().orElse(null);
    }
}
