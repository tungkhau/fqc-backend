package vn.com.assistant.fqcbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.assistant.fqcbackend.entity.UserCredential;
import vn.com.assistant.fqcbackend.entity.UserDetailsImpl;
import vn.com.assistant.fqcbackend.exception.UnauthorizedException;
import vn.com.assistant.fqcbackend.repository.UserCredentialRepository;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImp implements UserDetailsService {
    private final UserCredentialRepository _repository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserCredential userCredential = _repository.findByUserCode(username);
        if (userCredential != null){
            return UserDetailsImpl.build(userCredential);
        }else throw new UnauthorizedException("Username " + username + " not found ");
    }
}
