package carrot.challenge.mypage;

import carrot.challenge.domain.comment.dto.Comment;
import carrot.challenge.domain.item.dto.Item;
import carrot.challenge.domain.item.repository.DBItemRepository;
import carrot.challenge.domain.item.service.ItemService;
import carrot.challenge.domain.user.dto.User;
import carrot.challenge.domain.user.repository.DBUserRepository;
import carrot.challenge.domain.user.service.UserService;
import carrot.challenge.web.mypage.MyItemController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class MyItemControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserService userService;

    @Autowired
    private ItemService itemService;

    @MockBean
    private DBUserRepository dbUserRepository;

    @MockBean
    private DBItemRepository dbItemRepository;

    @Test
    public void Get_로그인한_상태일때_아이템_잘가져오는지_테스트() throws Exception {
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
                List.of(
                        Item.builder()
                                .item_id(1L)
                                .price(10000)
                                .main("a")
                                .status(0)
                                .name("a")
                                .comments(new HashSet<>())
                                .interests(new HashSet<>())
                                .user(dbUserRepository.findById(1L).get())
                                .build()
                )
        ).when(dbItemRepository).findAllByUserId(anyLong());
        MockHttpSession mockSession = new MockHttpSession();
        mockSession.setAttribute("id", 1L);

        //when
        MvcResult mvcResult = mvc.perform(get("/user/item")
                        .session(mockSession))
                .andExpect(status().is(200))
                .andReturn();
        List<Item> items = (List<Item>) mvcResult.getModelAndView().getModel().get("items");

        //then
        assertThat(items.get(0).getItem_id()).isEqualTo(1L);
        assertThat(items.get(0).getPrice()).isEqualTo(10000);
        assertThat(items.get(0).getMain()).isEqualTo("a");
        assertThat(items.get(0).getStatus()).isEqualTo(0);
        assertThat(items.get(0).getName()).isEqualTo("a");
    }

    @Test
    public void Get_로그인_안한_상태일때_로그인페이지_테스트() throws Exception {
        //given

        //when
        MvcResult mvcResult = mvc.perform(get("/user/item"))
                .andExpect(status().is(200))
                .andReturn();
        ModelAndView mav = mvcResult.getModelAndView();

        //then
        assertThat(mav.getViewName()).isEqualTo("Message");
        assertThat(mav.getModel().get("message")).isEqualTo("로그인이 필요한 화면입니다.");
        assertThat(mav.getModel().get("href")).isEqualTo("/user/login");
    }

    @Test
    public void Get_세션_ID가_데이터베이스에_존재하지_않을때_로그인페이지_테스트() throws Exception {
        //given
        MockHttpSession mockSession = new MockHttpSession();
        mockSession.setAttribute("id", 1L);

        //when
        MvcResult mvcResult = mvc.perform(get("/user/item")
                        .session(mockSession))
                .andExpect(status().is(200))
                .andReturn();
        ModelAndView mav = mvcResult.getModelAndView();

        //then
        assertThat(mav.getViewName()).isEqualTo("Message");
        assertThat(mav.getModel().get("message")).isEqualTo("로그인이 필요한 화면입니다.");
        assertThat(mav.getModel().get("href")).isEqualTo("/user/login");
    }
}
