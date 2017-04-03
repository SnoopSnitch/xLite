#!/bin/bash
# host_install.sh -- Installs the gsm-parser + libs on your local linux host 
#===========================================================================
# 
# Author:   emanuel@srlabs.de
# Date:     2017-02-04
#
# Description: 
#   
#   Installs the gsm-parser and dependent libraries on your local linux 
#   machine, specified as "host" in the compile.sh script. Executables 
#   and libraries are placed in one of the common paths: 
# 
#   trusted:        /lib,           /lib64 
#                   /usr/lib,       /usr/lib64
# 
#   non-trusted:    /usr/local/lib 
# 
#   You can check common library paths with:
#
#       cat /etc/ld.so.conf
#       cat /etc/ld.so.conf.d/*
# 
#   We install the gsm-parser binaries and libraries in: 
# 
#       /usr/local/lib/gsm-parser   (*.so.x)
#       /usr/local/bin              (diag_import, hex_import, gsmtap_import)
#
#   To run manually, you have to set LD_LIBRARY_PATH:
#
#       export LD_LIBRARY_PATH=/usr/local/lib/gsm-parser/
# 
#   Run parser with:
# 
#       diag_import -g output.pcap 2__66ca_0102164004_qdmon_2017-01-02_15-40UTC
# 
#===========================================================================
# cd ../contrib
#local GSMPLIB=/usr/lib/gsm-parser/
local GSMPLIB=/usr/local/lib/gsm-parser
local GSMPBIN=/usr/local/bin

if [-f $GSMPDIR]; then 
    echo "Making ${GSMPLIB}"
    sudo mkdir -m 755 $GSMPLIB
    #sudo chown root:root -R $GSMPLIB
fi

echo "Installing gsm-parser libraries to: ${GSMPLIB}"
sudo cp -n ./build-linux-x86_64-host-latest/out/lib/lib*.so.[0-9] ${GSMPLIB}/.
echo "Installing gsm-parser binaries to: ${GSMPBIN}"
sudo cp -n ./gsm-parser/*_import ${GSMPBIN}/. 
#sudo chmod 755 -R /usr/local/bin/*_import

#----------------------------------------------------------
# Prepare a "temp.sql" for later qdmon parser usage
#----------------------------------------------------------
cd gsm-parser
echo "Installing a template SQL file: ${GSMPLIB}/temp.sql"
# cat the sql files into a "temp.sql"
cat cell_info.sql si.sql sms.sql | sed -e 's/\/\*.*//g' >temp.sql
sudo mv temp.sql ${GSMPLIB}/.
cd -

#--------------------------------------
# Set the LD_LIBRARY_PATH
#--------------------------------------
# only perform ldconfig on the gsm-parser directory:
echo "Performing 'ldconfig -v -n' on: ${GSMPLIB}"
sudo ldconfig -v -n $GSMPLIB

#export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:${GSMPLIB}
echo -e "LD_LIBRARY_PATH set to: \n${LD_LIBRARY_PATH}"

echo -e "\nDone"
exit 0
