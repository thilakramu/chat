package com.example.chat.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Tracked {
	
	@Column(name="created_on", nullable=true)
	private Date createdOn;
	
	@Column(name="updated_on", nullable=true)
	private Date updatedOn;	
	
	@Column(name="deleted", columnDefinition = "boolean default false")
	private Boolean deleted;
	
	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

}
