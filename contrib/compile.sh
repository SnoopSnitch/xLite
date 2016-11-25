#!/bin/bash -e
# compile.sh -- Compiles all the binary parser library dependencies required
#============================================================================
# Description: 
# 		This script compliles all the dependencies for the radio TAP parser.
# 
# Depends:  on the followng scripts found in: ./scripts/ 
# 		compile_diag_helper.sh
# 		compile_gsm-parser.sh
# 		compile_libasn1c.sh
# 		compile_libosmo-asn1-rrc.sh
# 		compile_libosmocore.sh
# 		compile_openssl.sh
# 
# Libraries: 
# 		libasn1c			[own git]
# 		libosmocore			[own git]
# 		gsm-parser			[own git]
# 		libosmo-asn1-rrc 	[?]
# 		openssl 			(Android)
# 		diag_helper			(Android)
# 
# Date: 2016-11-25
# 
# ToDo: 
# 		[ ] Add option to only build OpenSSL libs
# 		[ ] Add automatic NDK ./platform/android-NN/ selector
# 		[x] Add compile "clock" time to Building...
# 		[x] Add auto-check for git submodules (see above)
# 		[ ] Add auto-import of git submodules
# 		[ ] Add automatic run of copy.sh script after compile.
# 
# NOTE: 
# 		1) To import forgotten or missing git submodules, do this: 
# 			cd <my_cloned_repo> 
# 			git submodule update --init --recursive
# 
# 		2) This script is location dependent, if you move it, 
# 		   make sure to edit the paths.
# 
#============================================================================
# Setting time-stamp to: 20161004-183200
#TS=`date +'%Y%m%d-%H%M%S'` 
mytime() { date +'%H:%M:%S'; }  # to remove newline add:  | tr '\n' ' '
deltat() { echo "  [$(($SECONDS / 60)):$(($SECONDS % 60))]"; }

#----------------------------------------------------------------------
usage() {
	echo >&2 "Usage: $0 -t {android|host} [-f] [-h]"
	echo >&2 "   -t <target>   Target to build for"
	echo >&2 "   -f            Fast mode - only build parser"
	echo >&2 "   -g            init git submodules"
	echo >&2 "   -u            update ./prebuilt directory"
	#echo >&2 "   -s            only build OpenSSL"
	echo >&2 "   -h            This help screen"
	#exit 1
}
#----------------------------------------------------------------------

# Use:  cecho green "my text"
# See: http://stackoverflow.com/questions/5947742/how-to-change-the-output-color-of-echo-in-linux
# Bug: output of "C:\tmp\temp" is garbled... because of "\", use something like: sed 's/\\\/\\\\\\/i'  
cecho() {
  local code="\033["
  case "$1" in
	black  | bk) color="${code}0;30m";;
	red    |  r) color="${code}1;31m";;
	green  |  g) color="${code}1;32m";;
	yellow |  y) color="${code}1;33m";;
	blue   |  b) color="${code}1;34m";;
	purple |  p) color="${code}1;35m";;
	cyan   |  c) color="${code}1;36m";;
	gray   | gr) color="${code}0;37m";;
	*) local text="$1"
  esac
  [ -z "${text}" ] && local text="${color}${2}${code}0m"
  echo -en "${text}"  # Cygwin seem to need -e flag
}

# print text between lines 
function lecho () { MYT=$1; mys=${#MYT}; MYL=$(printf '=%.0s' $(seq 1 $mys ) ); echo -e "${MYL}\n${MYT}\n${MYL}"; }

function repo_miss () { 
	cecho red "\n\nError:"; cecho yellow " Missing submodule sources!\n";
	echo "It seem that you have forgotten to clone the submodule repositories."
	echo "To import all forgotten or missing git submodules, do this:"
	cecho purple "cd repository_base_directory\n";
	cecho purple "git submodule update --init --recursive\n";
	miss=$(echo "$1" | sed 's/\/.*$//'; ) 
	echo "The missing submodule is: $miss";
}

#----------------------------------------------------------------------
# START
#----------------------------------------------------------------------

fast=""
update=""

while getopts hfgust: o
do
	case "$o" in
		t)      target="${OPTARG}";;
		f)      fast=1;;
		u)      update=1;;
		h)      usage; exit 0;; # ok
		g)      do_git=1;;
		s)      onlyssl=1;;
		[?])    usage; exit 1;;	# err
		#*)      usage; exit 1;;	# err
	esac
done

shift $(($OPTIND-1))

case ${target} in
		android|host) ;;
		*)            usage; exit 1;;
esac

# set platform
MACH=$(uname -m)
KERN=$(uname -s)

case ${KERN} in
		Darwin) 	HOST="darwin-${MACH}";;
		Linux)  	HOST="linux-${MACH}";;
		CYGWIN*) 	HOST="linux-${MACH}";; # may not work fully
		*)      	echo "Unknown platform ${KERN}-${MACH}!"; exit 1;;
esac

# Link to latest successful build
LATEST=build-${HOST}-${target}-latest

if [ "$fast" ]; 
then
	BUILD_DIR=${LATEST}
else
	BUILD_DIR=$(mktemp -d build-XXXXXXXXXX)
fi

# Full path to this script directory
export BASE_DIR="$( cd "$( dirname $0 )" && pwd )"


# Check if git submodules exists by looking for some random files:
TESTFILES="gsm-parser/diag_import.c libasn1c/src/ber_decoder.c libosmocore/src/gsmtap_util.c"
for x in ${TESTFILES}; do
	# instead of exit, we could download it automatically...
	[ -f "$x" ] || { repo_miss $x; exit 1 ;}
done


# [-g]: init submodules, if missing
if [ "$do_git" -a -z "$fast" ];then
	# ToDo: Need better path control and cd here!
	if [ ! "$(ls -A libasn1c)" ];     then (cd .. && git submodule init contrib/libasn1c && cd - ) ; fi
	if [ ! "$(ls -A libosmocore)" ];  then (cd .. && git submodule init contrib/libosmocore && cd - ) ; fi
	if [ ! "$(ls -A gsm-parser)" ];   then (cd .. && git submodule init contrib/gsm-parser && cd - ) ; fi
fi

# [-g] + [-f] FAST: clone/download submodules
if [ "$do_git" -a "$fast" ];then
		(cd .. && \
		git submodule update contrib/libasn1c && \
		git submodule update contrib/libosmocore && \
		git submodule update contrib/gsm-parser)
fi

lecho "Building on ${HOST} for ${target}..."

mytime;

cd ${BUILD_DIR}
OUTPUT_DIR=`pwd`
NDKTCVERS=$(ls -1 ${ANDROID_NDK}/toolchains/ |\grep -E "arm-linux-androideabi-*")

## Placeholder for ToDo: autoget android-NN

export MSD_DESTDIR="${OUTPUT_DIR}/out"

case ${target} in
	android)
		export SYSROOT="${NDK_DIR}/platforms/android-19/arch-arm"
		export MSD_CONFIGURE_OPTS="--host arm-linux-androideabi --prefix=${MSD_DESTDIR}"
		#export PATH=${PATH}:${NDK_DIR}/toolchains/arm-linux-androideabi-4.9/prebuilt/${HOST}/bin/
		export PATH=${PATH}:${ANDROID_NDK}/toolchains/${NDKTCVERS}/prebuilt/${HOST}/bin/
		export CROSS_COMPILE=arm-linux-androideabi
		export RANLIB=arm-linux-androideabi-ranlib
		export CFLAGS="--sysroot=${SYSROOT} -nostdlib"
		export CPPFLAGS="-I${NDK_DIR}/platforms/android-19/arch-arm/usr/include/"
		export LDFLAGS="--sysroot=${SYSROOT} -Wl,-rpath-link=${NDK_DIR}/platforms/android-19/arch-arm/usr/lib/,-L${NDK_DIR}/platforms/android-19/arch-arm/usr/lib/"
		export LIBS="-lc -lm"
		export GSM_PARSER_MAKE_ARGS="TARGET=android PCAP=1 PREFIX=${MSD_DESTDIR} DESTDIR=${MSD_DESTDIR}/gsm-parser SYSROOT=${SYSROOT} install"
		;;
	host)
		export MSD_CONFIGURE_OPTS="--prefix=${MSD_DESTDIR}"
		export GSM_PARSER_MAKE_ARGS="TARGET=host PCAP=1 PREFIX=${MSD_DESTDIR}"
		;;
	*)
		# Shouldn't happen
		echo "Invalid target \"${target}\""
		exit 1;;
esac

mkdir -p ${MSD_DESTDIR}

# Do not build dependencies in fast mode
if [ -z "$fast" ]; then
	TARGETS="libosmocore libasn1c libosmo-asn1-rrc"
	# Only build OpenSSL and diag_helper for Android targets
	if [ "$target" == "android" ]; then
		TARGETS="${TARGETS} openssl diag_helper"
	fi
fi

TARGETS="${TARGETS} gsm-parser"

#[ "$onlyssl" ] && (TARGETS="openssl");
if [ "$onlyssl" ]; then TARGETS="openssl"; fi;

# Run all the compile scripts
for i in ${TARGETS}; do
	#echo -n "Building $i ... "
	echo -n "Building "; cecho yellow "$i";  echo -n " ... "
	SECONDS=0;
	cd $OUTPUT_DIR
	if ${BASE_DIR}/scripts/compile_$i.sh > ${OUTPUT_DIR}/$i.compile_log 2>&1;then
	#if [ (${BASE_DIR}/scripts/compile_$i.sh > ${OUTPUT_DIR}/$i.compile_log 2>&1) ];then
		#echo "ok"
		cecho green "ok"; deltat;
	else
		cecho red "Failed!\n"
		echo "Please see the log file: ${OUTPUT_DIR}/${i}.compile_log"
		exit 1
	fi
done

mytime;

# OUTPUT_DIR = ./snoopsnitch/contrib/build-XXXXXXX/
# PARSER_DIR = ./snoopsnitch/contrib/build-XXXXXXX/parser
PARSER_DIR=${OUTPUT_DIR}/parser

if [ "$target" == "android" ]; then
	lecho "Installing files to: ${PARSER_DIR}/ ..."
	# Install parser
	install -d ${PARSER_DIR}
	install -m 755 ${OUTPUT_DIR}/out/lib/libasn1c.so            ${PARSER_DIR}/libasn1c.so
	install -m 755 ${OUTPUT_DIR}/out/lib/libosmo-asn1-rrc.so    ${PARSER_DIR}/libosmo-asn1-rrc.so
	install -m 755 ${OUTPUT_DIR}/out/lib/libosmocore.so         ${PARSER_DIR}/libosmocore.so
	install -m 755 ${OUTPUT_DIR}/out/lib/libosmogsm.so          ${PARSER_DIR}/libosmogsm.so
	install -m 755 ${OUTPUT_DIR}/out/gsm-parser/diag_import     ${PARSER_DIR}/libdiag_import.so
	install -m 755 ${OUTPUT_DIR}/out/gsm-parser/libcompat.so    ${PARSER_DIR}/libcompat.so

	install -m 644 ${OUTPUT_DIR}/out/gsm-parser/sm_2g.sql       ${PARSER_DIR}/sm_2g.sql
	install -m 644 ${OUTPUT_DIR}/out/gsm-parser/sm_3g.sql       ${PARSER_DIR}/sm_3g.sql
	install -m 644 ${OUTPUT_DIR}/out/gsm-parser/mcc.sql         ${PARSER_DIR}/mcc.sql
	install -m 644 ${OUTPUT_DIR}/out/gsm-parser/mnc.sql         ${PARSER_DIR}/mnc.sql
	install -m 644 ${OUTPUT_DIR}/out/gsm-parser/hlr_info.sql    ${PARSER_DIR}/hlr_info.sql
	install -m 644 ${OUTPUT_DIR}/out/gsm-parser/sm.sql          ${PARSER_DIR}/sm.sql

	install -m 644 ${BASE_DIR}/gsm-parser/cell_info.sql         ${PARSER_DIR}/cell_info.sql
	install -m 644 ${BASE_DIR}/gsm-parser/si.sql                ${PARSER_DIR}/si.sql
	install -m 644 ${BASE_DIR}/gsm-parser/sms.sql               ${PARSER_DIR}/sms.sql
	install -m 644 ${BASE_DIR}/gsm-parser/anonymize.sql         ${PARSER_DIR}/anonymize.sql
	
	# Put the S/MIME crt into the library directory since it needs to be a physical
	# file on the Android system so that it can be accessed from the openssl binary. 
	# Other parts of the App like assets are not stored as read files on the Android 
	# system and therefore can only be used from the Android java code but not from 
	# native binaries.

	#install -m 755 openssl-1.0.1i/apps/openssl                 ${PARSER_DIR}/libopenssl.so
	install -m 755 ./openssl-1*/apps/openssl                    ${PARSER_DIR}/libopenssl.so
	install -m 755 ${BASE_DIR}/smime.crt                        ${PARSER_DIR}/libsmime_crt.so

	# Really dirty hack: The Android build system and package installer require 
	# all files in the native library dir to have a filename like libXXX.so. If
	# the file extension ends with .so.5, it will not be copied to the APK file. 
	# So the following line of perl patches all references so that the libraries
	# are found with a .so extension instead of .so.[digit]
	#perl -i -pe 's/libasn1c\.so\.0/libasn1c.so\0\0/gs;s/libosmo-asn1-rrc\.so\.0/libosmo-asn1-rrc.so\0\0/gs;s/libosmocore\.so\.6/libosmocore.so\0\0/gs;s/libosmogsm\.so\.5/libosmogsm.so\0\0/gs' ${PARSER_DIR}/*.so

	# The clean hack is to use: rename
	rename -v 's/\.[0-9]$//' ${PARSER_DIR}/*.so.*
fi

ln -sf ${BUILD_DIR} ../${LATEST}

# Update prebuilt dir
if [ "$update" ]; then
	echo -n "Updating: copying diag_helper and PARSER_DIR files to: BASE_DIR/prebuilt/ ... "
	cp ${PARSER_DIR}/* ${BASE_DIR}/prebuilt/
	cp ${BASE_DIR}/diag_helper/libs/armeabi/libdiag-helper.so ${BASE_DIR}/prebuilt/
	cecho green "ok"; echo; 
fi

echo -e "Done.\n"
echo -e "Remeber to copy library (*.so) and sqlite3 (*.sql) files to app jniLibs and assests."
echo -e "To do this, run: ./copy.sh  from:  ../SnoopSnitch/"

exit 0
