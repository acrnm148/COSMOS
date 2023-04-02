package com.cosmos.back.service;

import com.cosmos.back.annotation.EnableMockMvc;
import com.cosmos.back.dto.user.UserProfileDto;
import com.cosmos.back.dto.user.UserUpdateDto;
import com.cosmos.back.model.NotificationType;
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
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@EnableMockMvc
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private StringRedisTemplate redisTemplate;

    @MockBean
    private NotificationService notificationService;

    @Test
    @DisplayName("유저 정보 조회")
    @WithMockUser(username="테스트_최고관리자", roles = {"SUPER"})
    public void getUserTest() throws Exception{
        //given
        User user = User.builder()
                .userSeq(1L)
                .coupleSuccessDate("2023-04-01")
                .userName("test")
                .build();

        when(userRepository.findByUserSeq(user.getUserSeq()))
                .thenReturn(user)
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
    public void updateUserInfoTest() throws Exception{
        //given
        UserUpdateDto dto = UserUpdateDto.builder()
                .userSeq(1L)
                .phoneNumber("01012345678")
                .build();
        User user = User.builder()
                .userSeq(1L)
                .coupleSuccessDate("2023-04-01")
                .userName("test")
                .build();

        when(userRepository.findByUserSeq(1L))
                .thenReturn(user);
        //when(userRepository.save(user)).thenReturn(user);

        //when
        userService.updateUserInfo(dto);

        //then
        //verify(userRepository).save(user);
        assertThat(user.getPhoneNumber()).isEqualTo(dto.getPhoneNumber());
    }

    @Test
    @DisplayName("Redis를 이용한 로그아웃")
    @WithMockUser(username="테스트_최고관리자", roles = {"SUPER"})
    public void logoutTest() throws Exception{
        //given
        String redisUserSeq = "1";
        when(redisTemplate.delete(redisUserSeq)).thenReturn(true);

        //when
        redisTemplate.delete(redisUserSeq);

        //then
        verify(redisTemplate, times(1)).delete(anyString());
    }

    /*
    @Test
    @DisplayName("커플 연결 수락")
    @WithMockUser(username="테스트_최고관리자", roles = {"SUPER"})
    public void acceptCoupleTest() throws Exception{
        //given
        User user = User.builder()
                .userSeq(1L)
                .build();
        User coupleUser = User.builder()
                .userSeq(2L)
                .build();
        User coupleUser2 = null;
        User user3 = User.builder()
                .userSeq(4L)
                .coupleId(123123L)
                .build();
        User coupleUser3 = User.builder()
                .userSeq(5L)
                .coupleId(123123L)
                .build();
        String nowDate = "2023-04-01";

        //verify(userRepository).save(user);
        //verify(userRepository).save(coupleUser);
        when(userService.acceptCouple(1L,2L, 123123L))
                .thenReturn(123123L);
        //when(userService.acceptCouple(1L,11L, 123123L)).thenReturn(null);
        when(userService.acceptCouple(11L,12L, 123123L))
                .thenReturn(null);
        doNothing().when(notificationService).send(anyLong(), NotificationType.MESSAGE, anyString(),anyString());

        //when
        Long code = userService.acceptCouple(1L,2L, 123123L);
        userService.acceptCouple(1L,3L, 123123L);
        userService.acceptCouple(4L,5L, 123123L);

        //then
        assertThat(user.getCoupleId()).isEqualTo(123123L);
    }
     */

    @Test
    @DisplayName("커플 연결 끊기")
    @WithMockUser(username="테스트_최고관리자", roles = {"SUPER"})
    public void disconnectCoupleTest() throws Exception{
        //given
        List<User> list = new ArrayList<>();
        User user = User.builder().userSeq(1L).build();
        User coupleUser = User.builder().userSeq(2L).build();
        when(userRepository.findByCoupleId(anyLong())).thenReturn(list);
        list.add(user);
        list.add(coupleUser);
        //when
        userService.disconnectCouple(anyLong());
        //then
        assertThat(user.getCoupleYn()).isEqualTo("N");
        verify(userRepository, times(1)).findByCoupleId(anyLong());
    }

    @Test
    @DisplayName("사용자 유형 등록")
    @WithMockUser(username="테스트_최고관리자", roles = {"SUPER"})
    public void saveTypesCoupleTest() throws Exception{

        //given
        String type1 = "testType1";
        String type2 = "testType2";
        User user = User.builder().userSeq(1L).type1(type1).type2(type2).build();
        //when(userService.saveTypes(anyLong(), anyString(), anyString())).thenReturn(user);
        when(userRepository.findByUserSeq(anyLong())).thenReturn(user);
        //when
        userService.saveTypes(1L, type1, type2);
        //then
        assertThat(user.getType1()).isEqualTo("testType1");
        verify(userRepository).save(user); //save가 수행되었는지 검증
    }
}
