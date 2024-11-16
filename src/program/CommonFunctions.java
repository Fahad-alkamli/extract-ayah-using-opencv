package program;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import entities.AyahLocation;
import entities.SurahBasmalah;

/**
 * This class will have common functions that are required for other classes and are called frequently
 * @author pc-fahad
 *
 */
public class CommonFunctions {
	
	
	
	/**
	 * After we have run @extractBasmalahFromImagesToList we got a list of basmalah this will help later when we detect
	 * ayah images and find out their surah based on their location and the basmalah location from this list
	 * To Note: we have one Surah that do not have basmalah in it 'Al-tubah' so we inserted a fake location temp.add(new SurahBasmalah("{1,1}",187,1,9));
	 * If a page has surah we have it's location if not we have an empty string
	 * @param pageId we should return the page that matches the id
	 * @return This will return a list of basmalah since some pages will have multiple basmalah locations
	 */
	   public static ArrayList<SurahBasmalah> getSurahBasmalahByPageId(int pageId)
	    {
	        ArrayList<SurahBasmalah> temp= new ArrayList<>();
	        ArrayList<SurahBasmalah> foundList= new ArrayList<>();
	        
	        temp.add(new SurahBasmalah("{180,179}",50,1,3));
	        temp.add(new SurahBasmalah("{100,133}",77,1,4));
	        temp.add(new SurahBasmalah("{500,228}",106,1,5));
	        temp.add(new SurahBasmalah("{180,176}",128,1,6));
	        temp.add(new SurahBasmalah("{180,132}",151,1,7));
	        temp.add(new SurahBasmalah("{180,131}",177,1,8));
	        temp.add(new SurahBasmalah("{170,179}",187,1,9));
	        temp.add(new SurahBasmalah("{110,177}",208,1,10));
	        temp.add(new SurahBasmalah("{560,177}",221,1,11));
	        temp.add(new SurahBasmalah("{700,169}",235,1,12));
	        temp.add(new SurahBasmalah("{180,125}",249,1,13));
	        temp.add(new SurahBasmalah("{310,176}",255,1,14));
	        temp.add(new SurahBasmalah("{180,214}",262,1,15));
	        temp.add(new SurahBasmalah("{560,131}",267,1,16));
	        temp.add(new SurahBasmalah("{180,218}",282,1,17));
	        temp.add(new SurahBasmalah("{770,140}",293,1,18));
	        temp.add(new SurahBasmalah("{180,170}",305,1,19));
	        temp.add(new SurahBasmalah("{440,218}",312,1,20));
	        temp.add(new SurahBasmalah("{190,224}",322,1,21));
	        temp.add(new SurahBasmalah("{110,217}",332,1,22));
	        temp.add(new SurahBasmalah("{100,218}",342,1,23));
	        temp.add(new SurahBasmalah("{120,222}",350,1,24));
	        temp.add(new SurahBasmalah("{840,135}",359,1,25));
	        temp.add(new SurahBasmalah("{110,124}",367,1,26));

	        temp.add(new SurahBasmalah("{110,135}",377,1,27));
	        temp.add(new SurahBasmalah("{630,179}",385,1,28));
	        temp.add(new SurahBasmalah("{630,226}",396,1,29));
	        temp.add(new SurahBasmalah("{760,229}",404,1,30));
	        temp.add(new SurahBasmalah("{180,178}",411,1,31));
	        temp.add(new SurahBasmalah("{100,134}",415,1,32));
	        temp.add(new SurahBasmalah("{110,222}",418,1,33));
	        temp.add(new SurahBasmalah("{180,178}",428,1,34));
	        temp.add(new SurahBasmalah("{630,177}",434,1,35));
	        temp.add(new SurahBasmalah("{370,171}",440,1,36));
	        temp.add(new SurahBasmalah("{100,223}",446,1,37));
	        temp.add(new SurahBasmalah("{100,179}",453,1,38));
	        temp.add(new SurahBasmalah("{370,177}",458,1,39));
	        temp.add(new SurahBasmalah("{320,140}",467,1,40));
	        temp.add(new SurahBasmalah("{180,144}",477,1,41));
	        temp.add(new SurahBasmalah("{180,180}",483,1,42));
	        temp.add(new SurahBasmalah("{450,177}",489,1,43));
	        temp.add(new SurahBasmalah("{180,231}",496,1,44));
	        temp.add(new SurahBasmalah("{100,140}",499,1,45));
	        temp.add(new SurahBasmalah("{570,224}",502,1,46));
	        temp.add(new SurahBasmalah("{100,137}",507,1,47));
	        temp.add(new SurahBasmalah("{180,181}",511,1,48));
	        temp.add(new SurahBasmalah("{580,142}",515,1,49));
	        temp.add(new SurahBasmalah("{180,180}",518,1,50));
	        temp.add(new SurahBasmalah("{890,176}",520,1,51));
	        temp.add(new SurahBasmalah("{630,177}",523,1,52));
	        temp.add(new SurahBasmalah("{110,224}",526,1,53));
	        temp.add(new SurahBasmalah("{750,227}",528,1,54));
	        temp.add(new SurahBasmalah("{440,141}",531,1,55));
	        temp.add(new SurahBasmalah("{570,226}",534,1,56));
	        temp.add(new SurahBasmalah("{830,179}",537,1,57));
	        temp.add(new SurahBasmalah("{180,218}",542,1,58));
	        temp.add(new SurahBasmalah("{570,178}",545,1,59));
	        temp.add(new SurahBasmalah("{100,176}",549,1,60));
	        temp.add(new SurahBasmalah("{570,180}",551,1,61));
	        temp.add(new SurahBasmalah("{180,135}",553,1,62));
	        temp.add(new SurahBasmalah("{560,218}",554,1,63));
	        temp.add(new SurahBasmalah("{110,156}",556,1,64));
	        temp.add(new SurahBasmalah("{110,230}",558,1,65));
	        temp.add(new SurahBasmalah("{180,226}",560,1,66));
	        temp.add(new SurahBasmalah("{180,222}",562,1,67));
	        temp.add(new SurahBasmalah("{510,222}",564,1,68));
	        temp.add(new SurahBasmalah("{770,228}",566,1,69));
	        temp.add(new SurahBasmalah("{700,177}",568,1,70));
	        temp.add(new SurahBasmalah("{440,177}",570,1,71));
	        temp.add(new SurahBasmalah("{180,221}",572,1,72));
	        temp.add(new SurahBasmalah("{180,174}",574,1,73));
	        temp.add(new SurahBasmalah("{630,139}",575,1,74));
	        temp.add(new SurahBasmalah("{500,140}",577,1,75));
	        temp.add(new SurahBasmalah("{770,225}",578,1,76));
	        temp.add(new SurahBasmalah("{570,177}",580,1,77));
	        temp.add(new SurahBasmalah("{180,227}",582,1,78));
	        temp.add(new SurahBasmalah("{640,178}",583,1,79));
	        temp.add(new SurahBasmalah("{100,145}",585,1,80));
	        temp.add(new SurahBasmalah("{250,180}",586,1,81));
	        temp.add(new SurahBasmalah("{180,138}",587,2,82));
	        temp.add(new SurahBasmalah("{890,132}",587,2,83));
	        temp.add(new SurahBasmalah("{310,134}",589,1,84));
	        temp.add(new SurahBasmalah("{240,175}",590,1,85));
	        temp.add(new SurahBasmalah("{180,135}",591,2,86));
	        temp.add(new SurahBasmalah("{770,134}",591,2,87));
	        temp.add(new SurahBasmalah("{430,184}",592,1,88));
	        temp.add(new SurahBasmalah("{310,181}",593,1,89));
	        temp.add(new SurahBasmalah("{510,221}",594,1,90));
	        temp.add(new SurahBasmalah("{250,182}",595,2,91));
	        temp.add(new SurahBasmalah("{830,178}",595,2,92));
	        temp.add(new SurahBasmalah("{510,220}",596,2,93));
	        temp.add(new SurahBasmalah("{960,229}",596,2,94));
	        temp.add(new SurahBasmalah("{310,179}",597,2,95));
	        temp.add(new SurahBasmalah("{700,177}",597,2,96));
	        temp.add(new SurahBasmalah("{380,228}",598,2,97));
	        temp.add(new SurahBasmalah("{700,229}",598,2,98));
	        temp.add(new SurahBasmalah("{500,174}",599,2,99));
	        temp.add(new SurahBasmalah("{890,179}",599,2,100));
	        temp.add(new SurahBasmalah("{370,223}",600,2,101));
	        temp.add(new SurahBasmalah("{820,226}",600,2,102));
	        temp.add(new SurahBasmalah("{190,172}",601,3,103));
	        temp.add(new SurahBasmalah("{440,175}",601,3,104));
	        temp.add(new SurahBasmalah("{830,179}",601,3,105));
	        temp.add(new SurahBasmalah("{190,177}",602,3,106));
	        temp.add(new SurahBasmalah("{500,179}",602,3,107));
	        temp.add(new SurahBasmalah("{890,179}",602,3,108));
	        temp.add(new SurahBasmalah("{180,182}",603,3,109));
	        temp.add(new SurahBasmalah("{500,182}",603,3,110));
	        temp.add(new SurahBasmalah("{830,176}",603,3,111));
	        temp.add(new SurahBasmalah("{180,186}",604,3,112));
	        temp.add(new SurahBasmalah("{440,177}",604,3,113));
	        temp.add(new SurahBasmalah("{770,176}",604,3,114));
	        for(SurahBasmalah temp2:temp)
	        {
	            if(temp2.getPage()==pageId)
	            {
	                foundList.add(temp2);
	            }
	        }
	        return foundList;
	    }


	   /**
	    * This function will detect the Surah based on the basmalah location and the Y location of the Ayah
	    * this function is very important as some pages will have two or three Surahs on a single page
	    * So to find out which surah we compare the Y location of the ayah to the basmalah
	    * @param currentY This will be the y location for the detected ayah we use Y not X as we check pages from top to Bottom
	    * @param loc This is SurahBasmalah location that we have detected before
	    * @return if found we return the surah location if not we return 0 as this page doesn't have a basmalah
	    */
	   public static SurahBasmalah getSurahNumberBasedOnBasmalah(int currentY,SurahBasmalah loc)
	    {
	        String temp=loc.getAllBasmalahArray().split(",")[0].replace("{","");
	        int yToCompare=Integer.parseInt(temp);
	        if(currentY>=yToCompare)
	        {
	        	// System.out.println("Validate: currenyY:"+currentY+" surahY:"+yToCompare);
	            return loc;
	        }

	        return null;
	    }
	   /**
	    * This function will take a list of basmalah and ayah Y location to try and get Surah number
	    * @param currentAyahY
	    * @param AllBasmlahArray
	    * @return
	    */
	   public static int getSurahNumberBasedOnBasmalah2(int currentAyahY,ArrayList<SurahBasmalah> AllBasmlahArray)
	    {
		   try {
			   if(AllBasmlahArray==null ||AllBasmlahArray.size()==0)
			   {
				   	   return 0;
			   }
			
			   //System.out.println("Hello:"+currentAyahY);
			   ArrayList<SurahBasmalah> founded=new ArrayList<>();
			   	//System.out.println("Check: "+currentAyahY+" in basmlahSize:"+AllBasmlahArray.size());
			    
			   	for(SurahBasmalah temp:AllBasmlahArray)
			   	{
			   		//System.out.println("Basmlah Y:"+temp.getY());
			   		if(currentAyahY>=temp.getY())
			   		{
			   			//System.out.println("Bigger than Basmlah:AyahY:"+currentAyahY+" BasmlahY:"+temp.getY());
			   			founded.add(temp);
			   		}
			   	}
			   	//System.out.println("Check size:"+founded.size());
			   	//For errors
			   	if(founded.size()==0)
			   	{
			   		return 0;
			   	}
			   	
			   	if(founded.size()==1)
			   	{
			   		return founded.get(0).getSurah();
			   	}else {
			   		//System.out.println("found basmlah:"+founded.get(founded.size()-1).getSurah());
			   		return founded.get(founded.size()-1).getSurah();
			   	}
			   
			   
			   
			   }catch(Exception e)
			   {
				   e.printStackTrace();
			   }


	        return 0;
	    }
	   
	   
	   /**
	    * This function will get the basmalah Y location of the basmalah, it's useful to know if the ayah is above or below the Basmalah Y location
	    * @param currentAyahY
	    * @param AllBasmlahArray
	    * @return Basmalah Y location
	    */
	   public static int getBasmalahLocationBasedOnY(int currentAyahY,ArrayList<SurahBasmalah> AllBasmlahArray)
	    {
		   try {
			   if(AllBasmlahArray==null ||AllBasmlahArray.size()==0)
			   {
				   	   return 0;
			   }
			
			   //System.out.println("Hello:"+currentAyahY);
			   ArrayList<SurahBasmalah> founded=new ArrayList<>();
			   	//System.out.println("Check: "+currentAyahY+" in basmlahSize:"+AllBasmlahArray.size());
			    
			   	for(SurahBasmalah temp:AllBasmlahArray)
			   	{
			   		//System.out.println("Basmlah Y:"+temp.getY());
			   		if(currentAyahY>=temp.getY())
			   		{
			   			//System.out.println("Bigger than Basmlah:AyahY:"+currentAyahY+" BasmlahY:"+temp.getY());
			   			founded.add(temp);
			   		}
			   	}
			   	//System.out.println("Check size:"+founded.size());
			   	//For errors
			   	if(founded.size()==0)
			   	{
			   		return 0;
			   	}
			   	
			   	if(founded.size()==1)
			   	{
			   		return founded.get(0).getY();
			   	}else {
			   		//System.out.println("found basmlah:"+founded.get(founded.size()-1).getSurah());
			   		return founded.get(founded.size()-1).getY();
			   	}
			   
			   
			   
			   }catch(Exception e)
			   {
				   e.printStackTrace();
			   }


	        return 0;
	    }
	   
	   
	   /**
	    * This function is also very important as the list after detection will have different values but we need them organized
	    * by Top to Bottom and Right to Left but detection is done by Left to Right.
	    * Top to Bottom from 0 value we take the lowest as the lowest Y means this is the first line from top
	    * Right to Left we pick the largest X as the detection will be from left to right 0 so the bigger the x means this is start of
	    * The right page
	    * @param correctList this will take the list before ordering
	    * @return The list after ordering it from Top to bottom and Right to Left
	    */
	   public static ArrayList<String> orgnizingArrayByYandX(	ArrayList<String> correctList)
	   {
		   List<AyahLocation> ayahLocation=new ArrayList<>();
		   
		   
		   for(int i=0;i<correctList.size();i++)
		   {
			   String val=correctList.get(i);
			   //System.out.println(val.replace("{", ""));
			   ayahLocation.add(new AyahLocation(val.replace("{", "").split(",")[0],val.replace("{", "").split(",")[1],"",""));
		   }
		   
		   Collections.sort(ayahLocation, new Comparator<AyahLocation>() {

		        public int compare(AyahLocation o1, AyahLocation o2) {

		            int num1 = Integer.parseInt(( o1).getY());
		            int num2 = Integer.parseInt(( o2).getY());

		            int num3 = Integer.parseInt(( o1).getX());
		            int num4 = Integer.parseInt(( o2).getX());
		            //if y values are the same we organize by getting the bigger X since Arabic is right to left.
		            int result= num1 < num2 ? -1 : (num1 > num2) ? 1 : 0;           
		            if(result==0)
		            {
		            	result= num3 > num4 ? -1 : (num3 < num4) ? 1 : 0;
		            	return result;
		            }

		            
		            //System.out.println("num1:"+num1+" num2:"+num2+" result:"+result) ;
	
		            return result;

		    }});
		   
		   correctList.clear();
			for(AyahLocation val:ayahLocation)
			{
				correctList.add(val.toString());		
				//System.out.println(val.toString());
			}
			
			return correctList;

	   }
	   
	   /**
	    * This function will compare if a value is between two values we use this to detect for close pixels and remove them
	    * @param toCheck the value to compare
	    * @param from From value
	    * @param to To value
	    * @return true or false
	    */
		public static boolean isBetween(int toCheck, int from, int to)
		{
			// eventually validate the range
			return from <= toCheck && toCheck <= to;
		}

}
