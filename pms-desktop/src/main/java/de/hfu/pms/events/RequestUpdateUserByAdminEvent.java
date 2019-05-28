package de.hfu.pms.events;

        import de.hfu.pms.shared.dto.UserDTO;

public class RequestUpdateUserByAdminEvent {
    private UserDTO userDTO;

    public RequestUpdateUserByAdminEvent(UserDTO userDTO){
        this.userDTO = userDTO;
    }

    public UserDTO getUser(){
        return userDTO;
    }
}
