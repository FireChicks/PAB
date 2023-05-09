package com.kbd.PAB.Repository;

import com.kbd.PAB.VO.StorageVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoRepository extends JpaRepository <StorageVO, Integer> {

    @Query("SELECT c FROM storages c WHERE c.brand = :Brand")
    List<StorageVO> findbyBrandName(@Param("Brand") String Brand);

    @Query("SELECT DISTINCT brand FROM storages")
    List<String> findDistinctBrand();

    @Query("SELECT DISTINCT stoType FROM storages")
    List<String> findDistinctSocket();

}
