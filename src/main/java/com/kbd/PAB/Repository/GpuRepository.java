package com.kbd.PAB.Repository;

import com.kbd.PAB.VO.CpuVO;
import com.kbd.PAB.VO.GpuVO;
import com.kbd.PAB.VO.MbVO;
import com.kbd.PAB.VO.RamVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GpuRepository extends JpaRepository <GpuVO, Integer> {

    @Query("SELECT m FROM GpuVO m WHERE m.brand = :brandName")
    List<GpuVO> findbyBrandName(@Param("brandName") String brandName);

    @Query("SELECT m FROM GpuVO m WHERE m.gpuName LIKE :gpuName%")
    GpuVO findbyGPUName(@Param("gpuName") String gpuName);

    @Query("SELECT DISTINCT brand FROM GpuVO")
    List<String> findDistinctBrand();

}
