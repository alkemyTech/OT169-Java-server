package com.alkemy.ong.entity;

import java.sql.Timestamp;

import java.time.Instant;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.NonNull;


@Entity
@Table(name = "activities")
@SQLDelete(sql = "UPDATE activities SET soft_delete = true WHERE id=?")
@Where(clause = "soft_delete = false")
public class Activity {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private UUID id;
	@NonNull
	@Column(unique = true)
	private String name;
	@NonNull
	@Lob
	private String content;
	@NonNull
	private String image;
	private Timestamp timestamp = Timestamp.from(Instant.now());
	private boolean softDelete = Boolean.FALSE;
	
	public Activity() {
		// TODO Auto-generated constructor stub
	}
	
	public Activity(String name, String content, String image) {
		this.name = name;
		this.image = image;
		this.content = content;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getImage() {
		return image;
	}
	
	public void setImage(String image) {
		this.image = image;
	}
	
	public Timestamp getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	
	public boolean isSoftDeleted() {
		return softDelete;
	}
	
	public void setSoftDelete(boolean softDelete) {
		this.softDelete = softDelete;
	}
}
