package co.spring.rest.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import co.spring.rest.entity.bo.Category;
import co.spring.rest.entity.dto.CategoryDto;
import co.spring.rest.entity.mapper.CategoryMapper;
import co.spring.rest.entity.repository.ICategoryRepository;
import co.spring.rest.error.CreatedError;
import co.spring.rest.error.NotFoundError;
import co.spring.rest.iservice.ICategoryServ;
import co.spring.rest.iservice.ICrudServ;

@Service
public class CategoryServ implements ICategoryServ{

    @Autowired
    private ICategoryRepository iCategoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public Map<String, Object> getList(Integer pageNumber, Integer pageSize, String sort, String direction){
        
        Map<String, Object> mapCategoryDto = new HashMap<>();
        List<CategoryDto> listCategoryDto = null;

        if(sort == null)
            sort = "id";

        if(direction == null)
            direction = Direction.ASC.name();

        Direction d = (direction.equalsIgnoreCase(Direction.DESC.name())) ? Direction.DESC : Direction.ASC;

        if(pageNumber == null || pageSize == null){

            listCategoryDto = iCategoryRepository
                .findAll(Sort.by(d, sort))
                .stream()
                .map(categoryMapper::toCategoryDto)
                .toList();

            mapCategoryDto.put(ICrudServ.CONTENT, listCategoryDto);

        }else{

            Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(d, sort));

            Page<Category> categoryPage = iCategoryRepository.findAll(pageable);

            listCategoryDto = categoryPage
                .stream()
                .map(categoryMapper::toCategoryDto)
                .toList();
            
            mapCategoryDto.put(ICrudServ.CONTENT, listCategoryDto);
            mapCategoryDto.put(ICrudServ.NUMBER_PAGE, categoryPage.getNumber());
            mapCategoryDto.put(ICrudServ.SIZE_PAGE, categoryPage.getSize());
            mapCategoryDto.put(ICrudServ.NUMBER_OF_ELEMENTS, categoryPage.getNumberOfElements());
            mapCategoryDto.put(ICrudServ.TOTAL_PAGE, categoryPage.getTotalPages());
            mapCategoryDto.put(ICrudServ.TOTAL_ELEMENTS, categoryPage.getTotalElements());
            mapCategoryDto.put(ICrudServ.IS_FIRST, categoryPage.isFirst());
            mapCategoryDto.put(ICrudServ.IS_LAST, categoryPage.isLast());
            mapCategoryDto.put(ICrudServ.IS_EMPTY, categoryPage.isEmpty());

        }

        return mapCategoryDto;

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
