#!/bin/bash

OPENSRC_DIR=/QOpenSys/pkgs/bin
	
################################################################################
#
#                               Procedures.
#
################################################################################

#       exist_directory dest
#
#       dest is an directory to check
#
#       exit 1 (succeeds) directort exist, else 0.

exist_directory()

{	
        [ -d "${1}" ] && return 0  || return 1      

}

#
#       install_yum_dependencies
#


install_yum_dependencies()

{	
        yum -y install 'make-gnu' 'maven' 'jq'    
}

#
#       build_project
#


build_project()

{
		cd ./IWSBuilder	
		mvn clean assembly:single	
        gmake
		cd ..
}

################################################################################
#
#                               Main
#
################################################################################

if exist_directory "${OPENSRC_DIR}";  then
    echo "5733-OPS product is installed ..."
else 
    echo "Please install 5733-OPS product first."
fi

# set path to OpenSource
echo "setting path to OpenSource ..."
export PATH=${OPENSRC_DIR}:$PATH

echo "installing dependencies for si-iws-builder ..."
echo "installing yum dependencies ..."
install_yum_dependencies

echo "build si-iws-builder ..."
build_project

