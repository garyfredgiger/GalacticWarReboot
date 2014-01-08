GalacticWarReboot
=================

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


Note: I just started creating this game and it may contains bugs.
