GalacticWarReboot
=================

##About this Project

This is my second 2D arcade game that uses the *[2D Game Framework](https://github.com/garyfredgiger/GameFramework.git)* that I have been developing and evolving since Sept 2013. This game is an adaption of the original Galactic War game from Jonathan S. Harbour's book Beginning Java Game Programming (Second Edition). I created this game for several reasons.

1. While looking for another job I needed a way to keep my current skillset sharp.
2. I always wanted to create video games and never found the time before. Since getting laid off in Sept 2013 I thought I would take advantage of the time off to pursue this personal interest of mine.
3. I wrote it as another project to add to my persoal portfolio.
4. To server a tutorial for other on how to create classic arcade games.
5. Another resume builder so I can now say I am a contributor to at least one open source project.

Hopefully you will find it fun, entertaining and reminiscent of the old school arcade games from the 1980s. Enjoy!

##A Few Preliminary Notes

This game is a work in progress. I am continually updating it by adding new features, refactoring the code and attempting to improve the overall gameplay experience. Please check back often for updates.

Note that even though my intention it to release this game and corresponding framework as an Open Source project, I have yet to choose a specific open source license under which to release it.

###Goal for this Game

My goal it to release this game so that it can be played within a browser (e.g., as an Applet/JApplet or using Java Web Start). Currently the only way to play this game is by cloning this project, downloading other dependencies (image and audio files), building it and running it on your local machine as a Java Application. Below in a later section there are detailed instructions on how to setup this project on your local machine.

###Become a Contributor

This game was only tested on my laptop with Ubuntu vesion 12.10 within the Eclipse IDE running it as a Java application. If you have expereince with deploying Java applicaitons as Applet/JApplets, using Java Web Start and/or simply want to become a tester and you are looking for something to do in your free time, become a contributor. What is in it for you? Pride, a resume builder and you can brag to your circle of friends that you are involevd with yet another open source project.

###Debugging

Since this game is still in development, there is a debugging features that can be enabled. During game play press *SHIFT* + *~* (tilda) to display debugging information about the current game. Also, pressing the 'a' key during game play will increase your firepower and pressing 'b' will decrease it.

##Setting up and Running the Project for use in Eclipse

This project was devloped and built using Eclipse (Kepler Release Build id: 20130614-0229) with Java 1.6. Note: If you are new to Eclipse and need instructions on how to install and set it up, refer to this link *[here](http://wiki.eclipse.org/Eclipse/Installation)*.

###What You Need

1) First, you need to clone this project. Open up a terminal window and in a local directory on your machine, clone this project using the command below.

    git clone https://github.com/garyfredgiger/GalacticWarReboot.git

Note that when you clone this project it will only pull the master branch, which contains the lastest release version of the game.

2) You will also need the 2D Game Framework *[here](https://github.com/garyfredgiger/GameFramework.git)*. Again, in your terminal window enter the command below.

    git clone git clone https://github.com/garyfredgiger/GameFramework.git

At this point there should be two sub folders in your current directory as shown below.

    drwxr--r-- 4 user user 4096 Mar  7 14:56 GalacticWarReboot
    drwxr--r-- 4 user user 4096 Mar  7 15:06 GameFramework

3) Open up Eclispe and import both projects (Right-click on Package Explorer and select Import, then select General -> Existing Projects into Workspace). After importing both projects you will notice there are build errors. To correct these errors we will need to perform a few actions, which I will cover next.

4) First, before these errors can be corrected you will need to download three JAR files required by this game in order to play the sound effects. To add the required JAR sound libraries you will need to download them from my DropBox folder [here](https://dl.dropboxusercontent.com/u/103427211/GalacticWarReboot/GalacticWarRebootSoundLibraryJARS.zip), then place them in a directory that you can easily access (For instance, I created a sub folder in my project directory call 'Libs' and place these jar files here).

5) Next, go back into Eclispe and right-click on the project *GalacticWarReboot* and select the option *Build Path -> Configure Build Path* and select the *Libraries* tab. Once the *Libraries* tab is selected, click on the button labeled *Add External JARS* and select add the three jar files in the Jar Selection dialog, then click on the OK button. This will take you back to the *Libraries* tab. But, before exiting this dialog you need to perform one more action. You need to add the *GameFramework* project to the *GalacticWarReboot* project. To do this select the *Projects* tab (to the right of the *Libraries* tab). Once you selected the *Projects* tab the list should be empty. To add the *GameFramework* project click on the Add button and select the *GameFramework* project, the click on the OK button, this will take you back to the *Projects* tab. Next you can click on the OK button to accept all changes. Now, when you build the project it should build without any errors.

6) Before you can play the game you will need to download the image files used in the game. However, before retrieving the image files you will need to create a source folder under the GalacticWarReboot project. To do this right-click on the project *GalacticWarReboot* in Eclipse and select New -> Source Folder, which will display the New Source Folder dialog box. Enter the name *images* in the Folder Name field and then click the Finish button. NOTE: The folder you create must be a Source folder and not a regular folder. If you creata a regular folder the applicaiton will not be able to find the images. After creating the source folder *images* download the image files from my DropBox folder [here](https://dl.dropboxusercontent.com/u/103427211/GalacticWarReboot/GalacticWarRebootImages.zip) and place them in the *images* source folder.

7) The last thing that needs to be done before running the game is to download the audio files for the sound effects. Before you do this we need to create a sound folderin which to place the audio files. First go into Eclipse and expand the *GalacticWarReboot* project, you will see the a folder labeled *src*. Right-click on this folder and select New -> Package, which will display the New Java Package dialog box. Enter the name *Sounds* in the Name field and click on Finish button. You will see the new folder *Sounds* under the *src* folder in the Eclipse Package Explorer. Now you can download the audio files from my DropBox folder [here](https://dl.dropboxusercontent.com/u/103427211/GalacticWarReboot/GalacticWarRebootAudioFiles.zip) and place them in the *Sounds* folder. Your probably wondering why the audio files need to reside in a folder under src? Well, the third party sound library I am using to play sound effects requires them to be in this location by default and I have not yet figued out how to change the path.

8) To run the game from within Eclipse, right click on the project *GalacticWarReboot* and select the opton Run As -> Java Application. Note, you may need to force a refresh first before running the game so all the image and audio files are loaded into the IDE. To do this highlight the *GalacticWarReboot* project and press F5.

### Notes About the Game

##### Known Problems/Bugs/Glitches

<ul>

<li>
There are currently no known major bugs in this game (i.e., bugs that impact the gameplay, game performance or cause the game to crash severly). That is not to say that there are no bugs at all in this game. I have tested it extensively and everything appears to be working as designed.
</li>

<li>
There are a few minor glitches that occur from time to time, but they are not "show stoppers". Some of the glitches include:
  <ul>

    <li>
    When a new level begins, sometimes one of the large asteroids is already broken up into three smaller ones before the player even shoots it.
    </li>
  </ul>
  
</li>
</ul>

## If You Encounter Problems?

If you do encounter a bug please email me at garyfredgiger@gmail.com and include in the subject line GALACTIC WAR REBOOT BUG. In the email please provide a detailed description of the bug and the steps that you followed to arrive at the bug. If I am unable to reproduce the problem then I will not be able to fix it.

## Additional Notes

I included the eclipse project file .project in this repository so you can simply import this project without having to create a new project from your cloned copy of the code. Some suggest it is not a good idea to include project specific files, but I thought I would be a rebel and do it anyway.
