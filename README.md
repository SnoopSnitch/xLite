
### README ###

---
* Status:     **WIP**
* Date:       `2017-06-15`
---


### SnoopSnitch xLite ###

This is the SnoopSnitch xLite source-code repository. *SnoopSnitch xLite* is a
light weight and not funded, extended support version of the original 
SnoopSnitch from Security Research Labs.

SnoopSnitch collects and analyzes mobile radio data to make you aware of your
mobile network security and to warn you about threats like fake base stations
(IMSI catchers), user tracking and over-the-air updates.

---

### License ###


   * Copyright (C) 2017       GPLv3  5GSD
   * Copyright (C) 2014-2016  GPLv3  Security Research Labs

   This program is free software; you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation; either version 3 of the License, or
   (at your option) any later version. See [COPYING](https://github.com/SnoopSnitch/xLite/blob/master/COPYING) for details.



### Resources ###

Useful GitHub [Markdown Cheatsheet](https://github.com/adam-p/markdown-here/wiki/Markdown-Cheatsheet)

TBA


#### THIS work: ####

* Project Website:       https://github.com/SnoopSnitch/xLite/
* Public Git repository: https://github.com/SnoopSnitch/xLite.git
* Email:                 emigenix@gmail.com
* PGP:                   950B 7745 565A 4A48 1D66  D699 4923 6E35 27D8 F4E6


#### Original work: ####

* Project Website:       https://opensource.srlabs.de/projects/snoopsnitch
* Public Git repository: https://opensource.srlabs.de/git/snoopsnitch.git
* Mailing list:          https://lists.srlabs.de/cgi-bin/mailman/listinfo/gsmmap
* Email:                 snoopsnitch@srlabs.de 
* PGP:                   9728 A7F9 D457 1FBB 746F  5381 D52C AC10 634A 9561


For all technical questions concerning the detection mechnaism and its 
detailed functionality, please refer to the SRLabs Wiki and FAQ pages.
If that is not sufficient, then please use the gsmmap mailing list at SRLabs.
For other security affairs, please send private emails to SRLabs. 


For development and maintenance questions of SnoopSnitch xLite,
please email emigenix@gmail.com. Please understand and resepct that 
this is an independent and forked venture that is likely to change 
some of the non-essential functionality and user interface behaviour 
of original the app. 

---

### How to build and install SnoopSnitch xLite ###

 1. [Installation of Android Studio](https://github.com/SnoopSnitch/xLite/blob/master/INSTALL_AS.md)
 2. [How to compile parser dependencies](https://github.com/SnoopSnitch/xLite/blob/master/COMPILE.md)
 3. Building and Signing the app (WIP)


### Building from source ###


Please please consult the Android documentation on how to set up the tools and
perform a release build.


[1]: https://developer.android.com/sdk/ 
[2]: https://developer.android.com/tools/sdk/ndk/ 


---

### app permissions ###

The following permissions are required to run SnoopSnitch:

| Permission               | Our Usage |
|:------------------------ |:--------- |
| `ACCESS_SUPERUSER`       | Open Qualcomm diagnosis interface to capture radio data |
| `CALL_PHONE,`            |  |
| `READ_PHONE_STATE,`      |  |
| `SEND_SMS,`              |  |
| `RECEIVE_SMS`            | Generate mobile network traffic recorded in active tests |
| `GET_TASKS`              | Retrieve state of helper processes interacting with diagnosis interface |
| `WAKE_LOCK`              | Acquire CPU for long-running analysis steps |
| `ACCESS_FINE_LOCATION,`  |  |
| `ACCESS_COARSE_LOCATION` | Record location of IMSI catchers and security events if configured |
| `INTERNET`               | Download new data from gsmmap.org, upload radio traces and debug logs upon request |
| `ACCESS_NETWORK_STATE`   | Postpone uploads until network is available |


In addition, the app requires root privileges, which are only used to access 
the */dev/diag* interface from which the baseband netwrok information is read.

---

### Known Bugs ###

For the most recent list of bugs, please refer to the currently [open GitHb issues](https://github.com/SnoopSnitch/xLite/issues).

For technical bugs, and limitations please refer to the GitHub Wiki article "Bugs and Limitations".



----

EOF

