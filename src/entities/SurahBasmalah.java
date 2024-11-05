package entities;


public class SurahBasmalah {
    String allBasmalahArray;
    int counter,surah;
    int page;
    Integer y;

    public SurahBasmalah(String allBasmalahArray, int page,int counter,int surah) {
        this.allBasmalahArray = allBasmalahArray;
        this.page = page;
        this.counter=counter;
        this.surah=surah;
        if(allBasmalahArray!=null && allBasmalahArray.trim().length()>0)
        {
            String temp=allBasmalahArray.split(",")[0].replace("{","");
            y=Integer.parseInt(temp);
        }
    }

    public String getAllBasmalahArray() {
        return allBasmalahArray;
    }

    public void setAllBasmalahArray(String allBasmalahArray) {
        this.allBasmalahArray = allBasmalahArray;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public int getSurah() {
        return surah;
    }

    public void setSurah(int surah) {
        this.surah = surah;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }
}
