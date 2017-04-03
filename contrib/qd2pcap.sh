#!/bin/bash
# qd2pcap.sh -- Convert raw qdmon files to a PCAP + sqlite3 DB 
#===========================================================================
# 
# Author:   emanuel@srlabs.de
# Date:     2017-02-04
#
# Description: 
#
#   This is a wrapper script to parse raw Qualcomm qdmon files into 
#   PCAP files and populate a SnoopSnitch compatible SQLite3 DB. The 
#   PCAP files can be parsed in Wireshark.
#
# Input files:   
#   <blahblah>_qdmon_<timestamp>
# 
# Output files:
#   <filename>.pcap
#   <filename>.db
# 
# Dependencies:
#   - sqlite3
#   - gsm-parser (local host)
# 
# Important:
#
#   This script should live in $GSMPBIN !!
# 
# Usage: 
#   
#       qd2pcap.sh -g <output>.pcap <qdmon_file_1> ... <qdmon_file_n>
# 
# ToDo: 
#
# Q:  How can I extract radio traces in PCAP/GSMTAP format?
# A:  Add the parameter "-g output.pcap":
#     ./diag_import -g output.pcap <your input files>
#
#===========================================================================
# 1. check if we have installed gsm-parser on local machine
# 2. Build the SQLite3 DB structure from latest *.sql files
# 3. Extract PCAP and SQL insert statemetns from qdmon file(s)
#===========================================================================
local GSMPLIB=/usr/local/lib/gsm-parser
local GSMPBIN=/usr/local/bin
#local CWD=$PWD

#--------------------------------------
# (1): check for gsm-parser
#--------------------------------------
#cd $GSMPBIN
if [[-f $GSMPLIB ]]; then 
    echo "$GSMPLIB library doesn't exsist!"
    echo "Please install the gsm-parser using the host-install.sh script."
    exit 1
fi

if [[-f $GSMPBIN/diag_import ]]; then 
    echo "diag_import binary not found!"
    echo "Please install the gsm-parser using the host-install.sh script."
    exit 1
fi

#--------------------------------------
# (2): copy sql template to CWD
#--------------------------------------
#cat cell_info.sql si.sql sms.sql | sed -e 's/\/\*.*//g' >temp.sql 
cp ${GSMPLIB}/temp.sql .

#--------------------------------------
# (3): Extract PCAP and SQL from qdmon
#--------------------------------------
# DARGS can be a number of files and can also have "-g filename.pcap"
local DARGS=$@

if diag_import ${DARGS} | sed -ne 's/SQL://p' >>temp.sql ; then 
    cat temp.sql | sqlite3 result.db
else 
    echo "diag_import failed!"
    rm temp.sql
    exit 1
fi

echo "ok"
exit 0
