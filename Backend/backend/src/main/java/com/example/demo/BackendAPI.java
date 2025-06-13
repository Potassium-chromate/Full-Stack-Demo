package com.example.demo;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class BackendAPI {

    private final JdbcTemplate jdbcTemplate;

    // Inject JdbcTemplate via constructor
    public BackendAPI(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/")
    public String hello() {
        return "Hey, Spring Boot Hello World!";
    }

    @GetMapping("/Test")
    public String SQL_Test() {
        String query = "SELECT * FROM Course";

        // Query the database and get results as a list of maps
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);

        StringBuilder result = new StringBuilder();
        if (rows.isEmpty()) {
            result.append("No data found.");
        } else {
            for (Map<String, Object> row : rows) {
                String courseID = (String) row.get("c_id");
                String courseName = (String) row.get("c_name");
                String studentID = (String) row.get("t_id");

                result.append("courseID: ").append(courseID)
                      .append(", courseName: ").append(courseName)
                      .append(", studentID: ").append(studentID)
                      .append("<br>");
            }
        }

        return result.toString();
    }
}
