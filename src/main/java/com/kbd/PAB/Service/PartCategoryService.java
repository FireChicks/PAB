package com.kbd.PAB.Service;

import com.kbd.PAB.Repository.PartCategoryRepository;
import com.kbd.PAB.VO.PartCategory;
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
