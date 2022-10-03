package vn.com.assistant.fqcbackend.service.imp;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.com.assistant.fqcbackend.dto.PasswordRequestDTO;
import vn.com.assistant.fqcbackend.dto.StaffRequestDTO;
import vn.com.assistant.fqcbackend.dto.StaffResponseDTO;
import vn.com.assistant.fqcbackend.entity.User;
import vn.com.assistant.fqcbackend.exception.InvalidException;
import vn.com.assistant.fqcbackend.repository.UserRepository;
import vn.com.assistant.fqcbackend.service.StaffService;
import vn.com.assistant.fqcbackend.mapper.StaffMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
@PropertySource(value = "classpath:messages.properties", encoding = "UTF-8")
public class StaffServiceImp implements StaffService {

    @Value("${defaultPassword}")
    private String defaultPassword;
    private final UserRepository userRepository;
    private final Environment env;
    private final PasswordEncoder passwordEncoder;


    @Override
    public List<StaffResponseDTO> fetch() {
        List<User> userList = userRepository.findAllByOrderByCreatedTimeDesc();
        return StaffMapper.INSTANCE.listUserToListStaffResponseDTO(userList);
    }

    @Override
    public void create(StaffRequestDTO staffRequestDTO) {
        User user = StaffMapper.INSTANCE.staffRequestDTOtoUser(staffRequestDTO);
        user.setEncryptedPassword(passwordEncoder.encode(defaultPassword));
        userRepository.save(user);
    }

    @Override
    public void update(StaffRequestDTO staffRequestDTO, String staffId) {
        User user = userRepository.findById(staffId).orElseThrow(() -> new InvalidException(env.getProperty("staff.notExisted")));
        StaffMapper.INSTANCE.updateUserFromStaffRequestDTO(staffRequestDTO, user);
        userRepository.save(user);
    }

    @Override
    public void resetPassword(String staffId) {
        User user = userRepository.findById(staffId).orElseThrow(() -> new InvalidException(env.getProperty("staff.notExisted")));
        user.setEncryptedPassword(passwordEncoder.encode(defaultPassword));
        userRepository.save(user);
    }
}
