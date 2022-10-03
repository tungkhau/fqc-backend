package vn.com.assistant.fqcbackend.service;

import vn.com.assistant.fqcbackend.dto.PasswordRequestDTO;
import vn.com.assistant.fqcbackend.dto.ResponseBodyDTO;
import vn.com.assistant.fqcbackend.dto.UserRequestDTO;

public interface AuthService {
    ResponseBodyDTO login (UserRequestDTO requestDTO);
    void changePassword (PasswordRequestDTO passwordRequestDTO, String id);
}
