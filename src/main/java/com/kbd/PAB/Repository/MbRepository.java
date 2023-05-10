package com.kbd.PAB.Repository;

import com.kbd.PAB.VO.MbVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MbRepository extends JpaRepository <MbVO, Integer> {

    @Query("SELECT m FROM MbVO m WHERE m.brand = :brandName")
    List<MbVO> findbyBrandName(@Param("brandName") String brandName);

    @Query("SELECT m FROM MbVO m WHERE m.mbName LIKE :mbName%")
    MbVO findbyMBName(@Param("mbName") String mbName);

    @Query("SELECT DISTINCT brand FROM MbVO")
    List<String> findDistinctBrand();

    @Query("SELECT DISTINCT mb_cpu_socket FROM MbVO")
    List<String> findDistinctSocket();


}
