# extract-ayah-using-opencv
This project will take advantage of openCV template matching to extract Basmalahs and Ayahs coordinates from Quran images:
# Known limitations:

1-As page 1 and 2 from the holy quran will have decorations,detection will be almost impossible therefor we didn’t attempt to fix this issue.

# How the program works:
1-First you should enable extractBasmalahFromImagesToList() This function will give you the Basmalah so that ayah detection can detect the start and the end of a Surah based on the Basmalah.

2-After we receive the result from extractBasmalahFromImagesToList() We need to edit these values in the getSurahBasmalahByPageId method in the CommonFunctions class.

3-Now Images should be inside images folder and we don’t need the first three pages as they are not part of the quran so the first image will be from page 5 (remember we can’t extract Surat al Fataha  and the first page of Surat al baqra as they both have decorations) In the quran this is page 3 but keep in mind the file name will start from 5.


# Things to know:
1-We are using 21 templates for this detection you are welcome to add new templates but you need to edit:
```java
public static int templateCounter=21;
```

2-Since we are skipping the first two pages the second ayah will be ayah 6 so keep in mind of this variable:
```java
	static int AyahCounter=6;
```
3-The whole process is to detect as much detection as possible from the templates after that we send them thru functions to remove duplicates.

4-duplications are not always the same as different templates may detect the same ayah but with very close values for example:

Ayah 6 using Template one will detect it's location as Y:120,X:200 and  a second Template will detect the same ayah but on Y:125,X:200 and another Template on Y:150,X:200
* So simply looking for exact values is not going to work here we have implemented methods to detect very close values and make sure we have only a single location for Ayah 6.



#  How we make sure we are having all the detections and that we are not missing ayahs?
We have a file called temp.txt this file contains each page and the number of expected ayahs on this single page.
After we have run the detection data thru the duplications methods we compare what we found with this file.

