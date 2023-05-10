package com.kbd.PAB.Repository;

import com.kbd.PAB.VO.StorageVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoRepository extends JpaRepository <StorageVO, Integer> {

    @Query("SELECT s FROM storages s WHERE s.brand = :Brand")
    List<StorageVO> findbyBrandName(@Param("Brand") String Brand);

    @Query("SELECT s FROM storages s WHERE s.stoName LIKE :stoName%")
    StorageVO findbyStorageName(@Param("stoName") String stoName);

    @Query("SELECT DISTINCT brand FROM storages")
    List<String> findDistinctBrand();

    @Query("SELECT DISTINCT stoType FROM storages")
    List<String> findDistinctSocket();

}
