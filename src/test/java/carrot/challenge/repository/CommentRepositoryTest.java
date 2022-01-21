package carrot.challenge.repository;

import carrot.challenge.domain.Interest.dto.Interest;
import carrot.challenge.domain.comment.dto.Comment;
import carrot.challenge.domain.comment.repository.DBCommentRepository;
import carrot.challenge.domain.user.dto.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
class CommentRepositoryTest {

    @Autowired
    DBCommentRepository dbCommentRepository;

    @Test
    void save() {
        //given
        Comment comment = new Comment();
        comment.setContents("a");
        comment.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        comment.setItem_id(1L);
        comment.setUser(User.builder()
                .user_id(2L)
                .email("user2@a.com")
                .password("user2")
                .username("user2")
                .phone_number("01022345678")
                .nickname("user2")
                .build());

        //when
        Comment save = dbCommentRepository.save(comment);

        //then
        assertThat(comment.getItem_id()).isEqualTo(save.getItem_id());
        assertThat(comment.getUser()).isEqualTo(save.getUser());
        assertThat(comment.getContents()).isEqualTo(save.getContents());
    }

    @Test
    void findById() {
        //given
        Comment comment = new Comment();
        comment.setContents("a");
        comment.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        comment.setItem_id(1L);
        comment.setUser(User.builder()
                .user_id(2L)
                .email("user2@a.com")
                .password("user2")
                .username("user2")
                .phone_number("01022345678")
                .nickname("user2")
                .build());
        Comment save = dbCommentRepository.save(comment);

        //when
        Comment find = dbCommentRepository.findById(save.getComment_id()).get();


        //then
        assertThat(save.getItem_id()).isEqualTo(find.getItem_id());
        assertThat(save.getUser()).isEqualTo(find.getUser());
        assertThat(save.getContents()).isEqualTo(find.getContents());
    }

    @Test
    void findAllByItemId() {
        //given
        List<Comment> before1 = dbCommentRepository.findAllByItemId(2L);
        List<Comment> before2 = dbCommentRepository.findAllByItemId(1L);
        Comment comment = new Comment();
        comment.setContents("a");
        comment.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        comment.setItem_id(1L);
        comment.setUser(User.builder()
                .user_id(2L)
                .email("user2@a.com")
                .password("user2")
                .username("user2")
                .phone_number("01022345678")
                .nickname("user2")
                .build());
        Comment save = dbCommentRepository.save(comment);

        //when
        List<Comment> after1 = dbCommentRepository.findAllByItemId(2L);
        List<Comment> after2 = dbCommentRepository.findAllByItemId(1L);


        //then
        assertThat(after1.size()-before1.size()).isEqualTo(0);
        assertThat(after2.size()-before2.size()).isEqualTo(1);
    }

    @Test
    void findAll() {
        //given
        List<Comment> before = dbCommentRepository.findAll();
        Comment comment = new Comment();
        comment.setContents("a");
        comment.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        comment.setItem_id(1L);
        comment.setUser(User.builder()
                .user_id(2L)
                .email("user2@a.com")
                .password("user2")
                .username("user2")
                .phone_number("01022345678")
                .nickname("user2")
                .build());
        Comment save = dbCommentRepository.save(comment);

        //when
        List<Comment> after = dbCommentRepository.findAll();


        //then
        assertThat(after.size()-before.size()).isEqualTo(1);
    }
}