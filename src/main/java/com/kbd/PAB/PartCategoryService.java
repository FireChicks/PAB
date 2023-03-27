package com.kbd.PAB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartCategoryService {
    @Autowired
    private PartCategoryRepository partCategoryRepository;


    public List<PartCategory> getCategory() {
        return partCategoryRepository.findAll();
    }


}
