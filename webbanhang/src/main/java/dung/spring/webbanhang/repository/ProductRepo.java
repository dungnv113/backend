package dung.spring.webbanhang.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dung.spring.webbanhang.entity.Category;
import dung.spring.webbanhang.entity.Product;

public interface ProductRepo extends JpaRepository<Product, Integer>{
	@Query("SELECT p FROM Product p WHERE p.name LIKE :x")
	Page<Product> searchName(@Param("x") String s, Pageable pageable);

}
