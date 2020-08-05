package com.hans.demo.service;

import com.hans.demo.model.Theme;
import com.hans.demo.repository.ThemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ThemeService {
    @Autowired
    private ThemeRepository themeRepository;
    public List<Theme> getByNames(List<String> names){
        return themeRepository.findByNames(names);
    }

    public Optional<Theme> getByName(String name){
        return themeRepository.findByName(name);
    }
}
