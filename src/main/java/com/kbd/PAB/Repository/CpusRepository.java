package com.kbd.PAB.Repository;

import com.kbd.PAB.VO.CpuVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

    @Repository
    public interface CpusRepository extends JpaRepository <CpuVO, Integer> {

        @Query("SELECT c FROM cpus c WHERE c.brand = :Brand")
        List<CpuVO> findbyBrandName(@Param("Brand") String Brand);

        @Query("SELECT DISTINCT brand FROM cpus")
        List<String> findDistinctBrand();

        @Query("SELECT DISTINCT cpu_socket FROM cpus")
        List<String> findDistinctSocket();

        @Query("SELECT c FROM cpus c WHERE c.cpuName LIKE :cpuName%")
        CpuVO findbyCPUName(@Param("cpuName") String cpuName);

    }
