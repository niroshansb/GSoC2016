**This directory contains all the necessary files and instructions to provide geotools as a sevice in the ZOO-Service.** 

Geotools has been succeussfully tested as a service on the Zoo-Project.
Basically this document is explaining about building of geotools and maven has been used for building it. 
(Note: Here the process has been explained in the mac OSX environment )


You can successfully setup the geotools as a service in ZOO-Project by follow the below steps. 

Here the process is explaining the installation by using pom.xml file 

(You should have already build zoo-api otherwise you can run "make" command and build it in the zoo-api directory)

You should create the ZOO.jar file in the  path ~/zoo-api/java/  by using the following command 
	jar -cvf ./ZOO.jar ./org/zoo_project/ZOO.class


Now place the pom.xml file in zoo-api/java  directory 
then run, 
	mvn install:install-file -Dfile=ZOO.jar -DgroupId=zoo -DartifactId=ZOO -Dversion=1.6.0 -Dpackaging=jar -DgeneratePom=true
	

Now you are ready to download the geotools and copy it in to your zoo-services directory.
then go in the new directories created: geotools/base-vect-ops

Run the following command 
 	CP="."; mvn dependency:tree > log ; j=1 list=$(grep "\- " log | grep -v "Building" | grep -v "\-\-\- "| cut -d":" -f2); list0=$(grep "\- "  log | grep -v "Building" | grep -v "\-\-\-" | cut -d":" -f4) ; for i in $list; do lt="$(echo $list0 | cut -d' ' -f$j)"; echo Scaning for $i version $lt ; for k in $(find /.m2/repository/ -name "*jar" | grep $i | grep /$lt/); do echo $k has been found; CP="$CP:$k"; done  ; j=$(expr $j + 1) ; done; pkg="$(grep "O) org.zoo_project:" log | cut -d":" -f2)" ; vers="$(grep "O) org.zoo_project:" log | cut -d":" -f4)" ; echo Searching for $pkg version $vers; for k in $(find /.m2/repository/ -name "*jar" | grep $vers | grep $pkg ); do echo $k has been found; CP="$CP:$k"; done
 	
 	mvn dependency:tree
 	
 	
	j=1;  list=$(grep "\- " log | grep -v "Building" | grep -v "\-\-\- "| cut -d":" -f2); list0=$(grep "\- "  log | grep -v "Building" | grep -v "\-\-\-" | cut -d":" -f4) ; for i in $list; do lt="$(echo $list0 | cut -d' ' -f$j)"; echo Scaning for $i version $lt ; for k in $(find /.m2/repository/ -name "*jar" | grep $i | grep /$lt/); do echo $k has been found; CP="$CP:$k"; done  ; j=$(expr $j + 1) ; done; pkg="$(grep "O) org.zoo_project:" log | cut -d":" -f2)" ; vers="$(grep "O) org.zoo_project:" log | cut -d":" -f4)" ; echo Searching for $pkg version $vers; for k in $(find /.m2/repository/ -name "*jar" | grep $vers | grep $pkg ); do echo $k has been found; CP="$CP:$k"; done
	
now let's define list
	
	list=$(grep "\- " log | grep -v "Building" | grep -v "\-\-\- "| cut -d":" -f2)
	ist0=$(grep "\- "  log | grep -v "Building" | grep -v "\-\-\-" | cut -d":" -f4)
	j=1
	
	echo $list
	echo $CP
You may get a result under this command,
	
now, copy this content in your main.cfg file in an [env] section 

	define CLASSPATH=<CP>  (CP should be replaced the result of echo $CP)

Apart from that you should add the following path under the [java] section in the main.cfg

	[java]
	java.lib.path=/Library/WebServer/CGI-Executables/mm
	java.awt.headless=true

Then, libZOO.dylib file should be placed from zoo-api/java to /Library/WebServer/CGI-Executables/mm/

now you should be able to run the requests available from zoo-api/geotools/base-vect-ops/req
( Note: run them as root)
	
you have to copy the *.zcfg from getools/cgi-env to  /Library/WebServer/CGI-Executables/mm/

now run the following command to make the link:
	
	sudo ln –s /Library/Java/JavaVirtualMachines/jdk1.8.0_92.jdk/Contents/Home/jre/lib/server/libjvm.dylib /usr/local/lib/libjvm.dylib


now you are able to check the available capabilities 

	./zoo_loader.cgi "request=GetCapabilities&service=WPS"

You should be able to run the geotools services from now! 

Now, 
	export REQUEST_METHOD=POST
	export CONTENT_TYPE=text/xml
	
and 

	for i in ~/source/zoo/zoo-project/zoo-services/geotools/base-vect-ops/req/test_req*.xml; do ./zoo_loader.cgi < $i; done

Now you can execute the functions in the geotools, 

	./zoo_loader.cgi < /Users/user/zoo-src/zoo-project/zoo-services/geotools/base-vect-ops/req/test_req0.xml 


Further, You can develop a User interface for geotools services, Here we have develop an exaple UI, 

you can test it by copying it in your ~/Sites directory. 







	
	
	


