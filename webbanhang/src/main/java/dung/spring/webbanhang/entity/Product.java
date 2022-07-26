package dung.spring.webbanhang.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Table
@Entity
@Data
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@NotEmpty(message = "{product.name.notempty}")
	private String name;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	private long price;

	@NotEmpty(message = "{product.desciption.notempty}")
	private String description;

	private String image;

}
