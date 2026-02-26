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

import co.spring.rest.entity.bo.Product;
import co.spring.rest.entity.dto.ProductDto;
import co.spring.rest.entity.mapper.ProductMapper;
import co.spring.rest.entity.repository.IProductRepository;
import co.spring.rest.error.CreatedError;
import co.spring.rest.error.NotFoundError;
import co.spring.rest.iservice.ICrudServ;
import co.spring.rest.iservice.IProductServ;

@Service
public class ProductServ implements IProductServ{

    @Autowired
    private IProductRepository iProductRepository;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public Map<String, Object> getList(Integer pageNumber, Integer pageSize, String sort, String direction){ 
        
        Map<String,Object> mapProductDto = new HashMap<>();
        List<ProductDto> listProductDto = null;

        if(sort == null)
            sort = "id";

        if(direction == null)
            direction = Direction.ASC.name();
        
        Direction d = (direction.equalsIgnoreCase(Direction.DESC.name())) ? Direction.DESC : Direction.ASC;

        if(pageNumber == null || pageSize == null){

            listProductDto = iProductRepository
                .findAll(Sort.by(d, sort))
                .stream()
                .map(productMapper::toProductDto)
                .toList();
            
            mapProductDto.put(ICrudServ.CONTENT, listProductDto);

        }else{

            Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(d, sort));
            Page<Product> productPage = iProductRepository.findAll(pageable);

            listProductDto = productPage
                .stream()
                .map(productMapper::toProductDto)
                .toList();

            mapProductDto.put(ICrudServ.CONTENT, listProductDto);
            mapProductDto.put(ICrudServ.NUMBER_PAGE, productPage.getNumber());
            mapProductDto.put(ICrudServ.SIZE_PAGE, productPage.getSize());
            mapProductDto.put(ICrudServ.NUMBER_OF_ELEMENTS, productPage.getNumberOfElements());
            mapProductDto.put(ICrudServ.TOTAL_PAGE, productPage.getTotalPages());
            mapProductDto.put(ICrudServ.TOTAL_ELEMENTS, productPage.getTotalElements());
            mapProductDto.put(ICrudServ.IS_FIRST, productPage.isFirst());
            mapProductDto.put(ICrudServ.IS_LAST, productPage.isLast());
            mapProductDto.put(ICrudServ.IS_EMPTY, productPage.isEmpty());

        }

        return mapProductDto;


    }

    @Override
    public ProductDto findById(long id){
        
        Product aProduct = iProductRepository.findById(id).orElseThrow(() -> new NotFoundError("Product not found", "Product cloud not find in the list", null));

        return productMapper.toProductDto(aProduct);

    }
    
    @Override
    public ProductDto add(ProductDto productDto){

        Product aProduct = null;

        try {
            aProduct = iProductRepository.save(productMapper.toProduct(productDto));
        } catch (Exception e) {
            throw new CreatedError("Product not created", "Product not created, error internal "+e.getMessage(), e);
        }

        return productMapper.toProductDto(aProduct);

    }

    @Override
    public ProductDto update(long id, ProductDto productDto){

        Product aProduct = iProductRepository.findById(id).orElseThrow(() -> new NotFoundError("Product not found", "Product cloud not find in the list", null));

        Product p = productMapper.toProduct(productDto);

        aProduct.setName(p.getName());
        aProduct.setDescription(p.getDescription());
        aProduct.setReleaseDate(p.getReleaseDate());
        aProduct.setImg(p.getImg());
        aProduct.setCategory(p.getCategory());

        try {
            iProductRepository.save(aProduct);
        } catch (Exception e) {
            throw new CreatedError("Product not update", "Product not update, error internal "+e.getMessage(), e);
        }

        return productMapper.toProductDto(aProduct);

    }

    @Override
    public void delete(long id){

        Product aProduct = iProductRepository.findById(id).orElseThrow(() -> new NotFoundError("Product not found", "Product cloud not find in the list", null));

        iProductRepository.delete(aProduct);

    }


}
