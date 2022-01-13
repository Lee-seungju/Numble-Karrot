package carrot.challenge.domain.item.repository;

import carrot.challenge.domain.item.dto.Category;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;

@Repository
public class MemoryCategoryRepository implements CategoryRepository{

    private static final Map<Long, Category> data = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public List<Category> cateList() {
        return new ArrayList<>(data.values());
    }

    @Override
    public Optional<Category> findById(Long cateId) {
        return Optional.ofNullable(data.get(cateId));
    }

    @Override
    public Optional<Category> findByName(String cateName) {
        List<Category> cateList = cateList();
        for (Category category : cateList) {
            if (category.getName().equals(cateName))
                return Optional.of(category);
        }
        return null;
    }

    public Category save(Category category) {
        category.setId(++sequence);
        data.put(category.getId(), category);
        return category;
    }

    /**
     * 테스트용 카테고리 데이터 넣기
     */
    @PostConstruct
    void save() {
        String[] a = ("생활가전,가구/인테리어,유아동,생활/가공식품,유아도서,스포츠/레저,여성잡화,여성의류,남성패션/잡화,게임/취미,뷰티/미용,반려동물용품," +
                "도서/티켓/음반,식물,기타 중고물품,중고차").split(",");
        for (String b : a) {
            Category category = new Category();
            category.setName(b);
            save(category);
        }
    }
}
