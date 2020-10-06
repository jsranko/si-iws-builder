# si-iws-builder (IWS Builder)
si-iws-builder is an opensource library to save IBM IWS (IBM Integrated Web Application Server) server definition in text/json format.

Examples and documentation are available here https://github.com/jsranko/si-iws-builder/wiki

## download



## Usage

### without Maven

1. Download a .jar file
```
curl https://api.github.com/repos/jsranko/si-iws-builder/releases/latest --insecure 
| jq '.assets[].browser_download_url | select(contains("with-"))' 
| xargs -n1 curl --output si-iws-builder-latest.jar --insecure
```
2. Run with configuration from ifs
```
java -cp si-iws-builder-latest.jar de.sranko_informatik.ibmi.iwsbuilder.App "SIIIA.iws"
```
3. Run with configuration from QSYS.LIB
```
java -cp si-iws-builder-latest.jar de.sranko_informatik.ibmi.iwsbuilder.App "/QSYS.LIB/SIIIA.LIB/QIWSSSRC.FILE/SIIIA.MBR"
```

## debug

1. Debug with configuration from ifs
```
java -Xdebug -Xrunjdwp:transport=dt_socket,address=8998,server=y -cp si-iws-builder-latest.jar de.sranko_informatik.ibmi.iwsbuilder.App "SIIIA.iws"
```
2. Debug with configuration from QSYS.LIB
```
java -Xdebug -Xrunjdwp:transport=dt_socket,address=8998,server=y -cp si-iws-builder-latest.jar de.sranko_informatik.ibmi.iwsbuilder.App "/QSYS.LIB/SIIIA.LIB/QIWSSSRC.FILE/SIIIA.MBR"
```

