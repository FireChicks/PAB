package com.kbd.PAB.VO;

import jakarta.persistence.*;

import java.util.ArrayList;

@Entity
@Table(name = "rams")
public class RamVO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ram_ID")
    private int ramId;

    @Column(name = "ram_name", nullable = false, length = 300)
    private String ramName;

    @Column(name = "brand", nullable = false, length = 100)
    private String brand;

    @Column(name = "ram_capacity", nullable = false, length = 100)
    private String ramCapacity;

    @Column(name = "ram_gen", nullable = false, length = 100)
    private String ramGen;

    @Column(name = "ram_info", nullable = false, length = 2000)
    private String ramInfo;

    @Column(name = "amazon_link", nullable = false, length = 500)
    private String amazonLink;

    @Column(name = "ram_clock", length = 100)
    private String ramClock;

    @Column(name="amazon_img_link", columnDefinition = "VARCHAR(500)")
    private String amazon_img_link;
    // getters and setters

    public int getRamId() {
        return ramId;
    }

    public void setRamId(int ramId) {
        this.ramId = ramId;
    }

    public String getRamName() {
        return ramName;
    }

    public void setRamName(String ramName) {
        this.ramName = ramName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getRamCapacity() {
        return ramCapacity;
    }

    public void setRamCapacity(String ramCapacity) {
        this.ramCapacity = ramCapacity;
    }

    public String getRamGen() {
        return ramGen;
    }

    public void setRamGen(String ramGen) {
        this.ramGen = ramGen;
    }

    public String getRamInfo() {
        return ramInfo;
    }

    public void setRamInfo(String ramInfo) {
        this.ramInfo = ramInfo;
    }

    public String getAmazonLink() {
        return amazonLink;
    }

    public void setAmazonLink(String amazonLink) {
        this.amazonLink = amazonLink;
    }

    public String getRamClock() {
        return ramClock;
    }

    public void setRamClock(String ramClock) {
        this.ramClock = ramClock;
    }

    public String getAmazon_img_link() {
        return amazon_img_link;
    }

    public void setAmazon_img_link(String amazon_img_link) {
        this.amazon_img_link = amazon_img_link;
    }

    public RamVO() {
    }

    public RamVO(ArrayList<String> ramInfos) {
        int ramInfoIndex = 0;
        StringBuilder stringBuilder = new StringBuilder();

        this.amazonLink = ramInfos.get(ramInfoIndex++);
        this.ramName = ramInfos.get(ramInfoIndex++);
        this.brand = ramInfos.get(ramInfoIndex++);
        this.ramCapacity = ramInfos.get(ramInfoIndex++);
        this.ramGen = ramInfos.get(ramInfoIndex++);
        this.ramClock = ramInfos.get(ramInfoIndex++);
        for (int i = ramInfoIndex; i < ramInfos.size(); i++) { //나머지 문자열들은 Ram 부가정보들
            stringBuilder.append(ramInfos.get(i));
            stringBuilder.append("/");
        }

        this.ramInfo  = stringBuilder.toString().substring(0, stringBuilder.toString().length() -1);
    }


}