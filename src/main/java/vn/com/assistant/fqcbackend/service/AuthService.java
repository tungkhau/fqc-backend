package vn.com.assistant.fqcbackend.service;

import vn.com.assistant.fqcbackend.dto.ResponseBodyDTO;
import vn.com.assistant.fqcbackend.dto.UserCredentialRequestDTO;

public interface AuthService {
    ResponseBodyDTO login (UserCredentialRequestDTO requestDTO);
}
