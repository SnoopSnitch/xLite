/*
 * Generated by asn1c-0.9.24 (http://lionet.info/asn1c)
 * From ASN.1 module "InformationElements"
 * 	found in "../asn/InformationElements.asn"
 * 	`asn1c -fcompound-names -fnative-types`
 */

#ifndef	_PollWindow_H_
#define	_PollWindow_H_


#include <asn_application.h>

/* Including external dependencies */
#include <NativeEnumerated.h>

#ifdef __cplusplus
extern "C" {
#endif

/* Dependencies */
typedef enum PollWindow {
	PollWindow_pw50	= 0,
	PollWindow_pw60	= 1,
	PollWindow_pw70	= 2,
	PollWindow_pw80	= 3,
	PollWindow_pw85	= 4,
	PollWindow_pw90	= 5,
	PollWindow_pw95	= 6,
	PollWindow_pw99	= 7
} e_PollWindow;

/* PollWindow */
typedef long	 PollWindow_t;

/* Implementation */
extern asn_TYPE_descriptor_t asn_DEF_PollWindow;
asn_struct_free_f PollWindow_free;
asn_struct_print_f PollWindow_print;
asn_constr_check_f PollWindow_constraint;
ber_type_decoder_f PollWindow_decode_ber;
der_type_encoder_f PollWindow_encode_der;
xer_type_decoder_f PollWindow_decode_xer;
xer_type_encoder_f PollWindow_encode_xer;
per_type_decoder_f PollWindow_decode_uper;
per_type_encoder_f PollWindow_encode_uper;

#ifdef __cplusplus
}
#endif

#endif	/* _PollWindow_H_ */
#include <asn_internal.h>