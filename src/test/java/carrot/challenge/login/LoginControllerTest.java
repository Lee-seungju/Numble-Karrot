package carrot.challenge.login;

import carrot.challenge.domain.item.service.ItemService;
import carrot.challenge.domain.user.dto.User;
import carrot.challenge.domain.user.repository.DBUserRepository;
import carrot.challenge.domain.user.service.UserService;
import carrot.challenge.web.login.LoginController;
import carrot.challenge.web.login.LoginForm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
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
public class LoginControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserService userService;

    @Autowired
    private ItemService itemService;

    @MockBean
    private DBUserRepository dbUserRepository;

    @Test
    void ?????????_?????????_???_???????????????() throws Exception {
        mvc.perform(get("/user/login"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void ?????????_??????() throws Exception {
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
        ).when(dbUserRepository).findByEmail(anyString());

        //when
        MvcResult mvcResult = mvc.perform(post("/user/login")
                        .param("email", "user1@a.com")
                        .param("password", "user1"))
                .andExpect(status().is(302))
                .andReturn();
        HttpSession session = mvcResult.getRequest().getSession();
        Cookie cookie = mvcResult.getResponse().getCookie("id");

        //then
        assertThat(cookie.getValue()).isEqualTo("1");
        assertThat((Long)session.getAttribute("id")).isEqualTo(1);
    }

    @Test
    void ?????????_??????_????????????_????????????() throws Exception {
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
        ).when(dbUserRepository).findByEmail(anyString());

        //when then
        mvc.perform(post("/user/login")
                        .param("email", "user1@a.com")
                        .param("password", "user2"))
                .andExpect(status().is(200))
                .andExpect(model().attributeHasFieldErrors("loginForm", "password"));
    }

    @Test
    void ?????????_??????_????????????_?????????1() throws Exception {
        //given
        doReturn(
                Optional.of(
                        User.builder()
                                .user_id(1L)
                                .email("user1")
                                .password("user1")
                                .username("user1")
                                .phone_number("01012345678")
                                .nickname("user1")
                                .build()
                )
        ).when(dbUserRepository).findByEmail(anyString());

        //????????? ????????? ?????? ?????? ?????????
        mvc.perform(post("/user/login")
                        .param("email", "user1")
                        .param("password", "user1"))
                .andExpect(status().is(200))
                .andExpect(model().attributeHasFieldErrors("loginForm", "email"));
    }

    @Test
    void ?????????_??????_????????????_?????????2() throws Exception {
        //????????? ????????? ????????? ?????? ?????????
        mvc.perform(post("/user/login")
                        .param("email", "user1")
                        .param("password", "user2"))
                .andExpect(status().is(200))
                .andExpect(model().attributeHasFieldErrors("loginForm", "email"));
    }

    @Test
    void ?????????_??????_????????????_?????????_????????????() throws Exception {
        //given

        //when then
        mvc.perform(post("/user/login")
                        .param("email", "")
                        .param("password", ""))
                .andExpect(status().is(200))
                .andExpect(model().attributeHasFieldErrors("loginForm", "email", "password"));
    }
}
