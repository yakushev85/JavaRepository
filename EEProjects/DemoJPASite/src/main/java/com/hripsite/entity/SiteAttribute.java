package com.hripsite.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity(name="hsattribute")
@Table(name="hripsite_attributes")
public class SiteAttribute {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="hripsite_attributes_seq")
	@SequenceGenerator(name="hripsite_attributes_seq", allocationSize=1, sequenceName="hripsite_attributes_seq")
	@Column(name="attribute_id")
	private int attribute_id;

	@Column(name="name")
	private String name;
	
	@Column(name="description")
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getAttribute_id() {
		return attribute_id;
	}
}
