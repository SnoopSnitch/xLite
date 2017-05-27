
(1) Installing Android Studio (Linux)
=====================================

Background:
-----------

Until now, we have been using Ant as the build tool to compile
Android-Studio (hereafter referred to as simply **AS**). However, due
to the decreasing popularity of Ant and much smaller user base
combined with reduced up-to-date instructions, we have [migrated] to
use the standard Gradle build tools. This is what is and has been used
by most Android app developers since the beginning of the Android
revolution. Today, the Gradle build tool is integrated in Android-
Studio, even if other tools are supported as well. Gradle is a script
based task handler and can be a little tricky to get to work as it
doesn’t necessarily follow standard scripting logic. Below we present
how to get AS up and running on a standard Linux distribution.

What is [Android-Studio] ?

 > Android Studio is the official Integrated Development Environment (IDE) for Android app development, based on IntelliJ IDEA.

OS and hardware setup
---------------------

These instructions were made and verified to work with [Linux Mint] **18** *Sarah* (LTS) \[Cinnamon/x64\].

The optimal way to run this for portable and quick concurrent use, is to install the OS in a
Virtual Machine (VM), such as Virtualbox or VMware. There are also Docker images available.

Package Dependencies and Requirements
-------------------------------------

To get Android-Studio quickly installed you need to first install and configure some required package dependencies.

It’s always hard to remember what exact packages are needed, but in general terms we need:

-   Java
-   Android SDK
-   Android NDK
-   Android-Studio
-   

The [installation instructions] for AS are as follows:

 > If you are running a 64-bit OS version, you need to install some 32-bit libraries with the following command:

    # For Mint:
    sudo apt-get install libc6:i386 libncurses5:i386 libstdc++6:i386 
    sudo apt-get install lib32z1 libc6-i386
    sudo apt-get install android-tools-adb

    # For Ubuntu:
    sudo apt-get install libc6:i386 libncurses5:i386 libstdc++6:i386 lib32z1 lib32bz2-1.0

    # For Fedora:
    sudo yum install zlib.i686 ncurses-libs.i686 bzip2-libs.i686

Create and setup the installation paths and directories
-------------------------------------------------------

First create the installation directories, to which the environments paths will be set.
We have found the ideal place is in /opt, like this:

    /opt/android/android-studio/
    /opt/android/sdk/
    /opt/android/ndk/

To create them, do the following:

     
    sudo mkdir -p <each_path_shown_above>
    sudo chown -R you:you /opt/android
    sudo chmod 755 -R /opt/android

where “**you**” is your linux username, preferably not “root”.

Next you need to setup a few environment variables. This is most easily done
by editing your **~./bashrc** and add the following lines to the end of the file.

  [migrated]: https://developer.android.com/studio/intro/migrate.html
  [Android-Studio]: https://developer.android.com/studio/intro/index.html
  [Linux Mint]: https://linuxmint.com/
  [installation instructions]: https://developer.android.com/studio/install.html
  
  
    #----------------------------------------------------------
    # Android Development
    #----------------------------------------------------------
    export ANDROID_NDK=/opt/android/ndk
    export ANDROID_SDK=/opt/android/sdk
    export ANDROID_HOME=${ANDROID_SDK}
    export PATH=${PATH}:$ANDROID_NDK
    export PATH=${PATH}:$ANDROID_SDK/tools:$ANDROID_SDK/platform-tools
    # For SnoopSnitch compilation:
    export NDK_DIR=${ANDROID_NDK}
    # For AS/Gradle compilation:
    export IDE_HOME=/opt/android/android-studio/

Download and Install all the Android packages
---------------------------------------------

Go to the following web pages and download each into: */opt/android/*

    https://developer.android.com/studio/index.html   
    # [~445 MB] Download: android-studio-ide-14n.nnnnnn-linux.zip
    # [~311 MB] Download: android-sdk_r24.4.1-linux.tgz

    https://developer.android.com/ndk/downloads/index.html
    # [~656 MB] Download: android-ndk-r13-linux-x86_64.zip

-   Extract/Unzip the files. This will create their default directory names for their respective directories.
-   Move the extracted contents into your shorter named directories (i.e. sdk, ndk, android-studio).
    (This is essentially just a rename.)

For example:

    tar -xvf android-sdk_r24.4.1-linux.tgz
    mv android-sdk-linux sdk

    unzip android-ndk-r13-linux-x86_64.zip
    mv android-ndk-r13 ndk

The result should be:

    /opt/android $ tree -dL 2 ./
    ./
    ├── android-studio
    │   ├── bin
    │   ├── gradle
    │   ├── jre
    │   ├── lib
    │   ├── license
    │   └── plugins
    ├── ndk
    │   ├── build
    │   ├── platforms
    │   ├── prebuilt
    │   ├── python-packages
    │   ├── shader-tools
    │   ├── simpleperf
    │   ├── sources
    │   └── toolchains
    └── sdk
        ├── add-ons
        ├── build-tools
        ├── extras
        ├── patcher
        ├── platforms
        ├── platform-tools
        ├── sources
        ├── system-images
        ├── temp
        └── tools

Check your Java installation (OpenJDK should already be installed.)
-------------------------------------------------------------------

    $ javac -version
    javac 1.8.0_91

    $ javaws -version
    icedtea-web 1.6.2 (1.6.2-3ubuntu1)

    $ java -version
    openjdk version "1.8.0_91"
    OpenJDK Runtime Environment (build 1.8.0_91-8u91-b14-3ubuntu1~16.04.1-b14)
    OpenJDK 64-Bit Server VM (build 25.91-b14, mixed mode)

If you have issues, also check what has been installed:

    # Check what you have installed with:
    ll /usr/lib/jvm/

    alias lpacks='sudo dpkg --list'
    lpacks |grep "jdk" && lpacks |grep "jre"

    # Check what is used:
    sudo update-alternatives --config java
    sudo update-alternatives --config javac
    sudo update-alternatives --config javaws

Soft-link some binaries for easy access.
----------------------------------------

    sudo ln -s /opt/android/android-studio/bin/studio.sh /usr/local/bin/studio
    sudo chmod 755 /usr/local/bin/studio

After you have done this, you should be able to launch the Android-Studio IDE from anywhere, with:

    $ studio &

You can also try to test the SDK and NDK environments with:

    cd $HOME
    # Test NDK with:
    ndk-build -v
    # Test SDK with:
    android -h
    
    However, in order to be able to do anything useful in AS, you need to make sure to:

-   download a bunch of packages from the SDK (depending on what you’re compiling.)
-   each downloaded package need to have its License accepted.

Accept all licenses!
OR read:

1.  \[1\] https://developer.android.com/studio/intro/update.html
2.  \[2\] https://developer.android.com/studio/releases/build-tools.html
3.  \[3\] https://developer.android.com/studio/build/building-cmdline.html

Run the SDK manager with:

    android 2>/dev/null &

(You want to redirect *stderr* to /dev/null to rid the screen from annoying GLib errors.)

To get familiar with the SDK, you can try to do this:

    # De-select:    "Android 7.0 (API 24)"
    # ADD:
    # Select:   "Android 4.4.2 (API 19)"
    # In "Choose Packages to Install" window, select:
    # "Accept License" (for all packages shown)
    # 
    # ADD: 
    #   "Extras" >> "Android Support Repository"
    #
    # CLOSE and REPEAT a few times until there are no updates.

To compile SnooSnitch, you will have to install a large number of additional SDK packages.

For SnoppSnitch version **1.1.0**, please install:

|               |         |     |
|---------------|---------|-----|
| package\_name | version | API |
| WIP           | WIP2    | 19  |

Additional AS tweaks
--------------------

To improve you AS experience, try editing the following:

    # Show line numbers:   
    # File >> Settings > Editor > General > Appearance >> [x] Show line numbers

    # Jump to last tab when closing active one:
    # File >> Settings > Editor > General > Editor Tabs >> (o) Activate most recently opened tab

    # Set right margin to 140 char instead of 100:
    # File >> Settings > Editor > Code Style >> (Default Options) >> Right margin (columns): 140

    # To use tab character (should we?)
    # http://stackoverflow.com/questions/24873906/tab-key-not-working-in-android-studio
    # File >> Settings > Editor > Code Style > C/C++ > TAB:{Tabs and Indents} > [x] Use tab character && [x] Use smart tabs

    # Indentation (~2 ways): 
    # (1) File >> Settings > Editor > Code Style >> (Indents Detection) >> "[x] Detect and use existing file indents for editing"
    # (2) Edit >> Convert Indents

    # To change Android Lint settings:
    # File >> Settings > Editor > Inspections >>  "Android > Lint >" ... 

    #========================================
    # Extras
    #========================================
    # Disable unused Webbrowsers:
    # File >> Settings > Tools > Web Browsers >> [uncheck all but Chrome and Firefox]

    # The debug and unsigned release APK will be found under:
    #   ./app/build/outputs/apk/app-debug.apk
    #   ./app/build/outputs/apk/app-release-unsigned.apk

    # ^^ This need to be set in Androiud-Studio settings: 
    # (a) Project Settings >> Modules > snoopsnitch > "Paths":
    # (b) Project Settings >> Project > "Project compiler output:"
    # Output path:      /production/snoopsnitch
    # Test output path:     /test/snoopsnitch
    # Change to: ??
    #       ./app/build/outputs/apk/

Next steps
----------

-   Go to section on how to install and compile SnoopSnitch on Android-Studio

---

EOF

