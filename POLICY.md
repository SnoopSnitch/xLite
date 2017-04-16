
## Privacy Policy ##

![Privacy image](http://www.smilemultimedia.com/sites/default/files/styles/home_slider/public/page-banner/privacy_policy.jpg?itok=PCYhFx9H)

* Last Update: `2017-04-16`


### Introduction

This document is the privacy policy for **SnoopSnitch** and **SnoopSnitch XLite** (hereafter simply referred to as "SNSN" or "app") Android security applications. By design, the **primary** concern of SNSN, is to help mobile users maintain their privacy by detecting network originated attacks. Our **secondary** concern is to provide a fact-based incentive to Mobile Network Operators to better and improve the security of their networks. In doing this we also respect your privacy concerns from using the app itself. Here we specify what kind of information SNSN is collecting while in operation, and how this information is treated.

### Privacy Summary 

By default, we do not collect or transmit any personally identifiable information. However, the user may choose to upload detailed event logs in either clear-text or encrypted form. These logs may contain some personally identifiable information, such as phone numbers, GPS locations, IMEI, IMSI or other mobile network data, even though we have implemented methods to remove such information. We do not have any advertisements or any other 3rd-party plug-ins that do so. We do not share any of the uploaded data with anyone, except the security researchers at Security Research Labs (SRLabs), Berlin.

### Google Privacy Ambiguity

Even though SNSN do not collect any personalized data, we cannot guarantee that Google does not. As SNSN is provided by Google on their Google Play store, we do not know what kind of information that is collected from this acquisition and subsequent app installation when using their services. We do know that they provide our Play Store developer account with detailed hardware information about the devices that the app has been installed on. This also include some crash and error logs. For example, ANR ("Application Not Responding") and FC ("Forced Closed") logs as provided by the Android Operating System. If you do not agree with that policy, we suggest you to compile and install the app by yourself from our GitHub repository sources and disable uploading of such logs in your AOS settings. 

### Logging of ANR and FC Events

If you experience an ANR or FC event while using SNSN for Android, you may be asked for permission to upload a crash report. If you agree, some information about the crash will be uploaded. This information is designed to not contain any personally identifiable information, but may include information such as the stack trace of what the program was trying to do when it crashed, as well as limited information about your phone's software (such as which version of SNSN for Android you are using) and the hardware.


### What information is collected

- Information provided directly by the user. This may include: 

    - personal data such as: phone number and email. 

- Information provided indirectly by the user. This may include: 

    - hardware details: phone model and processor information. 
    - software details: detailed AOS, Kernel and SNSN application versions.
    - GPS locations, IMEI, IMSI and other mobile network data (LAC,CID, encryption status etc.)
    - Complete radio network PCAP traces of detection events

- We may also collect other information intentionally provided to us by the user. In particular, user data processed in the framework of our services. Transfer of these data is not mandatory, but is some cases required to use the full functionality of SNSN.

- SNSN does not use cookies, but the websites linked through the text, within the app, may do so.


### Why is this data collected and how is it used?

- Providing and improving the SNSN application (UI, UX and compatibility etc.)
- Analyzing and securing mobile networks & services, worldwide.
- Provide mobile network statistics (through GSMap) that help us understand how secure various Mobile Network Operators (MNO) are.
- Provide statistics of how, where and when mobile networks are being attacked.
- Provide a warning to users when their phones and network is being attacked by IMSI catchers and user tracking by SS7 or Silent SMS.


### Information sharing

Anonymously collected analytics are kept safe on a database while personal data eventually provided by e-mail are used only for users support purposes and nothing else. No data are sold or shared with third party entities or companies.



### Application Permissions

SNSN asks for several permissions on behalf of command line tools that run from within the app. The current set of permissions that are requested are:

- ACCESS_FINE_LOCATION / ACCESS_COARSE_LOCATION: Allow you to record your location when IMSI catchers and security events are detected
- ACCESS_NETWORK_STATE: Is used to check for available network so that up or downloads can proceed
- ACCESS_SUPERUSER: To use the non API supported Qualcomm diagnosis interface to capture radio data, you need root access. See below.
- CALL_PHONE/ SEND_SMS / RECEIVE_SMS: Needed to make the test calls used to generate the network traffic to be analyzed
- GET_TASKS: Retrieve state of helper processes interacting with diagnostic interface
- INTERNET: Is used to download new data from gsmmap.org and to upload radio traces and debug logs upon user request
- READ/WRITE_EXTERNAL_STORAGE: To allow saving debug/trace logs to your SD card
- READ_PHONE_STATE: Used to detect what kind of network you are currently using (GSM,UMTS,LTE etc)
- RECEIVE_BOOT_COMPLETED: To start app automatically when phone is restarted 
- GET_TASKS: Retrieve state of helper processes interacting with diagnostic interface
- WAKE_LOCK: Stop phone from falling asleep during long-running analysis steps


### Root and Superuser access

Because SNSN is collecting data directly from the radio diagnostics interface, it require your phone to be rooted and asks for root permission using Superuser (SU) access. This is required for the app to function as the Android API does not provide enough of network details for the analysis to be performed. This permission is not a standard Android system permission and is ignored by normal Android devices. It is an informal standard developed by the Android developer community. It allows a program to indicate that it would like to acquire super-user permission. SNSN does nothing else with this permission. It simply asks for the permission in order to allow command-line tools to run as root. The "su" command is an example of a command that will use this permission.


### What are my opt-out rights?

You can easily stop all collection of information by either deleting the application, or disabling the app from the Android OS settings. You can also change the apps own settings to not upload any data and/or use any network communication. 


### Data Retention Policy

We will retain user provided data for as long as you use the application and for a reasonable time thereafter. We will retain (user approved) collected information for up to 24 months and thereafter may store it in aggregate (in backups). If you’d like us to delete user provided data that you have provided via the application, please contact us at the email below and we will respond in a reasonable time.


### Children

We do not use the application to knowingly solicit data from or market to children under the age of 13. If a parent or guardian becomes aware that his or her child has provided us with information without their consent, he or she should contact us at the email provided below and we will remove that information from our servers within a reasonable time.


### Security

We are concerned about safeguarding the confidentiality of your information. We provide electronic safeguards to protect information we process and maintain. For example, we limit access to this information to authorized persons who need to know that information in order to operate, develop or improve our application. Please be aware that, although we seek to provide reasonable security for the information we process and maintain, no security system can prevent all potential security breaches.


### Your Consent

By using the application, you are consenting to our processing of your information as set forth in this Privacy Policy now and as amended by us. 


### Changes

This Privacy Policy may be updated from time to time for any reason. We will notify you of any changes to our Privacy Policy by posting the new Privacy Policy here and informing you via the application built-in announcement feature. You are advised to consult this Privacy Policy regularly for any changes, as continued use is deemed approval of all changes.


### Contact

If you have questions or concerns regarding this policy, please contact us via email given in README.

---
EOF
