package com.hans.demo.service;

import com.hans.demo.model.Banner;
import com.hans.demo.repository.BannerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BannerServiceImpl implements BannerService{
    @Autowired
    private BannerRepository bannerRepository;

    public Banner getByName(String name) {
        Banner banner = bannerRepository.findOneByName(name);
        return banner;
    }
}
