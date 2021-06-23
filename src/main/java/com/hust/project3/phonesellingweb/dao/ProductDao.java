package com.hust.project3.phonesellingweb.dao;

import java.util.List;
import java.util.Optional;

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
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "UPDATE Product p SET p.manufacturer_id=0 WHERE p.manufacturer_id=:manufacturerId")
	public int updateManufacturerIdToNoBrand(@Param("manufacturerId") int manufacturerId);
	
	@Transactional
	@Modifying
	@Query("UPDATE Product p SET p.deleted=1 WHERE p.id=:productId")
	public int updateDeleteById(@Param("productId") int productId);
	
	public Page<Product> findByNameContaining(Pageable pageable, String search);
	
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

	public Page<Product> findByDeletedIsFalseOrderByPrice_ValueAsc(Pageable pageable);
	
	public Page<Product> findByDeletedIsFalseAndPrice_ValueBetweenOrderByPrice_ValueAsc(Double min, Double max, Pageable pageable);
	
	public Page<Product> findByDeletedIsFalseAndPrice_ValueLessThanOrderByPrice_ValueAsc(Double max, Pageable pageable);
	
	public Page<Product> findByDeletedIsFalseAndPrice_ValueGreaterThanOrderByPrice_ValueAsc(Double min, Pageable pageable);

	public Page<Product> findByDeletedIsFalseOrderByPrice_ValueDesc(Pageable pageable);
	
	public Page<Product> findByDeletedIsFalseAndPrice_ValueBetweenOrderByPrice_ValueDesc(Double min, Double max, Pageable pageable);
	
	public Page<Product> findByDeletedIsFalseAndPrice_ValueLessThanOrderByPrice_ValueDesc(Double max, Pageable pageable);
	
	public Page<Product> findByDeletedIsFalseAndPrice_ValueGreaterThanOrderByPrice_ValueDesc(Double min, Pageable pageable);

	public Page<Product> findByDeletedIsFalseAndNameContaining(Pageable pageable, String searchKey);

	public Page<Product> findByDeletedIsFalseAndNameContainingOrderByPrice_ValueAsc(Pageable pageable,
			String searchKey);

	public Page<Product> findByDeletedIsFalseAndNameContainingOrderByPrice_ValueDesc(Pageable pageable,
			String searchKey);

	public Page<Product> findByDeletedIsFalseAndPrice_ValueLessThan(double d, Pageable pageable);

	public Page<Product> findByDeletedIsFalseAndPrice_ValueGreaterThan(double d, Pageable pageable);

	public Page<Product> findByDeletedIsFalseAndPrice_ValueBetween(double d, double e, Pageable pageable);

	public Page<Product> findByDeletedIsFalseAndNameContainingAndPrice_ValueLessThan(String searchKey, double d,
			Pageable pageable);

	public Page<Product> findByDeletedIsFalseAndNameContainingAndPrice_ValueGreaterThan(String searchKey, double d,
			Pageable pageable);

	public Page<Product> findByDeletedIsFalseAndNameContainingAndPrice_ValueBetween(String searchKey, double d,
			double e, Pageable pageable);

	public Page<Product> findByDeletedIsFalseAndNameContainingAndPrice_ValueLessThanOrderByPrice_ValueAsc(
			String searchKey, double d, Pageable pageable);

	public Page<Product> findByDeletedIsFalseAndNameContainingAndPrice_ValueGreaterThanOrderByPrice_ValueAsc(
			String searchKey, double d, Pageable pageable);

	public Page<Product> findByDeletedIsFalseAndNameContainingAndPrice_ValueBetweenOrderByPrice_ValueAsc(
			String searchKey, double d, double e, Pageable pageable);

	public Page<Product> findByDeletedIsFalseAndNameContainingAndPrice_ValueLessThanOrderByPrice_ValueDesc(
			String searchKey, double d, Pageable pageable);

	public Page<Product> findByDeletedIsFalseAndNameContainingAndPrice_ValueGreaterThanOrderByPrice_ValueDesc(
			String searchKey, double d, Pageable pageable);

	public Page<Product> findByDeletedIsFalseAndNameContainingAndPrice_ValueBetweenOrderByPrice_ValueDesc(
			String searchKey, double d, double e, Pageable pageable);

	public Page<Product> findByDeletedIsFalseAndManufacturer_IdAndPrice_ValueLessThan(int manufacturerId, double d,
			Pageable pageable);

	public Page<Product> findByDeletedIsFalseAndManufacturer_IdAndPrice_ValueGreaterThan(int manufacturerId, double d,
			Pageable pageable);

	public Page<Product> findByDeletedIsFalseAndManufacturer_IdAndPrice_ValueBetween(int manufacturerId, double d,
			double e, Pageable pageable);

	public Page<Product> findByDeletedIsFalseAndManufacturer_IdAndPrice_ValueLessThanOrderByPrice_ValueAsc(
			int manufacturerId, double d, Pageable pageable);

	public Page<Product> findByDeletedIsFalseAndManufacturer_IdAndPrice_ValueGreaterThanOrderByPrice_ValueAsc(
			int manufacturerId, double d, Pageable pageable);

	public Page<Product> findByDeletedIsFalseAndManufacturer_IdAndPrice_ValueBetweenOrderByPrice_ValueAsc(
			int manufacturerId, double d, double e, Pageable pageable);

	public Page<Product> findByDeletedIsFalseAndManufacturer_IdAndPrice_ValueLessThanOrderByPrice_ValueDesc(
			int manufacturerId, double d, Pageable pageable);

	public Page<Product> findByDeletedIsFalseAndManufacturer_IdAndPrice_ValueGreaterThanOrderByPrice_ValueDesc(
			int manufacturerId, double d, Pageable pageable);

	public Page<Product> findByDeletedIsFalseAndManufacturer_IdAndPrice_ValueBetweenOrderByPrice_ValueDesc(
			int manufacturerId, double d, double e, Pageable pageable);

	public Page<Product> findByDeletedIsFalseAndManufacturer_IdAndNameContainingAndPrice_ValueLessThan(
			int manufacturerId, String searchKey, double d, Pageable pageable);

	public Page<Product> findByDeletedIsFalseAndManufacturer_IdAndNameContainingAndPrice_ValueGreaterThan(
			int manufacturerId, String searchKey, double d, Pageable pageable);

	public Page<Product> findByDeletedIsFalseAndManufacturer_IdAndNameContainingAndPrice_ValueBetween(
			int manufacturerId, String searchKey, double d, double e, Pageable pageable);

	public Page<Product> findByDeletedIsFalseAndManufacturer_IdAndNameContainingAndPrice_ValueLessThanOrderByPrice_ValueAsc(
			int manufacturerId, String searchKey, double d, Pageable pageable);

	public Page<Product> findByDeletedIsFalseAndManufacturer_IdAndNameContainingAndPrice_ValueGreaterThanOrderByPrice_ValueAsc(
			int manufacturerId, String searchKey, double d, Pageable pageable);

	public Page<Product> findByDeletedIsFalseAndManufacturer_IdAndNameContainingAndPrice_ValueBetweenOrderByPrice_ValueAsc(
			int manufacturerId, String searchKey, double d, double e, Pageable pageable);

	public Page<Product> findByDeletedIsFalseAndManufacturer_IdAndNameContainingAndPrice_ValueLessThanOrderByPrice_ValueDesc(
			int manufacturerId, String searchKey, double d, Pageable pageable);

	public Page<Product> findByDeletedIsFalseAndManufacturer_IdAndNameContainingAndPrice_ValueGreaterThanOrderByPrice_ValueDesc(
			int manufacturerId, String searchKey, double d, Pageable pageable);

	public Page<Product> findByDeletedIsFalseAndManufacturer_IdAndNameContainingAndPrice_ValueBetweenOrderByPrice_ValueDesc(
			int manufacturerId, String searchKey, double d, double e, Pageable pageable);

	public int countByDeletedIsFalse();

	public Optional<Product> findByIdAndDeletedIsFalse(int productIdNumber);

}
