package com.kbd.PAB.Repository;


import com.kbd.PAB.VO.CpuVO;
import com.kbd.PAB.VO.UserVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository <UserVO, String> {

    @Query("SELECT u FROM user u WHERE u.userName = :userName")
    List<UserVO> isUserNameValid(@Param("userName") String userName);

    @Query("SELECT u.userProfile FROM user u WHERE u.userID = :userID")
    byte[] getUserProfile(@Param("userID") String userID);

}
