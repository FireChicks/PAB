package com.kbd.PAB.Repository;

import com.kbd.PAB.VO.BbsVO;
import com.kbd.PAB.VO.ComVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComRepository extends JpaRepository<ComVO, Integer> {
    @Query("SELECT b FROM com b WHERE b.bbsID = :bbsID order by b.writeDate asc")
    List<ComVO> findByBbsIDCom(@Param("bbsID") int bbsID);
}
