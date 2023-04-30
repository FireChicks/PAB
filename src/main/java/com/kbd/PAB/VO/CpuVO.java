package com.kbd.PAB.VO;

import jakarta.persistence.*;

import java.util.ArrayList;

@Entity(name = "cpus")
@Table
public class CpuVO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cpuID", columnDefinition = "INT")
    private int cpuID;

    @Column(name = "cpu_Name", columnDefinition = "VARCHAR(300)")
    private String cpuName;
    @Column(name = "brand", columnDefinition = "VARCHAR(30)")
    private String brand;
    @Column(name = "cpu_Manufacturer", columnDefinition = "VARCHAR(30)")
    private String cpuManufacturer;
    @Column(name = "cpu_Model", columnDefinition = "VARCHAR(30)")
    private String cpuModel;
    @Column(name = "cpu_Speed", columnDefinition = "VARCHAR(20)")
    private String cpuSpeed;
    @Column(name = "cpu_socket", columnDefinition = "VARCHAR(100)")
    private String cpu_socket;
    @Column(name = "cpu_Info", columnDefinition = "VARCHAR(1000)")
    private String cpuInfo;

    @Column(name = "amazon_Link", columnDefinition = "VARCHAR(500)")
    private String amazon_Link;

    @Column(name = "amazon_img_link", columnDefinition = "VARCHAR(500)")
    private String amazon_img_link;

    public CpuVO() { // 기본 생성자
    }

    public CpuVO(ArrayList<String> cpuInfos) { // CPU 정보 크롤링 후 집어넣기 위한 생성자
        int cpuInfosIndex = 0;
        StringBuilder stringBuilder = new StringBuilder();
        this.amazon_Link = cpuInfos.get(cpuInfosIndex++);
        this.cpuName = cpuInfos.get(cpuInfosIndex++);
        this.brand = cpuInfos.get(cpuInfosIndex++);
        this.cpuManufacturer = cpuInfos.get(cpuInfosIndex++);
        this.cpuModel = cpuInfos.get(cpuInfosIndex++);
        this.cpuSpeed = cpuInfos.get(cpuInfosIndex++);
        this.cpu_socket = cpuInfos.get(cpuInfosIndex++);
        for (int i = cpuInfosIndex; i < cpuInfos.size(); i++) { //나머지 문자열들은 CPU 부가정보들
            stringBuilder.append(cpuInfos.get(i));
            stringBuilder.append("/");
        }

        this.cpuInfo = stringBuilder.toString().substring(0, stringBuilder.toString().length() - 1);
    }

    public int getCpuID() {
        return cpuID;
    }

    public void setCpuID(int cpuID) {
        this.cpuID = cpuID;
    }

    public String getCpuName() {
        return cpuName;
    }

    public void setCpuName(String cpuName) {
        this.cpuName = cpuName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCpuManufacturer() {
        return cpuManufacturer;
    }

    public void setCpuManufacturer(String cpuManufacturer) {
        this.cpuManufacturer = cpuManufacturer;
    }

    public String getCpuModel() {
        return cpuModel;
    }

    public void setCpuModel(String cpuModel) {
        this.cpuModel = cpuModel;
    }

    public String getCpuSpeed() {
        return cpuSpeed;
    }

    public void setCpuSpeed(String cpuSpeed) {
        this.cpuSpeed = cpuSpeed;
    }

    public String getCpu_socket() {
        return cpu_socket;
    }

    public void setCpu_socket(String cpu_socket) {
        this.cpu_socket = cpu_socket;
    }

    public String getCpuInfo() {
        return cpuInfo;
    }

    public void setCpuInfo(String cpuInfo) {
        this.cpuInfo = cpuInfo;
    }

    public String getAmazon_Link() {
        return amazon_Link;
    }

    public void setAmazon_Link(String amazon_Link) {
        this.amazon_Link = amazon_Link;
    }

    public String getAmazon_img_link() {
        return amazon_img_link;
    }

    public void setAmazon_img_link(String amazon_img_link) {
        this.amazon_img_link = amazon_img_link;
    }

}
