package com.cvt.springbootunittesting.service;

import com.cvt.springbootunittesting.model.Students;
import com.cvt.springbootunittesting.repository.StudentRepository;
import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;


import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.test.web.servlet.MockMvc;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;


public class StudentServiceTest {



    private MockMvc mockMvc;

    @Mock
    StudentRepository studentRepository;

    @InjectMocks
    StudentsServiceImpl studentService;

    List<Students> studentsList;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
         studentsList = Arrays.asList(
                new Students(1,"Aman", (double) 12000),
                new Students(2,"Raman", (double) 12000),
                new Students(3,"Ram", (double) 12000),
                new Students(4,"Naveen", (double) 12000)
        );

    }


    @Test
    public void getAll() throws Exception {
       when(studentRepository.findAll()).thenReturn(studentsList);

       assertFalse(studentService.getAll().isEmpty());
    }

    @Test(expected = NullPointerException.class)
    public void getAll_Null() throws Exception {
        when(studentRepository.findAll()).thenReturn(null);

        assertTrue(studentService.getAll().isEmpty());
    }

    @Test
    public void getOneById() throws Exception {
        Students students= new Students(1,"Aman", (double) 12000);
        when(studentRepository.findById(anyInt()) ).thenReturn( Optional.ofNullable(students));
        assertTrue(studentService.getOne(5).getName().equals("Aman"));
    }

    @Test(expected = NullPointerException.class)
    public void getOneById_NULL() throws Exception {

        when(studentRepository.findById(anyInt()) ).thenReturn( null);
        assertTrue(studentService.getOne(5).getName().equals("Aman"));
    }

    @Test
    public void save() throws Exception {
        Students students= new Students(1,"Aman", (double) 12000);
        when(studentRepository.save(any(Students.class))).thenReturn(students);
        assertTrue(studentService.save(new Students(1,"Aman", (double) 12000)).getName().equals("Aman"));
    }

    @Test(expected = NullPointerException.class)
    public void save_Null() throws Exception {
        Students students= new Students(1,"Aman", (double) 12000);
        when(studentRepository.save(any(Students.class))).thenReturn(students);
        assertTrue(studentService.save(null).getName().equals("Aman"));
    }

    @Test
    public void delete() throws Exception {
        doThrow(NullPointerException.class).when(studentRepository).deleteById(any(Integer.class));


    }
}
