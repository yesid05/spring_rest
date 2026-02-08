package co.spring.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.spring.rest.entity.bo.Product;
import co.spring.rest.entity.dto.ProductDto;
import co.spring.rest.service.ProductServ;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/product")
public class ProductCtrl {

    @Autowired
    private ProductServ productServ;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getListProduct() {

        List<ProductDto> listProduct = productServ.getListProduct();

        return ResponseEntity.ok(listProduct);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> findById(@PathVariable long id) {
        
        ProductDto aProductDto = productServ.findById(id);

        if(aProductDto != null)
            return ResponseEntity.ok(aProductDto);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public ResponseEntity<ProductDto> add(@RequestBody Product product) {
        ProductDto aProductDto = productServ.add(product);

        if(aProductDto == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(aProductDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> update(@PathVariable long id, @RequestBody Product product) {
        
        ProductDto aProductDto = productServ.update(id, product);

        if(aProductDto == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(aProductDto);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductDto> delete(@PathVariable long id){

        ProductDto aProductDto = productServ.delete(id);

        if(aProductDto == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(aProductDto);
    }
    

    

}
