package com.hans.demo.service;

import com.hans.demo.model.Banner;
import org.springframework.stereotype.Service;


public interface BannerService {
    Banner getByName(String name);
}
