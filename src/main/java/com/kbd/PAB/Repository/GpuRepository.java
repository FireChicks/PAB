package com.kbd.PAB.Repository;

import com.kbd.PAB.VO.GpuVO;
import com.kbd.PAB.VO.RamVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GpuRepository extends JpaRepository <GpuVO, Integer> {

}
