package com.kbd.PAB.VO;

import jakarta.persistence.*;

import java.util.Date;

@Entity(name = "bbs")
@Table
public class BbsVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="bbs_ID", columnDefinition = "INT")
    private int bbsID;

    @Column(name = "bbs_title", columnDefinition = "VARCHAR(100)")
    private String bbsTitle;

    @Column(name = "bbs_content", columnDefinition = "VARCHAR(3000)")
    private String bbsContent;

    @Column(name = "write_date", columnDefinition = "timestamp")
    private Date writeDate;

    @Column(name = "update_date", columnDefinition = "timestamp")
    private Date updateDate;

    @Column(name = "bbs_estimate_ID", columnDefinition = "INT")
    private int bbsEstimateID;

    @Column(name = "thumbnail_image", columnDefinition = "blob")
    private byte[] thumbnailImage;
    @Column(name = "views", columnDefinition = "int")
    private int views;

    public BbsVO() {
    }

    public BbsVO(String bbsTitle, String bbsContent, int estimateID) {
        this.bbsTitle = bbsTitle;
        this.bbsContent = bbsContent;
        this.bbsEstimateID = estimateID;
    }


    public int getBbsID() {
        return bbsID;
    }

    public void setBbsID(int bbsID) {
        this.bbsID = bbsID;
    }

    public String getBbsTitle() {
        return bbsTitle;
    }

    public void setBbsTitle(String bbsTitle) {
        this.bbsTitle = bbsTitle;
    }

    public String getBbsContent() {
        return bbsContent;
    }

    public void setBbsContent(String bbsContent) {
        this.bbsContent = bbsContent;
    }

    public Date getWriteDate() {
        return writeDate;
    }

    public void setWriteDate(Date writeDate) {
        this.writeDate = writeDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public int getBbsEstimateID() {
        return bbsEstimateID;
    }

    public void setBbsEstimateID(int bbsEstimateID) {
        this.bbsEstimateID = bbsEstimateID;
    }

    public byte[] getThumbnailImage() {
        return thumbnailImage;
    }

    public void setThumbnailImage(byte[] thumbnailImage) {
        this.thumbnailImage = thumbnailImage;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }
}
