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

@Service
public class ProductServ {

    @Autowired
    private IProductRepository iProductRepository;

    @Autowired
    private ProductMapper productMapper;

    public List<ProductDto> getListProduct(){
        return iProductRepository
            .findAll()
            .stream()
            .map(productMapper::toProductDto)
            .toList();
    }

    public ProductDto findById(long id){
        
        Product aProduct = iProductRepository.findById(id).orElseThrow(() -> new NotFoundError("Product not found", "Product cloud not find in the list", null));

        return productMapper.toProductDto(aProduct);

    }
    
    public ProductDto add(Product product){

        Product aProduct = null;

        try {
            aProduct = iProductRepository.save(product);
        } catch (Exception e) {
            throw new CreatedError("Product not created", "Product not created, error internal "+e.getMessage(), e);
        }

        return productMapper.toProductDto(aProduct);

    }

    public ProductDto update(long id, Product product){

        Product aProduct = iProductRepository.findById(id).orElseThrow(() -> new NotFoundError("Product not found", "Product cloud not find in the list", null));

        aProduct.setName(product.getName());
        aProduct.setDescription(product.getDescription());
        aProduct.setReleaseDate(product.getReleaseDate());
        aProduct.setImg(product.getImg());
        aProduct.setIdCategoria(product.getIdCategoria());

        try {
            iProductRepository.save(aProduct);
        } catch (Exception e) {
            throw new CreatedError("Product not update", "Product not update, error internal "+e.getMessage(), e);
        }

        return productMapper.toProductDto(aProduct);

    }

    public ProductDto delete(long id){

        Product aProduct = iProductRepository.findById(id).orElseThrow(() -> new NotFoundError("Product not found", "Product cloud not find in the list", null));

        iProductRepository.delete(aProduct);

        return productMapper.toProductDto(aProduct);

    }


}
