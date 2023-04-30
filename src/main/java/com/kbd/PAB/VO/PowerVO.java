package com.kbd.PAB.VO;

import jakarta.persistence.*;

import java.util.ArrayList;

@Entity
@Table(name = "powers")
public class PowerVO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pow_ID")
    private int powId;

    @Column(name = "pow_name", length = 300)
    private String powName;

    @Column(name = "brand", length = 100)
    private String brand;

    @Column(name = "pow_pins", length = 100)
    private String powPins;

    @Column(name = "pow_watts", length = 100)
    private String powWatts;

    @Column(name = "pow_form_factor", length = 100)
    private String powFormFactor;

    @Column(name = "pow_info", length = 2000)
    private String powInfo;

    @Column(name = "amazon_link", length = 500)
    private String amazonLink;

    @Column(name="amazon_img_link", columnDefinition = "VARCHAR(500)")
    private String amazon_img_link;

    public int getPowId() {
        return powId;
    }

    public void setPowId(int powId) {
        this.powId = powId;
    }

    public String getPowName() {
        return powName;
    }

    public void setPowName(String powName) {
        this.powName = powName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getPowPins() {
        return powPins;
    }

    public void setPowPins(String powPins) {
        this.powPins = powPins;
    }

    public String getPowWatts() {
        return powWatts;
    }

    public void setPowWatts(String powWatts) {
        this.powWatts = powWatts;
    }

    public String getPowFormFactor() {
        return powFormFactor;
    }

    public void setPowFormFactor(String powFormFactor) {
        this.powFormFactor = powFormFactor;
    }

    public String getPowInfo() {
        return powInfo;
    }

    public void setPowInfo(String powInfo) {
        this.powInfo = powInfo;
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
// getters and setters

    public PowerVO() {
    }

    public PowerVO(ArrayList<String> powInfos) {
        int powInfoIndex = 0;
        StringBuilder stringBuilder = new StringBuilder();

        this.amazonLink = powInfos.get(powInfoIndex++);
        this.powName = powInfos.get(powInfoIndex++);
        this.brand = powInfos.get(powInfoIndex++);
        powInfoIndex++;
        this.powPins = powInfos.get(powInfoIndex++);
        this.powWatts = powInfos.get(powInfoIndex++);
        this.powFormFactor = powInfos.get(powInfoIndex++);
        for (int i = powInfoIndex; i < powInfos.size(); i++) { //나머지 문자열들은 Ram 부가정보들
            stringBuilder.append(powInfos.get(i));
            stringBuilder.append("/");
        }

        this.powInfo  = stringBuilder.toString().substring(0, stringBuilder.toString().length() -1);
    }


}