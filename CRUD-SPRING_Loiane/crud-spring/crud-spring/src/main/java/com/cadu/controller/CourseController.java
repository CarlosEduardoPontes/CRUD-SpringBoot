package com.cadu.controller;

import com.cadu.model.Course;
import com.cadu.repository.CourseRepository;
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
import java.util.List;

import java.util.Optional;
@Validated
@RestController
@RequestMapping("/api/cursos")
public class CourseController {

    private final CourseRepository courseRepository;

    //Construtor usado no lugar do @Autowired
    public CourseController(CourseRepository courseRepository) {

        this.courseRepository = courseRepository;
    }

    @GetMapping
    public List<Course> list() {
        return courseRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> findById(@PathVariable("id") @NotNull @Positive Long id) {
        return courseRepository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElseThrow(( )->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "**************** ID NÃO ENCONTRADO"));
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Course create(@RequestBody @Valid Course course) {
        return courseRepository.save(course);
    }

    //    @PostMapping
//    public ResponseEntity<Course> create(@RequestBody Course course) {
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .body(courseRepository.save(course));
//    }
    @PutMapping("/{id}")
    public ResponseEntity<Course> update(@PathVariable("id") @NotNull @Positive Long id, @RequestBody Course course) {
        return courseRepository.findById(id)
                .map(recordFound -> {
                    recordFound.setName(course.getName());
                    recordFound.setCategory(course.getCategory());
                    Course updated = courseRepository.save(recordFound);
                    return ResponseEntity.ok().body(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @NotNull @Positive Long id){
        return courseRepository.findById(id)
                .map(recordFound -> {
                    courseRepository.deleteById(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/api/cursos")
    public ResponseEntity find( Course filtro){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(filtro, matcher);
        List<Course> lista = courseRepository.findAll(example);
        return ResponseEntity.ok(lista);
    }
}
