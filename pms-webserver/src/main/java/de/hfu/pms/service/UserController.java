package de.hfu.pms.service;

import de.hfu.pms.shared.dto.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void newUser (@RequestBody User user){
        new de.hfu.pms.model.User(user.getUsername(),user.getPassword());
    }


}