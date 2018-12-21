package com.example.chat.entity;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity // This tells Hibernate to make a table out of this class
@Table(name="chat_messages")
public class ChatMessage {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;	
	
	@Column(name="from_id")
	private Integer fromId;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="to_id", referencedColumnName="id")
	private User toId;
	
	@Column(length=1056)
	private String message;
	
	@Column(name="is_read")
	private Boolean read;

	@Column(name="created_on", nullable=true)
	private Date createdOn;
	
	@Column(name="updated_on", nullable=true)
	private Date updatedOn;	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFromId() {
		return fromId;
	}

	public void setFromId(Integer fromId) {
		this.fromId = fromId;
	}

	public User getToId() {
		return toId;
	}

	public void setToId(User toId) {
		this.toId = toId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public Boolean getRead() {
		return read;
	}

	public void setRead(Boolean read) {
		this.read = read;
	}
	
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

}
