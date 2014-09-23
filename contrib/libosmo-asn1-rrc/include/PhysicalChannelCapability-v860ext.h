/*
 * Generated by asn1c-0.9.24 (http://lionet.info/asn1c)
 * From ASN.1 module "InformationElements"
 * 	found in "../asn/InformationElements.asn"
 * 	`asn1c -fcompound-names -fnative-types`
 */

#ifndef	_PhysicalChannelCapability_v860ext_H_
#define	_PhysicalChannelCapability_v860ext_H_


#include <asn_application.h>

/* Including external dependencies */
#include "DL-PhysChCapabilityFDD-v860ext.h"
#include <constr_SEQUENCE.h>
#include "DL-PhysChCapabilityTDD-128-v860ext.h"

#ifdef __cplusplus
extern "C" {
#endif

/* PhysicalChannelCapability-v860ext */
typedef struct PhysicalChannelCapability_v860ext {
	struct PhysicalChannelCapability_v860ext__fddPhysChCapability {
		DL_PhysChCapabilityFDD_v860ext_t	 downlinkPhysChCapability;
		
		/* Context for parsing across buffer boundaries */
		asn_struct_ctx_t _asn_ctx;
	} *fddPhysChCapability;
	struct PhysicalChannelCapability_v860ext__tddPhysChCapability_128 {
		DL_PhysChCapabilityTDD_128_v860ext_t	 downlinkPhysChCapability;
		
		/* Context for parsing across buffer boundaries */
		asn_struct_ctx_t _asn_ctx;
	} *tddPhysChCapability_128;
	
	/* Context for parsing across buffer boundaries */
	asn_struct_ctx_t _asn_ctx;
} PhysicalChannelCapability_v860ext_t;

/* Implementation */
extern asn_TYPE_descriptor_t asn_DEF_PhysicalChannelCapability_v860ext;

#ifdef __cplusplus
}
#endif

#endif	/* _PhysicalChannelCapability_v860ext_H_ */
#include <asn_internal.h>