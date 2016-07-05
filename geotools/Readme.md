## This directory contains all the necessary files and instructions to provide geotools as a sevice in the ZOO-Service. ## 

Geotools has been succeussfully tested as a service on the Zoo-Project.
Basically this document is explaining about building of geotools, maven has been used for building it. 
(Note: Here the process has been explained in the mac OSX environment )


You can successfully setup the geotools as a service in ZOO-Project by following the below steps. 

Here the process is explaining the installation by using "pom.xml" file 

(You should have already build zoo-api, otherwise you can run "make" command and build it in the zoo-api directory)

You should create the ZOO.jar file in the  path ~/zoo-api/java/  by using the following command 
```	
jar -cvf ./ZOO.jar ./org/zoo_project/ZOO.class
```

Now place the pom.xml file in ~/zoo-api/java  directory 
then run,
``` 
mvn install:install-file -Dfile=ZOO.jar -DgroupId=zoo -DartifactId=ZOO -Dversion=1.6.0 -Dpackaging=jar -DgeneratePom=true
```	

Now you are ready to download the geotools and copy it in to your /zoo-services directory.
then go in the new directories created: /geotools/base-vect-ops/

Run the following command
``` 
 	CP="."; mvn dependency:tree > log ; j=1 list=$(grep "\- " log | grep -v "Building" | grep -v "\-\-\- "| cut -d":" -f2); list0=$(grep "\- "  log | grep -v "Building" | grep -v "\-\-\-" | cut -d":" -f4) ; for i in $list; do lt="$(echo $list0 | cut -d' ' -f$j)"; echo Scaning for $i version $lt ; for k in $(find /.m2/repository/ -name "*jar" | grep $i | grep /$lt/); do echo $k has been found; CP="$CP:$k"; done  ; j=$(expr $j + 1) ; done; pkg="$(grep "O) org.zoo_project:" log | cut -d":" -f2)" ; vers="$(grep "O) org.zoo_project:" log | cut -d":" -f4)" ; echo Searching for $pkg version $vers; for k in $(find /.m2/repository/ -name "*jar" | grep $vers | grep $pkg ); do echo $k has been found; CP="$CP:$k"; done

```
``` 	
mvn dependency:tree
```
``` 	
j=1;  list=$(grep "\- " log | grep -v "Building" | grep -v "\-\-\- "| cut -d":" -f2); list0=$(grep "\- "  log | grep -v "Building" | grep -v "\-\-\-" | cut -d":" -f4) ; for i in $list; do lt="$(echo $list0 | cut -d' ' -f$j)"; echo Scaning for $i version $lt ; for k in $(find /.m2/repository/ -name "*jar" | grep $i | grep /$lt/); do echo $k has been found; CP="$CP:$k"; done  ; j=$(expr $j + 1) ; done; pkg="$(grep "O) org.zoo_project:" log | cut -d":" -f2)" ; vers="$(grep "O) org.zoo_project:" log | cut -d":" -f4)" ; echo Searching for $pkg version $vers; for k in $(find /.m2/repository/ -name "*jar" | grep $vers | grep $pkg ); do echo $k has been found; CP="$CP:$k"; done
```	
now let's define list
```	
list=$(grep "\- " log | grep -v "Building" | grep -v "\-\-\- "| cut -d":" -f2)
ist0=$(grep "\- "  log | grep -v "Building" | grep -v "\-\-\-" | cut -d":" -f4)
j=1
```
Then run the following and get the results 
```	
echo $list
echo $CP
```
You may get a result under this command,

The result is similar to following string 
```
.:/Users/niroshan/.m2/repository/org/zoo_project/base-vect-ops/1.0-SNAPSHOT/base-vect-ops-1.0-SNAPSHOT.jar:/Users/niroshan/.m2/repository//junit/junit/4.11/junit-4.11.jar:/Users/niroshan/.m2/repository//org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar:/Users/niroshan/.m2/repository//com/vividsolutions/jts/1.13/jts-1.13.jar:/Users/niroshan/.m2/repository//org/geotools/gt-metadata/14.3/gt-metadata-14.3.jar:/Users/niroshan/.m2/repository//org/geotools/gt-opengis/14.3/gt-opengis-14.3.jar:/Users/niroshan/.m2/repository//net/java/dev/jsr-275/jsr-275/1.0-beta-2/jsr-275-1.0-beta-2.jar:/Users/niroshan/.m2/repository//commons-pool/commons-pool/1.5.4/commons-pool-1.5.4.jar:/Users/niroshan/.m2/repository//javax/media/jai_core/1.1.3/jai_core-1.1.3.jar:/Users/niroshan/.m2/repository//org/geotools/gt-main/14.3/gt-main-14.3.jar:/Users/niroshan/.m2/repository//org/jdom/jdom/1.1.3/jdom-1.1.3.jar:/Users/niroshan/.m2/repository//org/geotools/gt-api/14.3/gt-api-14.3.jar:/Users/niroshan/.m2/repository//org/geotools/gt-referencing/14.3/gt-referencing-14.3.jar:/Users/niroshan/.m2/repository//com/googlecode/efficient-java-matrix-library/core/0.26/core-0.26.jar:/Users/niroshan/.m2/repository//jgridshift/jgridshift/1.0/jgridshift-1.0.jar:/Users/niroshan/.m2/repository//net/sf/geographiclib/GeographicLib-Java/1.44/GeographicLib-Java-1.44.jar:/Users/niroshan/.m2/repository//org/geotools/gt-shapefile/14.3/gt-shapefile-14.3.jar:/Users/niroshan/.m2/repository//org/geotools/gt-data/14.3/gt-data-14.3.jar:/Users/niroshan/.m2/repository//org/geotools/gt-swing/14.3/gt-swing-14.3.jar:/Users/niroshan/.m2/repository//org/geotools/gt-render/14.3/gt-render-14.3.jar:/Users/niroshan/.m2/repository//org/geotools/gt-coverage/14.3/gt-coverage-14.3.jar:/Users/niroshan/.m2/repository//javax/media/jai_imageio/1.1/jai_imageio-1.1.jar:/Users/niroshan/.m2/repository//it/geosolutions/imageio-ext/imageio-ext-tiff/1.1.13/imageio-ext-tiff-1.1.13.jar:/Users/niroshan/.m2/repository//it/geosolutions/imageio-ext/imageio-ext-utilities/1.1.13/imageio-ext-utilities-1.1.13.jar:/Users/niroshan/.m2/repository//it/geosolutions/imageio-ext/imageio-ext-geocore/1.1.13/imageio-ext-geocore-1.1.13.jar:/Users/niroshan/.m2/repository//it/geosolutions/imageio-ext/imageio-ext-streams/1.1.13/imageio-ext-streams-1.1.13.jar:/Users/niroshan/.m2/repository//javax/media/jai_codec/1.1.3/jai_codec-1.1.3.jar:/Users/niroshan/.m2/repository//org/jaitools/jt-zonalstats/1.4.0/jt-zonalstats-1.4.0.jar:/Users/niroshan/.m2/repository//org/jaitools/jt-utils/1.4.0/jt-utils-1.4.0.jar:/Users/niroshan/.m2/repository//it/geosolutions/jaiext/affine/jt-affine/1.0.8/jt-affine-1.0.8.jar:/Users/niroshan/.m2/repository//it/geosolutions/jaiext/iterators/jt-iterators/1.0.8/jt-iterators-1.0.8.jar:/Users/niroshan/.m2/repository//it/geosolutions/jaiext/utilities/jt-utilities/1.0.8/jt-utilities-1.0.8.jar:/Users/niroshan/.m2/repository//it/geosolutions/jaiext/scale/jt-scale/1.0.8/jt-scale-1.0.8.jar:/Users/niroshan/.m2/repository//it/geosolutions/jaiext/translate/jt-translate/1.0.8/jt-translate-1.0.8.jar:/Users/niroshan/.m2/repository//it/geosolutions/jaiext/algebra/jt-algebra/1.0.8/jt-algebra-1.0.8.jar:/Users/niroshan/.m2/repository//it/geosolutions/jaiext/bandmerge/jt-bandmerge/1.0.8/jt-bandmerge-1.0.8.jar:/Users/niroshan/.m2/repository//it/geosolutions/jaiext/bandselect/jt-bandselect/1.0.8/jt-bandselect-1.0.8.jar:/Users/niroshan/.m2/repository//it/geosolutions/jaiext/bandcombine/jt-bandcombine/1.0.8/jt-bandcombine-1.0.8.jar:/Users/niroshan/.m2/repository//it/geosolutions/jaiext/border/jt-border/1.0.8/jt-border-1.0.8.jar:/Users/niroshan/.m2/repository//it/geosolutions/jaiext/buffer/jt-buffer/1.0.8/jt-buffer-1.0.8.jar:/Users/niroshan/.m2/repository//it/geosolutions/jaiext/crop/jt-crop/1.0.8/jt-crop-1.0.8.jar:/Users/niroshan/.m2/repository//it/geosolutions/jaiext/lookup/jt-lookup/1.0.8/jt-lookup-1.0.8.jar:/Users/niroshan/.m2/repository//it/geosolutions/jaiext/mosaic/jt-mosaic/1.0.8/jt-mosaic-1.0.8.jar:/Users/niroshan/.m2/repository//it/geosolutions/jaiext/nullop/jt-nullop/1.0.8/jt-nullop-1.0.8.jar:/Users/niroshan/.m2/repository//it/geosolutions/jaiext/rescale/jt-rescale/1.0.8/jt-rescale-1.0.8.jar:/Users/niroshan/.m2/repository//it/geosolutions/jaiext/stats/jt-stats/1.0.8/jt-stats-1.0.8.jar:/Users/niroshan/.m2/repository//com/google/guava/guava/17.0/guava-17.0.jar:/Users/niroshan/.m2/repository//it/geosolutions/jaiext/warp/jt-warp/1.0.8/jt-warp-1.0.8.jar:/Users/niroshan/.m2/repository//it/geosolutions/jaiext/zonal/jt-zonal/1.0.8/jt-zonal-1.0.8.jar:/Users/niroshan/.m2/repository//it/geosolutions/jaiext/binarize/jt-binarize/1.0.8/jt-binarize-1.0.8.jar:/Users/niroshan/.m2/repository//it/geosolutions/jaiext/format/jt-format/1.0.8/jt-format-1.0.8.jar:/Users/niroshan/.m2/repository//it/geosolutions/jaiext/colorconvert/jt-colorconvert/1.0.8/jt-colorconvert-1.0.8.jar:/Users/niroshan/.m2/repository//it/geosolutions/jaiext/errordiffusion/jt-errordiffusion/1.0.8/jt-errordiffusion-1.0.8.jar:/Users/niroshan/.m2/repository//it/geosolutions/jaiext/orderdither/jt-orderdither/1.0.8/jt-orderdither-1.0.8.jar:/Users/niroshan/.m2/repository//it/geosolutions/jaiext/colorindexer/jt-colorindexer/1.0.8/jt-colorindexer-1.0.8.jar:/Users/niroshan/.m2/repository//it/geosolutions/jaiext/imagefunction/jt-imagefunction/1.0.8/jt-imagefunction-1.0.8.jar:/Users/niroshan/.m2/repository//it/geosolutions/jaiext/piecewise/jt-piecewise/1.0.8/jt-piecewise-1.0.8.jar:/Users/niroshan/.m2/repository//it/geosolutions/jaiext/classifier/jt-classifier/1.0.8/jt-classifier-1.0.8.jar:/Users/niroshan/.m2/repository//it/geosolutions/jaiext/rlookup/jt-rlookup/1.0.8/jt-rlookup-1.0.8.jar:/Users/niroshan/.m2/repository//it/geosolutions/jaiext/vectorbin/jt-vectorbin/1.0.8/jt-vectorbin-1.0.8.jar:/Users/niroshan/.m2/repository//org/geotools/gt-cql/14.3/gt-cql-14.3.jar:/Users/niroshan/.m2/repository//org/geotools/gt-xml/14.3/gt-xml-14.3.jar:/Users/niroshan/.m2/repository//org/g
```
	
now, copy this content in your main.cfg file in an [env] section 

	define CLASSPATH=<CP>  (CP should be replaced the result of echo $CP)

Apart from that you should add the following path under the [java] section in the main.cfg
```
[java]
java.lib.path=/Library/WebServer/CGI-Executables/mm
java.awt.headless=true
```

Then, libZOO.dylib file should be placed from zoo-api/java to /Library/WebServer/CGI-Executables/mm/ (This may be different acording to your localhost path)

now you should be able to run the requests available from zoo-api/geotools/base-vect-ops/req
( Note: run them as root)
	
you have to copy the *.zcfg from getools/cgi-env to  /Library/WebServer/CGI-Executables/mm/

now run the following command to make the link:
```
	sudo ln –s /Library/Java/JavaVirtualMachines/jdk1.8.0_92.jdk/Contents/Home/jre/lib/server/libjvm.dylib /usr/local/lib/libjvm.dylib
```

now you are able to check the available capabilities 
```
	./zoo_loader.cgi "request=GetCapabilities&service=WPS"
```
You should be able to run the geotools services from now! 

Now,
``` 
	export REQUEST_METHOD=POST
	export CONTENT_TYPE=text/xml
```	
and 
```
	for i in ~/source/zoo/zoo-project/zoo-services/geotools/base-vect-ops/req/test_req*.xml; do ./zoo_loader.cgi < $i; done
```
Now you can execute the functions in the geotools, 
```
	./zoo_loader.cgi < /Users/user/zoo-src/zoo-project/zoo-services/geotools/base-vect-ops/req/test_req0.xml 
```

Further, You can develop a User interface for geotools services, Here we have developed an exaple Web UI, 

you can test it by copying it in your ~/Sites directory. 







	
	
	



