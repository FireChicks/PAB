package com.kbd.PAB.Repository;

import com.kbd.PAB.VO.BbsEstimateVO;
import com.kbd.PAB.VO.ComEstimateVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BbsEstimateRepository extends JpaRepository <BbsEstimateVO, Integer> {
    @Query(value = "SELECT comEstimateID, cpuInfo, gpu, mainBoard, power, ram, storage, userId FROM bbsEstimate WHERE comEstimateID = ?1", nativeQuery = true)
    Optional<BbsEstimateVO> selectEstimateOne(Integer comEstimateID);

    @Query("SELECT c FROM bbsestimate c WHERE c.userID = :userID")
    List<BbsEstimateVO> findbyUserID(@Param("userID") String userID);

    @Query("SELECT MAX(b.comEstimateID) FROM bbsestimate b")
    Integer findMaxBbsID();


}
