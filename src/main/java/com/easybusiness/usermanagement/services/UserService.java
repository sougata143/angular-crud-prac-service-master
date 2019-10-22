package com.easybusiness.usermanagement.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.easybusiness.usermanagement.DTO.UserDTO;

public interface UserService {

    public UserDTO getUser(String username);

    public ResponseEntity<UserDTO> persistUser(UserDTO user);

    public List<UserDTO> populateUserList();

    public UserDTO populateOneUserDetails(Long userId);

    public void destroyUser(Long userId);

    public List<UserDTO> getFieldEq(final Class<UserDTO> type, final String propertyName, final Object value, int offset,
	    int size);

    public void persistUser(UserDTO loggedUser, boolean changePassword);

    public void activateUser(Long userId);

    public void deActivateUser(Long userId);

    public UserDTO getActiveUser(String email);
    

    ResponseEntity<UserDTO> updateUser(UserDTO userDTO);
    public UserDTO getByEmailAndPassword(String email, String password); 
    
	
}
