/*
 * Generated by asn1c-0.9.24 (http://lionet.info/asn1c)
 * From ASN.1 module "InformationElements"
 * 	found in "../asn/InformationElements.asn"
 * 	`asn1c -fcompound-names -fnative-types`
 */

#include "SysInfoType19-vb50ext.h"

static int
memb_numberOfApplicableEARFCN_constraint_1(asn_TYPE_descriptor_t *td, const void *sptr,
			asn_app_constraint_failed_f *ctfailcb, void *app_key) {
	long value;
	
	if(!sptr) {
		_ASN_CTFAIL(app_key, td, sptr,
			"%s: value not given (%s:%d)",
			td->name, __FILE__, __LINE__);
		return -1;
	}
	
	value = *(const long *)sptr;
	
	if((value >= 0 && value <= 7)) {
		/* Constraint check succeeded */
		return 0;
	} else {
		_ASN_CTFAIL(app_key, td, sptr,
			"%s: constraint failed (%s:%d)",
			td->name, __FILE__, __LINE__);
		return -1;
	}
}

static asn_per_constraints_t asn_PER_memb_numberOfApplicableEARFCN_constr_4 = {
	{ APC_CONSTRAINED,	 3,  3,  0,  7 }	/* (0..7) */,
	{ APC_UNCONSTRAINED,	-1, -1,  0,  0 },
	0, 0	/* No PER value map */
};
static asn_TYPE_member_t asn_MBR_SysInfoType19_vb50ext_1[] = {
	{ ATF_POINTER, 7, offsetof(struct SysInfoType19_vb50ext, gsmTreselectionScalingFactor),
		(ASN_TAG_CLASS_CONTEXT | (0 << 2)),
		-1,	/* IMPLICIT tag at current level */
		&asn_DEF_TreselectionScalingFactor2,
		0,	/* Defer constraints checking to the member type */
		0,	/* No PER visible constraints */
		0,
		"gsmTreselectionScalingFactor"
		},
	{ ATF_POINTER, 6, offsetof(struct SysInfoType19_vb50ext, eutraTreselectionScalingFactor),
		(ASN_TAG_CLASS_CONTEXT | (1 << 2)),
		-1,	/* IMPLICIT tag at current level */
		&asn_DEF_TreselectionScalingFactor2,
		0,	/* Defer constraints checking to the member type */
		0,	/* No PER visible constraints */
		0,
		"eutraTreselectionScalingFactor"
		},
	{ ATF_POINTER, 5, offsetof(struct SysInfoType19_vb50ext, numberOfApplicableEARFCN),
		(ASN_TAG_CLASS_CONTEXT | (2 << 2)),
		-1,	/* IMPLICIT tag at current level */
		&asn_DEF_NativeInteger,
		memb_numberOfApplicableEARFCN_constraint_1,
		&asn_PER_memb_numberOfApplicableEARFCN_constr_4,
		0,
		"numberOfApplicableEARFCN"
		},
	{ ATF_POINTER, 4, offsetof(struct SysInfoType19_vb50ext, eutra_FrequencyAndPriorityInfoList_vb50ext),
		(ASN_TAG_CLASS_CONTEXT | (3 << 2)),
		-1,	/* IMPLICIT tag at current level */
		&asn_DEF_EUTRA_FrequencyAndPriorityInfoList_vb50ext,
		0,	/* Defer constraints checking to the member type */
		0,	/* No PER visible constraints */
		0,
		"eutra-FrequencyAndPriorityInfoList-vb50ext"
		},
	{ ATF_POINTER, 3, offsetof(struct SysInfoType19_vb50ext, eutra_FrequencyAndPriorityInfoExtensionList),
		(ASN_TAG_CLASS_CONTEXT | (4 << 2)),
		-1,	/* IMPLICIT tag at current level */
		&asn_DEF_EUTRA_FrequencyAndPriorityInfoExtensionList,
		0,	/* Defer constraints checking to the member type */
		0,	/* No PER visible constraints */
		0,
		"eutra-FrequencyAndPriorityInfoExtensionList"
		},
	{ ATF_POINTER, 2, offsetof(struct SysInfoType19_vb50ext, multipleEutraFrequencyInfoExtensionList),
		(ASN_TAG_CLASS_CONTEXT | (5 << 2)),
		-1,	/* IMPLICIT tag at current level */
		&asn_DEF_MultipleEUTRAFrequencyInfoExtensionList,
		0,	/* Defer constraints checking to the member type */
		0,	/* No PER visible constraints */
		0,
		"multipleEutraFrequencyInfoExtensionList"
		},
	{ ATF_POINTER, 1, offsetof(struct SysInfoType19_vb50ext, eutra_FrequencyRACHReportingInfo),
		(ASN_TAG_CLASS_CONTEXT | (6 << 2)),
		-1,	/* IMPLICIT tag at current level */
		&asn_DEF_EUTRA_FrequencyRACHReportingInfo,
		0,	/* Defer constraints checking to the member type */
		0,	/* No PER visible constraints */
		0,
		"eutra-FrequencyRACHReportingInfo"
		},
};
static int asn_MAP_SysInfoType19_vb50ext_oms_1[] = { 0, 1, 2, 3, 4, 5, 6 };
static ber_tlv_tag_t asn_DEF_SysInfoType19_vb50ext_tags_1[] = {
	(ASN_TAG_CLASS_UNIVERSAL | (16 << 2))
};
static asn_TYPE_tag2member_t asn_MAP_SysInfoType19_vb50ext_tag2el_1[] = {
    { (ASN_TAG_CLASS_CONTEXT | (0 << 2)), 0, 0, 0 }, /* gsmTreselectionScalingFactor at 21810 */
    { (ASN_TAG_CLASS_CONTEXT | (1 << 2)), 1, 0, 0 }, /* eutraTreselectionScalingFactor at 21811 */
    { (ASN_TAG_CLASS_CONTEXT | (2 << 2)), 2, 0, 0 }, /* numberOfApplicableEARFCN at 21812 */
    { (ASN_TAG_CLASS_CONTEXT | (3 << 2)), 3, 0, 0 }, /* eutra-FrequencyAndPriorityInfoList-vb50ext at 21813 */
    { (ASN_TAG_CLASS_CONTEXT | (4 << 2)), 4, 0, 0 }, /* eutra-FrequencyAndPriorityInfoExtensionList at 21814 */
    { (ASN_TAG_CLASS_CONTEXT | (5 << 2)), 5, 0, 0 }, /* multipleEutraFrequencyInfoExtensionList at 21815 */
    { (ASN_TAG_CLASS_CONTEXT | (6 << 2)), 6, 0, 0 } /* eutra-FrequencyRACHReportingInfo at 21817 */
};
static asn_SEQUENCE_specifics_t asn_SPC_SysInfoType19_vb50ext_specs_1 = {
	sizeof(struct SysInfoType19_vb50ext),
	offsetof(struct SysInfoType19_vb50ext, _asn_ctx),
	asn_MAP_SysInfoType19_vb50ext_tag2el_1,
	7,	/* Count of tags in the map */
	asn_MAP_SysInfoType19_vb50ext_oms_1,	/* Optional members */
	7, 0,	/* Root/Additions */
	-1,	/* Start extensions */
	-1	/* Stop extensions */
};
asn_TYPE_descriptor_t asn_DEF_SysInfoType19_vb50ext = {
	"SysInfoType19-vb50ext",
	"SysInfoType19-vb50ext",
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
	asn_DEF_SysInfoType19_vb50ext_tags_1,
	sizeof(asn_DEF_SysInfoType19_vb50ext_tags_1)
		/sizeof(asn_DEF_SysInfoType19_vb50ext_tags_1[0]), /* 1 */
	asn_DEF_SysInfoType19_vb50ext_tags_1,	/* Same as above */
	sizeof(asn_DEF_SysInfoType19_vb50ext_tags_1)
		/sizeof(asn_DEF_SysInfoType19_vb50ext_tags_1[0]), /* 1 */
	0,	/* No PER visible constraints */
	asn_MBR_SysInfoType19_vb50ext_1,
	7,	/* Elements count */
	&asn_SPC_SysInfoType19_vb50ext_specs_1	/* Additional specs */
};
