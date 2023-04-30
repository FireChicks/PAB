package com.kbd.PAB.VO;

import jakarta.persistence.*;

import java.util.ArrayList;

@Entity
@Table(name = "gpus")
public class GpuVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gpu_ID")
    private int gpuId;

    @Column(name = "gpu_name", columnDefinition = "varchar(300)")
    private String gpuName;

    @Column(name = "gpu_model", columnDefinition = "varchar(100)")
    private String gpuModel;

    @Column(name = "brand")
    private String brand;

    @Column(name = "gpu_ram_capacity", columnDefinition = "varchar(100)")
    private String gpuRamCapacity;

    @Column(name = "gpu_clock", columnDefinition = "varchar(100)")
    private String gpuClock;

    @Column(name = "gpu_info", columnDefinition = "varchar(2000)")
    private String gpuInfo;

    @Column(name = "amazon_link", columnDefinition = "varchar(500)")
    private String amazonLink;

    @Column(name="amazon_img_link", columnDefinition = "VARCHAR(500)")
    private String amazon_img_link;

    public int getGpuId() {
        return gpuId;
    }

    public void setGpuId(int gpuId) {
        this.gpuId = gpuId;
    }

    public String getGpuName() {
        return gpuName;
    }

    public void setGpuName(String gpuName) {
        this.gpuName = gpuName;
    }

    public String getGpuModel() {
        return gpuModel;
    }

    public void setGpuModel(String gpuModel) {
        this.gpuModel = gpuModel;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getGpuRamCapacity() {
        return gpuRamCapacity;
    }

    public void setGpuRamCapacity(String gpuRamCapacity) {
        this.gpuRamCapacity = gpuRamCapacity;
    }

    public String getGpuClock() {
        return gpuClock;
    }

    public void setGpuClock(String gpuClock) {
        this.gpuClock = gpuClock;
    }

    public String getGpuInfo() {
        return gpuInfo;
    }

    public void setGpuInfo(String gpuInfo) {
        this.gpuInfo = gpuInfo;
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

    public GpuVO() {
    }

    public GpuVO(ArrayList<String> gpuInfos) {
        int gpuInfoIndex = 0;
        StringBuilder stringBuilder = new StringBuilder();

        this.amazonLink = gpuInfos.get(gpuInfoIndex++);
        this.gpuName = gpuInfos.get(gpuInfoIndex++);
        this.gpuModel = gpuInfos.get(gpuInfoIndex++);
        this.brand = gpuInfos.get(gpuInfoIndex++);
        this.gpuRamCapacity = gpuInfos.get(gpuInfoIndex++);
        this.gpuClock = gpuInfos.get(gpuInfoIndex++);
        for (int i = gpuInfoIndex; i < gpuInfos.size(); i++) { //나머지 문자열들은 Ram 부가정보들
            stringBuilder.append(gpuInfos.get(i));
            stringBuilder.append("/");
        }

        this.gpuInfo  = stringBuilder.toString().substring(0, stringBuilder.toString().length() -1);
    }


}