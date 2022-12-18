package com.example.mongoApp.serviceLayer;

import com.example.mongoApp.DTOs.UserDTO;
import com.example.mongoApp.controllers.handlers.exceptions.model.ResourceNotFoundException;
import com.example.mongoApp.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.mongoApp.repositoryLayer.IUserRepository;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final IUserRepository _userRepository;
    @Autowired
    public UserService (IUserRepository _userRepository) {
        this._userRepository = _userRepository;
    }
    public List<UserDTO> getUsers() {
        List<User> users = this._userRepository.findAll();
        return users.stream()
                .map( (user) -> new UserDTO(
                        user.getId(),
                        user.getRole(),
                        user.getUsername(),
                        user.getPassword()
                ))
                .collect(Collectors.toList());
    }
    public UserDTO getUserByID(String id) throws ResourceNotFoundException {
        Optional<User> user = _userRepository.findById(id);
        if( user.isPresent()){
            User myUser = user.get();
            return new UserDTO(
                    myUser.getId(),
                    myUser.getRole(),
                    myUser.getUsername(),
                    myUser.getPassword()
            );
        }
        else {
            throw new ResourceNotFoundException("User with id : " + id + "was not found");
        }
    }

    public String insertUser(UserDTO userDTO) {
        User user = new User(
                userDTO.getId(),
                userDTO.getRole(),
                userDTO.getUsername(),
                userDTO.getPassword()
        );
        User insertedUser = this._userRepository.save(user);
        return insertedUser.getId();
    }

    public String updateUser(String id, Map<Object, Object> fields) {
        Optional<User> user = _userRepository.findById(id);
        if( user.isPresent() ) {
            fields.forEach( (key, value) -> {
                Field field = ReflectionUtils.findField(User.class, (String) key);
                assert field != null;
                field.setAccessible(true);
                ReflectionUtils.setField(field, user.get(), value);
            });
            User savedUser = _userRepository.save(user.get());
            return savedUser.getId();
        }
        else {
            throw new ResourceNotFoundException("User with id : " + id + "was not found");
        }
    }

    public void deleteUserById(String id) {
        _userRepository.deleteById(id);
    }

}
