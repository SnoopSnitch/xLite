/*
 * Generated by asn1c-0.9.24 (http://lionet.info/asn1c)
 * From ASN.1 module "InformationElements"
 * 	found in "../asn/InformationElements.asn"
 * 	`asn1c -fcompound-names -fnative-types`
 */

#ifndef	_MBMS_MICHConfigurationInfo_v770ext_H_
#define	_MBMS_MICHConfigurationInfo_v770ext_H_


#include <asn_application.h>

/* Including external dependencies */
#include "MidambleShiftAndBurstType-r7.h"
#include <constr_SEQUENCE.h>
#include "MidambleShiftAndBurstType-VHCR.h"
#include "TimeSlotLCR-ext.h"
#include <constr_CHOICE.h>

#ifdef __cplusplus
extern "C" {
#endif

/* Dependencies */
typedef enum MBMS_MICHConfigurationInfo_v770ext__mode_PR {
	MBMS_MICHConfigurationInfo_v770ext__mode_PR_NOTHING,	/* No components present */
	MBMS_MICHConfigurationInfo_v770ext__mode_PR_tdd384,
	MBMS_MICHConfigurationInfo_v770ext__mode_PR_tdd768,
	MBMS_MICHConfigurationInfo_v770ext__mode_PR_tdd128
} MBMS_MICHConfigurationInfo_v770ext__mode_PR;

/* MBMS-MICHConfigurationInfo-v770ext */
typedef struct MBMS_MICHConfigurationInfo_v770ext {
	struct MBMS_MICHConfigurationInfo_v770ext__mode {
		MBMS_MICHConfigurationInfo_v770ext__mode_PR present;
		union MBMS_MICHConfigurationInfo_v770ext__mode_u {
			struct MBMS_MICHConfigurationInfo_v770ext__mode__tdd384 {
				MidambleShiftAndBurstType_r7_t	 midambleShiftAndBurstType;
				
				/* Context for parsing across buffer boundaries */
				asn_struct_ctx_t _asn_ctx;
			} tdd384;
			struct MBMS_MICHConfigurationInfo_v770ext__mode__tdd768 {
				MidambleShiftAndBurstType_VHCR_t	 midambleShiftAndBurstType;
				
				/* Context for parsing across buffer boundaries */
				asn_struct_ctx_t _asn_ctx;
			} tdd768;
			struct MBMS_MICHConfigurationInfo_v770ext__mode__tdd128 {
				TimeSlotLCR_ext_t	*mbsfnSpecialTimeSlot	/* OPTIONAL */;
				
				/* Context for parsing across buffer boundaries */
				asn_struct_ctx_t _asn_ctx;
			} tdd128;
		} choice;
		
		/* Context for parsing across buffer boundaries */
		asn_struct_ctx_t _asn_ctx;
	} mode;
	
	/* Context for parsing across buffer boundaries */
	asn_struct_ctx_t _asn_ctx;
} MBMS_MICHConfigurationInfo_v770ext_t;

/* Implementation */
extern asn_TYPE_descriptor_t asn_DEF_MBMS_MICHConfigurationInfo_v770ext;

#ifdef __cplusplus
}
#endif

#endif	/* _MBMS_MICHConfigurationInfo_v770ext_H_ */
#include <asn_internal.h>
