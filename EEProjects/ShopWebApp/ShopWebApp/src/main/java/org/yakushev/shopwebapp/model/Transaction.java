package org.yakushev.shopwebapp.model;

import lombok.Data;
import org.yakushev.shopwebapp.util.MergeableBean;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Transaction")
@Data
public class Transaction extends MergeableBean implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String description;
	private Long productId;
	private Long userId;
	private Date createdAt;
}
