package com.cvt.springbootunittesting.controller;

import com.cvt.springbootunittesting.model.Students;
import com.cvt.springbootunittesting.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentRestController {

    @Autowired
    StudentService studentService;

    @GetMapping("/all")
    public List<Students> getAll(){
            return studentService.getAll();
    }

    @GetMapping("/get/{id}")
    public Students getById(@PathVariable int id){
        return studentService.getOne(id);
    }

    @PostMapping("/save")
    public ResponseEntity save(@RequestBody Students students){
        return new ResponseEntity(studentService.save(students), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public Students updateStudent(@PathVariable int id,@RequestBody Students students){
        return studentService.update(id,students);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteStudent(@PathVariable int id){
        if(!studentService.isExist(id)) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        studentService.delete(id);
        return new ResponseEntity(HttpStatus.OK);

    }


}
