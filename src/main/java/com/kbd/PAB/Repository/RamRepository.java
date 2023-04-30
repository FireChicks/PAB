package com.kbd.PAB.Repository;

import com.kbd.PAB.VO.RamVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RamRepository extends JpaRepository <RamVO, Integer> {

}
