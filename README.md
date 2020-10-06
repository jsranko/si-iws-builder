# Download

```
curl https://api.github.com/repos/jsranko/si-iws-builder/releases/latest --insecure 
| jq '.assets[].browser_download_url | select(contains("with-"))' 
| xargs -n1 wget -O si-iws-builder-latest.jar --no-check-certificate
```

# Run

```
java -cp IWSBuilder-0.0.1-SNAPSHOT.jar:./dependency/* de.sranko_informatik.ibmi.iwsbuilder.App "SIIIA.iws"
```
```
java -cp IWSBuilder-0.0.1-SNAPSHOT.jar:./dependency/* de.sranko_informatik.ibmi.iwsbuilder.App "/QSYS.LIB/SIIIA.LIB/QIWSSSRC.FILE/SIIIA.MBR"
```

# debug
```
java -Xdebug -Xrunjdwp:transport=dt_socket,address=8998,server=y -cp IWSBuilder-0.0.1-SNAPSHOT.jar:./dependency/* de.sranko_informatik.ibmi.iwsbuilder.App "SIIIA.iws"
```
"x" 
