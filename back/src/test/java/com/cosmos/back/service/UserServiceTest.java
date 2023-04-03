package com.cosmos.back.service;

import com.cosmos.back.annotation.EnableMockMvc;
import com.cosmos.back.dto.user.UserProfileDto;
import com.cosmos.back.dto.user.UserUpdateDto;
import com.cosmos.back.model.User;
import com.cosmos.back.repository.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@EnableMockMvc
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    @DisplayName("유저 정보 조회")
    @WithMockUser(username="테스트_최고관리자", roles = {"SUPER"})
    public void getUserTest() throws Exception{
        //given
        User user = User.builder().userSeq(1L).userName("test").build();

        when(userRepository.findByUserSeq(user.getUserSeq())).thenReturn(user)
                .thenThrow(new NullPointerException());

        //when
        UserProfileDto userProfileDtoSuccess = userService.getUser(user.getUserSeq());
        UserProfileDto userProfileDtoFail = userService.getUser(user.getUserSeq());

        //then
        assertThat(userProfileDtoSuccess.getUserSeq()).isEqualTo(user.getUserSeq());
        assertEquals(userProfileDtoSuccess.getUserName(), user.getUserName());
        assertEquals(userProfileDtoFail, null);
    }

    @Test
    @DisplayName("유저 정보 수정")
    @WithMockUser(username="테스트_최고관리자", roles = {"SUPER"})
    public void updateUserInfoTest() throws Exception {
        //given
        UserUpdateDto userUpdateDto = UserUpdateDto.builder()
                .userSeq(1L)
                .phoneNumber("01012341234")
                .build();
        User user = User.builder()
                .userSeq(1L)
                .build();
        when(userRepository.findByUserSeq(1L))
                .thenReturn(user);

        //when



        //then


    }
}
