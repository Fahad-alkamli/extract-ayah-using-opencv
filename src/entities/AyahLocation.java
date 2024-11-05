package entities;

public class AyahLocation {
	public String endY,endX,ayah,page,startY,startX;

	
	
	
	
	
	
	public AyahLocation(String endY, String endX, String ayah, String page, String startY, String startX) {
		super();
		this.endY = endY;
		this.endX = endX;
		this.ayah = ayah;
		this.page = page;
		this.startY = startY;
		this.startX = startX;
	}

	public AyahLocation() {
		super();
	}

	public String getY() {
		return endY;
	}

	public void setY(String y) {
		this.endY = y;
	}

	public String getX() {
		return endX;
	}

	public void setX(String x) {
		this.endX = x;
	}

	public AyahLocation(String y, String x,String ayah,String page) 
	{
		super();
			if(y.length()==3)
			{
				y=""+y.charAt(0)+y.charAt(1)+"0";
			}
		this.endY = y;
		this.endX = x;
		this.ayah=ayah;
		this.page=page;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String value=""+getY()+","+getX()+"";
		return value;
	}

	public String getAyah() {
		return ayah;
	}

	public void setAyah(String ayah) {
		this.ayah = ayah;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getEndY() {
		return endY;
	}

	public void setEndY(String endY) {
		this.endY = endY;
	}

	public String getEndX() {
		return endX;
	}

	public void setEndX(String endX) {
		this.endX = endX;
	}

	public String getStartY() {
		return startY;
	}

	public void setStartY(String startY) {
		this.startY = startY;
	}

	public String getStartX() {
		return startX;
	}

	public void setStartX(String startX) {
		this.startX = startX;
	}
	
	

}
