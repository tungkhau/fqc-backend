package vn.com.assistant.fqcbackend.service.imp;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.com.assistant.fqcbackend.dto.ResponseBodyDTO;
import vn.com.assistant.fqcbackend.dto.UserRequestDTO;
import vn.com.assistant.fqcbackend.dto.UserResponseDTO;
import vn.com.assistant.fqcbackend.entity.Token;
import vn.com.assistant.fqcbackend.entity.User;
import vn.com.assistant.fqcbackend.exception.InvalidException;
import vn.com.assistant.fqcbackend.service.AuthService;
import vn.com.assistant.fqcbackend.service.TokenService;
import vn.com.assistant.fqcbackend.utility.JwtTokenUtility;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@PropertySource(value = "classpath:messages.properties", encoding = "UTF-8")
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authManager;
    private final JwtTokenUtility jwtTokenUtility;
    private final Environment env;
    private final TokenService tokenService;

    @Override
    public ResponseBodyDTO login(UserRequestDTO requestDTO) {
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(requestDTO.getCode(), requestDTO.getPassword());
        try {
            Authentication authentication = authManager.authenticate(authenticationToken);
            if (authentication.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
                String token = jwtTokenUtility.generateToken(authentication);
                User userDetails = (User) authentication.getPrincipal();
                String role = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining());
                UserResponseDTO responseDTO =
                        new UserResponseDTO(userDetails.getId(), userDetails.getCode(), token, role);
                tokenService.save(new Token(responseDTO.getCode(), responseDTO.getToken()));
                return new ResponseBodyDTO(env.getProperty("login.success"), "OK", responseDTO);
            }
            return new ResponseBodyDTO("Đăng nhập ko thành công", "UNAUTHORIZED", null);
        } catch (Exception e) {
            throw new InvalidException("Sai mã nhân viên hoặc mật khẩu");
        }
    }

}
