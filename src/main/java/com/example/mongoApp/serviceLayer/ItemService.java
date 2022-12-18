package com.example.mongoApp.serviceLayer;

import com.example.mongoApp.DTOs.ItemDTO;
import com.example.mongoApp.controllers.handlers.exceptions.model.ResourceNotFoundException;
import com.example.mongoApp.entities.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.mongoApp.repositoryLayer.IItemRepository;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemService {
    private final IItemRepository _itemRepository;
    @Autowired
    public ItemService (IItemRepository _itemRepository) {
        this._itemRepository = _itemRepository;
    }
    public List<ItemDTO> getItems() {
        List<Item> items = this._itemRepository.findAll();
        return items.stream()
                .map( (item) -> new ItemDTO(
                        item.getId(),
                        item.getName(),
                        item.getQuantity(),
                        item.getCategory()
                ))
                .collect(Collectors.toList());
    }
    public ItemDTO getItemByID(String id) throws ResourceNotFoundException {
        Optional<Item> item = _itemRepository.findById(id);
        if( item.isPresent()){
            Item myItem = item.get();
            return new ItemDTO(
                    myItem.getId(),
                    myItem.getName(),
                    myItem.getQuantity(),
                    myItem.getCategory()
            );
        }
        else {
            throw new ResourceNotFoundException("Item with id : " + id + "was not found");
        }
    }

    public String insertItem(ItemDTO itemDTO) {
        Item item = new Item(
                itemDTO.getId(),
                itemDTO.getName(),
                itemDTO.getQuantity(),
                itemDTO.getCategory()
        );
        Item insertedItem = this._itemRepository.save(item);
        return insertedItem.getId();
    }

    public String updateItem(String id, Map<Object, Object> fields) {
        Optional<Item> item = _itemRepository.findById(id);
        if( item.isPresent() ) {
            fields.forEach( (key, value) -> {
                Field field = ReflectionUtils.findField(Item.class, (String) key);
                assert field != null;
                field.setAccessible(true);
                ReflectionUtils.setField(field, item.get(), value);
            });
            Item savedItem = _itemRepository.save(item.get());
            return savedItem.getId();
        }
        else {
            throw new ResourceNotFoundException("Item with id : " + id + "was not found");
        }
    }

    public void deleteItemById(String id) {
        _itemRepository.deleteById(id);
    }

}
