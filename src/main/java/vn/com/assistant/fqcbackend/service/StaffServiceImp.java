package vn.com.assistant.fqcbackend.service;

import org.springframework.stereotype.Service;
import vn.com.assistant.fqcbackend.dto.StaffRequestDTO;
import vn.com.assistant.fqcbackend.entity.User;
import vn.com.assistant.fqcbackend.repository.UserRepository;
import vn.com.assistant.fqcbackend.utility.StaffMapper;

@Service
public class StaffServiceImp implements StaffService {
    private UserRepository userRepository;

    public StaffServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void create(StaffRequestDTO staffRequestDTO) {
        User user = StaffMapper.INSTANCE.staffRequestDTOtoUser(staffRequestDTO);

    }

    @Override
    public void update(StaffRequestDTO staffRequestDTO) {

    }

    @Override
    public void resetPassword(String id) {

    }
}
