package carrot.challenge.domain.category.repository;

import carrot.challenge.domain.category.dto.Category;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryCategoryRepository implements CategoryRepository {

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
        category.setCategory_id(++sequence);
        data.put(category.getCategory_id(), category);
        return category;
    }
}
