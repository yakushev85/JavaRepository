package com.hripsite.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity(name="hsobjet_type")
@Table(name="hripsite_object_types")
public class SiteObject_Type {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="hripsite_object_types_seq")
	@SequenceGenerator(name="hripsite_object_types_seq", allocationSize=1, sequenceName="hripsite_object_types_seq")
	@Column(name="object_type_id")
	private int object_type_id;
	
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

	public int getObject_type_id() {
		return object_type_id;
	}

}
