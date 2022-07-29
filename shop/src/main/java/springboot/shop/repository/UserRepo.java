package springboot.shop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import springboot.shop.entity.User;

public interface UserRepo extends JpaRepository<User, Integer>{
//	List<Department> findByphoneNumber(String phone);
	@Query("SELECT u FROM User u WHERE u.name LIKE :x")
	Page<User> search(@Param("x") String s,Pageable pageable);
////	
////	@Query("SELECT t FROM Ticket t WHERE t.processingTime >= :from")
////	List<Ticket> searchByFrom(@Param("from") Date from);
////	
////	@Query("SELECT t FROM Ticket t WHERE t.processingTime <= :to")
////	List<Ticket> searchByTo(@Param("to") Date to);
////	
////	@Query("SELECT t FROM Ticket t WHERE t.processingTime <= :to AND t.processingTime >= :from")
////	List<Ticket> searchByTo(@Param("to") Date to,@Param("from") Date from);
//////	
//////	@Query("SELECT t FROM Ticket t JOIN d.department d WHERE d.id =  :dId")
//////	List<Ticket> searchByDepartment(@Param("to") int  departmentId);
	
	
	User findByUsername(String username);
}