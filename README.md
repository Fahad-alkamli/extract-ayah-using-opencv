![Gitea Last Commit](https://img.shields.io/gitea/last-commit/Fahad-alkamli/extract-ayah-using-opencv)

### extract-ayah-using-opencv
This project will take advantage of openCV template matching to extract Basmalahs and Ayahs coordinates from Quran images.

# Requirements for the project to run and Things good to know

1- Because GitHub doesn't support large files, I was not able to include the opencv jar file in the project, so you need to download it and place it in the lib folder in the project. You can download version opencv-4.9.0-0.jar from this link: https://1drv.ms/u/s!AghHPDsdk5STpd0lMfVhK5SIdg4g3g?e=PDpWYL


2- You will need to include your images into the app. I have only included few images so that you can see an example please remove them and include your images, you should start from page 1 which is Surat al fatha but the name should be "quran-all-pages-same-size-3 resized.jpg".

3- The counter will start actually from page 3 which is surat al bagrah since the first two pages include decorations which is impossible to detect.

4- I have included a few templates based on my version of the Quran, which is king Abdullah version, I will include a link if requested.

5- If you encounter an error in detection you need to manually add templates, templates images can be found under images/templates but keep track of the templates number variable, please increase the number as you add more templates. The naming should be like this "template24.png" which is the current template so next will be "template25.png" but remeber increase the counter !!!

6- I have included file called "pages_ayah_count.txt" this file is for validating the detection, it includes each page and the expected ayahs to find.
For example, page 1 expected detection 7

7- This project will need first to find the basmalah locations for the whole Quran and you need to manually add the results to the CommonFunctions class.


# Known limitations:

1-As page 1 and 2 from the holy quran will have decorations,detection will be almost impossible therefor we didn’t attempt to fix this issue.

# How the program works:
1-First you should enable extractBasmalahFromImagesToList() This function will give you the Basmalah so that ayah detection can detect the start and the end of a Surah based on the Basmalah.

2-After we receive the result from extractBasmalahFromImagesToList() We need to edit these values in the getSurahBasmalahByPageId method in the CommonFunctions class.

3-Now all the Images should be inside images folder and we don’t need the first three pages as they are not part of the quran so the first image will be from page 5 (remember we can’t extract Surat al Fataha  and the first page of Surat al baqra as they both have decorations) In the quran this is page 3 but keep in mind the file name will start from 5.


# Things to know:
1-We are using 24 templates for this detection you are welcome to add new templates, but you need to edit:
```java
public static int templateCounter=21;
```

2-Since we are skipping the first two pages the second ayah will be ayah 6 so keep in mind of this variable:
```java
	static int AyahCounter=6;
```
3-The full process is to detect as much detection as possible from the templates. After that we send them through functions to remove duplicates.

4-duplications are not always the same as different templates may detect the same ayah but with very close values for example:

Ayah 6 using Template one will detect its location as Y:120,X:200 and  a second Template will detect the same ayah but on Y:125,X:200 and another Template on Y:150,X:200
* So simply looking for exact values is not going to work here. We have implemented methods to detect very close values and make sure we have only a single location for Ayah 6.



#  How we make sure we have all the detections and that we are not missing ayahs?
We have a file called pages_ayah_count.txt can be found under images folder, this file contains each page and the number of expected ayahs on this single page.
After we have run the detection data through the duplications methods, we compare what we found with this file.

# Step by Step guide on how to run the application 
1- Make sure you have downloaded the opencv library and include it in the lib folder.

2- Make sure to change the reference to this jar file in the Build Path.

3- Include all your images starting from page 3 on the Quran until the last page, please keep in mind that the first page will be called "quran-all-pages-same-size-5 resized.jpg"  as page 1,2 are not actually Surah but an introduction to the Quran so we skip them and page 3-4 have decorations so we will start from page 5 which in the Holy Quran is page 3 , I know this must be annoying but you can fix it yourself.

4- Next we enable extracting the basmalah first.
5- We copy the results and edit getSurahBasmalahByPageId method in the CommonFunctions class with the new values.
6- Finally, we are ready to extract the Ayah but don't forget to comment out extracting the Basmalah.
7- Run the application and you will get the result as shown below:


# Understand the Results (the output):
