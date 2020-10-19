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

### Clone project
A local copy of the project must be created:
```
git clone https://github.com/jsranko/si-iws-builder.git
```

### Build project

```
cd IWSBuilder
bash setup.sh
```

## Run the App
See [How to use IWS Builder](https://github.com/jsranko/si-iws-builder/wiki#how-to-use-iws-builder) 
