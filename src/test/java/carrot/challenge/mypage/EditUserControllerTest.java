package carrot.challenge.mypage;

import carrot.challenge.domain.upload.FileStore;
import carrot.challenge.domain.user.dto.User;
import carrot.challenge.domain.user.repository.DBUserRepository;
import carrot.challenge.domain.user.service.UserService;
import carrot.challenge.web.mypage.MyPageForm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;

import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class EditUserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserService userService;

    @Autowired
    private FileStore userFileStore;

    @MockBean
    private DBUserRepository dbUserRepository;

    @Test
    void Get_쿠키값이_없을때_로그인페이지() throws Exception {
        //given

        //when
        MvcResult mvcResult = mvc.perform(get("/user/edit"))
                .andExpect(status().isOk())
                .andReturn();
        ModelAndView mav = mvcResult.getModelAndView();

        //then
        assertThat(mav.getViewName()).isEqualTo("Message");
        assertThat(mav.getModel().get("message")).isEqualTo("로그인이 필요한 화면입니다.");
        assertThat(mav.getModel().get("href")).isEqualTo("/user/login");
    }

    @Test
    void Get_쿠키값이_데이터베이스에_존재하지않을때_로그인페이지() throws Exception {
        //given
        Cookie cookie = new Cookie("id", "1");

        //when
        MvcResult mvcResult = mvc.perform(get("/user/edit")
                        .cookie(cookie))
                .andExpect(status().isOk())
                .andReturn();
        ModelAndView mav = mvcResult.getModelAndView();

        //then
        assertThat(mav.getViewName()).isEqualTo("Message");
        assertThat(mav.getModel().get("message")).isEqualTo("로그인이 필요한 화면입니다.");
        assertThat(mav.getModel().get("href")).isEqualTo("/user/login");
    }

    @Test
    void Get_로그인된_상태에서_데이터_잘가져오는지_테스트() throws Exception {
        //given
        doReturn(
                Optional.of(
                        User.builder()
                                .user_id(1L)
                                .email("user1@a.com")
                                .password("user1")
                                .username("user1")
                                .phone_number("01012345678")
                                .nickname("user1")
                                .build()
                )
        ).when(dbUserRepository).findById(anyLong());
        Cookie cookie = new Cookie("id", "1");

        //when
        MvcResult mvcResult = mvc.perform(get("/user/edit")
                        .cookie(cookie))
                .andExpect(status().isOk())
                .andReturn();
        MyPageForm myPageForm = (MyPageForm) mvcResult.getModelAndView().getModel().get("myPageForm");

        //then
        assertThat(myPageForm.getNickname()).isEqualTo("user1");
    }

    @Test
    void Post_로그인된_상태에서_데이터_잘가져오는지_테스트() throws Exception {
        //given
        doReturn(
                Optional.of(
                        User.builder()
                                .user_id(1L)
                                .email("user1@a.com")
                                .password("user1")
                                .username("user1")
                                .phone_number("01012345678")
                                .nickname("user1")
                                .build()
                )
        ).when(dbUserRepository).findById(anyLong());

        //when
        mvc.perform(post("/user/edit")
                        .param("id", "1")
                        .param("nickname", "user2"))
                .andExpect(status().is(302))
                .andReturn();
    }

    @Test
    void Post_닉네임이_비어있을때_테스트() throws Exception {
        //given
        doReturn(
                Optional.of(
                        User.builder()
                                .user_id(1L)
                                .email("user1@a.com")
                                .password("user1")
                                .username("user1")
                                .phone_number("01012345678")
                                .nickname("user1")
                                .build()
                )
        ).when(dbUserRepository).findById(anyLong());

        //when
        mvc.perform(post("/user/edit")
                        .param("id", "1")
                        .param("nickname", ""))
                .andExpect(model().attributeHasFieldErrors("myPageForm", "nickname"))
                .andExpect(status().is(200))
                .andReturn();
    }

    @Test
    void Post_닉네임이_중복될때_테스트() throws Exception {
        //given
        doReturn(
                Optional.of(
                        User.builder()
                                .user_id(1L)
                                .email("user1@a.com")
                                .password("user1")
                                .username("user1")
                                .phone_number("01012345678")
                                .nickname("user1")
                                .build()
                )
        ).when(dbUserRepository).findById(anyLong());

        doReturn(
                Optional.of(
                        User.builder()
                                .user_id(2L)
                                .email("user1@a.com")
                                .password("user1")
                                .username("user1")
                                .phone_number("01012345678")
                                .nickname("user2")
                                .build()
                )
        ).when(dbUserRepository).findByNickname(anyString());

        //when
        mvc.perform(post("/user/edit")
                        .param("id", "1")
                        .param("nickname", "user2"))
                .andExpect(model().attributeHasFieldErrors("myPageForm", "nickname"))
                .andExpect(status().is(200))
                .andReturn();
    }

    @Test
    void Post_수정할닉네임이_이전본인_닉네임일때_수정되야하는_테스트() throws Exception {
        //given
        doReturn(
                Optional.of(
                        User.builder()
                                .user_id(1L)
                                .email("user1@a.com")
                                .password("user1")
                                .username("user1")
                                .phone_number("01012345678")
                                .nickname("user1")
                                .build()
                )
        ).when(dbUserRepository).findById(anyLong());

        doReturn(
                Optional.of(
                        User.builder()
                                .user_id(1L)
                                .email("user1@a.com")
                                .password("user1")
                                .username("user1")
                                .phone_number("01012345678")
                                .nickname("user1")
                                .build()
                )
        ).when(dbUserRepository).findByNickname(anyString());

        //when
        mvc.perform(post("/user/edit")
                        .param("id", "1")
                        .param("nickname", "user1"))
                .andExpect(status().is(302))
                .andReturn();
    }
}
