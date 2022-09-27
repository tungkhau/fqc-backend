package vn.com.assistant.fqcbackend.service.imps;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.assistant.fqcbackend.entity.User;
import vn.com.assistant.fqcbackend.exception.UnauthorizedException;
import vn.com.assistant.fqcbackend.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImp implements UserDetailsService {
    private final UserRepository _repository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = _repository.findByCode(username);
        if (user != null) {
            return user;
        } else throw new UnauthorizedException("Username " + username + " not found ");
    }
}
