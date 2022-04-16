package spring.framework.api.v1.mapper;

import org.junit.jupiter.api.Test;
import spring.framework.api.v1.model.CategoryDTO;
import spring.framework.domain.Category;

import static org.junit.jupiter.api.Assertions.*;

class CategoryMapperTest {

    public static final String NAME = "Joe";
    CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    @Test
    void categoryToCategoryDTO() {
        Category category = new Category();
        category.setName(NAME);

        CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);

        assertEquals(NAME, categoryDTO.getName());
    }
}