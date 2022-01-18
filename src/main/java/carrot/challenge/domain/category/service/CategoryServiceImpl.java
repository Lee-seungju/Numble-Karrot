package carrot.challenge.domain.category.service;

import carrot.challenge.domain.category.repository.DBCategoryRepository;
import carrot.challenge.domain.category.dto.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final DBCategoryRepository dbCategoryRepository;

    @Override
    public Long findCategoryId(String cate) {
        return dbCategoryRepository.findByName(cate).get().getCategory_id();
    }

    @Override
    public List<Category> cateList() {
        return dbCategoryRepository.cateList();
    }

    @Override
    public String findById(Long cateId) {
        return dbCategoryRepository.findById(cateId).get().getName();
    }
}
