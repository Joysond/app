package com.todo.app.service;

import com.todo.app.models.Item;
import com.todo.app.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    ItemRepository itemRepository;

    /**
     * Update / save  of any new item
     *
     * @param item
     */
    public void save(Item item) {
        itemRepository.save(item);
    }

    public void update(Item item) {
        Item savedItem = itemRepository.getOne(item.getId());
        savedItem.setItem(item.getItem());
        savedItem.setState(item.getState());
        save(savedItem);
    }
    /**
     * Delete an item
     *
     * @param item
     */
    public void delete(Long id) {
        itemRepository.deleteById(id);
    }

    public Optional<List<Item>> getAllItems() {
        return Optional.ofNullable(itemRepository.findAll(Sort.by(Sort.Order.asc("id"))));
    }
}
