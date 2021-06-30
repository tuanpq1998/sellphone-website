package com.hust.project3.phonesellingweb.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hust.project3.phonesellingweb.entity.setting.Banner;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Integer> {

	public List<Banner> findByPositionIs(String position);

}
