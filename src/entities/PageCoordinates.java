package entities;


public class PageCoordinates {
    private String allCoordinates;
    private String pageNumber;


    public PageCoordinates(String allCoordinates, String pageNumber) {
        this.allCoordinates = allCoordinates;
        this.pageNumber = pageNumber;
    }


    public String getAllCoordinates() {
        return allCoordinates;
    }

    public void setAllCoordinates(String allCoordinates) {
        this.allCoordinates = allCoordinates;
    }

    public String getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(String pageNumber) {
        this.pageNumber = pageNumber;
    }
}
