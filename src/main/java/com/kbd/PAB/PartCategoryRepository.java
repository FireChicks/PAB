package com.kbd.PAB;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartCategoryRepository extends JpaRepository<PartCategory, String> {

}
