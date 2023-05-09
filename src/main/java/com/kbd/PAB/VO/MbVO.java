package com.kbd.PAB.VO;

import jakarta.persistence.*;

import java.util.ArrayList;

@Entity
@Table(name="mbs")
public class MbVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mb_ID")
    private int mbId;

    @Column(name = "mb_name", nullable = false, columnDefinition = "VARCHAR(300) COMMENT '메인보드 이름'")
    private String mbName;

    @Column(name = "brand", nullable = false, columnDefinition = "VARCHAR(100) COMMENT '제조사'")
    private String brand;

    @Column(name="mb_cpu_socket", nullable=false, columnDefinition="VARCHAR(100) COMMENT 'CPU 소켓'")
    private String mb_cpu_socket;

    @Column(name = "mb_mem_gen", nullable = false, columnDefinition = "VARCHAR(100) COMMENT '메모리 세대'")
    private String mbMemGen;

    @Column(name = "mb_info", nullable = false, columnDefinition = "VARCHAR(2000) COMMENT '메인보드 상세 정보'")
    private String mbInfo;

    @Column(name = "amazon_link", nullable = false, columnDefinition = "VARCHAR(500) COMMENT 'Amazon 상품 링크'")
    private String amazonLink;

    @Column(name="amazon_img_link", columnDefinition = "VARCHAR(500)")
    private String amazon_img_link;

    public int getMbId() {
        return mbId;
    }

    public void setMbId(int mbId) {
        this.mbId = mbId;
    }

    public String getMbName() {
        return mbName;
    }

    public void setMbName(String mbName) {
        this.mbName = mbName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getMb_cpu_socket() {
        return mb_cpu_socket;
    }

    public void setMb_cpu_socket(String mb_cpu_socket) {
        this.mb_cpu_socket = mb_cpu_socket;
    }

    public String getMbMemGen() {
        return mbMemGen;
    }

    public void setMbMemGen(String mbMemGen) {
        this.mbMemGen = mbMemGen;
    }

    public String getMbInfo() {
        return mbInfo;
    }

    public void setMbInfo(String mbInfo) {
        this.mbInfo = mbInfo;
    }

    public String getAmazonLink() {
        return amazonLink;
    }

    public void setAmazonLink(String amazonLink) {
        this.amazonLink = amazonLink;
    }

    public String getAmazon_img_link() {
        return amazon_img_link;
    }

    public void setAmazon_img_link(String amazon_img_link) {
        this.amazon_img_link = amazon_img_link;
    }

    public MbVO() {
    }

    public MbVO(ArrayList<String> mbInfos) {
        int mbInfosIndex = 0;
        StringBuilder stringBuilder = new StringBuilder();

        this.amazonLink = mbInfos.get(mbInfosIndex++);
        this.mbName = mbInfos.get(mbInfosIndex++);
        this.brand = mbInfos.get(mbInfosIndex++);
        this.mb_cpu_socket = mbInfos.get(mbInfosIndex++);
        mbInfosIndex++;
        this.mbMemGen = mbInfos.get(mbInfosIndex++);
        for (int i = mbInfosIndex; i < mbInfos.size(); i++) { //나머지 문자열들은 CPU 부가정보들
            stringBuilder.append(mbInfos.get(i));
            stringBuilder.append("/");
        }

        this.mbInfo  = stringBuilder.toString().substring(0, stringBuilder.toString().length() -1);
    }


}