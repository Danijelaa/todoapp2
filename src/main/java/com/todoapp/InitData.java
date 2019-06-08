package com.todoapp;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.todoapp.model.Dashboard;
import com.todoapp.model.DashboardColumn;
import com.todoapp.model.Task;
import com.todoapp.model.User;
import com.todoapp.service.DashboardColumnService;
import com.todoapp.service.DashboardService;
import com.todoapp.service.TaskService;
import com.todoapp.service.UserService;

@Component
public class InitData {

	@Autowired
	UserService userSevice;
	@Autowired
	DashboardColumnService dashboardColumnService;
	@Autowired
	DashboardService dashboardService;
	@Autowired
	TaskService taskService;
	
	@PostConstruct
	public void init() {
		int counter=1;
		User user=new User(); 
		user.setUsername("user"); 
		user.setPassword(new BCryptPasswordEncoder().encode("password")); 
		userSevice.save(user);
		for(int i=1; i<=2; i++) {
			User u=new User(); 
			u.setUsername("user"+i); 
			u.setPassword(new BCryptPasswordEncoder().encode("password"+i)); 
			userSevice.save(u);
			
			Dashboard d=new Dashboard();
			d.setTitle("dashboard of user "+i);
			d.setUser(u);
			dashboardService.save(d);
			
			for(int j=1; j<=2; j++) {
				DashboardColumn dc=new DashboardColumn();
				dc.setTitle("dashboardColumn "+j+" of dashboard "+i);
				dc.setDashboard(d);
				dashboardColumnService.save(dc);
				
				for(int k=1; k<=2; k++) {
					Task t=new Task();
					t.setTitle("task "+k+" of column "+j);
					t.setDescription("description"+counter++);
					t.setDashboardColumn(dc);
					taskService.save(t);
				}
			}
			
		}
	}	

}
