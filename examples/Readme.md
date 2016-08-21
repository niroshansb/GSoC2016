## Geotools Basic vector services

Now we are going to tun the implimented services. Given folders are already available in your /zoo-project/zoo-services/geotools/base-vect-ops/ directory.

This cgi-env contains all the [ZOO-Service configuration files (.zcfg)](http://zoo-project.org/docs/services/zcfg-reference.html). It describe the WPS service. As mentioned in the [setup guids](https://github.com/niroshansb/GSoC2016/tree/master/GeotoolsInZOOProject) now you already copied them in the cgi directory. If not you can copy them now to your cgi directory.
This example contains eleven (11) vectors services. You can add or remove them from [GeotoolsBasicServices.java](https://github.com/niroshansb/GSoC2016/blob/master/GeotoolsInZOOProject/geotools/base-vect-ops/src/main/java/org/zoo_project/GeotoolsBasicServices.java) file.
 
(Note: if you have done any changes in the GeotoolsBasicServices.java file, you should run "mvn clean install" again in the basic vector service directory )

Now let's go the the cgi directory, in my case the directory is /Library/WebServer/CGI-Executables/mm

We can check the available capabilities by using following command,
```
./zoo_loader.cgi "request=GetCapabilities&service=WPS"
```



