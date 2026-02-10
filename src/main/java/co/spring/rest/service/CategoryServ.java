package co.spring.rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.spring.rest.entity.bo.Category;
import co.spring.rest.entity.dto.CategoryDto;
import co.spring.rest.entity.mapper.CategoryMapper;
import co.spring.rest.entity.repository.ICategoryRepository;
import co.spring.rest.error.CreatedError;
import co.spring.rest.error.NotFoundError;

@Service
public class CategoryServ {

    @Autowired
    private ICategoryRepository iCategoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    public List<CategoryDto> getListCategory(){
        return iCategoryRepository
            .findAll()
            .stream()
            .map(categoryMapper::toCategoryDto)
            .toList();
    }

    public CategoryDto findById(long id){
        Category aCategory = iCategoryRepository.findById(id).orElseThrow(() -> new NotFoundError("Category not found", "Category cloud not find int the list", null));

        return categoryMapper.toCategoryDto(aCategory);
    }

    public CategoryDto add(Category category){

        Category aCategory = null;

        try {
            aCategory = iCategoryRepository.save(category);
        } catch (Exception e) {
            throw new CreatedError("Category not created", "Category not created, error internal", e);
        }

        return categoryMapper.toCategoryDto(aCategory);
        
    }

    public CategoryDto update(long id, Category category){

        Category aCategory = iCategoryRepository.findById(id).orElseThrow(() -> new NotFoundError("Category not found", "Category cloud not find int the list", null));

        aCategory.setName(category.getName());
        aCategory.setDescription(category.getDescription());

        try {
            iCategoryRepository.save(aCategory);
        } catch (Exception e) {
            throw new CreatedError("Category not update", "Category not update, error internal", e);
        }

        return categoryMapper.toCategoryDto(aCategory);

    }

    public CategoryDto delete(long id){

        Category aCategory = iCategoryRepository.findById(id).orElseThrow(() -> new NotFoundError("Category not found", "Category cloud not find int the list", null));

        iCategoryRepository.delete(aCategory);

        return categoryMapper.toCategoryDto(aCategory);

    }

}
