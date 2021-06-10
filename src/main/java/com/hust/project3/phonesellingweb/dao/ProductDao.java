package com.hust.project3.phonesellingweb.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hust.project3.phonesellingweb.entity.Product;

@Repository
public interface ProductDao extends JpaRepository<Product, Integer> {
	
	@Transactional
	@Modifying
	@Query("UPDATE Product p SET p.seenCount=:count WHERE p.id=:id")
	public int increaseSeenNum(@Param("count") int count, @Param("id") int id);

	@Transactional
	@Modifying
	@Query("UPDATE Product p SET p.buyCount=:buyCount WHERE p.id=:id")
	public int increaseBuyNum(@Param("buyCount")int buyCount,@Param("id") int id);
	
	public List<Product> findByDeletedIsFalseAndManufacturer_Id(int manufacturerId);

	public List<Product> findByDeletedIsFalse();
}
