# Bezier curves
## Summary
Simple Bezier curves manipulation tool developed for Numerical Analysis 2 course at University of Wrocław, Poland.
Features:
* edit bezier curves in real time
* reduce curve degree (i.e. number of control points) using 3 different techniques

## Working with source code
You will need:
* Java 7 JDK: www.oracle.com/technetwork/java/javase/downloads/index.html
* gradle 1.6 (or newer) build tool: www.gradle.org/ 

### Compilation
Just do ```gradle build``` in root directory. gradle will download all required dependencies, compile classes, run unit tests and build an executable jar into ```build/libs``` directory

### Editing
I strongly recommend IntelliJ for editing the source. The community edition is free for non-commercial purposes and can be downloaded here: www.jetbrains.com/idea/
Execute ```gradle idea```, which will generate all required files. Then open the project in IntelliJ and enjoy!

## Running the app
Double-click on executable jar when using Windows or execute ```java -jar path/to/executable/jar.jar``` on Linux.

### Usage
It's really simple: 
* left click on the grey area to place control points. If two or more control points are placed, then appropriate Bezier curve will be drawn. 
* move any of the control points interactively by dragging it with a mouse.
* remove control points with right click
* reduce degree of the curve by choosing the method from the list at the top and pushing the Reduce button. New curve will be created on top of the old one and it will automatically become an active curve.
* change the active curve by selecting appropriate one from the list on the right side.

## Algorithms used
### Drawing
Standard De Casteljau's algorithm: http://en.wikipedia.org/wiki/De_Casteljau%27s_algorithm
### Degree reduction
3 methods available:
* Forrest and Farin methods described here: http://kowon.dongseo.ac.kr/~lbg/cagd/kmmcs/200401/sunwoo.ps
* using linear algebra approach: www.sirver.net/blog/2011/08/23/degree-reduction-of-bezier-curves/
