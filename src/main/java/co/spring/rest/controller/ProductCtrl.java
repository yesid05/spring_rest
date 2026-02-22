package co.spring.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.spring.rest.entity.dto.ProductDto;
import co.spring.rest.service.ProductServ;

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<Map<String, Object>> getListProduct(@RequestParam(required = false) Integer pageNumber,@RequestParam(required = false) Integer pageSize, @RequestParam(required = false) String direction,@RequestParam(required = false) String sort) {

        Map<String, Object> mapProductDto = productServ.getList(pageNumber,pageSize,sort,direction);

        return ResponseEntity.ok(mapProductDto);
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
    public ResponseEntity<ProductDto> add(@RequestBody ProductDto product) {
        ProductDto aProductDto = productServ.add(product);

        if(aProductDto == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(aProductDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> update(@PathVariable long id, @RequestBody ProductDto product) {
        
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
