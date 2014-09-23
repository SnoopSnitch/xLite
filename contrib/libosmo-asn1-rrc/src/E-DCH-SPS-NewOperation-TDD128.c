/*
 * Generated by asn1c-0.9.24 (http://lionet.info/asn1c)
 * From ASN.1 module "InformationElements"
 * 	found in "../asn/InformationElements.asn"
 * 	`asn1c -fcompound-names -fnative-types`
 */

#include "E-DCH-SPS-NewOperation-TDD128.h"

static int
memb_n_E_UCCH_constraint_4(asn_TYPE_descriptor_t *td, const void *sptr,
			asn_app_constraint_failed_f *ctfailcb, void *app_key) {
	long value;
	
	if(!sptr) {
		_ASN_CTFAIL(app_key, td, sptr,
			"%s: value not given (%s:%d)",
			td->name, __FILE__, __LINE__);
		return -1;
	}
	
	value = *(const long *)sptr;
	
	if((value >= 1 && value <= 8)) {
		/* Constraint check succeeded */
		return 0;
	} else {
		_ASN_CTFAIL(app_key, td, sptr,
			"%s: constraint failed (%s:%d)",
			td->name, __FILE__, __LINE__);
		return -1;
	}
}

static int
memb_timeslotResourceRelatedInfo_constraint_4(asn_TYPE_descriptor_t *td, const void *sptr,
			asn_app_constraint_failed_f *ctfailcb, void *app_key) {
	const BIT_STRING_t *st = (const BIT_STRING_t *)sptr;
	size_t size;
	
	if(!sptr) {
		_ASN_CTFAIL(app_key, td, sptr,
			"%s: value not given (%s:%d)",
			td->name, __FILE__, __LINE__);
		return -1;
	}
	
	if(st->size > 0) {
		/* Size in bits */
		size = 8 * st->size - (st->bits_unused & 0x07);
	} else {
		size = 0;
	}
	
	if((size == 5)) {
		/* Constraint check succeeded */
		return 0;
	} else {
		_ASN_CTFAIL(app_key, td, sptr,
			"%s: constraint failed (%s:%d)",
			td->name, __FILE__, __LINE__);
		return -1;
	}
}

static int
memb_powerResourceRelatedInfo_constraint_4(asn_TYPE_descriptor_t *td, const void *sptr,
			asn_app_constraint_failed_f *ctfailcb, void *app_key) {
	long value;
	
	if(!sptr) {
		_ASN_CTFAIL(app_key, td, sptr,
			"%s: value not given (%s:%d)",
			td->name, __FILE__, __LINE__);
		return -1;
	}
	
	value = *(const long *)sptr;
	
	if((value >= 1 && value <= 32)) {
		/* Constraint check succeeded */
		return 0;
	} else {
		_ASN_CTFAIL(app_key, td, sptr,
			"%s: constraint failed (%s:%d)",
			td->name, __FILE__, __LINE__);
		return -1;
	}
}

static int
memb_subframeNum_constraint_4(asn_TYPE_descriptor_t *td, const void *sptr,
			asn_app_constraint_failed_f *ctfailcb, void *app_key) {
	long value;
	
	if(!sptr) {
		_ASN_CTFAIL(app_key, td, sptr,
			"%s: value not given (%s:%d)",
			td->name, __FILE__, __LINE__);
		return -1;
	}
	
	value = *(const long *)sptr;
	
	if((value >= 0 && value <= 1)) {
		/* Constraint check succeeded */
		return 0;
	} else {
		_ASN_CTFAIL(app_key, td, sptr,
			"%s: constraint failed (%s:%d)",
			td->name, __FILE__, __LINE__);
		return -1;
	}
}

static int
memb_initialTxPatternIndex_constraint_4(asn_TYPE_descriptor_t *td, const void *sptr,
			asn_app_constraint_failed_f *ctfailcb, void *app_key) {
	long value;
	
	if(!sptr) {
		_ASN_CTFAIL(app_key, td, sptr,
			"%s: value not given (%s:%d)",
			td->name, __FILE__, __LINE__);
		return -1;
	}
	
	value = *(const long *)sptr;
	
	if((value >= 0 && value <= 3)) {
		/* Constraint check succeeded */
		return 0;
	} else {
		_ASN_CTFAIL(app_key, td, sptr,
			"%s: constraint failed (%s:%d)",
			td->name, __FILE__, __LINE__);
		return -1;
	}
}

static asn_per_constraints_t asn_PER_memb_n_E_UCCH_constr_5 = {
	{ APC_CONSTRAINED,	 3,  3,  1,  8 }	/* (1..8) */,
	{ APC_UNCONSTRAINED,	-1, -1,  0,  0 },
	0, 0	/* No PER value map */
};
static asn_per_constraints_t asn_PER_memb_timeslotResourceRelatedInfo_constr_7 = {
	{ APC_UNCONSTRAINED,	-1, -1,  0,  0 },
	{ APC_CONSTRAINED,	 0,  0,  5,  5 }	/* (SIZE(5..5)) */,
	0, 0	/* No PER value map */
};
static asn_per_constraints_t asn_PER_memb_powerResourceRelatedInfo_constr_8 = {
	{ APC_CONSTRAINED,	 5,  5,  1,  32 }	/* (1..32) */,
	{ APC_UNCONSTRAINED,	-1, -1,  0,  0 },
	0, 0	/* No PER value map */
};
static asn_per_constraints_t asn_PER_memb_subframeNum_constr_10 = {
	{ APC_CONSTRAINED,	 1,  1,  0,  1 }	/* (0..1) */,
	{ APC_UNCONSTRAINED,	-1, -1,  0,  0 },
	0, 0	/* No PER value map */
};
static asn_per_constraints_t asn_PER_memb_initialTxPatternIndex_constr_11 = {
	{ APC_CONSTRAINED,	 2,  2,  0,  3 }	/* (0..3) */,
	{ APC_UNCONSTRAINED,	-1, -1,  0,  0 },
	0, 0	/* No PER value map */
};
static asn_TYPE_member_t asn_MBR_initialSPSInfoForEDCH_4[] = {
	{ ATF_NOFLAGS, 0, offsetof(struct E_DCH_SPS_NewOperation_TDD128__initialSPSInfoForEDCH, n_E_UCCH),
		(ASN_TAG_CLASS_CONTEXT | (0 << 2)),
		-1,	/* IMPLICIT tag at current level */
		&asn_DEF_NativeInteger,
		memb_n_E_UCCH_constraint_4,
		&asn_PER_memb_n_E_UCCH_constr_5,
		0,
		"n-E-UCCH"
		},
	{ ATF_NOFLAGS, 0, offsetof(struct E_DCH_SPS_NewOperation_TDD128__initialSPSInfoForEDCH, codeResourceInfo),
		(ASN_TAG_CLASS_CONTEXT | (1 << 2)),
		-1,	/* IMPLICIT tag at current level */
		&asn_DEF_UL_TS_ChannelisationCode,
		0,	/* Defer constraints checking to the member type */
		0,	/* No PER visible constraints */
		0,
		"codeResourceInfo"
		},
	{ ATF_NOFLAGS, 0, offsetof(struct E_DCH_SPS_NewOperation_TDD128__initialSPSInfoForEDCH, timeslotResourceRelatedInfo),
		(ASN_TAG_CLASS_CONTEXT | (2 << 2)),
		-1,	/* IMPLICIT tag at current level */
		&asn_DEF_BIT_STRING,
		memb_timeslotResourceRelatedInfo_constraint_4,
		&asn_PER_memb_timeslotResourceRelatedInfo_constr_7,
		0,
		"timeslotResourceRelatedInfo"
		},
	{ ATF_NOFLAGS, 0, offsetof(struct E_DCH_SPS_NewOperation_TDD128__initialSPSInfoForEDCH, powerResourceRelatedInfo),
		(ASN_TAG_CLASS_CONTEXT | (3 << 2)),
		-1,	/* IMPLICIT tag at current level */
		&asn_DEF_NativeInteger,
		memb_powerResourceRelatedInfo_constraint_4,
		&asn_PER_memb_powerResourceRelatedInfo_constr_8,
		0,
		"powerResourceRelatedInfo"
		},
	{ ATF_NOFLAGS, 0, offsetof(struct E_DCH_SPS_NewOperation_TDD128__initialSPSInfoForEDCH, activationTime),
		(ASN_TAG_CLASS_CONTEXT | (4 << 2)),
		-1,	/* IMPLICIT tag at current level */
		&asn_DEF_ActivationTime,
		0,	/* Defer constraints checking to the member type */
		0,	/* No PER visible constraints */
		0,
		"activationTime"
		},
	{ ATF_NOFLAGS, 0, offsetof(struct E_DCH_SPS_NewOperation_TDD128__initialSPSInfoForEDCH, subframeNum),
		(ASN_TAG_CLASS_CONTEXT | (5 << 2)),
		-1,	/* IMPLICIT tag at current level */
		&asn_DEF_NativeInteger,
		memb_subframeNum_constraint_4,
		&asn_PER_memb_subframeNum_constr_10,
		0,
		"subframeNum"
		},
	{ ATF_NOFLAGS, 0, offsetof(struct E_DCH_SPS_NewOperation_TDD128__initialSPSInfoForEDCH, initialTxPatternIndex),
		(ASN_TAG_CLASS_CONTEXT | (6 << 2)),
		-1,	/* IMPLICIT tag at current level */
		&asn_DEF_NativeInteger,
		memb_initialTxPatternIndex_constraint_4,
		&asn_PER_memb_initialTxPatternIndex_constr_11,
		0,
		"initialTxPatternIndex"
		},
};
static ber_tlv_tag_t asn_DEF_initialSPSInfoForEDCH_tags_4[] = {
	(ASN_TAG_CLASS_CONTEXT | (2 << 2)),
	(ASN_TAG_CLASS_UNIVERSAL | (16 << 2))
};
static asn_TYPE_tag2member_t asn_MAP_initialSPSInfoForEDCH_tag2el_4[] = {
    { (ASN_TAG_CLASS_CONTEXT | (0 << 2)), 0, 0, 0 }, /* n-E-UCCH at 8301 */
    { (ASN_TAG_CLASS_CONTEXT | (1 << 2)), 1, 0, 0 }, /* codeResourceInfo at 8302 */
    { (ASN_TAG_CLASS_CONTEXT | (2 << 2)), 2, 0, 0 }, /* timeslotResourceRelatedInfo at 8303 */
    { (ASN_TAG_CLASS_CONTEXT | (3 << 2)), 3, 0, 0 }, /* powerResourceRelatedInfo at 8304 */
    { (ASN_TAG_CLASS_CONTEXT | (4 << 2)), 4, 0, 0 }, /* activationTime at 8305 */
    { (ASN_TAG_CLASS_CONTEXT | (5 << 2)), 5, 0, 0 }, /* subframeNum at 8306 */
    { (ASN_TAG_CLASS_CONTEXT | (6 << 2)), 6, 0, 0 } /* initialTxPatternIndex at 8307 */
};
static asn_SEQUENCE_specifics_t asn_SPC_initialSPSInfoForEDCH_specs_4 = {
	sizeof(struct E_DCH_SPS_NewOperation_TDD128__initialSPSInfoForEDCH),
	offsetof(struct E_DCH_SPS_NewOperation_TDD128__initialSPSInfoForEDCH, _asn_ctx),
	asn_MAP_initialSPSInfoForEDCH_tag2el_4,
	7,	/* Count of tags in the map */
	0, 0, 0,	/* Optional elements (not needed) */
	-1,	/* Start extensions */
	-1	/* Stop extensions */
};
static /* Use -fall-defs-global to expose */
asn_TYPE_descriptor_t asn_DEF_initialSPSInfoForEDCH_4 = {
	"initialSPSInfoForEDCH",
	"initialSPSInfoForEDCH",
	SEQUENCE_free,
	SEQUENCE_print,
	SEQUENCE_constraint,
	SEQUENCE_decode_ber,
	SEQUENCE_encode_der,
	SEQUENCE_decode_xer,
	SEQUENCE_encode_xer,
	SEQUENCE_decode_uper,
	SEQUENCE_encode_uper,
	0,	/* Use generic outmost tag fetcher */
	asn_DEF_initialSPSInfoForEDCH_tags_4,
	sizeof(asn_DEF_initialSPSInfoForEDCH_tags_4)
		/sizeof(asn_DEF_initialSPSInfoForEDCH_tags_4[0]) - 1, /* 1 */
	asn_DEF_initialSPSInfoForEDCH_tags_4,	/* Same as above */
	sizeof(asn_DEF_initialSPSInfoForEDCH_tags_4)
		/sizeof(asn_DEF_initialSPSInfoForEDCH_tags_4[0]), /* 2 */
	0,	/* No PER visible constraints */
	asn_MBR_initialSPSInfoForEDCH_4,
	7,	/* Elements count */
	&asn_SPC_initialSPSInfoForEDCH_specs_4	/* Additional specs */
};

static asn_TYPE_member_t asn_MBR_E_DCH_SPS_NewOperation_TDD128_1[] = {
	{ ATF_NOFLAGS, 0, offsetof(struct E_DCH_SPS_NewOperation_TDD128, e_hich_Info),
		(ASN_TAG_CLASS_CONTEXT | (0 << 2)),
		-1,	/* IMPLICIT tag at current level */
		&asn_DEF_E_HICH_Information_For_SPS_TDD128,
		0,	/* Defer constraints checking to the member type */
		0,	/* No PER visible constraints */
		0,
		"e-hich-Info"
		},
	{ ATF_POINTER, 2, offsetof(struct E_DCH_SPS_NewOperation_TDD128, e_dch_TxPattern),
		(ASN_TAG_CLASS_CONTEXT | (1 << 2)),
		-1,	/* IMPLICIT tag at current level */
		&asn_DEF_E_DCH_TxPatternList_TDD128,
		0,	/* Defer constraints checking to the member type */
		0,	/* No PER visible constraints */
		0,
		"e-dch-TxPattern"
		},
	{ ATF_POINTER, 1, offsetof(struct E_DCH_SPS_NewOperation_TDD128, initialSPSInfoForEDCH),
		(ASN_TAG_CLASS_CONTEXT | (2 << 2)),
		0,
		&asn_DEF_initialSPSInfoForEDCH_4,
		0,	/* Defer constraints checking to the member type */
		0,	/* No PER visible constraints */
		0,
		"initialSPSInfoForEDCH"
		},
};
static int asn_MAP_E_DCH_SPS_NewOperation_TDD128_oms_1[] = { 1, 2 };
static ber_tlv_tag_t asn_DEF_E_DCH_SPS_NewOperation_TDD128_tags_1[] = {
	(ASN_TAG_CLASS_UNIVERSAL | (16 << 2))
};
static asn_TYPE_tag2member_t asn_MAP_E_DCH_SPS_NewOperation_TDD128_tag2el_1[] = {
    { (ASN_TAG_CLASS_CONTEXT | (0 << 2)), 0, 0, 0 }, /* e-hich-Info at 8298 */
    { (ASN_TAG_CLASS_CONTEXT | (1 << 2)), 1, 0, 0 }, /* e-dch-TxPattern at 8299 */
    { (ASN_TAG_CLASS_CONTEXT | (2 << 2)), 2, 0, 0 } /* initialSPSInfoForEDCH at 8301 */
};
static asn_SEQUENCE_specifics_t asn_SPC_E_DCH_SPS_NewOperation_TDD128_specs_1 = {
	sizeof(struct E_DCH_SPS_NewOperation_TDD128),
	offsetof(struct E_DCH_SPS_NewOperation_TDD128, _asn_ctx),
	asn_MAP_E_DCH_SPS_NewOperation_TDD128_tag2el_1,
	3,	/* Count of tags in the map */
	asn_MAP_E_DCH_SPS_NewOperation_TDD128_oms_1,	/* Optional members */
	2, 0,	/* Root/Additions */
	-1,	/* Start extensions */
	-1	/* Stop extensions */
};
asn_TYPE_descriptor_t asn_DEF_E_DCH_SPS_NewOperation_TDD128 = {
	"E-DCH-SPS-NewOperation-TDD128",
	"E-DCH-SPS-NewOperation-TDD128",
	SEQUENCE_free,
	SEQUENCE_print,
	SEQUENCE_constraint,
	SEQUENCE_decode_ber,
	SEQUENCE_encode_der,
	SEQUENCE_decode_xer,
	SEQUENCE_encode_xer,
	SEQUENCE_decode_uper,
	SEQUENCE_encode_uper,
	0,	/* Use generic outmost tag fetcher */
	asn_DEF_E_DCH_SPS_NewOperation_TDD128_tags_1,
	sizeof(asn_DEF_E_DCH_SPS_NewOperation_TDD128_tags_1)
		/sizeof(asn_DEF_E_DCH_SPS_NewOperation_TDD128_tags_1[0]), /* 1 */
	asn_DEF_E_DCH_SPS_NewOperation_TDD128_tags_1,	/* Same as above */
	sizeof(asn_DEF_E_DCH_SPS_NewOperation_TDD128_tags_1)
		/sizeof(asn_DEF_E_DCH_SPS_NewOperation_TDD128_tags_1[0]), /* 1 */
	0,	/* No PER visible constraints */
	asn_MBR_E_DCH_SPS_NewOperation_TDD128_1,
	3,	/* Elements count */
	&asn_SPC_E_DCH_SPS_NewOperation_TDD128_specs_1	/* Additional specs */
};
