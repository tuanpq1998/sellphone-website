package com.hust.project3.phonesellingweb.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	
	public Page<Product> findByDeletedIsFalseAndManufacturer_Id(int manufacturerId, Pageable pageable);
	
	public Page<Product> findByDeletedIsFalseAndManufacturer_IdOrderByPrice_ValueAsc(int manufacturerId, Pageable pageable);
	
	public Page<Product> findByDeletedIsFalseAndManufacturer_IdOrderByPrice_ValueDesc(int manufacturerId, Pageable pageable);

	public Page<Product> findByDeletedIsFalse(Pageable pageable);

	public List<Product> findTop4ByDeletedIsFalseOrderByBuyCountDesc();

	public List<Product> findTop6ByDeletedIsFalseAndManufacturer_Id(int manufacturerId);

	public Page<Product> findByDeletedIsFalseAndManufacturer_IdAndNameContaining(int manufacturerId, Pageable pageable,
			String searchKey);

	public Page<Product> findByDeletedIsFalseAndManufacturer_IdAndNameContainingOrderByPrice_ValueAsc(
			int manufacturerId, Pageable pageable, String searchKey);

	public Page<Product> findByDeletedIsFalseAndManufacturer_IdAndNameContainingOrderByPrice_ValueDesc(
			int manufacturerId, Pageable pageable, String searchKey);
}
