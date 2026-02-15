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
import co.spring.rest.iservice.ICategoryServ;

@Service
public class CategoryServ implements ICategoryServ{

    @Autowired
    private ICategoryRepository iCategoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<CategoryDto> getList(){
        return iCategoryRepository
            .findAll()
            .stream()
            .map(categoryMapper::toCategoryDto)
            .toList();
    }

    @Override
    public CategoryDto findById(long id){
        Category aCategory = iCategoryRepository.findById(id).orElseThrow(() -> new NotFoundError("Category not found", "Category cloud not find int the list", null));

        return categoryMapper.toCategoryDto(aCategory);
    }

    @Override
    public CategoryDto add(CategoryDto categoryDto){

        Category aCategory = null;

        try {
            aCategory = iCategoryRepository.save(categoryMapper.toCategory(categoryDto));
        } catch (Exception e) {
            throw new CreatedError("Category not created", "Category not created, error internal", e);
        }

        return categoryMapper.toCategoryDto(aCategory);
        
    }

    @Override
    public CategoryDto update(long id, CategoryDto categoryDto){

        Category aCategory = iCategoryRepository.findById(id).orElseThrow(() -> new NotFoundError("Category not found", "Category cloud not find int the list", null));

        Category c = categoryMapper.toCategory(categoryDto);

        aCategory.setName(c.getName());
        aCategory.setDescription(c.getDescription());

        try {
            iCategoryRepository.save(aCategory);
        } catch (Exception e) {
            throw new CreatedError("Category not update", "Category not update, error internal", e);
        }

        return categoryMapper.toCategoryDto(aCategory);

    }

    @Override
    public CategoryDto delete(long id){

        Category aCategory = iCategoryRepository.findById(id).orElseThrow(() -> new NotFoundError("Category not found", "Category cloud not find int the list", null));

        iCategoryRepository.delete(aCategory);

        return categoryMapper.toCategoryDto(aCategory);

    }

}
