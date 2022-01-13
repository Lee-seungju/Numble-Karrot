package carrot.challenge.domain.item.service;

import carrot.challenge.domain.item.dto.Category;

import java.util.List;

public interface CategoryService {
    Long findCategoryId(String cate);
    List<Category> cateList();
    String findById(Long cateId);
}
