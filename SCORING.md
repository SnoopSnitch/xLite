IMSI Catcher Score

The IMSI catcher heuristic calculates an overall score (sum) out of a number of sub-scores. If this overall score exceeds a specified maximum value, an alarm is raised in the app. In the detailed event view the sub-scores are displayed together with the overall score to allow the user to derive more details from an incident. The following sections outline all sub-scores used in SnoopSnitch.



A1 - Different LAC/CID for the same ARFCN (Removed)

The LAC/CID recently seen on a frequency suddenly changed.
Rationale

An IMSI catcher may use the frequency of an existing base station that has a weak signal in the area the catcher operates. To force the mobile into a location update, a LAC different from all neighboring stations is chosen by the IMSI catcher.

The change of the LAC on a given frequency can be detected. Note, that for resource efficiency, GSM reuses frequency in different geographic areas very frequently. Hence, changes of the LAC/CID for a given frequency are specific to the geographic location of the cell.

As associating an frequency/LAC/CID combination with a precise location reliably can be challenging, a simpler approach is taken. The frequency and LAC/CID is recorded together with a time stamp. If the same frequency is used with a different LAC/CID within a certain time frame, this is considered an attack.
False Positives

Multiple base stations operating on the same frequency may be receivable in the same locations, e.g. in elevated places. Furthermore, an operator may reconfigure the cell to use a different LAC/CID.
A2 - Inconsistent LAC

The LAC of the current base station differs from the LAC of many neighboring cells.
Rationale

A mobile will only perform a normal location update when changing to a different area, i.e. a base station with a different LAC. An IMSI catcher needs to force a location update to be able to interact with the phone and derive the desired information. Therefore, it must span a cell with a LAC different to all neighboring cells, but with a much better signal strength than the other cells. For an IMSI catcher announcing realistic neighboring cells, this difference between the LAC of the serving cell and all neighboring cell can be detected.
False Positives

Femto cells may or may not announce a LAC different from all their neighboring cells. Their may be other special situations, like in-house cells where this is the case.
A4 - Same LAC/CID on different ARFCNs

A cell is received on different ARFCNs within a short time.
Rationale

To avoid leaving traces of a new, non-existent cell, an IMSI catcher may choose to reuse the cell ID and LAC of an existing cell in an area, but using a different frequency. The IMSI catcher must have a location area different from the current serving cell, such that the MS performs a location update once it close enough. The use of the cell ID on different frequencies may be detected by the analysis if system information of the original cell was received earlier.
False Positives

A cell may be reconfigured to use a different frequency, but this should happen very rarely.
A5 - Lonesome location area

A cell is the only cell observed in its location area
Rationale

A mobile will only perform a normal location update when changing to a different area, i.e. a base station with a different LAC. An IMSI catcher needs to force a location update to be able to interact with the phone and derive the desired information. Therefore, it must span a cell with a LAC different to all neighboring cells, but with a much better signal strength than the other cells. An IMSI catcher creating a new LAC for its fake cell will be the only cell operating in this location area. The lack of system information for other cells of this location area can be detected.
False Positives

When traveling at high speeds or in areas with poor coverage the mobile may record system information for only a single cell of location area.
"Unexpected neighbors also do happen often with subway cells. In some cases the BTS is in a central place, and the RF heads are far away, connected with optical fiber. In these cases cell IDs and LACs are carried over many kilometers into places where they usually do not belong, and often not all neighbors are set correctly, due to restrictions in neighbor list size. I can imagine that such circumstances could trigger a false positive." (source)
K1 - No neighboring cells

The serving cell is not advertising any neighbor cells.
Rationale

Active IMSI catchers which record voice and data will try to prevent an MS from transitioning back to a regular cell. For that reason an IMSI catcher might announce no neighboring cells such that the MS will use the cell spawned by the IMSI catcher until its signal level is too low. Note, that less suspicious options exist for an IMSI catcher to solve that problem. It could announce a normal amount of neighboring cells, but choose frequency not used by any base station in that area.
False Positives

Only few regular situations, like small islands with only a single call, are thinkable where no neighboring cells may be announced.
K2 - High cell reselect offset

The cell reselect offset is high.
Rationale

The goal of announcing a large CELL RESELECT OFFSET is similar to K1. The CELL RESELECT OFFSET is used to calculate the reselection criterion C2. Together with the path loss criterion parameter C1 it is ”used to ensure that the MS is camped on the cell with which it has the highest probability of successful communication on uplink and downlink.” [GSM 05.08, 6.4] In cases where an IMSI catcher e.g. announces real, available neighboring cells, it might choose to announce a CELL RESELECTION OFFSET that would require the signal quality of any neighboring cell to be impossibly good to consider it as an alternative to the cell spawned by the IMSI catcher. This makes the MS camp on that cell until the desired information has been collected.
False Positives

Networks may announce a high CELL RESELECTION OFFSET in areas with poor coverage.
C1 - Encryption Downgrade

After using an encryption algorithm with a cell previously, encryption got downgraded to a weaker algorithm.
Rationale

Encryption may be disabled completely (A5/0) or limited to a deliberately weakened algorithm (A5/2) due to legal restrictions in some countries. Furthermore, an operator may not yet have the technical capabilities to use the more secure A5/3 algorithm in all of his cells in favor of the broken A5/1 one.

If a weaker encryption algorithm is observed for the same cell at a later time, this may be an indication for an attacker forcing the MS into an encryption mode that she can attack (more easily). The most likely situation for an active IMSI catcher is a downgrade to A5/0, i.e. null encryption. A downgrade from A5/3 to A5/1 is expected to be rare, as for an active attacker it is much easier to disable encryption all together instead of cracking A5/1.

This metric is relevant and applicable only when an existing LAC/CID is reused for an IMSI catcher. This is not very common, though, as IMSI catchers usually create a new cell with different LAC to force the MS into a location update. However, if an encryption downgrade happens, this is a very strong sign for an attack.
False Positives

An operator may mis-configure some of its base stations to use a weaker encryption algorithm. In rare cases A5/0 transaction are observed, most likely caused by hardware faults.
C2 - Delayed CIPHER MODE COMPLETE ack.

In the CIPHER MODE COMMAND message from the network no IMEISV was requested and subsequent CIPHER MODE COMPLETE messages are acknowledged with significant delay.
Rationale

The absence of an IMEISV makes the CIPHER MODE COMPLETE message – the response to the CIPHER MODE COMMAND message – fully predictable. This enables an IMSI catcher to mount a known-plaintext attack against the crypto algorithm. Consequently, IMSI catchers may
direct the mobile to omit the IMSISV in its response.

When an active IMSI catcher mounts an attack against the A5/1 algorithm it takes a significant amount of time to break the encryption (up to a couple of seconds). During this period, the CIPHER MODE COMPLETE message is retransmitted by the mobile until an acknowledgment is received, assuming a previous message has not reached the base station. Both can be detected, the increase delay and a higher retransmission count.
False Positives

Lost CIPHER MODE COMPLETE packets due to bad network reception.

Not requesting the IMEISV is normal behavior in some networks, as the IMEI may be retrieved through a subsequent IDENTITY REQUEST at any later time.
C3 - CIPHER MODE CMD msg. without IMEISV (removed)

IMEISV is not requested during the encryption handshake.

Rationale

The IMEISV is different for each phone. When it is not requested within the encryption handshake, the encryption handshake becomes fully predictable. This simplifies cracking of the encryption keys.

Notes

The metric has been moved into C2, because the fact that no IMEISV is requested is not conclusive if no other parameters are taken into account.
C4 - ID requests during location update

The network queries identity information (like IMSI and IMEI) after a LOCATION UPDATE REQUEST and then rejects that request.
Rationale

This is a fingerprint of an IMSI catcher in identification mode, which ends the transaction as soon as the identity of the MS has been recorded. In a sound network setup, one can assume that an identity request only happens when encryption is in place, i.e. after a CIPHER MODE COMPLETE.

A mobile has to perform a cell reselection when a LOCATION UPDATE REQUEST has been rejected with a ”location area not allowed”. This may be used by the IMSI catcher to force the MS so select a different cell after the desired information was collected.
False Positives

Unknown.

C5 - Cipher setting out of average

A cell does not offer encryption in a network that normally encrypts

Rationale

An IMSI-Catcher usually is unaware of the subscriber keys and because of this, not able to offer encryption of any sort. A cell with encryption turned off, in a network that is known to encrypt, is suspicious.
False Positives

Unknown.

T1 - Low registration timer

The initial value of the registration timer T3212 is low.

Rationale

The registration timer T3212 controls the interval a mobile performs a periodic LOCATION UPDATE, i.e. one that is performed regularly when the location area of the MS does not change. The T3212 of a mobile is initialized from the time-out value in the Control Channel Description which is broadcast as part of SI3 on the serving cells BCCH. It can be set at a granularity of decihours (6 minutes) and supports a maximum value of 25.5 hours (255 decihours). The value of 0 disables periodic location updates completely.

An IMSI catcher might broadcast an initialization value for the registration timer that causes the phone to updates its location with the catcher very often, e.g. every 6 minutes. This allows for rather precise presence tracking.

False Positives

Common value for T3212 are 1-4 hours. Operators may lower T3212 to e.g. 30 minutes to reallocate subscribers from one MSC/VLR to another for maintenance or upgrade.
T3 - Paging without transaction

The MS is paged without entering a transaction.

Rationale

Paging by IMSI and subsequently releasing the transaction without SMS or call data being transmitted may be a pattern of a tracking IMSI catcher trying to detect whether a particular user is in the area of the catcher.

False Positives

A similar pattern occurs when a MS is called, but the caller releases the call quickly enough such that the MS is paged, but no ALARM is signaled.
T4 - Orphaned traffic channel

A traffic channel is assigned, but no call control state is entered or text message received for a long time.

Rationale

When a traffic channel is assigned, the MS is constantly sending (idle messages) until the channel is released. This constant transmission can be exploited by an IMSI catcher to perform a more accurate localization of the MS.
False Positives

Unknown.

R1 - Inconsistent neighbor list

The neighbor list of most neighboring cells does not contain serving cell.

Rationale

In a regular network one can assume that the majority of neighboring cells of the current serving cell also announce the current cell as one of their neighboring stations.

A strategy of an IMSI catcher to prevent an MS that was already connected to register again, is to send a neighbor list over the BCCH that contains only neighboring cells which do not have the frequency of the IMSI catcher in their own neighbor list [EP20000107879, 0027]. When the MS selects one of those neighboring cells, it will not consider the IMSI catcher at least for the next cell reselection.
False Positives

Unknown.

R2 - High number of paging groups

The cell is configured in a way that maximizes the number of paging groups.

Rationale

When an IMSI catcher uses the technique described in [EP20000107879, 0027] to force the mobile back into a regular network, invalid data is sent on the PCH of the respective paging group of that MS until the MS performs a cell reselection. This has the side effect that any other MS on the same paging group also selects a different cell. Hence, the cell spawned by an IMSI catcher using this technique will try to maximize the number of paging groups in that cell to increase the granularity at which mobiles can be disconnected.
False Positives

Unknown.

F1 - Few paging requests (removed)

Rate of paging requests suddenly drops.
Rationale

A cell phone network that is in normal operation is usually used by many subscribers at the same time. This causes a constant load of paging requests (broadcast traffic). An IMSI-Catcher is a small network for itself. It will only serve its interception target, but not anybody else. This means that the rate of paging requests inside the catcher cell will be lower by orders of magnitude.

False Positives

The metric was discarded because the required information could not be gathered reliably.












