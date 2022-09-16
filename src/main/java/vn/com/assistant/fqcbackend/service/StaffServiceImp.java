package vn.com.assistant.fqcbackend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.com.assistant.fqcbackend.dto.StaffRequestDTO;
import vn.com.assistant.fqcbackend.entity.User;
import vn.com.assistant.fqcbackend.exception.InvalidException;
import vn.com.assistant.fqcbackend.repository.UserRepository;
import vn.com.assistant.fqcbackend.utility.StaffMapper;

@Service
public class StaffServiceImp implements StaffService {

    @Value("${defaultPassword}")
    private String defaultPassword;
    private final UserRepository userRepository;
    private final Environment env;
    private final PasswordEncoder passwordEncoder;


    public StaffServiceImp(UserRepository userRepository, Environment env, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.env = env;
        this.passwordEncoder = passwordEncoder;
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
        StaffMapper.INSTANCE.updateUserFromStaffRequestDTO(staffRequestDTO,user);
        userRepository.save(user);
    }

    @Override
    public void resetPassword(String staffId) {
        User user = userRepository.findById(staffId).orElseThrow(() -> new InvalidException(env.getProperty("staff.notExisted")));
        user.setEncryptedPassword(passwordEncoder.encode(defaultPassword));
        userRepository.save(user);
    }
}
