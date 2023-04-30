package com.kbd.PAB.Repository;

import com.kbd.PAB.VO.MbVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MbRepository extends JpaRepository <MbVO, Integer> {

}
