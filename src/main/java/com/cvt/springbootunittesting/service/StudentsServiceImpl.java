package com.cvt.springbootunittesting.service;

import com.cvt.springbootunittesting.ExceptionHandler.StudentNotFoundException;
import com.cvt.springbootunittesting.model.Students;
import com.cvt.springbootunittesting.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentsServiceImpl implements StudentService {
    @Autowired
    StudentRepository studentRepository;

    @Override
    public List<Students> getAll() {
        return studentRepository.findAll();
    }

    @Override
    public Students getOne(int id) {
        Optional<Students> op=studentRepository.findById(id);
        if(!op.isPresent())
            throw new StudentNotFoundException(id);
        return op.get();
    }

    @Override
    public Students save(Students students) {
        return studentRepository.save(students);
    }

    @Override
    public Students update(int id, Students students) {

        Students  students1=studentRepository.findById(id).orElseThrow(() -> new NullPointerException());

            students1.setName(students.getName());
            students1.setSalary(students.getSalary());

            return students1;

    }

    @Override
    public void delete(int id) {

        studentRepository.deleteById(id);
    }

    @Override
    public boolean isExist(int id){
        boolean res=false;
        if(studentRepository.findById(id).isPresent())
            res= true;
        return res;
    }
}
