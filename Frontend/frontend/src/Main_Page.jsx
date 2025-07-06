import { useState, useEffect } from 'react'


function Main_Page() {
  const [courses, setCourses] = useState([]);

  useEffect(() => {
        // Fetch data from the backend
        fetch('http://localhost:8080/test_SQL', {credentials: 'include'})
            .then(response => response.json())
            .then(data => setCourses(data))
            .catch(error => console.error('Error fetching data:', error));
    }, []);

  return (
        <div>
            <h1>Course List</h1>
            <table>
                <thead>
                    <tr>
                        <th>courseID</th>
                        <th>courseName</th>
                        <th>studentID</th>
                    </tr>
                </thead>
                <tbody>
                    {courses.map(course => (
                        <tr key={course.courseID}>
                            <td>{course.courseID}</td>
                            <td>{course.courseName}</td>
                            <td>{course.studentID}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}

export default Main_Page
