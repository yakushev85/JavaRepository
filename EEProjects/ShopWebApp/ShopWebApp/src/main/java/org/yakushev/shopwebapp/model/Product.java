package org.yakushev.shopwebapp.model;

import org.yakushev.shopwebapp.util.MergeableBean;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Product")
public class Product extends MergeableBean implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private Float price;
	private Date createdAt;
	private String createdBy;

	public Product() {
	}

	public Product(String name, Float price, Date createdAt, String createdBy) {
		this.name = name;
		this.price = price;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Float getPrice() {
		return price;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedBy() {
		return createdBy;
	}

}
