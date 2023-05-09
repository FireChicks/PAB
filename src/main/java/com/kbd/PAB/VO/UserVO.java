package com.kbd.PAB.VO;

import jakarta.persistence.*;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.salt.StringFixedSaltGenerator;
import org.jasypt.util.password.StrongPasswordEncryptor;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;


@Entity(name = "user")
@Table
public class UserVO {

    @Id
    @Column(name = "user_ID", columnDefinition = "VARCHAR(20)")
    private String userID;
    @Column(name = "user_Password", columnDefinition = "VARCHAR(30)")
    private String userPassword;
    @Column(name = "user_Name", columnDefinition = "VARCHAR(20)")
    private String userName;
    @Column(name = "all_Recommend", columnDefinition = "INT")
    private int allRecommend;
    @Column(name = "user_Profile", columnDefinition = "LONGBLOB")
    private byte[] userProfile;
    @Column(name = "is_Accept", columnDefinition = "TINYINT(1)")
    private int isAccept;

    public UserVO() {
    }

    public UserVO(String userID, String userPassword, String userName, int allRecommend, byte[] userProfile, int isAccept) {
        this.userID = userID;
        this.userPassword = JasyptPassword(userPassword);
        this.userName = userName;
        this.allRecommend = allRecommend;
        this.userProfile = userProfile;
        this.isAccept = isAccept;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAllRecommend() {
        return allRecommend;
    }

    public void setAllRecommend(int allRecommend) {
        this.allRecommend = allRecommend;
    }

    public byte[] getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(byte[] userProfile) {
        this.userProfile = userProfile;
    }

    public int getIsAccept() {
        return isAccept;
    }

    public void setIsAccept(int isAccept) {
        this.isAccept = isAccept;
    }

    public String JasyptPassword(String userPassword) {
        StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
        String encryptedPassword = passwordEncryptor.encryptPassword(userPassword);
        return encryptedPassword;
    }

    public boolean isCorrectPassword(String userPassword) {
        StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
        if(passwordEncryptor.checkPassword(userPassword, this.userPassword)) {
            return true;
        }else {
            return false;
        }

    }
}
