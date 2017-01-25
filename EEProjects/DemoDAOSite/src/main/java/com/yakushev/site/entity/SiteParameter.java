package com.yakushev.site.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

@Entity(name="hsparameter")
@Table(name="hripsite_parameters")
@Proxy(lazy=false)
public class SiteParameter {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="hripsite_parameters_seq")
	@SequenceGenerator(name="hripsite_parameters_seq", allocationSize=1, sequenceName="hripsite_parameters_seq")
	@Column(name="parameter_id")
	private int parameter_id;

	@Column(name="object_id")
	private int object_id;
	
	@Column(name="attribute_id")
	private int attribute_id;
	
	@Column(name="reference_id")
	private int reference_id;
	
	@Column(name="data_value")
	private String data_value;
	
	@Column(name="date_value")
	private Date date_value;

	public int getObject_id() {
		return object_id;
	}

	public void setObject_id(int object_id) {
		this.object_id = object_id;
	}

	public int getAttribute_id() {
		return attribute_id;
	}

	public void setAttribute_id(int attribute_id) {
		this.attribute_id = attribute_id;
	}

	public int getReference_id() {
		return reference_id;
	}

	public void setReference_id(int reference_id) {
		this.reference_id = reference_id;
	}

	public String getData_value() {
		return data_value;
	}

	public void setData_value(String data_value) {
		this.data_value = data_value;
	}

	public Date getDate_value() {
		return date_value;
	}

	public void setDate_value(Date date_value) {
		this.date_value = date_value;
	}

	public int getParameter_id() {
		return parameter_id;
	}
}
