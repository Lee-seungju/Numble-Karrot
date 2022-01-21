package carrot.challenge.item;

import carrot.challenge.domain.Interest.service.InterestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class InterestControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    @MockBean
    private InterestService interestService;

    @Test
    void Post_return_ok_add() throws Exception {
        //given
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("id", 1L);

        //when
        MvcResult mvcResult = mvc.perform(post("/interest/1/add")
                        .session(session))
                .andExpect(status().isOk())
                .andReturn();
        String result = mvcResult.getResponse().getContentAsString();

        //then
        assertThat(result).isEqualTo("ok");
    }

    @Test
    void Post_return_ok_delete() throws Exception {
        //given
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("id", 1L);

        //when
        MvcResult mvcResult = mvc.perform(post("/interest/1/delete")
                        .session(session))
                .andExpect(status().isOk())
                .andReturn();
        String result = mvcResult.getResponse().getContentAsString();

        //then
        assertThat(result).isEqualTo("ok");
    }
}
