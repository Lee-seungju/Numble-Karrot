package carrot.challenge.add;

import carrot.challenge.domain.user.dto.User;
import carrot.challenge.domain.user.service.UserService;
import carrot.challenge.web.validation.AddValidation;
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
public class AddControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    @MockBean
    private UserService userService;
    @Autowired
    private AddValidation addValidation;

    @Test
    void Get_회원가입() throws Exception {
        //given

        //when
        MvcResult mvcResult = mvc.perform(get("/user/add"))
                .andExpect(status().isOk())
                .andReturn();
        ModelAndView mav = mvcResult.getModelAndView();

        //then
        assertThat(mav.getViewName()).isEqualTo("user/addForm");
    }

    @Test
    void Post_비어있을때_회원가입() throws Exception {
        //given

        //when
        MvcResult mvcResult = mvc.perform(post("/user/add")
                        .param("email", "")
                        .param("password", "")
                        .param("username", "")
                        .param("phone_number", "")
                        .param("nickname", ""))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("addForm", "email"))
                .andExpect(model().attributeHasFieldErrors("addForm", "password"))
                .andExpect(model().attributeHasFieldErrors("addForm", "username"))
                .andExpect(model().attributeHasFieldErrors("addForm", "phone_number"))
                .andExpect(model().attributeHasFieldErrors("addForm", "nickname"))
                .andReturn();
        ModelAndView mav = mvcResult.getModelAndView();

        //then
        assertThat(mav.getViewName()).isEqualTo("user/addForm");
    }

    @Test
    void Post_이메일형식이_아닐때_회원가입() throws Exception {
        //given

        //when
        MvcResult mvcResult = mvc.perform(post("/user/add")
                        .param("email", "aaa")
                        .param("password", "")
                        .param("username", "")
                        .param("phone_number", "")
                        .param("nickname", ""))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("addForm", "email"))
                .andExpect(model().attributeHasFieldErrors("addForm", "password"))
                .andExpect(model().attributeHasFieldErrors("addForm", "username"))
                .andExpect(model().attributeHasFieldErrors("addForm", "phone_number"))
                .andExpect(model().attributeHasFieldErrors("addForm", "nickname"))
                .andReturn();
        ModelAndView mav = mvcResult.getModelAndView();

        //then
        assertThat(mav.getViewName()).isEqualTo("user/addForm");
    }

    @Test
    void Post_이미_가입된_이메일_전화번호_닉네임__회원가입() throws Exception {
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
        ).when(userService).findByEmail(anyString());
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
        ).when(userService).findByPhoneNumber(anyString());
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
        ).when(userService).findByNickName(anyString());

        //when
        MvcResult mvcResult = mvc.perform(post("/user/add")
                        .param("email", "user1@a.com")
                        .param("password", "a")
                        .param("username", "a")
                        .param("phone_number", "01012345678")
                        .param("nickname", "user1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("addForm", "email"))
                .andExpect(model().attributeHasFieldErrors("addForm", "phone_number"))
                .andExpect(model().attributeHasFieldErrors("addForm", "nickname"))
                .andReturn();
        ModelAndView mav = mvcResult.getModelAndView();

        //then
        assertThat(mav.getViewName()).isEqualTo("user/addForm");
    }

    @Test
    void Post_입력값_잘들어옴_회원가입() throws Exception {
        //given

        //when
        MvcResult mvcResult = mvc.perform(post("/user/add")
                        .param("email", "a@a")
                        .param("password", "a")
                        .param("username", "a")
                        .param("phone_number", "a")
                        .param("nickname", "a"))
                .andExpect(status().is(302))
                .andReturn();
        ModelAndView mav = mvcResult.getModelAndView();

        //then
        assertThat(mav.getViewName()).isEqualTo("redirect:/user");
    }
}
