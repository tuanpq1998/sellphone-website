package com.hust.project3.phonesellingweb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hust.project3.phonesellingweb.dao.BannerRepository;
import com.hust.project3.phonesellingweb.entity.setting.Banner;
import com.hust.project3.phonesellingweb.utility.ConstantVariable;

@Service
public class BannerService {

	@Autowired
	private BannerRepository bannerRepository;
	
	public List<Banner> getProductSideBanner() {
		return bannerRepository.findByPositionIs(ConstantVariable.POS_BANNER_PRODUCT_SIDE);
	}
	
	public List<Banner> getPostSideBanner() {
		return bannerRepository.findByPositionIs(ConstantVariable.POS_BANNER_POST_SIDE);
	}
	
	public List<Banner> getPostBottomBanner() {
		return bannerRepository.findByPositionIs(ConstantVariable.POS_BANNER_POST_BOTTOM);
	}
	
	public List<Banner> getProductBottomBanner() {
		return bannerRepository.findByPositionIs(ConstantVariable.POS_BANNER_PRODUCT_BOTTOM);
	}
}
