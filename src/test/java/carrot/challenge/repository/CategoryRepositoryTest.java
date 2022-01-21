package carrot.challenge.repository;

import carrot.challenge.domain.category.dto.Category;
import carrot.challenge.domain.category.repository.DBCategoryRepository;
import carrot.challenge.domain.comment.dto.Comment;
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
class CategoryRepositoryTest {

    @Autowired
    DBCategoryRepository dbCategoryRepository;

    @Test
    void cateList() {
        //given

        //when
        List<Category> categories = dbCategoryRepository.cateList();

        //then
        assertThat(categories.get(0).getName()).isEqualTo("디지털기기");
    }

    @Test
    void findById() {
        //given

        //when
        Category category = dbCategoryRepository.findById(1L).get();

        //then
        assertThat(category.getName()).isEqualTo("디지털기기");
    }

    @Test
    void findByName() {
        //given

        //when
        Category category = dbCategoryRepository.findByName("디지털기기").get();

        //then
        assertThat(category.getCategory_id()).isEqualTo(1L);
    }
}