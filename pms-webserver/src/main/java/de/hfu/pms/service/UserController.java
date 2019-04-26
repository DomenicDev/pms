package de.hfu.pms.service;


import de.hfu.pms.model.UserRole;
import de.hfu.pms.service.impl.UserServiceImpl;
import de.hfu.pms.shared.dto.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService service = new UserServiceImpl();

    @PostMapping("/create")
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