package com.todoapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

@Entity
public class Task {

	@Id
	@GeneratedValue
	@Column
	private Long id;
	@Column(nullable = false)
	@Size(min = 1, max = 50)
	private String title;
	@Column
	@Size(min = 1, max = 200)
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
