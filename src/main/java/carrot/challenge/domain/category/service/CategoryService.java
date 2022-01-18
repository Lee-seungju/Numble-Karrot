package carrot.challenge.domain.category.service;

import carrot.challenge.domain.category.dto.Category;

import java.util.List;

public interface CategoryService {
    Long findCategoryId(String cate);
    List<Category> cateList();
    String findById(Long cateId);
}
