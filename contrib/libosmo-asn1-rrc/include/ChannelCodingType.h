/*
 * Generated by asn1c-0.9.24 (http://lionet.info/asn1c)
 * From ASN.1 module "InformationElements"
 * 	found in "../asn/InformationElements.asn"
 * 	`asn1c -fcompound-names -fnative-types`
 */

#ifndef	_ChannelCodingType_H_
#define	_ChannelCodingType_H_


#include <asn_application.h>

/* Including external dependencies */
#include <NULL.h>
#include "CodingRate.h"
#include <constr_CHOICE.h>

#ifdef __cplusplus
extern "C" {
#endif

/* Dependencies */
typedef enum ChannelCodingType_PR {
	ChannelCodingType_PR_NOTHING,	/* No components present */
	ChannelCodingType_PR_noCoding,
	ChannelCodingType_PR_convolutional,
	ChannelCodingType_PR_turbo
} ChannelCodingType_PR;

/* ChannelCodingType */
typedef struct ChannelCodingType {
	ChannelCodingType_PR present;
	union ChannelCodingType_u {
		NULL_t	 noCoding;
		CodingRate_t	 convolutional;
		NULL_t	 turbo;
	} choice;
	
	/* Context for parsing across buffer boundaries */
	asn_struct_ctx_t _asn_ctx;
} ChannelCodingType_t;

/* Implementation */
extern asn_TYPE_descriptor_t asn_DEF_ChannelCodingType;

#ifdef __cplusplus
}
#endif

#endif	/* _ChannelCodingType_H_ */
#include <asn_internal.h>
