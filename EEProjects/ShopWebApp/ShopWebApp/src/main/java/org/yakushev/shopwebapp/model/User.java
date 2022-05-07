package org.yakushev.shopwebapp.model;

import lombok.Data;
import org.yakushev.shopwebapp.util.MergeableBean;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "swa_user")
@Data
public class User extends MergeableBean implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String username;
	private String password;
	private String role;
	private Date createdAt;
}
