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
		git pull
		cd ./IWSBuilder	
		mvn clean verify assembly:single	
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
    echo -e "\e[31m Please install 5733-OPS product first.\e[0m"
fi

# set path to OpenSource
echo -e "\e[32m setting path to OpenSource ...\e[0m"
export PATH=${OPENSRC_DIR}:$PATH

echo -e "\e[32m installing dependencies for si-iws-builder ...\e[0m"
echo -e "\e[32m installing yum dependencies ...\e[0m"
install_yum_dependencies

echo -e "\e[32m build si-iws-builder ...\e[0m"
build_project

