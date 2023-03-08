package com.cadu.controller;

import com.cadu.model.Course;
import com.cadu.repository.CourseRepository;
import com.cadu.service.CourseService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.xml.ws.soap.Addressing;
import java.util.List;

import java.util.Optional;
@Validated
@RestController
@RequestMapping("/api/cursos")
public class CourseController {

    private final CourseService courseService;

    //Construtor usado no lugar do @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public @ResponseBody List<Course> list() {
        return courseService.list();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> findById(@PathVariable("id") @NotNull @Positive Long id) {
        return courseService.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElseThrow(( )->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "**************** ID NÃO ENCONTRADO"));
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Course create(@RequestBody @Valid Course course) {
        return courseService.create(course);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> update(@PathVariable("id") @NotNull @Positive Long id,
                                         @RequestBody @Valid Course course) {
        return courseService.update(id, course)
                .map(recordFound -> {
                   return ResponseEntity.ok().body(recordFound);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @NotNull @Positive Long id){
        if(courseService.delete(id))
            return ResponseEntity.noContent().<Void>build();
        return ResponseEntity.notFound().build();
    }
//    @GetMapping("/api/cursos")
//    public ResponseEntity find( Course filtro){
//        ExampleMatcher matcher = ExampleMatcher
//                .matching()
//                .withIgnoreCase()
//                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
//        Example example = Example.of(filtro, matcher);
//        List<Course> lista = courseRepository.findAll(example);
//        return ResponseEntity.ok(lista);
//    }
}
