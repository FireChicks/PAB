package com.kbd.PAB.VO;

import jakarta.persistence.*;

import java.util.ArrayList;

@Entity(name = "storages")
@Table
public class StorageVO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sto_ID", columnDefinition = "INT")
    private int stoID;

    @Column(name = "sto_name", columnDefinition = "VARCHAR(300)")
    private String stoName;
    @Column(name = "brand", columnDefinition = "VARCHAR(100)")
    private String brand;
    @Column(name = "sto_capacity", columnDefinition = "VARCHAR(30)")
    private String stoCapacity;
    @Column(name = "sto_interface", columnDefinition = "VARCHAR(30)")
    private String stoInterface;
    @Column(name = "sto_type", columnDefinition = "VARCHAR(500)")
    private String stoType;
    @Column(name = "sto_info", columnDefinition = "VARCHAR(100)")
    private String stoInfo;

    @Column(name = "amazon_Link", columnDefinition = "VARCHAR(1000)")
    private String amazon_Link;

    @Column(name = "amazon_img_link", columnDefinition = "VARCHAR(1000)")
    private String amazon_img_link;

    public StorageVO() { // 기본 생성자
    }

    public StorageVO(ArrayList<String> stoInfos) {
        if(stoInfos.contains("기계식 하드 디스크")) {
            int stoInfosIndex = 0;
            StringBuilder stringBuilder = new StringBuilder();
            this.amazon_Link = stoInfos.get(stoInfosIndex++);
            this.stoName = stoInfos.get(stoInfosIndex++);
            this.stoCapacity = stoInfos.get(stoInfosIndex++);
            stoInfosIndex++;
            this.stoInterface = stoInfos.get(stoInfosIndex++);
            this.brand = stoInfos.get(stoInfosIndex++);
            stoInfosIndex++;
            this.stoType= stoInfos.get(stoInfosIndex++);
            if(this.stoType.equals("기계식 하드 디스크")) {
                this.stoType = "HDD";
            }
            this.amazon_img_link = stoInfos.get(stoInfosIndex++);
            for (int i = stoInfosIndex; i < stoInfos.size(); i++) { //나머지 문자열들은 CPU 부가정보들
                stringBuilder.append(stoInfos.get(i));
                stringBuilder.append("/");
            }

            this.stoInfo = stringBuilder.toString().substring(0, stringBuilder.toString().length() - 1);
        } else {
            int stoInfosIndex = 0;
            StringBuilder stringBuilder = new StringBuilder();
            this.amazon_Link = stoInfos.get(stoInfosIndex++);
            this.stoName = stoInfos.get(stoInfosIndex++);
            this.stoCapacity = stoInfos.get(stoInfosIndex++);
            this.stoInterface = stoInfos.get(stoInfosIndex++);
            stoInfosIndex++;
            this.brand = stoInfos.get(stoInfosIndex++);
            this.stoType = stoInfos.get(stoInfosIndex++);
            this.amazon_img_link = stoInfos.get(stoInfosIndex++);
            for (int i = stoInfosIndex; i < stoInfos.size(); i++) { //나머지 문자열들은 CPU 부가정보들
                stringBuilder.append(stoInfos.get(i));
                stringBuilder.append("/");
            }

            this.stoInfo = stringBuilder.toString().substring(0, stringBuilder.toString().length() - 1);
        }
    }

    public int getStoID() {
        return stoID;
    }

    public void setStoID(int stoID) {
        this.stoID = stoID;
    }

    public String getStoName() {
        return stoName;
    }

    public void setStoName(String stoName) {
        this.stoName = stoName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getStoCapacity() {
        return stoCapacity;
    }

    public void setStoCapacity(String stoCapacity) {
        this.stoCapacity = stoCapacity;
    }

    public String getStoInterface() {
        return stoInterface;
    }

    public void setStoInterface(String stoInterface) {
        this.stoInterface = stoInterface;
    }

    public String getStoType() {
        return stoType;
    }

    public void setStoType(String stoType) {
        this.stoType = stoType;
    }

    public String getStoInfo() {
        return stoInfo;
    }

    public void setStoInfo(String stoInfo) {
        this.stoInfo = stoInfo;
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
