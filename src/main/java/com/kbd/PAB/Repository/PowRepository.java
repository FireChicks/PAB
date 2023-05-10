package com.kbd.PAB.Repository;

import com.kbd.PAB.VO.PowerVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PowRepository extends JpaRepository <PowerVO, Integer> {
    @Query("SELECT p FROM PowerVO p WHERE p.brand = :brandName")
    List<PowerVO> findbyBrandName(@Param("brandName") String brandName);

    @Query("SELECT p FROM PowerVO p WHERE p.powName LIKE :powName%")
    PowerVO findbyPowerName(@Param("powName") String powName);

    @Query("SELECT DISTINCT brand FROM PowerVO")
    List<String> findDistinctBrand();

    @Query("SELECT DISTINCT powWatts FROM PowerVO")
    List<String> findDistinctWatt();
}
