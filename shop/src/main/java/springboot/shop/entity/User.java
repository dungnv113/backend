package springboot.shop.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name = "users")
@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	@Column(unique = true)
	private String username;
	private String password;
	private String phone;
	@ElementCollection(fetch = FetchType.EAGER)
	@Column(name = "role")
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
	private List<String> role;
}
