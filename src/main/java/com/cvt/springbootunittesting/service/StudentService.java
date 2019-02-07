package com.cvt.springbootunittesting.service;

import com.cvt.springbootunittesting.model.Students;

import java.util.List;

public interface StudentService {
    List<Students> getAll();
    Students getOne(int id);
    Students save(Students students);
    Students update(int id,Students students);
    void delete(int id);
    boolean isExist(int id);
}
