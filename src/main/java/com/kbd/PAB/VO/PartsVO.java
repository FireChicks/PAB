package com.kbd.PAB.VO;

public class PartsVO {

    private int partID;

    private String partCategory;
    private String partName;

    private String partImgUrl;

    public PartsVO() {
    }

    public PartsVO(int partID, String partCategory, String partName, String partImgUrl) {
        this.partID = partID;
        this.partCategory = partCategory;
        this.partName = partName;
        this.partImgUrl = partImgUrl;
    }

    public int getPartID() {
        return partID;
    }

    public void setPartID(int partID) {
        this.partID = partID;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public String getPartImgUrl() {
        return partImgUrl;
    }

    public void setPartImgUrl(String partImgUrl) {
        this.partImgUrl = partImgUrl;
    }

    public String getPartCategory() {
        return partCategory;
    }

    public void setPartCategory(String partCategory) {
        this.partCategory = partCategory;
    }
}
