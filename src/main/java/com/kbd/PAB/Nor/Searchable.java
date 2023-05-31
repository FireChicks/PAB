package com.kbd.PAB.Nor;

public class Searchable {
    private String searchCategory;
    private String searchText;

    public Searchable() {
    }

    public Searchable(String searchCategory, String searchText) {
        setSearchCategory(searchCategory);
        setSearchText(searchText);
    }

    public String getSearchCategory() {
        return searchCategory;
    }

    public void setSearchCategory(String searchCategory) { //sql인젝션 방지를 위해 카테고리 재검사
        this.searchCategory = (searchCategory.equals("bbsTite") |
                                searchCategory.equals("bbsContent") |
                                searchCategory.equals("bbsWriter")) ? searchCategory : "bbsTitle"; //다른 특정한 문자가 삽입되었을 경우 bbsTitle을 삽입
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        String regex = "[^a-zA-Z0-9가-힣ㄱ-ㅎㅏ-ㅣ\\s]"; //졍규식을 통해 특수문자 제거
        searchText = searchText.replaceAll(regex, "");
        this.searchText = searchText;
    }
}
