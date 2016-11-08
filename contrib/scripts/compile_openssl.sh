#!/bin/bash -eux
# compile_openssl.sh --  This script compiles the latest OpenSSL for Android using NDK.
#------------------------------------------------------------------
# Used to be # ! /bin/sh
# See: https://github.com/openssl/openssl
# 
# NOTE: 
# 		Version 1.1.0 will be supported until 2018-08-31.
# 		Version 1.0.2 will be supported until 2019-12-31 (LTS).
# 
# Download with: 
# 		wget https://www.openssl.org/source/openssl-1.0.2j.tar.gz
#------------------------------------------------------------------

# The openssl build system does not add a minus between ${CROSS_COMPILE} and the tool to call (e.g. gcc).
export CROSS_COMPILE=${CROSS_COMPILE}-
export CC="gcc --sysroot=${SYSROOT}"

OPSSL=$(basename ${BASE_DIR}/openssl-1*.tar.gz .tar.gz)
echo "  - Compiling OpenSSL: ${OPSSL}"

tar xzf ${BASE_DIR}/${OPSSL}.tar.gz
cd ${OPSSL}
./Configure android -fPIE
# openssl doesn't like parallel makes "-j4". See PROBLEMS file.
make LDFLAGS="-fPIE -pie"
