package co.spring.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.spring.rest.entity.dto.CategoryDto;
import co.spring.rest.service.CategoryServ;

@RestController
@RequestMapping("/api/category")
public class CategoryCtrl {

    @Autowired
    private CategoryServ categoryServ;

    @GetMapping
    public ResponseEntity<?> getListCategory(){
        
        List<CategoryDto> listCategory = categoryServ.getList();

        return ResponseEntity.ok(listCategory);

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable long id){

        CategoryDto aCategoryDto = categoryServ.findById(id);

        if(aCategoryDto != null)
            return ResponseEntity.ok(aCategoryDto);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public ResponseEntity<?> add(@RequestBody CategoryDto category){

        CategoryDto aCategoryDto = categoryServ.add(category);

        if(aCategoryDto == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(aCategoryDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody CategoryDto category){

        CategoryDto aCategoryDto = categoryServ.update(id, category);

        if(aCategoryDto == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(aCategoryDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id){
        
        CategoryDto aCategoryDto = categoryServ.delete(id);

        if(aCategoryDto == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(aCategoryDto);
    }

}
