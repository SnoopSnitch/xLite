/*
 * Generated by asn1c-0.9.24 (http://lionet.info/asn1c)
 * From ASN.1 module "PDU-definitions"
 * 	found in "../asn/PDU-definitions.asn"
 * 	`asn1c -fcompound-names -fnative-types`
 */

#ifndef	_URAUpdateConfirm_r7_IEs_H_
#define	_URAUpdateConfirm_r7_IEs_H_


#include <asn_application.h>

/* Including external dependencies */
#include "C-RNTI.h"
#include "RRC-StateIndicator.h"
#include "URA-Identity.h"
#include <BOOLEAN.h>
#include <constr_SEQUENCE.h>

#ifdef __cplusplus
extern "C" {
#endif

/* Forward declarations */
struct IntegrityProtectionModeInfo_r7;
struct CipheringModeInfo_r7;
struct U_RNTI;
struct UTRAN_DRX_CycleLengthCoefficient_r7;
struct CN_InformationInfo;
struct PLMN_Identity;
struct DL_CounterSynchronisationInfo_r5;

/* URAUpdateConfirm-r7-IEs */
typedef struct URAUpdateConfirm_r7_IEs {
	struct IntegrityProtectionModeInfo_r7	*integrityProtectionModeInfo	/* OPTIONAL */;
	struct CipheringModeInfo_r7	*cipheringModeInfo	/* OPTIONAL */;
	struct U_RNTI	*new_U_RNTI	/* OPTIONAL */;
	C_RNTI_t	*new_C_RNTI	/* OPTIONAL */;
	RRC_StateIndicator_t	 rrc_StateIndicator;
	struct UTRAN_DRX_CycleLengthCoefficient_r7	*utran_DRX_CycleLengthCoeff	/* OPTIONAL */;
	struct CN_InformationInfo	*cn_InformationInfo	/* OPTIONAL */;
	struct PLMN_Identity	*primary_plmn_Identity	/* OPTIONAL */;
	URA_Identity_t	*ura_Identity	/* OPTIONAL */;
	BOOLEAN_t	*supportForChangeOfUE_Capability	/* OPTIONAL */;
	struct DL_CounterSynchronisationInfo_r5	*dl_CounterSynchronisationInfo	/* OPTIONAL */;
	
	/* Context for parsing across buffer boundaries */
	asn_struct_ctx_t _asn_ctx;
} URAUpdateConfirm_r7_IEs_t;

/* Implementation */
extern asn_TYPE_descriptor_t asn_DEF_URAUpdateConfirm_r7_IEs;

#ifdef __cplusplus
}
#endif

/* Referred external types */
#include "IntegrityProtectionModeInfo-r7.h"
#include "CipheringModeInfo-r7.h"
#include "U-RNTI.h"
#include "UTRAN-DRX-CycleLengthCoefficient-r7.h"
#include "CN-InformationInfo.h"
#include "PLMN-Identity.h"
#include "DL-CounterSynchronisationInfo-r5.h"

#endif	/* _URAUpdateConfirm_r7_IEs_H_ */
#include <asn_internal.h>