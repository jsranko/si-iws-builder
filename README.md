# si-iws-builder (IWS Builder)
si-iws-builder is an opensource library to save IBM IWS (IBM Integrated Web Application Server) server definition in text/json format.

Examples and documentation are available here https://github.com/jsranko/si-iws-builder/wiki


## Install

### Set PATH variable

Extend the environment variable PATH, so that the OpenSource packages do not have to enter qualified:

```
export PATH=/QOpenSys/pkgs/bin:$PATH
```

### Install git

Opensource package **git** must be installed. For installation execute the following command:
```
yum install git
```

### Install Maven

Opensource package **Apache Maven** must be installed. For installation execute the following command:
```
yum install maven
```

### Clone project
A local copy of the project must be created:
```
git clone https://github.com/jsranko/si-iws-builder.git
```

### Build project

```
cd si-iws-builder/IWSBuilder
```
Create a JAR file from project:
```
mvn clean verify assembly:single
```
After some time the following should be visible in the console
```
[INFO] Building jar: /home/CECUSER/si-iws-builder/IWSBuilder/target/si-iws-builder-0.0.1-SNAPSHOT-jar-with-dependencies.jar
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  01:28 min
[INFO] Finished at: 2020-10-08T05:08:59-04:00
[INFO] ------------------------------------------------------------------------
```
