package vn.com.assistant.fqcbackend;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import vn.com.assistant.fqcbackend.dto.StaffRequestDTO;
import vn.com.assistant.fqcbackend.entity.Role;
import vn.com.assistant.fqcbackend.entity.User;
import vn.com.assistant.fqcbackend.exception.InvalidException;
import vn.com.assistant.fqcbackend.repository.UserRepository;
import vn.com.assistant.fqcbackend.service.StaffServiceImp;
import vn.com.assistant.fqcbackend.utility.StaffMapper;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {StaffServiceImp.class})
@PropertySource(value = "classpath:messages.properties", encoding = "UTF-8")
public class StaffServiceTests {
    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    Environment env;
    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;
    @InjectMocks
    StaffServiceImp staffService;

    @Test
    void canCreate() {
        //given
        StaffRequestDTO requestDTO = genMockStaffRequest();
        userArgumentCaptor = ArgumentCaptor.forClass(User.class);

        User user = StaffMapper.INSTANCE.staffRequestDTOtoUser(requestDTO);
        //when
        staffService.create(requestDTO);
        //then
        Mockito.verify(userRepository).save(userArgumentCaptor.capture());
        User capturedCustomer = userArgumentCaptor.getValue();

        assertThat(capturedCustomer)
                .usingRecursiveComparison()
                .ignoringFields("id", "encryptedPassword")
                .isEqualTo(user);
    }

    @Test
    void canUpdate(){
        //give
        StaffRequestDTO requestDTO = genMockStaffRequest();
        String staffId = UUID.randomUUID().toString();
        User user = genMockStaff();
        user.setId(staffId);
        given(userRepository.findById(user.getId())).willReturn(Optional.of(user));
        userArgumentCaptor = ArgumentCaptor.forClass(User.class);

        //when
        staffService.update(requestDTO, staffId);

        //then
        Mockito.verify(userRepository).save(userArgumentCaptor.capture());
        User capturedCustomer = userArgumentCaptor.getValue();
        assertThat(capturedCustomer)
                .usingRecursiveComparison()
                .ignoringFields("encryptedPassword")
                .isEqualTo(user);
    }

    @Test
    void updateFailedNotFound(){
        //given
        StaffRequestDTO requestDTO = genMockStaffRequest();
        String staffId = UUID.randomUUID().toString();
        given(userRepository.findById(staffId)).willReturn(Optional.empty());
        when(env.getProperty("staff.notExisted")).thenReturn("Msg");

        //when & then
        Assertions.assertThatThrownBy(()-> staffService.update(requestDTO, staffId))
                .isInstanceOf(InvalidException.class)
                .hasMessageContaining("Msg");
        verify(userRepository, never()).save(any());
    }

    @Test
    void canResetPassword() {

    }

    private StaffRequestDTO genMockStaffRequest(){
        StaffRequestDTO requestDTO = new StaffRequestDTO();
        requestDTO.setCode("STAFF1");
        requestDTO.setName("Nguyen Van A");
        requestDTO.setRole(Role.STAFF);
        return  requestDTO;
    }

    private User genMockStaff(){
        return new User(UUID.randomUUID().toString(),"STAFF", "Nguyen Van B", "12345", "STAFF", false);
    }

}
