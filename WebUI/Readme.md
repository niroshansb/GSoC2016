## Web User Interface for Geotools Service

This web user interface can be used as a web interface for geotools services. You can download assets, css and index.html files. Then copy it to your sites directory. For my case, it has been copied to /Library/WebServer/Documents/ 
Then, you can open it on your browser. It will be shown as below.

![screenshot](https://raw.githubusercontent.com/wiki/niroshansb/GSoC2016/image/Screen1.png "Geotools web user interface" )

Now you can select any feature on the map. And run any process. You should change the cgi path according to your directory. 
It can be identified as below. 

![screenshot](https://raw.githubusercontent.com/wiki/niroshansb/GSoC2016/image/ScreenBug.png "Geotools web user interface directory" )

Here you can identify the zoo_loader.cgi location. The given web user interface zoo_loader.cgi is located to the cgi-bin/mm directory. You should change it according to you directory before use the interface. After you change the directory you can use it.

Now the web interface has been sucessfully setup! 
you can run any service by using it. 

Let's run buffer tools. 

For that click a feature and select a single geometry tool. For this case click on buffer tool.

First click on the feature
![](https://raw.githubusercontent.com/wiki/niroshansb/GSoC2016/image/buffer1.png)

Then click on the Buffer button, then you can ge the result 
![](https://raw.githubusercontent.com/wiki/niroshansb/GSoC2016/image/buffer2.png)

Then click another feature and select a multiple geometries tool.

Click on the second feature 
![](https://raw.githubusercontent.com/wiki/niroshansb/GSoC2016/image/buffer3.png)

Now, Click on the tool, for this case Union 
![](https://raw.githubusercontent.com/wiki/niroshansb/GSoC2016/image/buffer4.png)
 
You can run any tools by folowwing the same procedure.
  


