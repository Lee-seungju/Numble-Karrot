package carrot.challenge.error;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.MessageCodesResolver;

import static org.assertj.core.api.Assertions.assertThat;

public class AddFormErrorCodeTest {

    MessageCodesResolver codesResolver = new DefaultMessageCodesResolver();

    @Test
    void required관련_에러메시지_테스트() {
        String[] message = {"email", "password", "nickname", "username", "phone_number"};
        for (String msg : message) {
            String addMsg = "required." + msg;
            String[] messageCodes = codesResolver.resolveMessageCodes("required", msg);
            assertThat(messageCodes).containsExactly(addMsg, "required");
        }
    }

    @Test
    void already관련_에러메시지_테스트() {
        String[] message = {"email", "nickname", "phone_number"};
        for (String msg : message) {
            String addMsg = "already." + msg;
            String[] messageCodes = codesResolver.resolveMessageCodes("already", msg);
            assertThat(messageCodes).containsExactly(addMsg, "already");
        }
    }
}
