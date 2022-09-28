package vn.com.assistant.fqcbackend;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import vn.com.assistant.fqcbackend.dto.PasswordRequestDTO;
import vn.com.assistant.fqcbackend.dto.StaffRequestDTO;
import vn.com.assistant.fqcbackend.entity.enums.Role;
import vn.com.assistant.fqcbackend.entity.User;
import vn.com.assistant.fqcbackend.exception.InvalidException;
import vn.com.assistant.fqcbackend.repository.UserRepository;
import vn.com.assistant.fqcbackend.service.imp.StaffServiceImp;
import vn.com.assistant.fqcbackend.mapper.StaffMapper;

import java.util.Date;
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
    @Value("${defaultPassword}")
    private String defaultPassword;
    @Mock
    UserRepository userRepository;
    @Mock
    Environment env;
    @Mock
    PasswordEncoder passwordEncoder;
    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;
    @InjectMocks
    StaffServiceImp staffService;

    @Test
    void canFetch(){
        staffService.fetch();
        verify(userRepository).findAllByOrderByCreatedTimeDesc();
    }
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
        //given
        User user = genMockStaff();
        String staffId = UUID.randomUUID().toString();
        user.setId(staffId);
        given(userRepository.findById(user.getId())).willReturn(Optional.of(user));
        userArgumentCaptor = ArgumentCaptor.forClass(User.class);

        //when
        staffService.resetPassword(staffId);

        //then
        Mockito.verify(userRepository).save(userArgumentCaptor.capture());
        User capturedCustomer = userArgumentCaptor.getValue();
        assertThat(capturedCustomer.getPassword()).isEqualTo(defaultPassword);
    }

    @Test
    void canChangePassword() {
        //given
    }

    private StaffRequestDTO genMockStaffRequest(){
        StaffRequestDTO requestDTO = new StaffRequestDTO();
        requestDTO.setCode("STAFF1");
        requestDTO.setName("Nguyen Van A");
        requestDTO.setRole(Role.STAFF);
        return  requestDTO;
    }

    private User genMockStaff(){
        return new User(UUID.randomUUID().toString(),"STAFF", "Nguyen Van B",
                "$2a$12$3I0agSHQOyu7lUrM.oEba.soQQGTIWYmu5nEXJ9J2h225VNVT264K", "STAFF", false,new Date());
    }

    private PasswordRequestDTO genMockPasswordRequest(){
        PasswordRequestDTO requestDTO = new PasswordRequestDTO();
        requestDTO.setOldPassword("12345");
        requestDTO.setNewPassword("sa");
        requestDTO.setConfirmPassword("sa");
        return requestDTO;
    }

}
