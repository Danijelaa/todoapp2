package com.todoapp.test;

import static org.junit.Assert.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.todoapp.InitData;
import com.todoapp.ToDoApp2Application;
import com.todoapp.dto.DashboardColumnCreateDto;
import com.todoapp.dto.DashboardColumnDto;
import com.todoapp.dto.DashboardColumnLightDto;
import com.todoapp.dto.TaskDto;
import com.todoapp.mapper.DashboardColumnToDashboardColumnLightDto;
import com.todoapp.model.Dashboard;
import com.todoapp.model.DashboardColumn;
import com.todoapp.model.Task;
import com.todoapp.model.User;
import com.todoapp.service.DashboardColumnService;
import com.todoapp.service.DashboardService;
import com.todoapp.service.TaskService;
import com.todoapp.service.UserService;
import com.todoapp.utils.JsonMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ToDoApp2Application.class)
public class DashboardColumnControllerIntegrationTest {

	//@Autowired
	private MockMvc mvc;
	@Autowired
	WebApplicationContext webApplicationContext;
	
	@Autowired
	UserService userService;
	@Autowired
	JsonMapper jsonMapper;
	@Autowired
	DashboardColumnService dashboardColumnService;
	@Autowired
	DashboardService dashboardService;
	@Autowired
	TaskService taskService;
	MvcResult mvcResult;
	@Autowired
	DashboardColumnToDashboardColumnLightDto toDashboardColumnLightDto;
	
	Long userId;
	Long dashboardId;
	List<Long> dashboardColumnIds=new ArrayList<Long>();
	int numberOfDashboardColumns=2;
	int numberOfTasksInColumn=2;
	
	@Before
	public void setUp() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
		
		User user=new User();
		user.setUsername("username-test");
		user.setPassword("password-test");
		user=userService.save(user);
		userId=user.getId();
		
		Dashboard d=new Dashboard();
		d.setTitle("test dashboard of test user");
		d.setUser(user);
		d=dashboardService.save(d);
		dashboardId=d.getId();
		
		for(int j=1; j<=numberOfDashboardColumns; j++) {
			DashboardColumn dc=new DashboardColumn();
			dc.setTitle("dashboardColumn "+j);
			dc.setDashboard(d);
			dc=dashboardColumnService.save(dc);
			dashboardColumnIds.add(dc.getId());
			
			for(int k=1; k<=numberOfTasksInColumn; k++) {
				Task t=new Task();
				t.setTitle("task "+k+" of column "+j);
				t.setDescription("description");
				t.setDashboardColumn(dc);
				taskService.save(t);
			}
		}
	}
	
	@Test
	@WithMockUser(value = "username-test", password = "password-test")
	public void testFindById() throws Exception{
		//fetching existent dashboard column
		mvcResult=mvc.perform(MockMvcRequestBuilders.get("/api/dashboard-columns/"+dashboardColumnIds.get(0)).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
	   int status = mvcResult.getResponse().getStatus();
	   assertEquals(200, status);
	   String content = mvcResult.getResponse().getContentAsString();
	   DashboardColumnDto dashC = jsonMapper.mapFromJson(content, DashboardColumnDto.class);
	   assertEquals(numberOfTasksInColumn, dashC.getTasks().size());
	   assertNotNull(dashC.getTitle());
	   
	   //fetching non-existent dashboard column
	   mvcResult=mvc.perform(MockMvcRequestBuilders.get("/api/dashboard-columns/"+dashboardColumnIds.get(0)*100).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
	   status = mvcResult.getResponse().getStatus();
	   assertEquals(404, status);
	}
	
	@Test
	@WithMockUser(value = "***username-test***", password = "password-test")
	public void testFindByIdWithUnauthorizedUser() throws Exception{
		//fetching existent dashboard column
		mvcResult=mvc.perform(MockMvcRequestBuilders.get("/api/dashboard-columns/"+dashboardColumnIds.get(0)).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
	   int status = mvcResult.getResponse().getStatus();
	   assertEquals(401, status);
	}
	
	@Test
	@WithMockUser(value = "username-test", password = "password-test")
	public void testDeleteById() throws Exception{
		//deleting existent dashboard column
		mvcResult=mvc.perform(MockMvcRequestBuilders.delete("/api/dashboard-columns/"+dashboardColumnIds.get(0)).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(204, status);
		
		//deleting non-existent dashboard column
		mvcResult=mvc.perform(MockMvcRequestBuilders.delete("/api/dashboard-columns/"+dashboardColumnIds.get(0)*1000).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		status = mvcResult.getResponse().getStatus();
		assertEquals(404, status);
	}
	
	@Test
	@WithMockUser(value = "***username-test***", password = "password-test")
	public void testDeleteByIdWithUnauthorizedUser() throws Exception{
		//deleting existent dashboard column
		mvcResult=mvc.perform(MockMvcRequestBuilders.delete("/api/dashboard-columns/"+dashboardColumnIds.get(0)).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(401, status);
	}
	
	@Test
	@WithMockUser(value = "username-test", password = "password-test")
	public void testCreate() throws Exception{
		//setting id of existent Dashboard
		DashboardColumnCreateDto dashboardColumnCreate=new DashboardColumnCreateDto();
		dashboardColumnCreate.setDashboardId(dashboardId);
		dashboardColumnCreate.setTitle("title");
		String dashboardColumnCreateToJson=jsonMapper.mapToJson(dashboardColumnCreate);
		mvcResult=mvc.perform(MockMvcRequestBuilders.post("/api/dashboard-columns").contentType(MediaType.APPLICATION_JSON).content(dashboardColumnCreateToJson)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(201, status);
		DashboardColumnDto savedDashboardColumn=jsonMapper.mapFromJson(mvcResult.getResponse().getContentAsString(), DashboardColumnDto.class);
		assertNotNull(savedDashboardColumn.getId());
		assertEquals("title", savedDashboardColumn.getTitle());
		
		//setting id of non-existent Dashboard
		dashboardColumnCreate=new DashboardColumnCreateDto();
		dashboardColumnCreate.setDashboardId(dashboardId*100);
		dashboardColumnCreate.setTitle("title");
		dashboardColumnCreateToJson=jsonMapper.mapToJson(dashboardColumnCreate);
		mvcResult=mvc.perform(MockMvcRequestBuilders.post("/api/dashboard-columns").contentType(MediaType.APPLICATION_JSON).content(dashboardColumnCreateToJson)).andReturn();
		status = mvcResult.getResponse().getStatus();
		assertEquals(404, status);
	}
	
	@Test
	@WithMockUser(value = "***username-test***", password = "password-test")
	public void testCreateWithUnauthorizedUser() throws Exception{
		//setting id of existent Dashboard
		DashboardColumnCreateDto dashboardColumnCreate=new DashboardColumnCreateDto();
		dashboardColumnCreate.setDashboardId(dashboardId);
		dashboardColumnCreate.setTitle("title");
		String dashboardColumnCreateToJson=jsonMapper.mapToJson(dashboardColumnCreate);
		mvcResult=mvc.perform(MockMvcRequestBuilders.post("/api/dashboard-columns").contentType(MediaType.APPLICATION_JSON).content(dashboardColumnCreateToJson)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(401, status);
	}
	
	@Test
	@WithMockUser(value = "username-test", password = "password-test")
	public void testUpdate() throws Exception{
		//updating title
		DashboardColumn dashboardColumn=dashboardColumnService.findById(dashboardColumnIds.get(0));
		DashboardColumnLightDto dashboardColumnUpdate=toDashboardColumnLightDto.convert(dashboardColumn);
		String randomTitle=UUID.randomUUID().toString();
		dashboardColumnUpdate.setTitle(randomTitle);
		String dashboardColumnUpdateToJson=jsonMapper.mapToJson(dashboardColumnUpdate);
		mvcResult=mvc.perform(MockMvcRequestBuilders.put("/api/dashboard-columns/"+dashboardColumnUpdate.getId()).contentType(MediaType.APPLICATION_JSON).content(dashboardColumnUpdateToJson)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		DashboardColumnDto savedDashboardColumn=jsonMapper.mapFromJson(mvcResult.getResponse().getContentAsString(), DashboardColumnDto.class);
		assertEquals(randomTitle, savedDashboardColumn.getTitle());
		
		//update title to empty string
		dashboardColumnUpdate.setTitle("");
		dashboardColumnUpdateToJson=jsonMapper.mapToJson(dashboardColumnUpdate);
		mvcResult=mvc.perform(MockMvcRequestBuilders.put("/api/dashboard-columns/"+dashboardColumnUpdate.getId()).contentType(MediaType.APPLICATION_JSON).content(dashboardColumnUpdateToJson)).andReturn();
		status = mvcResult.getResponse().getStatus();
		assertEquals(400, status);
	}
	
	@Test
	@WithMockUser(value = "username-test", password = "password-test")
	public void testGetTasks() throws Exception{
		//fetching tasks of existent dashboard column
		mvcResult=mvc.perform(MockMvcRequestBuilders.get("/api/dashboard-columns/"+dashboardColumnIds.get(0)+"/tasks").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
	   int status = mvcResult.getResponse().getStatus();
	   assertEquals(200, status);
	   String content = mvcResult.getResponse().getContentAsString();
	   TaskDto[] taskDtos=jsonMapper.mapFromJson(content, TaskDto[].class);
	   assertTrue(taskDtos.length==numberOfTasksInColumn);
	   
	 //fetching tasks of non-existent dashboard column
	   mvcResult=mvc.perform(MockMvcRequestBuilders.get("/api/dashboard-columns/"+dashboardColumnIds.get(0)*1000+"/tasks").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
 	   status = mvcResult.getResponse().getStatus();
 	   assertEquals(404, status);
	}
	
	@Test
	@WithMockUser(value = "***username-test***", password = "password-test")
	public void testGetTasksWithUnauthorizedUser() throws Exception{
		//fetching tasks of existent dashboard column
		mvcResult=mvc.perform(MockMvcRequestBuilders.get("/api/dashboard-columns/"+dashboardColumnIds.get(0)+"/tasks").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
	   int status = mvcResult.getResponse().getStatus();
	   assertEquals(401, status);
	}
	
	@After
    public void after() throws Exception {
        userService.delete(userId);
    }
}
