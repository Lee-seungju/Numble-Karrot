package carrot.challenge.comment;


import carrot.challenge.domain.comment.service.CommentService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CommentControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    @MockBean
    private UserService userService;
    @Autowired
    @MockBean
    private ItemService itemService;
    @Autowired
    private CommentService commentService;

    @Test
    void Get_세션값이_없을때_item_comment() throws Exception {
        //given

        //when
        MvcResult mvcResult = mvc.perform(get("/item/1/comment"))
                .andExpect(status().isOk())
                .andReturn();
        ModelAndView mav = mvcResult.getModelAndView();

        //then
        assertThat(mav.getViewName()).isEqualTo("Message");
        assertThat(mav.getModel().get("message")).isEqualTo("로그인이 필요한 화면입니다.");
        assertThat(mav.getModel().get("href")).isEqualTo("/user/login");
    }

    @Test
    void Get_세션값은_있지만_해당아이디가_없을때_item_comment() throws Exception {
        //given
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("id", 1L);

        //when
        MvcResult mvcResult = mvc.perform(get("/item/1/comment")
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
    void Get_세션값은_있지만_아이템이없을때_item_comment() throws Exception {
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
        MvcResult mvcResult = mvc.perform(get("/item/1/comment")
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
    void Get_세션값도_있고_아이템도_있을때_item_comment() throws Exception {
        //given
        doReturn(
                Optional.of(
                        User.builder()
                                .user_id(2L)
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
        MvcResult mvcResult = mvc.perform(get("/item/1/comment")
                        .session(session))
                .andExpect(status().isOk())
                .andReturn();
        ModelAndView mav = mvcResult.getModelAndView();

        //then
        assertThat(mav.getViewName()).isEqualTo("item/comment");
    }

    @Test
    void Post_비어있을때_comment() throws Exception {
        //when
        MvcResult mvcResult = mvc.perform(post("/item/1/comment")
                        .param("comment", ""))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("commentForm", "comment"))
                .andReturn();
        ModelAndView mav = mvcResult.getModelAndView();

        //then
        assertThat(mav.getViewName()).isEqualTo("item/comment");
    }

    @Test
    void Post_값이잘들어올때_comment() throws Exception {
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
        MvcResult mvcResult = mvc.perform(post("/item/1/comment")
                        .param("comment", "abc")
                        .session(session))
                .andExpect(status().is(302))
                .andReturn();
        ModelAndView mav = mvcResult.getModelAndView();

        //then
        assertThat(mav.getViewName()).isEqualTo("redirect:/item/{itemId}/comment");
    }
}
