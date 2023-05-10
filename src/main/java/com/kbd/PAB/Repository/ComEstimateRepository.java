package com.kbd.PAB.Repository;

import com.kbd.PAB.VO.ComEstimateVO;
import com.kbd.PAB.VO.CpuVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComEstimateRepository extends JpaRepository <ComEstimateVO, Integer> {
    @Query(value = "SELECT comEstimateID, cpuInfo, gpu, mainBoard, power, ram, storage, userId FROM comEstimate WHERE comEstimateID = ?1", nativeQuery = true)
    Optional<ComEstimateVO> selectEstimateOne(Integer comEstimateID);

    @Query("SELECT c FROM comestimate c WHERE c.userID = :userID")
    List<ComEstimateVO> findbyUserID(@Param("userID") String userID);


}
