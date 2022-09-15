package vn.com.assistant.fqcbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.com.assistant.fqcbackend.Utils.JwtTokenUtil;
import vn.com.assistant.fqcbackend.dto.ResponseBodyDTO;
import vn.com.assistant.fqcbackend.dto.UserCredentialRequestDTO;
import vn.com.assistant.fqcbackend.dto.UserCredentialResponseDTO;
import vn.com.assistant.fqcbackend.entity.UserDetailsImpl;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@PropertySource(value = "classpath:messages.properties", encoding = "UTF-8")
public class AuthServiceImpl implements AuthService{
    private final AuthenticationManager authManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final Environment env;

    @Override
    public ResponseBodyDTO login(UserCredentialRequestDTO requestDTO) {
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(requestDTO.getCode(), requestDTO.getPassword());
        Authentication authentication = authManager.authenticate(authenticationToken);
        if (authentication.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = jwtTokenUtil.generateToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            String role = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining());

            UserCredentialResponseDTO responseDTO =
                    new UserCredentialResponseDTO(userDetails.getId(), userDetails.getCode(), token, role);
            return new ResponseBodyDTO(env.getProperty("login.success"), "OK", responseDTO);
        }
        return new ResponseBodyDTO("Đăng nhập ko thành công", "UNAUTHORIZED", null);
    }
}