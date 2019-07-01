package de.hfu.pms.controller;


import de.hfu.pms.exceptions.UserNotFoundException;
import de.hfu.pms.exceptions.WrongPasswordException;
import de.hfu.pms.model.User;
import de.hfu.pms.model.UserRole;
import de.hfu.pms.service.UserService;
import de.hfu.pms.shared.dto.ChangeUserInformationDTO;
import de.hfu.pms.shared.dto.UpdateUserDTO;
import de.hfu.pms.shared.dto.UserDTO;
import de.hfu.pms.shared.dto.UserInfoDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.LinkedList;
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

    @GetMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    public UserInfoDTO getCurrentUser(Principal principal) {
        String name = principal.getName();
        if (name == null) {
            return null;
        }
        User user = service.getUser(name);
        return convertToInfoDTO(user);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public UserInfoDTO newUser(@RequestBody UserDTO userDTO) {
        User user = convertToEntity(userDTO);
        return convertToInfoDTO(service.createUser(user));
    }

    @DeleteMapping("/delete/{username}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteUser(@PathVariable String username) {
        service.deleteUser(username);
        return "User " + username + " wurde erfolgreich gelöscht.";
    }

    @PostMapping("/updatePassword/{username}")
    @ResponseStatus(HttpStatus.OK)
    public UserInfoDTO updatePassword(@PathVariable String username, @RequestBody String newPassword) {
        return convertToInfoDTO(service.updatePassword(username, newPassword));
    }

    @PostMapping("/updateRole/{username}")
    @ResponseStatus(HttpStatus.OK)
    public UserInfoDTO updateUserRole(@PathVariable String username, @RequestBody UserRole newRole) {
        User user = service.updateRole(username , newRole);
        return convertToInfoDTO(user);
    }

    @PostMapping("/updateEmail/{username}")
    @ResponseStatus(HttpStatus.OK)
    public UserInfoDTO updateUserEmail(@PathVariable String username, @RequestBody String email) {
        return convertToInfoDTO(service.updateEmail(username , email));
    }

    @PatchMapping("/patch/{username}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> patchUserInformation(@PathVariable String username, @RequestBody ChangeUserInformationDTO changeDTO, Principal principal) {
        if (!principal.getName().equals(username)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        String forename = changeDTO.getForename();
        String surname = changeDTO.getLastName();
        String email = changeDTO.getEmail();

        User updatedUser = service.updateInformation(username, forename, surname, email);
        return ResponseEntity.ok(convertToInfoDTO(updatedUser));
    }

    @PatchMapping("/update/{username}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserInfoDTO> patchUser(@PathVariable String username, @RequestBody UpdateUserDTO userDTO){
        String forename = userDTO.getForename();
        String surname = userDTO.getSurname();
        UserRole role = userRoleHelper(userDTO.getRole());
        String email = userDTO.getEmail();

        if (forename == null || surname == null || role == null || email == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        // update
        service.updateInformation(username, forename, surname, email);
        service.updateRole(username, role);

        // return updated user as InfoDTO
        User user = service.getUser(username);
        return ResponseEntity.ok(convertToInfoDTO(user));
    }

    @GetMapping("/get/{username}")
    @ResponseStatus(HttpStatus.OK)
    public UserInfoDTO getUser(@PathVariable String username) {
        return convertToInfoDTO(service.getUser(username));
    }

    @GetMapping("/getList")
    @ResponseStatus(HttpStatus.OK)
    public List<UserInfoDTO> getAllUsers() {
        List<UserInfoDTO> infoUserList = new LinkedList<>();
        for (User user : service.getUserList()) {
            infoUserList.add(convertToInfoDTO(user));
        }
        return infoUserList;
    }

    private User convertToEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    private UserDTO convertToDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    private UserInfoDTO convertToInfoDTO(User user) {
        return modelMapper.map(user, UserInfoDTO.class);
    }

    private UserRole userRoleHelper(de.hfu.pms.shared.enums.UserRole userRole){

        if(userRole == null) {
            return null;
        }
        return UserRole.valueOf(userRole.name());
    }

    //Error Response

    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "This username already exists.")
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