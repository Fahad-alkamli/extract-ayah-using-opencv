package program;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.Set;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs; 
import org.opencv.imgproc.Imgproc;

import entities.AyahLocation;
import entities.SurahBasmalah;
import nu.pattern.OpenCV;


/**
 * This class is the main program and consist of two major methods we enable them as we need
 * We need to start with @extractBasmalahFromImagesToList as it will detect the basmalah from each page to a list and we modify this function getSurahBasmalahByPageId
 * Secondly, we can now detect the ayahs from @extractQuranAyahFromImage this function will call @testingPrint to print the output to the console for testing.
 * @author pc-fahad
 *
 */
public class Main {


	/**
	 *TemplateCounter counter for the templates to loop through 
	 */
	public static int templateCounter=23;
	

	/**
	 * AyahCounter Since we will start from page 3  ignoring the first two pages we need to set the start point to ayah 6
	 */
	static int AyahCounter=6;
	
	/**
	 * OldAyahCounter This counter to count if the top of the page has a different surah the ayah should continue from the previous page
	 */
	static int oldAyahCounter=0;
	/** 
	 * Threshold the opencv sensitivity , it lower will detect more but may encounter false positive 
	 */
	final static double Threshold=0.68;//0.68
	/**
	 * SurahStartingX for the first ayah we can't detect it's start X location so we assume it's location 
	 */
	final static int SurahStartingX=620;
	/**
	 * SurahStartingY for the first ayah we can't detect it's start Y location so we assume it's location 
	 */
	final static int SurahStartingY=124;

	/**
	 * tempAyahList Will hold the values without startY,startX so that we iterate on the list after we finish to add startY,StartX from previous ayah
	 */
	static ArrayList<String> tempAyahList=new ArrayList<>();
	
	/**
	 * finalCollection this is the final collection you can print it or do whatever you need
	 */
	static ArrayList<String> finalCollection=new ArrayList<>();

/**
 * This is the main method to run each method as we see needed
 */
	public static void main(String[] args) 
	{
		
		try {	
		extractQuranAyahFromImage();
		//extractBasmalahFromImagesToList();
		}catch(Exception e)
		{
e.printStackTrace();
		}
		

	}
	
	
	/**
	 * This method is responsible for organizing the startY,startX because we need to loop back to fetch them from the previous ayah, this can
	 * done only after the ayah finish feels more efficient.
	 */
	private static void testingPrint()
	{
	try {
		for(String obj:tempAyahList)
		{
			String[] ayahCollection=obj.split("\\|")[0].trim().split("}");
			String page=obj.split("\\|")[1].trim();	
			
			for(int i=0;i<ayahCollection.length;i++)
			{
				String ayahL=ayahCollection[i].replace("{", "");
				String endY,endX,startY,startX,ayah,surah;
				endY=ayahL.split(",")[0];
				endX=ayahL.split(",")[1];
				ayah=ayahL.split(",")[2];
				surah=ayahL.split(",")[3];
				//Now for each ayah we need to check the previous ayah to get the start location
				//But keep in mind to check if this ayah is the beginning of  page + first ayah
				ArrayList<SurahBasmalah> getAllBasmlahsOnThisPage=CommonFunctions.getSurahBasmalahByPageId(Integer.parseInt(page.trim()));
		
				if(getAllBasmlahsOnThisPage.size()>0)
				{
					//This page include a basmalah 
					int yStartLocation=CommonFunctions.getBasmalahLocationBasedOnY(Integer.parseInt(endY),getAllBasmlahsOnThisPage);
					if(yStartLocation==0)
					{
						//No basmalah before in this ayah
						finalCollection.add("{"+endY+","+endX+","+ayah+","+surah+","+SurahStartingY+","+SurahStartingX+"}");
					}else if(Integer.parseInt(ayah)==1)
					{
						//This page has basmalah but it's not in the top of the page==not the first ayah

						yStartLocation+=40;//This so we can go below the basmalah
							finalCollection.add("{"+endY+","+endX+","+ayah+","+surah+","+yStartLocation+","+SurahStartingX+"}");		
					}else {
						//Not the first basmalah
						if(i-1>=0)
						{
							String ayahL2=ayahCollection[i-1].replace("{", "");
							startY=ayahL2.split(",")[0];
							startX=ayahL2.split(",")[1];
							finalCollection.add("{"+endY+","+endX+","+ayah+","+surah+","+startY+","+startX+"}");		
						}
					}
				}else {
					if(i-1>=0)
					{
						String ayahL2=ayahCollection[i-1].replace("{", "");
						startY=ayahL2.split(",")[0];
						startX=ayahL2.split(",")[1];
						finalCollection.add("{"+endY+","+endX+","+ayah+","+surah+","+startY+","+startX+"}");		
					}else {
						//if there is no Basmalah in this page we just assume the startY,endX
						finalCollection.add("{"+endY+","+endX+","+ayah+","+surah+","+SurahStartingY+","+SurahStartingX+"}");
					}
			
				}

			}
	
			String output=" temp.add(new PageCoordinates(\"";
			for(String temp:finalCollection)
			{
				//System.out.println(temp);
				output+=temp;
			}
			output+="\",\""+page+"\"));";
			System.out.println(output);
			finalCollection.clear();		
		}

	} catch(Exception e) {
	e.printStackTrace();
	}
	
	}
	
	
	
	/**
	 * This function will extract the basmalah from each page
	 */
	@SuppressWarnings("unused")
	private static void extractBasmalahFromImagesToList() {
		OpenCV.loadLocally();
		//reading the images
		String filename="quran-all-pages-same-size-xxx resized.jpg";
		int counter=1;
		int surahCounter=2;
		for( int i=5;i<=606;i++)
		{
			String rowData=run("images/"+filename.replace("xxx", Integer.toString(i)),"images/templates/surah-templates/surah-template3.png",i+"-test.png",Imgproc.TM_CCOEFF_NORMED,true,0.32);
			if(rowData!= null && rowData.trim().length()>0)
			{
				ArrayList<String> correctList=removeDuplicate(rowData);
				correctList=compareArrays2(correctList);
	
				ArrayList<String> fixedArray=CommonFunctions.orgnizingArrayByYandX(correctList);
				//correctList.clear();
				correctList=new ArrayList<String>(fixedArray);
				
				
				if(correctList.size()==1)
				{
					//we found only one surah in this page
					surahCounter++;
					printSurahBasmalah(correctList.get(0),(i-2),correctList.size(),surahCounter);
				}else if(correctList.size()==2)
				{
					//we found two surah in the same page
					surahCounter++;
					printSurahBasmalah(correctList.get(0),(i-2),correctList.size(),surahCounter);


					surahCounter++;
					printSurahBasmalah(correctList.get(1),(i-2),correctList.size(),surahCounter);

				}else if(correctList.size()==3)
				{
					//we found three surah in the same page
					surahCounter++;
					printSurahBasmalah(correctList.get(0),(i-2),correctList.size(),surahCounter);


					surahCounter++;
					printSurahBasmalah(correctList.get(1),(i-2),correctList.size(),surahCounter);


					surahCounter++;
					printSurahBasmalah(correctList.get(2),(i-2),correctList.size(),surahCounter);

				}

				counter++;
			}else
			{
				if(i==187)
				{
					//this surah does not have a basmalah we need to add one for Surat al taobah we don't have basmalah we need to do it manually
					System.out.println("temp.add(new SurahBasmalah(\"{170,179}\",187,1,9));");
					surahCounter++;
				}else {
					//when a page doesn't have basmalah we need to link them back to the previous basmalah as they are both belong to the same Surah
					//System.out.println("temp.add(new SurahBasmalah(\"\","+(i-2)+",1,"+surahCounter+"));");
				}
			}



		}
	}
	

	/**
	 * This function will print the location Y and X for the basmalah from extractBasmalahFromImagesToList
	 * @param correctList correctList basmalah in string format
	 * @param page the page that has the basmalah
	 * @param count the number of basmalah found on this page
	 * @param surahCounter will have the surah counter we count from 1 for the first Surah we detect the Surah as we go.
	 */
	public static void printSurahBasmalah(String correctList, int page, int count, int surahCounter)
	{
		try {
					
				
				String pos="";

				pos+="{"+correctList+"}";

				System.out.println("temp.add(new SurahBasmalah(\""+pos+"\","+page+","+count+","+surahCounter+"));");
		}catch(Exception e)
		{
			e.printStackTrace();
		}

		
	}




	/**
	 * This function will extract all the ayah from each page of the Quran, we loop through the images and try to detect using the 21 templates
	 * we declared on templateCounter and add all the detections at once and than we remove duplicates.
	 */
	public static void extractQuranAyahFromImage() {
		try {
			OpenCV.loadLocally();
			String filename="quran-all-pages-same-size-xxx resized.jpg";
			HashMap<String,String> compareList = new HashMap<String,String>(); 

			/**
			 * This list will have each page and their ayah's to compare them against the openCV template detections and make sure we actually have 
			 * a working project.
			 * This list has been manually checked for comparison purposes.
			 * @param compareList reading the file to get each page and their expected ayah count
			 */
			compareList=readingAyahCount();

			/**
			 * Reading the images we started from 5 because the the pdf has page 1,2 as none Surah and page 3,4 are not possible to detect
			 * because of all the colors and Zakrafa so we did them manually.
			 * Note: please change this to 606 after you have added your images into the images folder.This will only work on the test images I have included.
			 */
			//for( int i=5;i<=606;i++)
			for( int i=5;i<=10;i++)
			{
				/**
				 * To begin our search we use the first template as a base line so that if
				 * we managed to find  all the ayahs we don't really need the other templates
				 */
				boolean outputImages=false;
				String rowData=run("images/"+filename.replace("xxx", Integer.toString(i)),"images/templates/template1.png",i+"-template1.png",Imgproc.TM_CCOEFF_NORMED,outputImages,Threshold); //Threshold

				ArrayList<String> correctList=removeDuplicate(rowData);
				if(correctList==null)
				{
					
					correctList=new ArrayList<String>();
				}
				//This method will remove very close pixels so that we have unique values
				correctList=compareArrays2(correctList);
				//comp will be the number of expected ayah's on this page to validate the detection
				int comp=Integer.parseInt(compareList.get(Integer.toString(i-2)));

				String oldString="";
				/**
				 * Here we loop through the templates to get as many detection as we could, later we shall reduce these detections
				 * based on their pixels to get the correct values only, we require 100% detection rate.  
				 */
	
				//This for debugging to detect how many templates we have used
				if(correctList.size()==comp)
				{
					//System.out.print("\033[31m");
					//System.out.println("We have used 1 Templates for page:"+(i-2));
					//run("images/"+filename.replace("xxx", Integer.toString(i)),"images/templates/template1.png",i+"-template1.png",Imgproc.TM_CCOEFF_NORMED,true,Threshold); //Threshold
					//System.out.print("\033[0m");
				}
				//template counter start from 2 because we have already used template 1 to begin our loop
				int count=2;
				while(correctList.size()!=comp)
				{
					if(count>templateCounter)
					{
						count--;
						break;

					}else {
						//System.out.println("looping through the templates:"+count);
						//System.out.println("correctList:"+correctList+"comp:"+comp);
						oldString=callTemplateViaNumber(count,filename,i,outputImages);
						rowData+=oldString;
						//Testing we stop looping when we have reached the correct ayah number no need to go throw all the templates
						correctList=removeDuplicate(rowData);
						correctList=compareArrays2(correctList);
						if(correctList.size()==comp)
						{
							//System.out.println("Stop we have found enough data current template count:"+count);		
							//System.out.println("We have used "+count+" Templates for page:"+(i-2));
							break;
						}
					}
					count++;
				}


				//We final check the result again for duplication
				correctList=removeDuplicate(rowData);

				if(rowData==null || rowData.trim().length()==0)
				{

					System.out.println("Error:Nothing could be detected for page: "+i+" Consider adding a new template");
					continue;

				}

				if(correctList.size()==comp)
				{
					//System.out.println("Page:"+(i-2)+" Found:"+correctList.size());
				}else {

					correctList=compareArrays2(correctList);
					if(correctList!= null && correctList.size()==comp)
					{
						//System.out.println("Fixed it");
					}else {
						System.out.println("Page Error: "+(i-2)+" , detected number:"+correctList.size()+" should be:"+compareList.get(Integer.toString(i-2)));
						//we tried,  we can't fix the duplicate values seems like there are no duplicates but missing template
						continue;
					}
				}

				//Last chance to print
				if(correctList!= null && correctList.size()==comp)
				{
					printAyahForPageUtily2(i, correctList);
				}
				

			}	
			//This function is responsible for adding the startY,startX and to print to the console
			testingPrint();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Print the page's ayah to the console
	 * Not used kept it for reference.
	 * 
	 */
	@SuppressWarnings("unused")
	private static void printAyahForPageUtily(int i, ArrayList<String> correctList, int comp) 
	{
		try {
			//first we organize the data we this @orgnizingArrayByYandX method to unify Y values on the same line
			ArrayList<String> fixedArray=CommonFunctions.orgnizingArrayByYandX(unifyY(correctList));
			
			correctList=new ArrayList<String>(fixedArray);


			final int page=i;
			String value2="";
			boolean found=false;
			HashMap<Integer,Integer> multipleBasmalArray=new LinkedHashMap<>();
			int multipleBasmalArrayCounter=0;
			
			for(int count2=0;count2<correctList.size();count2++)
			{
				String ayahYLocation=correctList.get(count2).trim().split(",")[0].replace("{", "");
				//This line of code will get all the basmalah location on a single page, if there are multiple basmalah it will return a list
				ArrayList<SurahBasmalah> foundList=CommonFunctions.getSurahBasmalahByPageId(page-2);
				//we shall loop from bottom to top to detect y location of the ayah and compare it to the basmalah y location
				//using this method we should see if the current ayah belongs to which Surah.
				for(int c=foundList.size()-1;c>=0;c--)
				{
					SurahBasmalah temp3=foundList.get(c);

					if(temp3.getAllBasmalahArray().length()==0)
					{
						//This page does not have basmalah therefore all the ayah's  belong to the previous Surah
						found=true;
						break;
					}else{
				
						int surah2=CommonFunctions.getSurahNumberBasedOnBasmalah2(Integer.parseInt(ayahYLocation),CommonFunctions.getSurahBasmalahByPageId(page-2));
						//System.out.println("This is surah:"+surah2+" at location:"+ayahYLocation);

						if(surah2!= 0)
						{
							///This is the first element which means there are no ayah left to search for from top to bottom search
							if(count2==0)
							{
								AyahCounter=1;
								//System.out.println(AyahCounter);
							}else
							{
								 if(foundList.size()>1)
								 {

									 multipleBasmalArray.put(Integer.parseInt(ayahYLocation),surah2);	
								 }
								
							}
							found=true;
							break;
						}else {
							//System.out.println("Check this");
						}
					}

				}
				
				
				SurahBasmalah temp5=foundList.get(0);
				if(found==false)
				{
					//This means this is the first Surah of the page
					oldAyahCounter+=AyahCounter;
					AyahCounter=1;
					value2+="{"+correctList.get(count2).trim()+","+oldAyahCounter+","+(temp5.getSurah()-1)+"}";

				}else 
				{
					//Deal with ayah location detected multiple basmalah in this page
					//we have an issue the third ayah counter is all wrong
					
					if(multipleBasmalArray.size()>0)
					{
						
						String y=correctList.get(count2).trim().split(",")[0];
				
						if(multipleBasmalArray.get(Integer.parseInt(y)) != null)
						{
							int  surah=multipleBasmalArray.get(Integer.parseInt(y));

							if(surah!= temp5.getSurah())
							{
								
								//This is a different surah we should change the surah , we only need to reset the counter once
								if(multipleBasmalArrayCounter==0)
								{	
									//reset the ayah counter
									multipleBasmalArrayCounter++;
									AyahCounter=1;
									//System.out.println("This is a different surah we should change the surah , we only need to reset the counter once");
									//System.out.println("Different Surah");
								}


							}
							value2+="{"+correctList.get(count2).trim()+","+AyahCounter+","+(surah)+"}";	
							AyahCounter++;			
							
						}else {
								value2+="{"+correctList.get(count2).trim()+","+AyahCounter+","+(temp5.getSurah())+"}";
								AyahCounter++;
								
						}

					}else {
						
						value2+="{"+correctList.get(count2).trim()+","+AyahCounter+","+(temp5.getSurah())+"}";
						AyahCounter++;

					}

				}

			

			}
			oldAyahCounter=0;
			String val3=" temp.add(new PageCoordinates(\"data\",\"pageId\"));";
			val3= val3.replace("pageId", Integer.toString(page-2));
			val3=val3.replace("data", value2);
			
			//remove after debugging for result
			System.out.println(val3.trim());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	

		
	}


	/**
	 * These two counters will keep values as we Iterate over the pages to keep the old ayah and the old surah 
	 * and change them if we encounter a new basmlah by increasing the surah number and reseting the ayah counter
	 */
	static int ayahCounter2=5;
	/**
	 * surahCounter we will start from Surah 2 which is Al-bagrah
	 */
	static int surahCounter=2;
	
	/**
	 * this function is responsible for organizing the ayah based on the page + Surah it prepares the data to be illterated over by @testPrint function
	 * That will add startY,startX.  
	 * @param page the page number we are currently working on
	 * @param correctList the list of ayah we detected
	 */
	private static void printAyahForPageUtily2(int page, ArrayList<String> correctList)
	{
		try {
			/*Because the file naming is different from the actual pages we need to account for the missing 
			//So imageFileName 5 is actually 3 so the filename -2 will give you the correct paging number*/
			page=page-2;
			correctList=new ArrayList<String>(CommonFunctions.orgnizingArrayByYandX(unifyY(correctList)));
			

			String pageOutputValue="";
			
			
			for(int counter=0;counter<correctList.size();counter++)
			{
				String ayahYLocation=correctList.get(counter).trim().split(",")[0].replace("{", "");
						
				ArrayList<SurahBasmalah> getAllBasmlahsOnThisPage=CommonFunctions.getSurahBasmalahByPageId(page);
				
				if(getAllBasmlahsOnThisPage.size()==0)//This page does not have basmalah we print the values from previous surah and increase the ayahCounter
				{
					ayahCounter2++;
					pageOutputValue+="{"+correctList.get(counter).trim()+","+ayahCounter2+","+surahCounter+"}";
					//System.out.println("Surah:"+surahCounter+" Ayah:"+ayahCounter2+" page:"+page);
				
				}else {
					//This page have either single or multiple basmlah we need to check for surah change
					int firstAyahFound=CommonFunctions.getSurahNumberBasedOnBasmalah2(Integer.parseInt(ayahYLocation), getAllBasmlahsOnThisPage);
					
					if(firstAyahFound!=0 && (firstAyahFound!= surahCounter))
					{
						//System.out.println("surah has been changed this is now Surah:"+firstAyahFound);
						//This surah has changed and this ayah is the first ayah we need to reset the surahCounter and the ayah
						ayahCounter2=1;
						surahCounter=firstAyahFound;
						//System.out.println("Surah:"+surahCounter+" Ayah:"+ayahCounter2+" page:"+page);
						pageOutputValue+="{"+correctList.get(counter).trim()+","+ayahCounter2+","+surahCounter+"}";
					
					}else {
						ayahCounter2++;
						//This not the first ayah for this Surah
						//System.out.println("Surah:"+surahCounter+" Ayah:"+ayahCounter2+" page:"+page);
						pageOutputValue+="{"+correctList.get(counter).trim()+","+ayahCounter2+","+surahCounter+"}";
					}
					
				}
				
				
				
			}
			
			//String val3=" temp.add(new PageCoordinates(\"data\",\"pageId\"));";
			String val3="data | pageId";
			val3= val3.replace("pageId", Integer.toString(page));
			val3=val3.replace("data", pageOutputValue);
			
			//remove after debugging for result
			//System.out.println(val3.trim());
			
			tempAyahList.add(val3.trim());
			
		
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	

	/**
	 * This function will compare each element inside the array to check if any pixel element is very close to the other.
	 * doing this we can remove duplicate pixels detected from different templates as templates will detect the same ayah but with different y,x values
	 * so we should try to find if y and x have a similar pixel by 10 pixels close +10 or -10
	 * @param data the list before error correction
	 * @return The list after error correction by removing very close pixels  or null if an error occurs
	 */
	//The issue now when two ayah are on the same line Y value will be the same we need to remove duplicate values
	public static ArrayList<String> compareArrays2(ArrayList<String> data)
	{
		try {
			ArrayList<String> toRemoveList=new ArrayList<String>();
			if(data== null)
			{
				return toRemoveList;
			}
			for(String val:data)
			{
				int y=Integer.parseInt(val.split(",")[0]);
				int x=Integer.parseInt(val.split(",")[1]);
				//System.out.println("compareArrays2:"+val1+":"+val2);
				data.forEach((temp)->{
					if(temp.equals(val))
					{
						//same value skip

					}else {
						int val3=Integer.parseInt(temp.split(",")[0]);
						int val4=Integer.parseInt(temp.split(",")[1]);
						if(CommonFunctions.isBetween(val3,y,(y+10)) && (CommonFunctions.isBetween(val4,x,(x+10))||CommonFunctions.isBetween(val4,x-10,(x+10))))
						{
							//System.out.println("check:"+val3+":"+y);
							toRemoveList.add(temp);
						}
					}

				});
			}
			//Now we have values inside toRemoveList we need to remove them
			ArrayList<String> finalList=new ArrayList<String>();
			for(String temp:data)
			{
				if(toRemoveList.contains(temp))
				{
					//skip 
					continue;
				}else {
					finalList.add(temp);
				}
			}
			return finalList;
		}catch(Exception e)
		{
			e.printStackTrace();
		}

		return new ArrayList<String>();

	}

	/**
	 * Since we already have a list to compare ayah detection we have it stored in pages_ayah_count.txt 
	 * this file will contain each page and the ayahs in that page for comparison's sake
	 * @return the list to compare to
	 */
	private static HashMap<String,String> readingAyahCount() {
		File file=new File("images/pages_ayah_count.txt");
		HashMap<String,String> compareList = new HashMap<String,String>(); 
		try {
			//Reading the ayah numbers file
			if(file.exists())
			{
				Scanner scan=new Scanner(file);
				while(scan.hasNext())
				{
					String temp=scan.nextLine();
					String[] value=temp.split(",");

					if(value.length>0)
					{
						compareList.put(value[0], value[1]);
					}

				}
				scan.close();
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return compareList;
	}


	/**
	 * This function will just remove duplicates elements so we don't have duplicates in our list
	 * @param rowData list before duplicates detection
	 * 
	 * @return list after removing duplicate element
	 */
	public static ArrayList<String> removeDuplicate( String rowData)
	{
		try {
			ArrayList<String> values=new ArrayList<String>();		
			String[] temp=rowData.split("},");

			ArrayList<Integer> list1=new ArrayList<Integer>();
			ArrayList<Integer> list2=new ArrayList<Integer>();	
			for(String val:temp)
			{
				//System.out.println(val);
				if(val!= null && val.trim().length()>0)
				{
					String val1=val.replace("{", "").replace("}", "").split(",")[0];
					String val2=val.replace("{", "").replace("}", "").split(",")[1];
					Double temp1=Double.parseDouble(val1.trim());
					Double temp2=Double.parseDouble(val2.trim());
					list1.add(temp1.intValue());
					list2.add(temp2.intValue());
				}

			}
			Set<String> hash_Set = new HashSet<String>(); 
			for(int i=0;i<list1.size();i++)
			{
				//System.out.println(""+list1.get(i)+","+list2.get(i));
				hash_Set.add(list2.get(i)+","+list1.get(i));

			}
			values.clear();
			values.addAll(hash_Set);	
			return removeClosePixels(values);

		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return new ArrayList<String>();
	}


	/**
	 * Actually we have two methods that try to remove very close pixels and use this function first to try and remove very close
	 * pixels and if that didn't work we use another function @compareArrays2 to try and detect very close pixels again for the last chance.
	 * @param values list of detected ayah
	 * @return list after correction
	 */
	public static ArrayList<String> removeClosePixels(ArrayList<String> values)
	{
		try {

			if(values.size()<1)
			{
				//System.out.println("before size:"+values.size());
				return null;
			}
			//System.out.println("before size:"+values.size());
			Set<String> toRemoveList = new HashSet<String>();

			for(int i=0;i<values.size();i++)
			{
				//System.out.println(values.get(i));
				for(int b=0;b<values.size();b++)
				{
					//skip the element itself
					if(b==i)
					{
						continue;
					}

					int val1=Integer.parseInt(values.get(i).split(",")[0]);
					int val2=Integer.parseInt(values.get(i).split(",")[1]);

					int comp1=Integer.parseInt(values.get(b).split(",")[0]);
					int comp2=Integer.parseInt(values.get(b).split(",")[1]);

					if(CommonFunctions.isBetween(comp1,val1,val1+20) && CommonFunctions.isBetween(comp2,val2,val2+20))

					{
						//System.out.println("val1:"+val1+";val2:"+val2+";comp1:"+values.get(b).split(",")[0]+";comp2:"+values.get(b).split(",")[1]);
						toRemoveList.add(""+comp1+","+comp2);
					}



				}
			}
			//System.out.println("Check removable size:"+toRemoveList.size()+" old size:"+values.size());
			ArrayList<String> finalList=new ArrayList<String>();
			for(String temp:values)
			{
				if(toRemoveList.contains(temp))
				{
					//skip 
					//System.out.println("Skip");
					continue;
				}else {
					finalList.add(temp);
				}
			}
			//System.out.println("FinalSize list:"+finalList.size());
			return finalList;
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return new ArrayList<String>();

	}




/**
 * Since we will loop through the template we need this function to execute the detection by giving it the template number
 * @param number Template number to try and use
 * @param filename The image file that we target
 * @param i The current image
 * @param printImage Boolean to print the image after detection to see the result
 * @return The detected values as string from this template
 */
	public static String callTemplateViaNumber(int number,String filename, int i,boolean printImage)
	{
		String rowData="";
		rowData+=run("images/"+filename.replace("xxx", Integer.toString(i)),"images/templates/template2.png".replace("template2", "template"+number),i+"-template2.png".replace("template2", "template"+number),Imgproc.TM_CCOEFF_NORMED,printImage,Threshold);
		//System.out.println("Trying template: "+number);
		return rowData;
	}


	/**
	 * This helpful function will convert a list to a string to easier print the values
	 * @param data list to convert to string
	 * @return String value from list
	 */
	public static String listToString(ArrayList<String> data)
	{
		return  String.join(", ", data);
	}


	/**
	 * This function will start the detection process from openCV
	 * @param inFile Image File to target
	 * @param templateFile Template File as use for detection
	 * @param outFile ExtractedFile name after detection
	 * @param match_method Method we use for detection
	 * @param printImage Boolean to state if we need to print the detection image result
	 * @param threshold The threshold
	 * @return The values we have detected using this template as string
	 */
	public static String run(String inFile, String templateFile, String outFile,
			int match_method, boolean printImage, double threshold) {
		//System.out.println("\nRunning Template Matching");
		String outputString="";

		Mat img = Imgcodecs.imread(inFile, Imgcodecs.IMREAD_GRAYSCALE);
		Mat templ = Imgcodecs.imread(templateFile,Imgcodecs.IMREAD_GRAYSCALE);

		// / Create the result matrix
		int result_cols = img.cols() - templ.cols() + 1;
		int result_rows = img.rows() - templ.rows() + 1;
		Mat result = new Mat(result_rows, result_cols, CvType.CV_32FC1);

		// / Do the Matching and Normalize
		Imgproc.matchTemplate(img, templ, result, match_method);



		Imgproc.threshold(result, result, 0.1, 1, Imgproc.THRESH_TOZERO);
		// double threshold = Threshold;
		double maxval;
		Mat dst;
		while(true) 
		{
			Core.MinMaxLocResult maxr = Core.minMaxLoc(result);

			Point maxp = maxr.maxLoc;
			maxval = maxr.maxVal;

			dst = img.clone();
			if(maxval >= threshold)
			{
				Imgproc.rectangle(img, maxp,new Point(maxp.x + templ.cols(),maxp.y + templ.rows()), new Scalar(0, 255, 0));
				Imgproc.rectangle(result, maxp,new Point(maxp.x + templ.cols(),maxp.y + templ.rows()), new Scalar(0, 255, 0));
				outputString+=maxp+",";

			}else{
				break;
			}
		}


		if(printImage && outputString!= null && outputString.trim().length()>0)
		{
			Imgcodecs.imwrite(outFile, dst);
		}

		return outputString;

	}

	
	/**
	 * We have an issue when using multiple templates the CV library will detect different values for the same ayah and we get different Y value
	 * for the ayahs on the same line we need to fix this with this value
	 * @param correctList the list before unify Y value for ayah on the same line
	 * @return the list after fixing Y values to be exactly the same
	 */
	public static ArrayList<String> unifyY(ArrayList<String> correctList)
	{
		ArrayList<String> fixedList=new ArrayList<String>();
		try {
	
			ArrayList<String> foundNumbersToSkip=new ArrayList<String>();
			for(String val:correctList)
			{
				
				boolean found=false;
				//String y, String x,String ayah,String page
				AyahLocation temp=new AyahLocation(val.split(",")[0],val.split(",")[1],"","");
				int yToCompare=Integer.parseInt(temp.getY());
				if(foundNumbersToSkip.contains(val))
				{
					continue;
				}
				for(String val2:correctList)
				{
					if(val.equals(val2))
					{
						continue;
					}
					AyahLocation temp2=new AyahLocation(val2.split(",")[0],val2.split(",")[1],"","");
					int y=Integer.parseInt(temp2.getY());
					if(CommonFunctions.isBetween(y,yToCompare,(yToCompare+10))
							||CommonFunctions.isBetween(y,yToCompare,(yToCompare-10))
							||CommonFunctions.isBetween(y,(yToCompare-10),(yToCompare))
							||CommonFunctions.isBetween(y,(yToCompare-10),(yToCompare+10))
					   )
					{
						//they are the same line we should have a fixed number for all the ayahs on the same line
						found=true;
						foundNumbersToSkip.add(val2);
						String fixedVal=""+yToCompare+","+temp2.getX();
						fixedList.add(fixedVal.trim().replace(" ", ""));
						//System.out.println("Found ayah on the same line:"+val+":"+val2);	
						
					}
				}
				if(found)
				{
					found=false;
				}
				fixedList.add(val.trim().replace(" ", ""));	
			}
//			System.out.println(listToString(fixedList));
	
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return fixedList; 
	}

	

}
