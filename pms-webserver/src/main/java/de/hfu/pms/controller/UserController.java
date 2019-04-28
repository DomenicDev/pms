package de.hfu.pms.controller;


import de.hfu.pms.controller.exceptions.UserNotFoundException;
import de.hfu.pms.dao.UserDao;
import de.hfu.pms.model.User;
import de.hfu.pms.service.UserService;
import de.hfu.pms.shared.dto.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserDao userDao;
    private final UserService service;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserDao userDao, UserService userService, ModelMapper modelMapper) {
        this.userDao = userDao;
        this.service = userService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public String newUser (@RequestBody UserDTO userDTO ){
        User user = convertToEntity(userDTO);
        service.createUser(user);
        return "User " + user.getUsername() + " wurde mit dem Passwort "+ user.getPasswordHash() + " und der Rolle " + user.getRole() + " erzeugt.";
    }

    @PostMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public String deleteUser (@RequestBody String username){
        service.deleteUser(username);
        return "User " + username + " wurde erfolgreich gelöscht.";
    }

    @PostMapping("/updateRole")
    @ResponseStatus(HttpStatus.OK)
    public String updateUserRole (@RequestBody User updateUser){
        service.updateUserRole(updateUser.getUsername(),updateUser.getRole());
        return "User " + updateUser.getUsername() + " wurde erfolgreich die Rolle " + updateUser.getRole() + " zugewiesen.";
    }

    @PostMapping("/getUser")
    @ResponseStatus(HttpStatus.OK)
    public String getUser (@RequestBody String username){
        try {
            User returnUser = service.getUser(username);
            return "User: " + returnUser.getUsername() + " Password: " + returnUser.getPasswordHash() + " Role: " + returnUser.getRole();
        }catch (NullPointerException e){
            throw new UserNotFoundException(username);
        }
    }

    private User convertToEntity(UserDTO userDTO) {
        User doctoralStudent = modelMapper.map(userDTO, User.class);
        return doctoralStudent;
    }

    private UserDTO convertToDTO(User user) {
        UserDTO doctoralStudentDTO = modelMapper.map(user, UserDTO.class);
        return doctoralStudentDTO;
    }


    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "This Username already exists.")
    @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
    public void uniqueUsernameViolation(){}

    @ResponseStatus(value= HttpStatus.BAD_REQUEST, reason = "This username is not in the database.")
    @ExceptionHandler(UserNotFoundException.class)
    public String UserNotFoundExceptionHandler(UserNotFoundException ux){
        return ux.getMessage();
    }
}