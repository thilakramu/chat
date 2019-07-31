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
@Table(name="user_private_files")
public class UserPrivateFile {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;	
	
	@Column(name="uuid")
	private String uuid;
	
	@ManyToOne()
	@JoinColumn(name="user_id", referencedColumnName="id")
	private User user;
	
	@Column(name="file_path", nullable=true)
	private String filePath;
	
	@Column(name="file_type", nullable=true)
	private String fileType;
	
	@Column(name="file_size", nullable=true)
	private Long fileSize;
	
	@Column(name="file_ext", nullable=true)
	private String fileExt;
	
	@Column(name="file_name", nullable=true)
	private String fileName;
	
	@Column(name="width", nullable=true)
	private Integer width;
	
	@Column(name="height", nullable=true)
	private Integer height;
	
	@Column(name="is_public", nullable=true)
	private Boolean isPublic = false;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileExt() {
		return fileExt;
	}

	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Boolean getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(Boolean isPublic) {
		this.isPublic = isPublic;
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

	@Column(name="created_on", nullable=true)
	private Date createdOn;
	
	@Column(name="updated_on", nullable=true)
	private Date updatedOn;	


}
