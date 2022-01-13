package carrot.challenge.domain.item.service;

import carrot.challenge.domain.item.dto.Category;
import carrot.challenge.domain.item.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemoryCategoryService implements CategoryService{

    private final CategoryRepository categoryRepository;

    @Override
    public Long findCategoryId(String cate) {
        return categoryRepository.findByName(cate).get().getId();
    }

    @Override
    public List<Category> cateList() {
        return categoryRepository.cateList();
    }

    @Override
    public String findById(Long cateId) {
        return categoryRepository.findById(cateId).get().getName();
    }
}
