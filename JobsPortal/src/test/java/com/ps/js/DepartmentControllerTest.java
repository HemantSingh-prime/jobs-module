package com.ps.js;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ps.js.controller.DepartmentController;
import com.ps.js.entity.Department;
import com.ps.js.mapper.DepartmentMapperImpl;
import com.ps.js.payload.DepartmentPayload;
import com.ps.js.service.DepartmentServiceImpl;
import com.ps.js.service.JobLocationServiceImpl;

@WebMvcTest(value = DepartmentController.class)
@RunWith(MockitoJUnitRunner.class)
@TestInstance(Lifecycle.PER_CLASS)
public class DepartmentControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private DepartmentServiceImpl departmentServiceImpl;
	@MockBean
	private DepartmentMapperImpl departmentMapperImpl;

	private static ObjectMapper objectMapper = new ObjectMapper();
	Department departmentRequest;
	Department departmentResponse;
	DepartmentPayload departmentPayloadRequest;
	DepartmentPayload departmentPayloadResponse;

	@BeforeAll
	void init() throws Exception {
		departmentRequest = objectMapper.readValue(new File("src/test/java/com/ps/js/payload/department-request.json"),
				Department.class);
		departmentResponse = objectMapper
				.readValue(new File("src/test/java/com/ps/js/payload/department-response.json"), Department.class);
		departmentPayloadResponse = objectMapper.readValue(
				new File("src/test/java/com/ps/js/payload/department-response.json"), DepartmentPayload.class);
		departmentPayloadRequest = objectMapper.readValue(
				new File("src/test/java/com/ps/js/payload/department-response.json"), DepartmentPayload.class);
	}

	/**
	 * To test find all department end point successful
	 * @throws Exception
	 */
	@Test
	void fetchDepartment_successful() throws Exception {
		List<DepartmentPayload> listDepartments = new ArrayList<DepartmentPayload>();
		listDepartments.add(departmentPayloadResponse);
		Mockito.when(departmentServiceImpl.fetchAllDepartment()).thenReturn(listDepartments);

		mockMvc.perform(get("/department/fetch-department/").contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful());
	}

	/**
	 * To test create department end point successful
	 * @throws Exception
	 */
	@Test
	void addDepartment_successful() throws Exception {
		Department department = new Department();
		departmentPayloadRequest.setDepartmentId(0);
		departmentRequest.setDepartmentId(0);
		Optional<DepartmentPayload> optionalDepartmentPayload=Optional.of(departmentPayloadResponse);
		Mockito.when(departmentMapperImpl.departmentPayloadToDepartmentMapper(departmentPayloadRequest, department))
				.thenReturn(departmentRequest);
		Mockito.when(departmentServiceImpl.addDepartment(departmentRequest)).thenReturn(optionalDepartmentPayload);
		String json=objectMapper.writeValueAsString(departmentPayloadRequest);
		
		mockMvc.perform(post("/department/add-department/").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful());
	}
	
	/**
	 * To test create department end point unsuccessful
	 * @throws Exception
	 */
	@Test
	void addDepartment_unsuccessful() throws Exception {
		Department department = new Department();
		departmentPayloadRequest.setDepartmentId(0);
		departmentRequest.setDepartmentId(0);
		Optional<DepartmentPayload> optionalDepartmentPayload=Optional.empty();
		Mockito.when(departmentMapperImpl.departmentPayloadToDepartmentMapper(departmentPayloadRequest, department))
				.thenReturn(departmentRequest);
		Mockito.when(departmentServiceImpl.addDepartment(departmentRequest)).thenReturn(optionalDepartmentPayload);
		String json=objectMapper.writeValueAsString(departmentPayloadRequest);
		
		mockMvc.perform(post("/department/add-department/").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().is4xxClientError());
	}
	
	
	/**
	 * To test update department end point successful
	 * @throws Exception
	 */
	@Test
	void updateDepartment_successful()throws Exception{
		Department department = new Department();
		Optional<DepartmentPayload> optionalDepartmentPayload=Optional.of(departmentPayloadResponse);
		Mockito.when(departmentMapperImpl.departmentPayloadToDepartmentMapper(departmentPayloadRequest, department)).thenReturn(departmentRequest);
		Mockito.when(departmentServiceImpl.updateDepartment(departmentRequest)).thenReturn(optionalDepartmentPayload);
		String json=objectMapper.writeValueAsString(departmentPayloadRequest);
		
		mockMvc.perform(patch("/department/update-department/").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful());
	}
	
	/**
	 * To test update department end point unsuccessful
	 * @throws Exception
	 */
	@Test
	void updateDepartment_unsuccessful()throws Exception{
		Department department = new Department();
		Optional<DepartmentPayload> optionalDepartmentPayload=Optional.empty();
		Mockito.when(departmentMapperImpl.departmentPayloadToDepartmentMapper(departmentPayloadRequest, department)).thenReturn(departmentRequest);
		Mockito.when(departmentServiceImpl.updateDepartment(departmentRequest)).thenReturn(optionalDepartmentPayload);
		String json=objectMapper.writeValueAsString(departmentPayloadRequest);
		
		mockMvc.perform(patch("/department/update-department/").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().is4xxClientError());
	}
	
	/**
	 * To test delete department end point successful
	 * @throws Exception
	 */
	@Test
	void deleteDepartment_successful()throws Exception{
		Optional<Department> optionalDepartment=Optional.of(departmentResponse);
		
		Mockito.when(departmentServiceImpl.removeDepartment(1)).thenReturn(optionalDepartment);
		
		mockMvc.perform(delete("/department/delete-department/{department-id}",1).contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful());
	}
	
	/**
	 * To test delete department end point unsuccessful
	 * @throws Exception
	 */
	@Test
	void deleteDepartment_unsuccessful()throws Exception{
		Optional<Department> optionalDepartment=Optional.empty();
		
		Mockito.when(departmentServiceImpl.removeDepartment(1)).thenReturn(optionalDepartment);
		
		mockMvc.perform(delete("/department/delete-department/{department-id}",1).contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON)).andExpect(status().is4xxClientError());
	}
}
