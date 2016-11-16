#!/bin/bash -e
# cleanup4git.sh -- Remove all binary build artifacts
#----------------------------------------------------
# Removing all non-essential files to either:
# (a) prepare for a super-clean (from-scratch) build 
# (b) tar operation.
#
# ToDo: 
# [ ] Add switches for: 
# [ ] -g only Remove: Java gradle build artifacts
# [ ] -p only Remove: gradle.properties  (has build keys)
# [ ] -l only Remove: NDK static libraries & linked objects
# [ ] -s only Remove: NDK built shared object libraries
# [ ] -a Remove All
# 
# How to run:
# - Run from project (gradlew) directory: ./SnoopSnitch/ 
#----------------------------------------------------
echo -n "Removing all build artifacts..."
# Remove all Android-Studio Java related build files
./gradlew clean

# Remove:  
#rm local.properties
#rm gradle.properties

# Go to project root directory
cd ..

# Remove all build directories
rm -fr ./contrib/build-*

# Remove static libraries and linked objects
find ./ -name "*.o" -type f -delete
find ./ -name "*.o.d" -type f -delete
find ./ -name "*.lo" -type f -delete
find ./ -name "*.a" -type f -delete
find ./ -name "*.la" -type f -delete
find ./ -name "*.lai" -type f -delete

# Remove dynamically liked shared object (.so) libraries
#find ./ -name "*.so" -type f -delete
#find ./ -name "*.soT" -type f -delete
echo "..shared objects (*.so) has NOT been removed, please edit this script."

cd -
echo "done."
#echo "You will have to recompile all binaries from scratch."
exit 0
