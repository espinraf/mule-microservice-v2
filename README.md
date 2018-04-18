# Mule Microservice

The example shows how to create a runnable Jar file with Mule Embedded.

This new example uses Mule Kernel Dependency from Mule, 
this library has to be downloaded from:

https://developer.mulesoft.com/download-mule-esb-runtime

After download the version 3.8.0, unpack the file and copy lib directory into this project.

The example uses Mule CE 3.8.0 and I could NOT find Mule Kernel for EE.

In case you find one for Mule EE, you need to:

+ Include Mule EE license file in src/main/resources.
+ Start the java process with the system property: 

```
-Djava.util.prefs.PreferencesFactory=com.mulesource.licm.pref.MulePreferencesFactory
```

##Requirements

a) Java 8+

b) Gradle 4.0+

## How to run it

1) Clone the project

2) Copy lib directory in the project as mentioned before

2) Go in the directory

3) Execute 

```
gradle clean build
```

4) Run
 
```
java -jar build/libs/mule-example-1.0.jar 
```

5) Go to http://localhost:8081/mule-ms

You should see in you browser: "Hello World from Mule Microservice"

Contact:
Rafael Espino (rafael.espino@gmail.com)

