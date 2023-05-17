package com.kbd.PAB.VO;

import jakarta.persistence.*;

@Entity(name = "bbsestimate")
@Table
public class BbsEstimateVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="comestimateid", columnDefinition = "INT")
    private int comEstimateID;

    @Column(name = "userID", columnDefinition = "VARCHAR(20)")
    private String userID;

    @Column(name = "cpuinfo", columnDefinition = "VARCHAR(50)")
    private String cpuName;

    @Column(name = "Mainboard", columnDefinition = "VARCHAR(50)")
    private String mainBoard;

    @Column(name = "Ram", columnDefinition = "VARCHAR(50)")
    private String ram;

    @Column(name = "Storage", columnDefinition = "VARCHAR(50)")
    private String storage;

    @Column(name = "Power", columnDefinition = "VARCHAR(50)")
    private String power;

    @Column(name = "GPU", columnDefinition = "VARCHAR(50)")
    private String gpu;

    public BbsEstimateVO() {
    }

    public BbsEstimateVO(String userID, String cpuName, String mainBoard, String ram, String storage, String power, String gpu) {
        this.userID = userID;
        this.cpuName = cpuName;
        this.mainBoard = mainBoard;
        this.ram = ram;
        this.storage = storage;
        this.power = power;
        this.gpu = gpu;
    }

    public int getComEstimateID() {
        return comEstimateID;
    }

    public void setComEstimateID(int comEstimateID) {
        this.comEstimateID = comEstimateID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getCpuName() {
        return cpuName;
    }

    public void setCpuName(String cpuName) {
        this.cpuName = cpuName;
    }

    public String getMainBoard() {
        return mainBoard;
    }

    public void setMainBoard(String mainBoard) {
        this.mainBoard = mainBoard;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getGpu() {
        return gpu;
    }

    public void setGpu(String gpu) {
        this.gpu = gpu;
    }
}
