package dung.spring.webbanhang.entity;

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
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotEmpty(message = "{user.name.notempty}")
	private String name;

	@NotEmpty(message = "{user.username.notempty}")
	private String username;

	@NotEmpty(message = "{user.password.notempty}")
	private String password;

	@NotEmpty(message = "{user.email.notempty}")
	private String email;

	@NotEmpty(message = "{user.role.notempty}")
	@ElementCollection(fetch = FetchType.EAGER)
	@Column(name = "role")
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
	private List<String> role;

}
