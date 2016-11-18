#!/bin/bash 
# cleanup4git.sh -- Remove all binary build artifacts
#----------------------------------------------------
# Removing all non-essential files to either:
# (a) prepare for a super-clean (from-scratch) build 
# (b) git/tar operation.
#
# ToDo: 
# [ ] check why "-e" bash flag causes stop when some find fails...
# [ ] Add switches for: 
# [ ] -g only Remove: Java gradle build artifacts
# [ ] -p only Remove: gradle.properties  (has build keys)
# [ ] -l only Remove: NDK static libraries & linked objects
# [ ] -s only Remove: NDK built shared object libraries
# [ ] -t only Remove: GIT files and directories
# [ ] -a Remove All
# [ ] -x show all excutions (Bash option: set -x) ??
# 
# How to run:
# - Run from project (gradlew) directory: ./SnoopSnitch/ 
# - Run with:  ./cleanup4git.sh 
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
echo "Removing all build directories..."
rm -fr ./contrib/build-*

echo "> Gradle and IntelliJ IDEA files and directories will NOT be removed."
echo "  Please remove these manually with:"
cat << EOF
# Remove Gradle and IntelliJ IDEA files
find ./ -iname "*.iml" -type f -delete
# Remove Gradle and IntelliJ IDEA directories
find ../ -iname "*.deps" -type d -exec rm -vr '{}' \;
find ../ -iname "*.idea" -type d -exec rm -vr '{}' \;

EOF

# Should we Remove *.Po , *.Pod files?

# Remove static libraries and linked objects
echo "Removing static libraries and linked objects (*.[o, o.d, lo, a, la, lai]) ..."
find ./ -name "*.o" -type f -delete
find ./ -name "*.o.d" -type f -delete
find ./ -name "*.lo" -type f -delete
find ./ -name "*.a" -type f -delete
find ./ -name "*.la" -type f -delete
find ./ -name "*.lai" -type f -delete


# Remove dynamically liked shared object (.so) output directories
echo "Removing shared objects build output (.libs) directories..."
find ./ -iname "*.libs" -type d -exec rm -vr '{}' \;

# Remove dynamically liked shared object (.so) libraries
echo "Removing most dynamically linked shared object (.so) libraries."
echo "> Files already in the ../contrib/prebuilt/ will NOT be removed."
# -prune doesn't work as expected
#find ./ -path ./contrib/prebuilt -prune -o -name "*.so" -type f -delete 
#find ./ -path ./contrib/prebuilt -prune -o -name "*.soT" -type f -delete 
find ./ -name "*.so" -not -path "./contrib/prebuilt/" -exec rm -v '{}' \;
find ./ -name "*.soT" -not -path "./contrib/prebuilt/" -exec rm -v '{}' \;


echo "  To remove these manually, use:"
cat << EOF
find ../ -name "*.so" -type f -delete
find ../ -name "*.soT" -type f -delete
# OR with: 
#find ../contrib -iname "prebuilt" -type d -exec rm -vr '{}' \;
rm -vfr ../contrib/prebuilt

EOF

echo "> GIT related ./app/ files and directories will NOT be removed."
echo "  To remove these manually, use:"
cat << EOF
find ./app -name "*.git" -type d -exec rm -vr '{}' \;

EOF


cd -
echo "done."
#echo "You will have to recompile all binaries from scratch."
exit 0
