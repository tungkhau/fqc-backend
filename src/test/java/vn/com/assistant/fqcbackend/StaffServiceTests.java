package vn.com.assistant.fqcbackend;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import vn.com.assistant.fqcbackend.dto.StaffRequestDTO;
import vn.com.assistant.fqcbackend.entity.Role;
import vn.com.assistant.fqcbackend.entity.User;
import vn.com.assistant.fqcbackend.repository.UserRepository;
import vn.com.assistant.fqcbackend.service.StaffServiceImp;
import vn.com.assistant.fqcbackend.utility.StaffMapper;

import java.util.Optional;

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

        given(userRepository.findByCode(requestDTO.getCode())).willReturn(null);
        //when
        staffService.create(requestDTO);
        //then
        Mockito.verify(userRepository).save(userArgumentCaptor.capture());
        User capturedCustomer = userArgumentCaptor.getValue();

        assertThat(capturedCustomer)
                .usingRecursiveComparison()
                .ignoringFields("id", "")
                .isEqualTo(user);
    }

    @Test
    void createFailedCodeDuplicated(){
        //given
        StaffRequestDTO requestDTO = genMockStaffRequest();
        User user = StaffMapper.INSTANCE.staffRequestDTOtoUser(requestDTO);
        given(userRepository.findByCode(requestDTO.getCode())).willReturn(user);

        when(env.getProperty("staff.code.existed")).thenReturn("Msg");

        //then
        Assertions.assertThatThrownBy(()-> staffService.create(requestDTO))
                .hasMessageContaining("Msg");

        verify(userRepository, never()).save(any());

    }


    private StaffRequestDTO genMockStaffRequest(){
        StaffRequestDTO requestDTO = new StaffRequestDTO();
        requestDTO.setCode("STAFF1");
        requestDTO.setName("Nguyen Van A");
        requestDTO.setRole(Role.STAFF);
        return  requestDTO;
    }

}
