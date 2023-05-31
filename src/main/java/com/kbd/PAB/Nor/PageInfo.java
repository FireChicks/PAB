package com.kbd.PAB.Nor;

public class PageInfo {
    private int page;
    private int pageIndexInterval;

    private int maxPageSize;
    private int startPageNum;
    private int endPageNum;

    public PageInfo() {
    }

    public PageInfo(int page, int maxPageSize) {
        this.page = page;
        this.pageIndexInterval = 10;              //한 페이지에 표기 가능한 페이징 개수 이 변수의 수만큼 숫자 출력
        this.maxPageSize = maxPageSize;

        if(this.page == 0) {
            this.startPageNum = 0;
        } else {
            if(startPageNum >= maxPageSize) {  //최대 페이지수보다 클 경우 최대 페이지수로 설정
                this.startPageNum = maxPageSize;
            } else {
                this.startPageNum = (page / pageIndexInterval); //최대 페이지수보다 작을 경우 공식에 따라 시작 페이지수 계산
                if(this.startPageNum >= 1) { //10의 자리수가 늘어날 때마다 10씩 startPageNum이 증가
                    this.startPageNum = calcStartPageNum(this.startPageNum);
                }
            }
        }
        setEndPageNum(0);
    }

    public int getPage() {
        return page;
    }

    public int calcStartPageNum(int startPageNum) { //재귀 메서드를 이용해서 10씩 증가하게끔
        if( startPageNum == 0) {
            return 0;
        } else {
            return pageIndexInterval + calcStartPageNum(startPageNum - 1);
        }
    }

    public void setPage(int page) {
        if(page == 0) {
            this.page = 0;
        } else {
            this.page = page;
        }
    }

    public int getPageSize() {
        return pageIndexInterval;
    }

    public void setPageSize(int pageSize) {
        this.pageIndexInterval = pageSize;
    }

    public int getStartPageNum() {
        return startPageNum;
    }

    public void setStartPageNum(int startPageNum) {
        this.startPageNum = startPageNum;
    }

    public int getEndPageNum() {
        return endPageNum;
    }

    public void setEndPageNum(int endPageNum) { //설정한 인터벌만큼 최대 페이지가 증가하게끔
        this.endPageNum = this.startPageNum + pageIndexInterval -  1;
        if(this.endPageNum >= maxPageSize) {
            this.endPageNum = maxPageSize;
        }
    }

    public int getMaxPageSize() {
        return maxPageSize;
    }

    public void setMaxPageSize(int maxPageSize) {
        this.maxPageSize = maxPageSize;
    }
}
