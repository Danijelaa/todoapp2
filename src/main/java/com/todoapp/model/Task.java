package com.todoapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Task {

	@Id
	@GeneratedValue
	@Column
	private Long id;
	@Column
	private String title;
	@Column
	private String description;
	@ManyToOne
	private DashboardColumn dashboardColumn;
	
	public Task() {
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public DashboardColumn getDashboardColumn() {
		return dashboardColumn;
	}

	public void setDashboardColumn(DashboardColumn dashboardColumn) {
		this.dashboardColumn = dashboardColumn;
	}



	
	
}
