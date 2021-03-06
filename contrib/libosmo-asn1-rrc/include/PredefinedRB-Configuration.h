/*
 * Generated by asn1c-0.9.24 (http://lionet.info/asn1c)
 * From ASN.1 module "InformationElements"
 * 	found in "../asn/InformationElements.asn"
 * 	`asn1c -fcompound-names -fnative-types`
 */

#ifndef	_PredefinedRB_Configuration_H_
#define	_PredefinedRB_Configuration_H_


#include <asn_application.h>

/* Including external dependencies */
#include "Re-EstablishmentTimer.h"
#include "SRB-InformationSetupList.h"
#include "RB-InformationSetupList.h"
#include <constr_SEQUENCE.h>

#ifdef __cplusplus
extern "C" {
#endif

/* PredefinedRB-Configuration */
typedef struct PredefinedRB_Configuration {
	Re_EstablishmentTimer_t	 re_EstablishmentTimer;
	SRB_InformationSetupList_t	 srb_InformationList;
	RB_InformationSetupList_t	 rb_InformationList;
	
	/* Context for parsing across buffer boundaries */
	asn_struct_ctx_t _asn_ctx;
} PredefinedRB_Configuration_t;

/* Implementation */
extern asn_TYPE_descriptor_t asn_DEF_PredefinedRB_Configuration;

#ifdef __cplusplus
}
#endif

#endif	/* _PredefinedRB_Configuration_H_ */
#include <asn_internal.h>
