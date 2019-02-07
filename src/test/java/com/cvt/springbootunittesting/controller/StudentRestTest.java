package com.cvt.springbootunittesting.controller;

import com.cvt.springbootunittesting.model.Students;
import com.cvt.springbootunittesting.service.StudentService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;

import org.mockito.Mockito;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(StudentRestController.class)
public class StudentRestTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Autowired
    private StudentRestController studentRestController;

    @Before
    public void setUp() throws Exception {
        mockMvc= MockMvcBuilders.standaloneSetup(studentRestController)
                .build();
        List<Students> studentsList = Arrays.asList(
                new Students(1,"Aman", (double) 12000),
                new Students(2,"Raman", (double) 12000),
                new Students(3,"Ram", (double) 12000),
                new Students(4,"Naveen", (double) 12000)
        );

        Mockito.when(studentService.getAll()).thenReturn(studentsList);
    }

    @Test
    public void test_Student_GetAll() throws Exception {
       String uri="/students/all";

       MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.get(uri)
        .accept(MediaType.APPLICATION_JSON)).andReturn();

        //status
        int status=mvcResult.getResponse().getStatus();
        Assert.assertThat(status, Matchers.equalTo(200));
        //content
        String content = mvcResult.getResponse().getContentAsString();
        Students[] students = mapFromJson(content, Students[].class);
        System.out.println(content); // printing content
        Assert.assertTrue(students.length > 0);

    }

    @Test
    public void test_Student_GetOne() throws Exception {
        String uri="/students/get/"+new Random().nextInt(5);

        int status =mockMvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse().getStatus();
        Assert.assertThat("STATUS_NOT_MATCH",status,Matchers.is(200));

    }

    @Test
    public void test_Student_NotFound() throws Exception {

        int status=mockMvc.perform(MockMvcRequestBuilders.get("/students/get/20")
                .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse().getStatus();

        Assert.assertThat(status, Matchers.is(404));
    }

    @Test
    public void test_Student_BadRequest() throws Exception {
        int status=mockMvc.perform(MockMvcRequestBuilders.get("/students/get/A")
                .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse().getStatus();

        Assert.assertThat(status, Matchers.is(400));
    }

    @Test
    public void test_Student_Created() throws Exception {

        String student=mapToJson(new Students(11,"Kiran", (double) 12000));

        int status =mockMvc.perform(MockMvcRequestBuilders.post("/students/save")
        .contentType(MediaType.APPLICATION_JSON).content(student)).andReturn().getResponse().getStatus();

        Assert.assertThat(status, Matchers.is(201));
    }

    @Test
    public void test_Studentd_Delete() throws Exception {
        int status=mockMvc.perform(MockMvcRequestBuilders.delete("/students/delete/1")
                .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse().getStatus();

        Assert.assertThat("STATUS_NOT_MATCH",status, Matchers.is(200));

    }

    @Test
    public void test_Studentd_Delete_NOT_FOUND() throws Exception {
        int status=mockMvc.perform(MockMvcRequestBuilders.delete("/students/delete/12")
                .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse().getStatus();

        System.out.println(status);
        Assert.assertThat("STATUS_NOT_MATCH",status, Matchers.is(HttpStatus.NOT_FOUND.value()));

    }

    @Test
    public void test_Studentd_Delete_BAD_REQUEST() throws Exception {
        int status=mockMvc.perform(MockMvcRequestBuilders.delete("/students/delete/a,d")
                .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse().getStatus();

        System.out.println(status);
        Assert.assertThat("STATUS_NOT_MATCH",status, Matchers.is(HttpStatus.BAD_REQUEST.value()));

    }

    @Test
    public void test_Student_Update() throws Exception {
        String student=mapToJson(new Students(11,"Kiran", (double) 12000));

        int status =mockMvc.perform(MockMvcRequestBuilders.put("/students/update/1")
                .contentType(MediaType.APPLICATION_JSON).content(student)).andReturn().getResponse().getStatus();

        Assert.assertThat("STATUS_NOT_MATCH",status, Matchers.is(HttpStatus.OK.value()));
    }

    @Test
    public void test_Student_Update_Null_BAD_REQUEST() throws Exception {
        String student=mapToJson(null);

        int status =mockMvc.perform(MockMvcRequestBuilders.put("/students/update/1")
                .contentType(MediaType.APPLICATION_JSON).content(student)).andReturn().getResponse().getStatus();

        Assert.assertThat("STATUS_NOT_MATCH",status, Matchers.is(HttpStatus.BAD_REQUEST.value()));
    }

    //Test using jsonPath
    @Test
    public void getTestAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/students/all")
        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*",Matchers.hasSize(Matchers.greaterThan(0))));
    }

    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    protected <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }
}
