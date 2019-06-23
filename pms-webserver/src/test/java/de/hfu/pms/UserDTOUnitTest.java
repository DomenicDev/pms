package de.hfu.pms;

import de.hfu.pms.model.User;
import de.hfu.pms.model.UserRole;
import de.hfu.pms.shared.dto.UserDTO;
import org.junit.Test;
import org.modelmapper.ModelMapper;

import static org.junit.Assert.assertEquals;

public class UserDTOUnitTest {

    private ModelMapper modelMapper = new ModelMapper();
    @Test
    public void whenConvertUserEntityToUserDTO_thenCorrect(){
        User user = new User();
        user.setUsername("bob");
        user.setForename("Bob");
        user.setLastname("Baumeiser");
        user.setEmail("bob@example.com");
        user.setPassword("12345");
        user.setRole(UserRole.ADMIN);

        UserDTO userDTO = modelMapper.map(user,UserDTO.class);

        assertEquals(user.getUsername(),userDTO.getUsername());
        assertEquals(user.getForename(),userDTO.getForename());
        assertEquals(user.getLastname(),userDTO.getLastname());
        assertEquals(user.getEmail(),userDTO.getEmail());
        assertEquals(user.getPassword(),userDTO.getPassword());
        assertEquals(user.getRole().name(),userDTO.getRole().name());


    }
}
