package carrot.challenge.repository;

import carrot.challenge.domain.Interest.dto.Interest;
import carrot.challenge.domain.Interest.repository.DBInterestRepository;
import carrot.challenge.domain.item.dto.Item;
import carrot.challenge.domain.item.repository.DBItemRepository;
import carrot.challenge.domain.user.dto.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
class InterestRepositoryTest {

    @Autowired
    DBInterestRepository dbInterestRepository;

    @Test
    void save() {
        //given
        Interest interest = new Interest();
        interest.setItem_id(1L);
        interest.setUser_id(1L);

        //when
        Interest save = dbInterestRepository.save(interest);

        //then
        assertThat(interest.getItem_id()).isEqualTo(save.getItem_id());
        assertThat(interest.getUser_id()).isEqualTo(save.getUser_id());
    }

    @Test
    void delete() {
        //given
        List<Interest> before = dbInterestRepository.findByUserId(1L);
        Interest interest = new Interest();
        interest.setItem_id(1L);
        interest.setUser_id(1L);
        Interest save = dbInterestRepository.save(interest);

        //when
        dbInterestRepository.delete(save);
        List<Interest> after = dbInterestRepository.findByUserId(1L);

        //then
        assertThat(after.size()-before.size()).isEqualTo(0);
    }

    @Test
    void findByUserIdItemId() {
        //given
        Interest interest = new Interest();
        interest.setItem_id(1L);
        interest.setUser_id(1L);
        Interest save = dbInterestRepository.save(interest);

        //when
        Interest find = dbInterestRepository.findByUserIdItemId(1L, 1L).get();

        //then
        assertThat(save.getInterest_id()).isEqualTo(find.getInterest_id());
    }

    @Test
    void findByUserId() {
        //given
        List<Interest> before = dbInterestRepository.findByUserId(1L);
        Interest interest = new Interest();
        interest.setItem_id(1L);
        interest.setUser_id(1L);
        Interest save = dbInterestRepository.save(interest);

        //when
        List<Interest> after = dbInterestRepository.findByUserId(1L);

        //then
        assertThat(after.size()-before.size()).isEqualTo(1);
    }
}