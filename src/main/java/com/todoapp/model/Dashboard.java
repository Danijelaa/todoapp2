package com.todoapp.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Dashboard {

	@Id
	@GeneratedValue
	@Column
	private Long id;
	@Column
	private String title;
	@ManyToOne
	private User user;
	@OneToMany(mappedBy = "dashboard",cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<DashboardColumn> dashboardColumns=new ArrayList<>();
	
	public Dashboard() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
