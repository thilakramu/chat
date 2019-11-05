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
public class ChatMessage extends Tracked {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;	
	
	@Column(name="from_id")
	private Integer fromId;
	
	@Column(name="to_id")
	private Integer toId;
	
	@Column(length=1056, nullable=true)
	private String message;
	
	@Column(name="is_read")
	private Boolean read=false;
	
	@Column(name="has_file", nullable=true)
	private Boolean hasFile;
	
	@ManyToOne()
	@JoinColumn(name="file_id", referencedColumnName="id")
	private UserPrivateFile file;

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

	public Integer getToId() {
		return toId;
	}

	public void setToId(Integer toId) {
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

	public Boolean getHasFile() {
		return hasFile;
	}

	public void setHasFile(Boolean hasFile) {
		this.hasFile = hasFile;
	}

	public UserPrivateFile getFile() {
		return file;
	}

	public void setFile(UserPrivateFile file) {
		this.file = file;
	}

}
