package com.hripsite.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity(name="hsobjet")
@Table(name="hripsite_objects")
public class SiteObject {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="hripsite_objects_seq")
	@SequenceGenerator(name="hripsite_objects_seq", allocationSize=1, sequenceName="hripsite_objects_seq")
	@Column(name="object_id")
	private int object_id;
	
	@Column(name="object_type_id")
	private int object_type_id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="order_num")
	private int order_num;

	public int getObject_type_id() {
		return object_type_id;
	}

	public void setObject_type_id(int object_type_id) {
		this.object_type_id = object_type_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getOrder_num() {
		return order_num;
	}

	public void setOrder_num(int order_num) {
		this.order_num = order_num;
	}

	public int getObject_id() {
		return object_id;
	}
}
