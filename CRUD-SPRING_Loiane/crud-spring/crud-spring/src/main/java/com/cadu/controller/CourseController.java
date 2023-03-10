package com.cadu.controller;

import com.cadu.model.Course;
import com.cadu.service.CourseService;
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
    public Course findById(@PathVariable("id") @NotNull @Positive Long id) {
        return courseService.findById(id);

    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Course create(@RequestBody @Valid Course course) {
        return courseService.create(course);
    }

    @PutMapping("/{id}")
    public Course update(@PathVariable("id") @NotNull @Positive Long id,
                                         @RequestBody @Valid Course course) {
        return courseService.update(id, course);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @NotNull @Positive Long id){
        courseService.delete(id);
    }
}
