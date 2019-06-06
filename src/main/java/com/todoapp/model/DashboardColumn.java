package com.todoapp.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class DashboardColumn {

	@Id
	@GeneratedValue
	@Column
	private Long id;
	@Column
	private String title;
	@ManyToOne
	private Dashboard dashboard;
	@OneToMany(mappedBy = "dashboardColumn")
	private List<Task> tasks=new ArrayList<Task>();
	
	public DashboardColumn() {
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

	public Dashboard getDashboard() {
		return dashboard;
	}

	public void setDashboard(Dashboard dashboard) {
		this.dashboard = dashboard;
	}
	
	
}
