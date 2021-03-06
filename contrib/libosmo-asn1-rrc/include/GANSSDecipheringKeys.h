/*
 * Generated by asn1c-0.9.24 (http://lionet.info/asn1c)
 * From ASN.1 module "InformationElements"
 * 	found in "../asn/InformationElements.asn"
 * 	`asn1c -fcompound-names -fnative-types`
 */

#ifndef	_GANSSDecipheringKeys_H_
#define	_GANSSDecipheringKeys_H_


#include <asn_application.h>

/* Including external dependencies */
#include <BIT_STRING.h>
#include <constr_SEQUENCE.h>

#ifdef __cplusplus
extern "C" {
#endif

/* GANSSDecipheringKeys */
typedef struct GANSSDecipheringKeys {
	BIT_STRING_t	 currentDecipheringKey;
	BIT_STRING_t	 nextDecipheringKey;
	
	/* Context for parsing across buffer boundaries */
	asn_struct_ctx_t _asn_ctx;
} GANSSDecipheringKeys_t;

/* Implementation */
extern asn_TYPE_descriptor_t asn_DEF_GANSSDecipheringKeys;

#ifdef __cplusplus
}
#endif

#endif	/* _GANSSDecipheringKeys_H_ */
#include <asn_internal.h>
