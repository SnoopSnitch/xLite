/*
 * Generated by asn1c-0.9.24 (http://lionet.info/asn1c)
 * From ASN.1 module "InformationElements"
 * 	found in "../asn/InformationElements.asn"
 * 	`asn1c -fcompound-names -fnative-types`
 */

#ifndef	_UE_DTX_Cycle2InactivityThreshold_H_
#define	_UE_DTX_Cycle2InactivityThreshold_H_


#include <asn_application.h>

/* Including external dependencies */
#include <NativeEnumerated.h>

#ifdef __cplusplus
extern "C" {
#endif

/* Dependencies */
typedef enum UE_DTX_Cycle2InactivityThreshold {
	UE_DTX_Cycle2InactivityThreshold_e_dch_tti_1	= 0,
	UE_DTX_Cycle2InactivityThreshold_e_dch_tti_4	= 1,
	UE_DTX_Cycle2InactivityThreshold_e_dch_tti_8	= 2,
	UE_DTX_Cycle2InactivityThreshold_e_dch_tti_16	= 3,
	UE_DTX_Cycle2InactivityThreshold_e_dch_tti_32	= 4,
	UE_DTX_Cycle2InactivityThreshold_e_dch_tti_64	= 5,
	UE_DTX_Cycle2InactivityThreshold_e_dch_tti_128	= 6,
	UE_DTX_Cycle2InactivityThreshold_e_dch_tti_256	= 7,
	UE_DTX_Cycle2InactivityThreshold_spare8	= 8,
	UE_DTX_Cycle2InactivityThreshold_spare7	= 9,
	UE_DTX_Cycle2InactivityThreshold_spare6	= 10,
	UE_DTX_Cycle2InactivityThreshold_spare5	= 11,
	UE_DTX_Cycle2InactivityThreshold_spare4	= 12,
	UE_DTX_Cycle2InactivityThreshold_spare3	= 13,
	UE_DTX_Cycle2InactivityThreshold_spare2	= 14,
	UE_DTX_Cycle2InactivityThreshold_spare1	= 15
} e_UE_DTX_Cycle2InactivityThreshold;

/* UE-DTX-Cycle2InactivityThreshold */
typedef long	 UE_DTX_Cycle2InactivityThreshold_t;

/* Implementation */
extern asn_TYPE_descriptor_t asn_DEF_UE_DTX_Cycle2InactivityThreshold;
asn_struct_free_f UE_DTX_Cycle2InactivityThreshold_free;
asn_struct_print_f UE_DTX_Cycle2InactivityThreshold_print;
asn_constr_check_f UE_DTX_Cycle2InactivityThreshold_constraint;
ber_type_decoder_f UE_DTX_Cycle2InactivityThreshold_decode_ber;
der_type_encoder_f UE_DTX_Cycle2InactivityThreshold_encode_der;
xer_type_decoder_f UE_DTX_Cycle2InactivityThreshold_decode_xer;
xer_type_encoder_f UE_DTX_Cycle2InactivityThreshold_encode_xer;
per_type_decoder_f UE_DTX_Cycle2InactivityThreshold_decode_uper;
per_type_encoder_f UE_DTX_Cycle2InactivityThreshold_encode_uper;

#ifdef __cplusplus
}
#endif

#endif	/* _UE_DTX_Cycle2InactivityThreshold_H_ */
#include <asn_internal.h>
