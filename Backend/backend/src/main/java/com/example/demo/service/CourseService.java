package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.repository.CourseRepository;
import com.example.demo.model.CourseEntity;

import java.util.List;
import java.util.Objects;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    public CourseService (CourseRepository courseRepository) {
    	this.courseRepository = Objects.requireNonNull(courseRepository);
    }
	
	public List<CourseEntity> getCourseTable() {
        // Call the repository to get the course data
        return courseRepository.findAll();
    }
	
	public List<CourseEntity> getCourseByName(String courseName) {
        return courseRepository.findByCourseName(courseName);
    }
}
