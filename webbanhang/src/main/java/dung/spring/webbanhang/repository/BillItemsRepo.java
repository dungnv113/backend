package dung.spring.webbanhang.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dung.spring.webbanhang.entity.BillItems;

public interface BillItemsRepo extends JpaRepository<BillItems, Integer>{
////	List<Department> findByphoneNumber(String phone);
//////	@Query("SELECT t FROM Ticket t WHERE t.phoneNumber LIKE :x")
//////	List<Ticket> search(@Param("x") String s);
//////	
//////	@Query("SELECT t FROM Ticket t WHERE t.processingTime >= :from")
//////	List<Ticket> searchByFrom(@Param("from") Date from);
//////	
//////	@Query("SELECT t FROM Ticket t WHERE t.processingTime <= :to")
//////	List<Ticket> searchByTo(@Param("to") Date to);
//////	
//////	@Query("SELECT t FROM Ticket t WHERE t.processingTime <= :to AND t.processingTime >= :from")
//////	List<Ticket> searchByTo(@Param("to") Date to,@Param("from") Date from);
////////	
////////	@Query("SELECT t FROM Ticket t JOIN d.department d WHERE d.id =  :dId")
////////	List<Ticket> searchByDepartment(@Param("to") int  departmentId);
//	@Query("SELECT b FROM Bill b WHERE b.name LIKE :x")
//	Page<Bill> search (@Param("x") String s, Pageable pageable);
//	@Query("SELECT t FROM Bill t WHERE t.buyDate = :x")
//	Page<Bill> search(@Param("x") Date s, Pageable pageable);
	@Query("SELECT b FROM BillItems b  JOIN b.bill u WHERE u.id = :uId")
	Page<BillItems> searchByBill(@Param("uId") int billId, Pageable pageable);
}
