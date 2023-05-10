package com.kbd.PAB.Repository;

import com.kbd.PAB.VO.GpuVO;
import com.kbd.PAB.VO.RamVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RamRepository extends JpaRepository <RamVO, Integer> {

    @Query("SELECT r FROM RamVO r WHERE r.brand = :brandName")
    List<RamVO> findbyBrandName(@Param("brandName") String brandName);

    @Query("SELECT r FROM RamVO r WHERE r.ramName LIKE :ramName%")
    RamVO findbyRamName(@Param("ramName") String ramName);

    @Query("SELECT DISTINCT brand FROM RamVO")
    List<String> findDistinctBrand();

}
