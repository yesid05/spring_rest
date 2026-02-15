package co.spring.rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.spring.rest.entity.bo.Product;
import co.spring.rest.entity.dto.ProductDto;
import co.spring.rest.entity.mapper.ProductMapper;
import co.spring.rest.entity.repository.IProductRepository;
import co.spring.rest.error.CreatedError;
import co.spring.rest.error.NotFoundError;
import co.spring.rest.iservice.IProductServ;

@Service
public class ProductServ implements IProductServ{

    @Autowired
    private IProductRepository iProductRepository;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<ProductDto> getList(){
        return iProductRepository
            .findAll()
            .stream()
            .map(productMapper::toProductDto)
            .toList();
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
    public ProductDto delete(long id){

        Product aProduct = iProductRepository.findById(id).orElseThrow(() -> new NotFoundError("Product not found", "Product cloud not find in the list", null));

        iProductRepository.delete(aProduct);

        return productMapper.toProductDto(aProduct);

    }


}
