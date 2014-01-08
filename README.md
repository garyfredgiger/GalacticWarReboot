GalacticWarReboot
=================

###Setting up the Project

My adaption of the Galactic War game from Jonathan S. Harbour's book Beginning Java Game Programming (Second Edition). This is my second game that I am creating using the game framework that I developed.

This project was devloped and built using Eclipse Kepler Release with Java 1.6.

When you clone this project, make sure you also clone the *[GameFramework](https://github.com/garyfredgiger/GameFramework.git)* project in this same repo. The GameFramework project is required for this game. Import both projects into Eclipse and before building the project, make sure you add the GameFramework project to this project under the menu options as described below.

<ol>
<li>Right click on the project and select the option Build Path -> Configure Build Path</li>
<li>Click on the Projects tab and add the GameFramework project, then click the OK button.</li>
</ol>

Note: If you are new to Eclipse and need instructions on how to install and set it up, refer to this link *[here](http://wiki.eclipse.org/Eclipse/Installation)*.

This folder contains source code only. Before running the game you will need to download the images for the game from my Dropbox account using the link below.

*[Image files for GalacticWar Reboot located in my Dropbox account](https://www.dropbox.com/sh/z3xzd2oqxmmk2nb/kBFzpMPkYg/GalacticWarReboot)*

Simply download the folder named **images** from this link above and place the entire folder under the directory named **src** in this GitHub project folder after you clone it. Make sure you refresh this project in Eclipse so the folder shows up in the Eclispe IDE along with all of the images.

Also note that you may need to add the following line to the .classpath file in order for the project to recognize the 'images' folder as a source folder. Otherwise, the images may not load properly when you run the project.

```xml
<classpathentry kind="src" path="images"/>
```

To run the game from within Eclipse, right click on the project **GameFrameworkGalacticWarReboot** (the name of this project when imported into Eclispe) and select the opton Run As -> Java Application.

### Notes About the Game

<ul>

<li>There are a few minor glitches that occur from time to time, but they are not "show stoppers". Some of the glitches include:

  <ul>
  
  <li>When a new level begins, sometimes one of the large asteroids is already broken up into three smaller ones before the player even shoots it.</li>
  <li>Sometime when you shoot a small asteroid, it completly disappaers without spawning three tiny asteroids.</li>
  </ul>
</li>


<li>There are currently no known major bugs in this game (i.e., bugs that impact the gameplay, game performance or cause the game to crash severly). That is not to say that there are no bugs at all in this game. I have tested it extensively and everything appears to be working as designed.</li>

<li>The game was only tested on my laptop running Ubuntu vesion 12.10 within Eclipse running it as a Java application. As I get more time for development and testing, my goal is to obviously test it on all platforms using the major browsers Chrome, IE, Firefox and Safari</li>

<li>For the most part this game is 90% or more completed. I still need to add a few more features in order to make it 100% completed. These features include:
  <ul>
  <li>Sounds. There are currently no sounds in this game.</li>

  <li>Adding controls to pause the game. Note that the game framework has the pause functionality built into the game loop. I have not added logic to the input handlers that map to the game framework's pause capability.</li>
  
  <li>Additional Power-ups. Even though there are several powerups including health, improved weaponry and point bonuses, I plan on adding additional powerups to better enhance the game play. These include a mega bomb that, when deployed, will destroy most asteroids on the screen, improved shields and possibly some type of guided projectile.</li>
  
  <li>High Score leader board</li>
  
  <li>Possibly adding difficulty levels. Although given the current game play, it get tough pretty quickly.</li>
  </ul>

<li>Since this game is still in development, there is a debugging features that can be enabled.</li>
<ul>
<li>During game play press *SHIFT* + *~* (tilda) to display debugging information about the current game.</li>
</ul>
</ul>

### If You Encounter Problems?

If you do encounter a bug please email me at garyfredgiger@gmail.com and include in the subject line GLACTIC WAR REBOOT BUG. In the email please provide a detailed description of the bug and the steps that you followed to arrive at the bug. If I am unable to reproduce the problem then I will not be able to fix it.

### Additional Notes

I included the eclipse project files (.project and .classpath) in this project so you can simply import this project without having to create a new project from your cloned copy of the code. Some suggest it is not a good idea to include project specific files, but I thought I would be a rebel and do it anyway.

This game was developed using Eclipse Kepler Release (Build id: 20130614-0229) with Java 1.6. 
