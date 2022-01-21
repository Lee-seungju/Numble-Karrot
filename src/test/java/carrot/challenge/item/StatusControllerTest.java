package carrot.challenge.item;

import carrot.challenge.domain.item.dto.Item;
import carrot.challenge.domain.item.service.ItemService;
import carrot.challenge.domain.user.dto.User;
import carrot.challenge.domain.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class StatusControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    @MockBean
    private ItemService itemService;

    @Autowired
    @MockBean
    private UserService userService;

    @Test
    void Get_세션값이_없을때_item_delete() throws Exception {
        //given

        //when
        MvcResult mvcResult = mvc.perform(get("/item/1/status/0"))
                .andExpect(status().isOk())
                .andReturn();
        ModelAndView mav = mvcResult.getModelAndView();

        //then
        assertThat(mav.getViewName()).isEqualTo("Message");
        assertThat(mav.getModel().get("message")).isEqualTo("로그인이 필요한 화면입니다.");
        assertThat(mav.getModel().get("href")).isEqualTo("/user/login");
    }

    @Test
    void Get_세션값은_있지만_해당아이디가_없을때_item_delete() throws Exception {
        //given
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("id", 1L);

        //when
        MvcResult mvcResult = mvc.perform(get("/item/1/status/0")
                        .session(session))
                .andExpect(status().isOk())
                .andReturn();
        ModelAndView mav = mvcResult.getModelAndView();

        //then
        assertThat(mav.getViewName()).isEqualTo("Message");
        assertThat(mav.getModel().get("message")).isEqualTo("로그인이 필요한 화면입니다.");
        assertThat(mav.getModel().get("href")).isEqualTo("/user/login");
    }

    @Test
    void Get_세션값은_있지만_아이템이없을때_item_delete() throws Exception {
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
        ).when(userService).findById(anyLong());
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("id", 1L);

        //when
        MvcResult mvcResult = mvc.perform(get("/item/1/status/0")
                        .session(session))
                .andExpect(status().isOk())
                .andReturn();
        ModelAndView mav = mvcResult.getModelAndView();

        //then
        assertThat(mav.getViewName()).isEqualTo("Message");
        assertThat(mav.getModel().get("message")).isEqualTo("로그인이 필요한 화면입니다.");
        assertThat(mav.getModel().get("href")).isEqualTo("/user/login");
    }

    @Test
    void Get_세션값도_있고_아이템도_있지만_아이템주인이_아닐때_item_delete() throws Exception {
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
        ).when(userService).findById(anyLong());
        doReturn(
                Optional.of(
                        Item.builder()
                                .item_id(1L)
                                .price(10000)
                                .main("a")
                                .status(0)
                                .name("a")
                                .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                                .comments(new HashSet<>())
                                .interests(new HashSet<>())
                                .user(User.builder()
                                        .user_id(2L)
                                        .email("user2@a.com")
                                        .password("user2")
                                        .username("user2")
                                        .phone_number("01022345678")
                                        .nickname("user2")
                                        .build())
                                .build()
                )
        ).when(itemService).findById(anyLong());
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("id", 1L);

        //when
        MvcResult mvcResult = mvc.perform(get("/item/1/status/0")
                        .session(session))
                .andExpect(status().isOk())
                .andReturn();
        ModelAndView mav = mvcResult.getModelAndView();

        //then
        assertThat(mav.getViewName()).isEqualTo("Message");
        assertThat(mav.getModel().get("message")).isEqualTo("로그인이 필요한 화면입니다.");
        assertThat(mav.getModel().get("href")).isEqualTo("/user/login");
    }

    @Test
    void Get_세션값도_있고_아이템도_있고_아이템_주인이지만_상태코드가_맞지않을때_item_delete() throws Exception {
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
        ).when(userService).findById(anyLong());
        doReturn(
                Optional.of(
                        Item.builder()
                                .item_id(1L)
                                .price(10000)
                                .main("a")
                                .status(0)
                                .name("a")
                                .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                                .comments(new HashSet<>())
                                .interests(new HashSet<>())
                                .user(User.builder()
                                        .user_id(2L)
                                        .email("user2@a.com")
                                        .password("user2")
                                        .username("user2")
                                        .phone_number("01022345678")
                                        .nickname("user2")
                                        .build())
                                .build()
                )
        ).when(itemService).findById(anyLong());
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("id", 1L);

        //when
        MvcResult mvcResult = mvc.perform(get("/item/1/status/1")
                        .session(session))
                .andExpect(status().isOk())
                .andReturn();
        ModelAndView mav = mvcResult.getModelAndView();

        //then
        assertThat(mav.getViewName()).isEqualTo("Message");
        assertThat(mav.getModel().get("message")).isEqualTo("로그인이 필요한 화면입니다.");
        assertThat(mav.getModel().get("href")).isEqualTo("/user/login");
    }

    @Test
    void Get_세션값도_있고_아이템도_있고_아이템_주인이고_상태코드도맞을때_item_delete() throws Exception {
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
        ).when(userService).findById(anyLong());
        doReturn(
                Optional.of(
                        Item.builder()
                                .item_id(1L)
                                .price(10000)
                                .main("a")
                                .status(0)
                                .name("a")
                                .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                                .comments(new HashSet<>())
                                .interests(new HashSet<>())
                                .user(userService.findById(1L).get())
                                .build()
                )
        ).when(itemService).findById(anyLong());
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("id", 1L);

        //when
        MvcResult mvcResult = mvc.perform(get("/item/1/status/0")
                        .session(session))
                .andExpect(status().is(302))
                .andReturn();
        ModelAndView mav = mvcResult.getModelAndView();

        //then
        assertThat(mav.getViewName()).isEqualTo("redirect:/user/item");
    }
}
