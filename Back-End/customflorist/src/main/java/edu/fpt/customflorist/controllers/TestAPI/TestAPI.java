package edu.fpt.customflorist.controllers.TestAPI;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("${api.prefix}/api/v1/test/students") // http://localhost:8080/custom-florist/api/v1/test/students
@RequiredArgsConstructor
public class TestAPI {

    private List<Student> studentList = new ArrayList<>();

    // Khởi tạo danh sách giả lập
    @PostConstruct
    public void InitialStudents() {
        studentList.add(new Student(1, "Nguyễn Văn A", 20, "Hà Nội"));
        studentList.add(new Student(2, "Trần Thị B", 22, "Hồ Chí Minh"));
        studentList.add(new Student(3, "Lê Văn C", 21, "Đà Nẵng"));
        studentList.add(new Student(4, "Phạm Thị D", 23, "Huế"));
        studentList.add(new Student(5, "Hoàng Văn E", 19, "Hải Phòng"));
        studentList.add(new Student(6, "Bùi Thị F", 22, "Cần Thơ"));
        studentList.add(new Student(7, "Đỗ Văn G", 20, "Nha Trang"));
        studentList.add(new Student(8, "Vũ Thị H", 21, "Bình Dương"));
        studentList.add(new Student(9, "Trịnh Văn I", 24, "Vũng Tàu"));
        studentList.add(new Student(10, "Ngô Thị J", 23, "Quảng Ninh"));
    }

    // 📌 1. Lấy danh sách tất cả học sinh
    @GetMapping
    public List<Student> getAllStudents() {
        return studentList;
    }

    // 📌 2. Lấy thông tin học sinh theo ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable int id) {
        Optional<Student> student = studentList.stream()
                .filter(s -> s.getId() == id)
                .findFirst();

        if (student.isPresent()) {
            return ResponseEntity.ok(student.get());
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Học sinh không tồn tại!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    // 📌 3. Thêm mới một học sinh
    @PostMapping
    public ResponseEntity<String> addStudent(@RequestBody Student student) {
        student.setId(studentList.size() + 1); // Tạo ID tự động
        studentList.add(student);
        return ResponseEntity.status(HttpStatus.CREATED).body("Học sinh đã được thêm thành công!");
    }

    // 📌 4. Cập nhật thông tin học sinh theo ID
    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable int id, @RequestBody Student updatedStudent) {
        for (Student student : studentList) {
            if (student.getId() == id) {
                student.setName(updatedStudent.getName());
                student.setAge(updatedStudent.getAge());
                student.setAddress(updatedStudent.getAddress());
                return ResponseEntity.ok("Học sinh đã được cập nhật!");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Học sinh không tồn tại!");
    }

    // 📌 5. Xóa học sinh theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable int id) {
        boolean removed = studentList.removeIf(student -> student.getId() == id);
        if (removed) {
            return ResponseEntity.ok("Học sinh đã được xóa!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Học sinh không tồn tại!");
    }

    // Định nghĩa model Student
    static class Student {
        private int id;
        private String name;
        private int age;
        private String address;

        public Student() {}

        public Student(int id, String name, int age, String address) {
            this.id = id;
            this.name = name;
            this.age = age;
            this.address = address;
        }

        public int getId() { return id; }
        public void setId(int id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public int getAge() { return age; }
        public void setAge(int age) { this.age = age; }
        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }
    }
}
