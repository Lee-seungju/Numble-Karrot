package carrot.challenge.mypage;

import carrot.challenge.domain.Interest.service.InterestService;
import carrot.challenge.domain.item.dto.Item;
import carrot.challenge.domain.user.dto.User;
import carrot.challenge.domain.user.repository.DBUserRepository;
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

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class InterestListControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserService userService;

    @Autowired
    @MockBean
    private InterestService interestService;

    @MockBean
    private DBUserRepository dbUserRepository;

    @Test
    void Get_세션값_없을때_로그인페이지() throws Exception {
        //given

        //when
        MvcResult mvcResult = mvc.perform(get("/user/interest"))
                .andExpect(status().isOk())
                .andReturn();
        ModelAndView mav = mvcResult.getModelAndView();

        //then
        assertThat(mav.getViewName()).isEqualTo("Message");
        assertThat(mav.getModel().get("message")).isEqualTo("로그인이 필요한 화면입니다.");
        assertThat(mav.getModel().get("href")).isEqualTo("/user/login");
    }

    @Test
    void Get_세션값이_데이터베이스에_없을때_로그인페이지() throws Exception {
        //given
        MockHttpSession mockSession = new MockHttpSession();
        mockSession.setAttribute("id", 1L);

        //when
        MvcResult mvcResult = mvc.perform(get("/user/interest")
                        .session(mockSession))
                .andExpect(status().isOk())
                .andReturn();
        ModelAndView mav = mvcResult.getModelAndView();

        //then
        assertThat(mav.getViewName()).isEqualTo("Message");
        assertThat(mav.getModel().get("message")).isEqualTo("로그인이 필요한 화면입니다.");
        assertThat(mav.getModel().get("href")).isEqualTo("/user/login");
    }

    @Test
    void Get_세션값이_잘들어올때_데이터가져오기() throws Exception {
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
                                .interests(new HashSet<>())
                                .comments(new HashSet<>())
                                .name("a")
                                .main("a")
                                .user(null)
                                .build()
                )
        ).when(interestService).findByInterest(anyLong());

        MockHttpSession mockSession = new MockHttpSession();
        mockSession.setAttribute("id", 1L);

        //when
        MvcResult mvcResult = mvc.perform(get("/user/interest")
                        .session(mockSession))
                .andExpect(status().isOk())
                .andReturn();
        ModelAndView mav = mvcResult.getModelAndView();
        List<Item> items = (List<Item>) mav.getModel().get("items");

        //then
        assertThat(items.get(0).getName()).isEqualTo("a");
        assertThat(items.get(0).getMain()).isEqualTo("a");
        assertThat(mav.getViewName()).isEqualTo("user/interestList");
    }
}
