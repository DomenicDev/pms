package de.hfu.pms.controller;


import de.hfu.pms.model.UserRole;
import de.hfu.pms.service.UserService;
import de.hfu.pms.shared.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @RequestMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void newUser (@RequestBody UserDto user){
        service.createUser(user);
    }

    @PostMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser (@RequestBody String username){
        service.deleteUser(username);
    }

    @PostMapping("/updateRole")
    @ResponseStatus(HttpStatus.OK)
    public void updateUserRole (@RequestBody String username, UserRole role){
        service.updateUserRole(username,role);
    }



}