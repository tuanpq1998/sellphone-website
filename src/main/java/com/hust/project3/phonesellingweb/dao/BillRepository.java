package com.hust.project3.phonesellingweb.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hust.project3.phonesellingweb.entity.Bill;

@Repository
public interface BillRepository extends JpaRepository<Bill, Integer> {

	public List<Bill> findTop5ByUser_IdOrderByIdDesc(int id);
}
