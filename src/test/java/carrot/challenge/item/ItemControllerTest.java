package carrot.challenge.item;

import carrot.challenge.domain.category.service.CategoryService;
import carrot.challenge.domain.item.dto.Item;
import carrot.challenge.domain.item.service.ItemService;
import carrot.challenge.domain.upload.FileStore;
import carrot.challenge.domain.user.dto.User;
import carrot.challenge.domain.user.service.UserService;
import carrot.challenge.web.validation.AddItemValidation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ItemControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private  AddItemValidation addItemValidation;

    @Autowired
    @MockBean
    private  CategoryService categoryService;

    @Autowired
    @MockBean
    private  ItemService itemService;

    @Autowired
    @MockBean
    private  UserService userService;

    @Autowired
    private  FileStore fileStore;

    @Test
    void Get_세션값이_없을때_item_add() throws Exception {
        //given

        //when
        MvcResult mvcResult = mvc.perform(get("/item/new"))
                .andExpect(status().isOk())
                .andReturn();
        ModelAndView mav = mvcResult.getModelAndView();

        //then
        assertThat(mav.getViewName()).isEqualTo("Message");
        assertThat(mav.getModel().get("message")).isEqualTo("로그인이 필요한 화면입니다.");
        assertThat(mav.getModel().get("href")).isEqualTo("/user/login");
    }

    @Test
    void Get_세션값은_있지만_해당아이디가_없을때_item_add() throws Exception {
        //given
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("id", 1L);

        //when
        MvcResult mvcResult = mvc.perform(get("/item/new")
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
    void Get_세션값은_있고_해당아이디도_존재할때_item_add() throws Exception {
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
        MvcResult mvcResult = mvc.perform(get("/item/new")
                        .session(session))
                .andExpect(status().isOk())
                .andReturn();
        ModelAndView mav = mvcResult.getModelAndView();

        //then
        assertThat(mav.getViewName()).isEqualTo("item/newForm");
    }

    @Test
    void Post_아이템에_빈공간_입력했을때_item_add() throws Exception {
        mvc.perform(post("/item/new")
                        .param("category", "")
                        .param("name", "")
                        .param("price", "")
                        .param("main", ""))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("itemForm", "category"))
                .andExpect(model().attributeHasFieldErrors("itemForm", "name"))
                .andExpect(model().attributeHasFieldErrors("itemForm", "price"))
                .andExpect(model().attributeHasFieldErrors("itemForm", "main"))
                .andReturn();
    }

    @Test
    void Post_숫자입력할곳에_문자입력했을때_item_add() throws Exception {
        mvc.perform(post("/item/new")
                        .param("category", "")
                        .param("name", "")
                        .param("price", "abc")
                        .param("main", ""))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("itemForm", "category"))
                .andExpect(model().attributeHasFieldErrors("itemForm", "name"))
                .andExpect(model().attributeHasFieldErrors("itemForm", "price"))
                .andExpect(model().attributeHasFieldErrors("itemForm", "main"))
                .andReturn();
    }

    @Test
    void Post_가격이1000원보다_작을때_item_add() throws Exception {
        mvc.perform(post("/item/new")
                        .param("category", "")
                        .param("name", "")
                        .param("price", "999")
                        .param("main", ""))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("itemForm", "category"))
                .andExpect(model().attributeHasFieldErrors("itemForm", "name"))
                .andExpect(model().attributeHasFieldErrors("itemForm", "price"))
                .andExpect(model().attributeHasFieldErrors("itemForm", "main"))
                .andReturn();
    }

    @Test
    void Post_파일업로드에_null이_들어올때_item_add() throws Exception {
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
        doReturn(1L).when(categoryService).findCategoryId(any());

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("id", 1L);

        //when
        MvcResult mvcResult = mvc.perform(post("/item/new")
                        .session(session)
                        .param("category", "a")
                        .param("name", "a")
                        .param("price", "1000")
                        .param("main", "a"))
                .andExpect(status().is(200))
                .andReturn();
        ModelAndView mav = mvcResult.getModelAndView();

        //then
        assertThat(mav.getViewName()).isEqualTo("error/nullPicture");
    }

    @Test
    void Get_세션값이_없을때_item_view() throws Exception {
        //given

        //when
        MvcResult mvcResult = mvc.perform(get("/item/1"))
                .andExpect(status().isOk())
                .andReturn();
        ModelAndView mav = mvcResult.getModelAndView();

        //then
        assertThat(mav.getViewName()).isEqualTo("Message");
        assertThat(mav.getModel().get("message")).isEqualTo("로그인이 필요한 화면입니다.");
        assertThat(mav.getModel().get("href")).isEqualTo("/user/login");
    }

    @Test
    void Get_세션값은_있지만_해당아이디가_없을때_item_view() throws Exception {
        //given
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("id", 1L);

        //when
        MvcResult mvcResult = mvc.perform(get("/item/1")
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
    void Get_세션값은_있고_해당아이디도_존재할때_item_view() throws Exception {
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
        MvcResult mvcResult = mvc.perform(get("/item/1")
                        .session(session))
                .andExpect(status().isOk())
                .andReturn();
        ModelAndView mav = mvcResult.getModelAndView();

        //then
        assertThat(mav.getViewName()).isEqualTo("item/viewItem");
    }

    @Test
    void Get_other_item() throws Exception {
        //given
        doReturn(
                List.of(
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
                                        .user_id(1L)
                                        .email("user1@a.com")
                                        .password("user1")
                                        .username("user1")
                                        .phone_number("01012345678")
                                        .nickname("user1")
                                        .build())
                                .build()
                )
        ).when(itemService).findAllWithUser(anyLong());

        //when
        MvcResult mvcResult = mvc.perform(get("/item/user/1"))
                .andExpect(status().isOk())
                .andReturn();
        ModelAndView mav = mvcResult.getModelAndView();

        //then
        assertThat(mav.getViewName()).isEqualTo("item/viewOtherItem");
    }

    @Test
    void Get_세션값이_없을때_item_edit() throws Exception {
        //given

        //when
        MvcResult mvcResult = mvc.perform(get("/item/1/edit"))
                .andExpect(status().isOk())
                .andReturn();
        ModelAndView mav = mvcResult.getModelAndView();

        //then
        assertThat(mav.getViewName()).isEqualTo("Message");
        assertThat(mav.getModel().get("message")).isEqualTo("로그인이 필요한 화면입니다.");
        assertThat(mav.getModel().get("href")).isEqualTo("/user/login");
    }

    @Test
    void Get_세션값은_있지만_해당아이디가_없을때_item_edit() throws Exception {
        //given
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("id", 1L);

        //when
        MvcResult mvcResult = mvc.perform(get("/item/1/edit")
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
    void Get_세션값은_있지만_아이템이없을때_item_edit() throws Exception {
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
        MvcResult mvcResult = mvc.perform(get("/item/1/edit")
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
    void Get_세션값도_있고_아이템도_있지만_아이템주인이_아닐때_item_edit() throws Exception {
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
        MvcResult mvcResult = mvc.perform(get("/item/1/edit")
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
    void Get_세션값도_있고_아이템도_있고_아이템_주인일때_item_edit() throws Exception {
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
        MvcResult mvcResult = mvc.perform(get("/item/1/edit")
                        .session(session))
                .andExpect(status().isOk())
                .andReturn();
        ModelAndView mav = mvcResult.getModelAndView();

        //then
        assertThat(mav.getViewName()).isEqualTo("item/newForm");
    }

    @Test
    void Post_비어있을때_item_edit() throws Exception {
        mvc.perform(post("/item/1/edit")
                        .param("category", "")
                        .param("name", "")
                        .param("price", "")
                        .param("main", ""))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("itemForm", "category"))
                .andExpect(model().attributeHasFieldErrors("itemForm", "name"))
                .andExpect(model().attributeHasFieldErrors("itemForm", "price"))
                .andExpect(model().attributeHasFieldErrors("itemForm", "main"));
    }

    @Test
    void Post_Integer에_String들어올때_item_edit() throws Exception {
        mvc.perform(post("/item/1/edit")
                        .param("category", "")
                        .param("name", "")
                        .param("price", "ababab")
                        .param("main", ""))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("itemForm", "category"))
                .andExpect(model().attributeHasFieldErrors("itemForm", "name"))
                .andExpect(model().attributeHasFieldErrors("itemForm", "price"))
                .andExpect(model().attributeHasFieldErrors("itemForm", "main"));
    }

    @Test
    void Post_가격이_1000원보다_작을때_item_edit() throws Exception {
        mvc.perform(post("/item/1/edit")
                        .param("category", "")
                        .param("name", "")
                        .param("price", "999")
                        .param("main", ""))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("itemForm", "category"))
                .andExpect(model().attributeHasFieldErrors("itemForm", "name"))
                .andExpect(model().attributeHasFieldErrors("itemForm", "price"))
                .andExpect(model().attributeHasFieldErrors("itemForm", "main"));
    }

    @Test
    void Post_파일업로드에_null이_들어올때_item_edit() throws Exception {
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
        doReturn(1L).when(categoryService).findCategoryId(any());

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("id", 1L);

        //when
        MvcResult mvcResult = mvc.perform(post("/item/1/edit")
                        .session(session)
                        .param("category", "a")
                        .param("name", "a")
                        .param("price", "1000")
                        .param("main", "a"))
                .andExpect(status().is(200))
                .andReturn();
        ModelAndView mav = mvcResult.getModelAndView();

        //then
        assertThat(mav.getViewName()).isEqualTo("error/nullPicture");
    }

    @Test
    void Get_세션값이_없을때_item_delete() throws Exception {
        //given

        //when
        MvcResult mvcResult = mvc.perform(get("/item/1/delete"))
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
        MvcResult mvcResult = mvc.perform(get("/item/1/delete")
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
        MvcResult mvcResult = mvc.perform(get("/item/1/delete")
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
        MvcResult mvcResult = mvc.perform(get("/item/1/delete")
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
    void Get_세션값도_있고_아이템도_있고_아이템_주인일때_item_delete() throws Exception {
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
        MvcResult mvcResult = mvc.perform(get("/item/1/delete")
                        .session(session))
                .andExpect(status().is(302))
                .andReturn();
        ModelAndView mav = mvcResult.getModelAndView();

        //then
        assertThat(mav.getViewName()).isEqualTo("redirect:/user");
    }
}
