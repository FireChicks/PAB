package com.kbd.PAB.VO;

import jakarta.persistence.*;

import java.util.Date;

@Entity(name = "com")
@Table
public class ComVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="com_ID", columnDefinition = "INT")
    private int comID;

    @Column(name = "bbs_ID", columnDefinition = "INT")
    private int bbsID;

    @Column(name = "com_content", columnDefinition = "VARCHAR(500)")
    private String comContent;

    @Column(name = "com_writer", columnDefinition = "VARCHAR(20)")
    private String comWriter;

    @Column(name = "write_date", columnDefinition = "timestamp")
    private Date writeDate;

    @Column(name = "update_date", columnDefinition = "timestamp")
    private Date updateDate;

    @Column(name = "opponent_writer", columnDefinition = "VARCHAR(20)")
    private String opponetWriter;

    @Column(name = "writer_views", columnDefinition = "int")
    private int writerViews;

    public ComVO() {
    }

    public ComVO(int bbsID, String comContent, String comWriter, String opponentWriter) {
        this.bbsID = bbsID;
        this.comContent = comContent;
        this.comWriter = comWriter;
        this.opponetWriter = opponentWriter;
        this.writeDate =  new Date();
    }

    public int getComID() {
        return comID;
    }

    public void setComID(int comID) {
        this.comID = comID;
    }

    public int getBbsID() {
        return bbsID;
    }

    public void setBbsID(int bbsID) {
        this.bbsID = bbsID;
    }

    public String getComContent() {
        return comContent;
    }

    public void setComContent(String comContent) {
        this.comContent = comContent;
    }

    public String getComWriter() {
        return comWriter;
    }

    public void setComWriter(String comWriter) {
        this.comWriter = comWriter;
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

    public String getOpponetWriter() {
        return opponetWriter;
    }

    public void setOpponetWriter(String opponetWriter) {
        this.opponetWriter = opponetWriter;
    }

    public int getWriterViews() {
        return writerViews;
    }

    public void setWriterViews(int writerViews) {
        this.writerViews = writerViews;
    }
}
