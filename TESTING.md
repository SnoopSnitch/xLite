### Release Testing

---
* Status:     **WIP** (Need urgent update)
* Date:       `2017-04-16` (OUTDATED)
---


#### Motivation


The goal of this testing approach is to ensure a release candidate (App) fulfills the main conditions for quality and functionality.

- all network anomalies are detected
- battery life is not severely affected
- no major crashes even for long uptime
- incidents are correctly uploaded and analysed in the backend server

### Requirements

#### Handsets

A representative number of test devices should be adopted and kept available in the office for release testing.
We want to cover several vendors and operating systems:

- Samsung S4 + Android 4.4.2
- LG D802 + Android 4.x
- Motorola Moto-E 1st gen + Android 4.4
- Motorola Moto-E 2nd gen + CM 12
- Sony + CM 12

#### Incident simulation

Most of the catcher detection tests need a functional BTS,
configure to either reject or accept the target mobile.
The stable (not moving) setup consists of a nanoBTS connected
via a dedicated POE LAN port to a office based VM running OpenBSC.
A second portable setup consists of the same VM ready to control
a bladeRF, calypso BTS or nanoBTS, ideally always ready for demos.

### Test cases

#### Preliminary steps


-   Stop running Snoopsnitch
-   Clean app data via settings
-   Install a default database if needed (some scenarios require long history)
    &gt; Currently starting the app with data and cache cleared suffices
-   Set 2G/3G/Auto mode as required for test
    &gt; 2G
    &gt; 3G
    &gt; 4G
-   Start Snoopsnitch
-   Set 2G/3G/Auto/Airplane mode as required (to force new transactions or active tests)


--- 

#### 1. Power usage and performance tests


For all tests: fully charge battery and then disconnect the external power source

-   Operate test devices with snoopsnitch running until the battery is drained (time comparison)
    carry-around
-   Perform uploading commands (test connection and data uploading)
    -   Upload suspicious data
    -   Upload

-\* Operate test devices with snoopsnitch running until the battery is drained (time comparison)
carry-around-

-   Perform uploading commands (test connection and data export/import)

#### 2. New functionality tests


-   Release specific tests, regression tests
-   Replay top 5 fixed/reported crashes if they were addressed in the release

#### 3. Catcher detection tests


-   Simulate network anomalies in the lab
    -   Catcher type 1: simple reject after ID requests
    -   Catcher type 2: accept LUR with no cipher/no auth
    -   Silent call (OpenBSC supports silent SDCCH and TCH)
    -   Silent SMS (OpenBSC+Kannel)
    -   Binary SMS (OpenBSC+Kannel)
    -   Null paging via SS7 PSI (on real network)

<!-- -->

-   Move along potential IMSI catcher sites in Berlin, by bicycle or public transport

#### 4. Autonomous mode


-   Operate a stationary mobile probe for a week
-   Operate a moving mobile probe for a week (delivery truck, taxi, bicycle)

---

EOF
