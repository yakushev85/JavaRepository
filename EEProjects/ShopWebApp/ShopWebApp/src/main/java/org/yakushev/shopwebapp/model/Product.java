package org.yakushev.shopwebapp.model;

import lombok.Data;
import org.yakushev.shopwebapp.util.MergeableBean;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Product")
@Data
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
}
