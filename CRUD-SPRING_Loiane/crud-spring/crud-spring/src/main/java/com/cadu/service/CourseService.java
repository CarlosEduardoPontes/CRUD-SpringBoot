package com.cadu.service;

import org.springframework.stereotype.Service;

@Service
public class CourseService {
    private final CourseService courseService;

    public CourseService(CourseService courseService) {
        this.courseService = courseService;
    }
}
