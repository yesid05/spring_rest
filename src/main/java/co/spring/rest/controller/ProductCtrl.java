package co.spring.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import co.spring.rest.entity.bo.Permission;
import co.spring.rest.entity.bo.Role;
import co.spring.rest.entity.dto.ProductDto;
import co.spring.rest.service.ProductServ;
import jakarta.validation.Valid;

import java.net.URI;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAuthority('"+Permission.READ+"')")
    @GetMapping
    public ResponseEntity<Map<String, Object>> getListProduct(@RequestParam(required = false) Integer pageNumber,@RequestParam(required = false) Integer pageSize, @RequestParam(required = false) String direction,@RequestParam(required = false) String sort) {

        Map<String, Object> mapProductDto = productServ.getList(pageNumber,pageSize,sort,direction);

        return ResponseEntity.ok(mapProductDto);
    }

    @PreAuthorize("hasAuthority('"+Permission.READ+"')")
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> findById(@PathVariable long id) {
        
        ProductDto aProductDto = productServ.findById(id);

        if(aProductDto != null)
            return ResponseEntity.ok(aProductDto);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasAuthority('"+Permission.CREATE+"')")
    @PostMapping()
    public ResponseEntity<ProductDto> add(@Valid @RequestBody ProductDto product, UriComponentsBuilder uriComponentsBuilder) {
        ProductDto aProductDto = productServ.add(product);

        if(aProductDto == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        URI uri = uriComponentsBuilder.path("/api/product/{id}").buildAndExpand(aProductDto.getId()).toUri();

        return ResponseEntity.created(uri).body(aProductDto);
    }

    @PreAuthorize("hasAuthority('"+Permission.UPDATE+"')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @Valid @RequestBody ProductDto product) {
        
        ProductDto aProductDto = productServ.update(id, product);

        if(aProductDto == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(aProductDto);

    }

    @PreAuthorize("hasAuthority('"+Permission.DELETE+"')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id){

        productServ.delete(id);

        return ResponseEntity.noContent().build();
        
    }
    

    

}
