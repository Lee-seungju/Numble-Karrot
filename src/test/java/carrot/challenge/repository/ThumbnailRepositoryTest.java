package carrot.challenge.repository;

import carrot.challenge.domain.thumbnail.dto.Thumbnail;
import carrot.challenge.domain.thumbnail.repository.DBThumbnailRepository;
import carrot.challenge.domain.thumbnail.repository.ThumbnailRepository;
import carrot.challenge.domain.user.dto.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
public class ThumbnailRepositoryTest {

    @Autowired
    DBThumbnailRepository dbThumbnailRepository;

    @Test
    public void 저장_잘_되는지() {
        //given
        Thumbnail thumbnail = Thumbnail.builder()
                .item_id(1L)
                .uploadFileName("a")
                .storeFileName("a")
                .build();

        //when
        Thumbnail save = dbThumbnailRepository.save(thumbnail);

        //then
        assertThat(thumbnail.getThumbnail_id()).isEqualTo(save.getThumbnail_id());
        assertThat(thumbnail.getStoreFileName()).isEqualTo(save.getStoreFileName());
        assertThat(thumbnail.getItem_id()).isEqualTo(save.getItem_id());
        assertThat(thumbnail.getUploadFileName()).isEqualTo(save.getUploadFileName());
    }

    @Test
    public void findAll_테스트() {
        //given
        List<Thumbnail> before = dbThumbnailRepository.findAll();
        Thumbnail thumbnail1 = Thumbnail.builder()
                .item_id(1L)
                .uploadFileName("a")
                .storeFileName("a")
                .build();
        Thumbnail thumbnail2 = Thumbnail.builder()
                .item_id(1L)
                .uploadFileName("a1")
                .storeFileName("a1")
                .build();
        dbThumbnailRepository.save(thumbnail1);
        dbThumbnailRepository.save(thumbnail2);

        //when
        List<Thumbnail> after = dbThumbnailRepository.findAll();

        //then
        assertThat(after.size()-before.size()).isEqualTo(2);
    }

    @Test
    public void update_테스트() {
        //given
        Thumbnail thumbnail1 = Thumbnail.builder()
                .item_id(1L)
                .uploadFileName("a")
                .storeFileName("a")
                .build();
        dbThumbnailRepository.save(thumbnail1);
        Thumbnail thumbnail2 = Thumbnail.builder()
                .thumbnail_id(thumbnail1.getThumbnail_id())
                .item_id(2L)
                .uploadFileName("a1")
                .storeFileName("a1")
                .build();

        //when
        dbThumbnailRepository.update(thumbnail2);

        //then
        assertThat(thumbnail1.getStoreFileName()).isEqualTo(thumbnail2.getStoreFileName());
        assertThat(thumbnail1.getItem_id()).isEqualTo(thumbnail2.getItem_id());
        assertThat(thumbnail1.getUploadFileName()).isEqualTo(thumbnail2.getUploadFileName());
    }

    @Test
    public void remove_테스트() {
        //given
        List<Thumbnail> before = dbThumbnailRepository.findAll();
        Thumbnail thumbnail1 = Thumbnail.builder()
                .item_id(1L)
                .uploadFileName("a")
                .storeFileName("a")
                .build();
        dbThumbnailRepository.save(thumbnail1);

        //when
        dbThumbnailRepository.remove(thumbnail1);
        List<Thumbnail> after = dbThumbnailRepository.findAll();

        //then
        assertThat(after.size()-before.size()).isEqualTo(0);
    }
}
