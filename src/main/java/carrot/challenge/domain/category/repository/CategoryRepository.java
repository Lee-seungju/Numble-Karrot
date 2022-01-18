package carrot.challenge.domain.category.repository;

import carrot.challenge.domain.category.dto.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    List<Category> cateList();
    Optional<Category> findById(Long cateId);
    Optional<Category> findByName(String cateName);
}
