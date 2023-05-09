package com.kbd.PAB.Service;

import com.kbd.PAB.Repository.UserRepository;
import com.kbd.PAB.VO.UserVO;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService {

    @Autowired
    UserRepository userRepository;


    public int login(String userID, String userPW) { // 1 정상 반환, 0 ID/PW 불일치, -1 예상하지 못한 오류
        Optional<UserVO> userVOs = userRepository.findById(userID);
        if(userVOs != null) {
            if(userVOs.get().getUserID().equals(userID)) {
                if(userVOs.get().isCorrectPassword(userPW)) {
                    return 1;
                }
                else {
                    return 0;
                }
            }
        } else {
            return 0;
        }
        return -1;
    }

    public int isValidID(String userID) { // 1 정상 반환, 0 ID/PW 불일치, -1 예상하지 못한 오류
        Optional<UserVO> userVOs = userRepository.findById(userID);
        if (userVOs != null) {
                return 1;
        }
        return 0;
    }

    public int isValidUserName(String userName) {
        if(userRepository.isUserNameValid(userName) != null) {
            return 1;
        } else {
            return 0;
        }
    }

    public void join(UserVO userVO) {
        userRepository.save(userVO);
        return;
    }

    public UserVO findUser(String userID) {
        Optional<UserVO> userVO = userRepository.findById(userID);
        return userVO.get();
    }

}
