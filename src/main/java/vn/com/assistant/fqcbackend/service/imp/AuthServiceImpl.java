package vn.com.assistant.fqcbackend.service.imp;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.com.assistant.fqcbackend.dto.PasswordRequestDTO;
import vn.com.assistant.fqcbackend.dto.ResponseBodyDTO;
import vn.com.assistant.fqcbackend.dto.UserRequestDTO;
import vn.com.assistant.fqcbackend.dto.UserResponseDTO;
import vn.com.assistant.fqcbackend.entity.Token;
import vn.com.assistant.fqcbackend.entity.User;
import vn.com.assistant.fqcbackend.exception.InvalidException;
import vn.com.assistant.fqcbackend.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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
    @Override
    public void changePassword(PasswordRequestDTO passwordRequestDTO, String id){
        User user = userRepository.findById(id).orElseThrow(() -> new InvalidException(env.getProperty("staff.notExisted")));
        validationPassword(passwordRequestDTO, user);
        user.setEncryptedPassword(passwordEncoder.encode(passwordRequestDTO.getNewPassword()));
        userRepository.save(user);
    }

    private void validationPassword(PasswordRequestDTO passwordRequestDTO, User user){
        if(!passwordEncoder.matches(passwordRequestDTO.getOldPassword(), user.getEncryptedPassword()))
            throw new InvalidException(env.getProperty("staff.wrongPassword"));

        if(!passwordRequestDTO.getNewPassword().equals(passwordRequestDTO.getConfirmPassword()))
            throw new InvalidException(env.getProperty("staff.notMatchPassword"));
    }

}
