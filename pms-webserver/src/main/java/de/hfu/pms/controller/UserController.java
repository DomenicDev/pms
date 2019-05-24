package de.hfu.pms.controller;


import com.fasterxml.jackson.databind.node.ObjectNode;
import de.hfu.pms.exceptions.UserNotFoundException;
import de.hfu.pms.exceptions.WrongPasswordException;
import de.hfu.pms.model.User;
import de.hfu.pms.model.UserRole;
import de.hfu.pms.service.UserService;
import de.hfu.pms.shared.dto.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService service;
    private final ModelMapper modelMapper;


    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.service = userService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO newUser(@RequestBody UserDTO userDTO) {
        User user = convertToEntity(userDTO);
        service.createUser(user);
        return userDTO;
    }

    @PostMapping("/delete/{username}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteUser(@PathVariable String username) {
        service.deleteUser(username);
        return "User " + username + " wurde erfolgreich gelöscht.";
    }

    @PostMapping("/updatePassword/{username}")
    @ResponseStatus(HttpStatus.OK)
    public String updatePassword(@PathVariable String username, @RequestBody ObjectNode json) {
        String newPassword = json.findValue("newPassword").asText();
        String oldPassword = json.findValue("oldPassword").asText();
        service.updatePassword(username, oldPassword, newPassword);
        return "Password sucessfully changed";
    }

    @PostMapping("/updateRole/{username}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO updateUserRole(@PathVariable String username, @RequestBody UserRole newRole) {
        User user = service.updateUserRole(username , newRole);
        UserDTO userDTO = convertToDTO(user);
        return userDTO;
    }

    @GetMapping("/get/{username}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getUser(@PathVariable String username) {
        try {
            User returnUser = service.getUser(username);
            return convertToDTO(returnUser);
        } catch (NullPointerException e) {
            throw new UserNotFoundException(username);
        }
    }

    @GetMapping("/getList")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllUsers() {
        return service.getUserList();
    }

    private User convertToEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    private UserDTO convertToDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    //Error Response

    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "This Username already exists.")
    @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
    public void uniqueUsernameViolation() {
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "This username is not in the database.")
    @ExceptionHandler(UserNotFoundException.class)
    public String UserNotFoundExceptionHandler(UserNotFoundException ux) {
        return ux.getMessage();
    }

    @ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Wrong Password")
    @ExceptionHandler(WrongPasswordException.class)
    public String WrongPasswordExceptionHandler(WrongPasswordException ex) {
        return ex.getMessage();
    }
}