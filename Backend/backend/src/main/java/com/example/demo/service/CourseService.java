package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.model.CourseEntity;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
@Service
public class CourseService {
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	public List<CourseEntity> getCourseTable() {
	    String query = "SELECT * FROM Course";

	    // Query the database and get results as a list of maps
	    List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
	    
	    List<CourseEntity> courses = new ArrayList<>();
	    if (!rows.isEmpty()) {
	        for (Map<String, Object> row : rows) {
	            String courseID = (String) row.get("c_id");
	            String courseName = (String) row.get("c_name");
	            String studentID = (String) row.get("t_id");

	            CourseEntity course = new CourseEntity();
	            course.setCourseID(courseID);
	            course.setCourseName(courseName);
	            course.setStudentID(studentID);

	            courses.add(course);
	        }
	    }

	    return courses;
	}
}
