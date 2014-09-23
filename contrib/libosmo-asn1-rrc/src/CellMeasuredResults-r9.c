/*
 * Generated by asn1c-0.9.24 (http://lionet.info/asn1c)
 * From ASN.1 module "InformationElements"
 * 	found in "../asn/InformationElements.asn"
 * 	`asn1c -fcompound-names -fnative-types`
 */

#include "CellMeasuredResults-r9.h"

static asn_per_constraints_t asn_PER_type_modeSpecificInfo_constr_4 = {
	{ APC_CONSTRAINED,	 1,  1,  0,  1 }	/* (0..1) */,
	{ APC_UNCONSTRAINED,	-1, -1,  0,  0 },
	0, 0	/* No PER value map */
};
static asn_TYPE_member_t asn_MBR_fdd_5[] = {
	{ ATF_NOFLAGS, 0, offsetof(struct CellMeasuredResults_r9__modeSpecificInfo__fdd, primaryCPICH_Info),
		(ASN_TAG_CLASS_CONTEXT | (0 << 2)),
		-1,	/* IMPLICIT tag at current level */
		&asn_DEF_PrimaryCPICH_Info,
		0,	/* Defer constraints checking to the member type */
		0,	/* No PER visible constraints */
		0,
		"primaryCPICH-Info"
		},
	{ ATF_POINTER, 4, offsetof(struct CellMeasuredResults_r9__modeSpecificInfo__fdd, cpich_Ec_N0),
		(ASN_TAG_CLASS_CONTEXT | (1 << 2)),
		-1,	/* IMPLICIT tag at current level */
		&asn_DEF_CPICH_Ec_N0,
		0,	/* Defer constraints checking to the member type */
		0,	/* No PER visible constraints */
		0,
		"cpich-Ec-N0"
		},
	{ ATF_POINTER, 3, offsetof(struct CellMeasuredResults_r9__modeSpecificInfo__fdd, cpich_RSCP),
		(ASN_TAG_CLASS_CONTEXT | (2 << 2)),
		-1,	/* IMPLICIT tag at current level */
		&asn_DEF_CPICH_RSCP,
		0,	/* Defer constraints checking to the member type */
		0,	/* No PER visible constraints */
		0,
		"cpich-RSCP"
		},
	{ ATF_POINTER, 2, offsetof(struct CellMeasuredResults_r9__modeSpecificInfo__fdd, deltaRSCPPerCell),
		(ASN_TAG_CLASS_CONTEXT | (3 << 2)),
		-1,	/* IMPLICIT tag at current level */
		&asn_DEF_DeltaRSCPPerCell,
		0,	/* Defer constraints checking to the member type */
		0,	/* No PER visible constraints */
		0,
		"deltaRSCPPerCell"
		},
	{ ATF_POINTER, 1, offsetof(struct CellMeasuredResults_r9__modeSpecificInfo__fdd, pathloss),
		(ASN_TAG_CLASS_CONTEXT | (4 << 2)),
		-1,	/* IMPLICIT tag at current level */
		&asn_DEF_Pathloss,
		0,	/* Defer constraints checking to the member type */
		0,	/* No PER visible constraints */
		0,
		"pathloss"
		},
};
static int asn_MAP_fdd_oms_5[] = { 1, 2, 3, 4 };
static ber_tlv_tag_t asn_DEF_fdd_tags_5[] = {
	(ASN_TAG_CLASS_CONTEXT | (0 << 2)),
	(ASN_TAG_CLASS_UNIVERSAL | (16 << 2))
};
static asn_TYPE_tag2member_t asn_MAP_fdd_tag2el_5[] = {
    { (ASN_TAG_CLASS_CONTEXT | (0 << 2)), 0, 0, 0 }, /* primaryCPICH-Info at 13296 */
    { (ASN_TAG_CLASS_CONTEXT | (1 << 2)), 1, 0, 0 }, /* cpich-Ec-N0 at 13297 */
    { (ASN_TAG_CLASS_CONTEXT | (2 << 2)), 2, 0, 0 }, /* cpich-RSCP at 13298 */
    { (ASN_TAG_CLASS_CONTEXT | (3 << 2)), 3, 0, 0 }, /* deltaRSCPPerCell at 13299 */
    { (ASN_TAG_CLASS_CONTEXT | (4 << 2)), 4, 0, 0 } /* pathloss at 13300 */
};
static asn_SEQUENCE_specifics_t asn_SPC_fdd_specs_5 = {
	sizeof(struct CellMeasuredResults_r9__modeSpecificInfo__fdd),
	offsetof(struct CellMeasuredResults_r9__modeSpecificInfo__fdd, _asn_ctx),
	asn_MAP_fdd_tag2el_5,
	5,	/* Count of tags in the map */
	asn_MAP_fdd_oms_5,	/* Optional members */
	4, 0,	/* Root/Additions */
	-1,	/* Start extensions */
	-1	/* Stop extensions */
};
static /* Use -fall-defs-global to expose */
asn_TYPE_descriptor_t asn_DEF_fdd_5 = {
	"fdd",
	"fdd",
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
	asn_DEF_fdd_tags_5,
	sizeof(asn_DEF_fdd_tags_5)
		/sizeof(asn_DEF_fdd_tags_5[0]) - 1, /* 1 */
	asn_DEF_fdd_tags_5,	/* Same as above */
	sizeof(asn_DEF_fdd_tags_5)
		/sizeof(asn_DEF_fdd_tags_5[0]), /* 2 */
	0,	/* No PER visible constraints */
	asn_MBR_fdd_5,
	5,	/* Elements count */
	&asn_SPC_fdd_specs_5	/* Additional specs */
};

static asn_TYPE_member_t asn_MBR_tdd_11[] = {
	{ ATF_NOFLAGS, 0, offsetof(struct CellMeasuredResults_r9__modeSpecificInfo__tdd, cellParametersID),
		(ASN_TAG_CLASS_CONTEXT | (0 << 2)),
		-1,	/* IMPLICIT tag at current level */
		&asn_DEF_CellParametersID,
		0,	/* Defer constraints checking to the member type */
		0,	/* No PER visible constraints */
		0,
		"cellParametersID"
		},
	{ ATF_POINTER, 4, offsetof(struct CellMeasuredResults_r9__modeSpecificInfo__tdd, proposedTGSN),
		(ASN_TAG_CLASS_CONTEXT | (1 << 2)),
		-1,	/* IMPLICIT tag at current level */
		&asn_DEF_TGSN,
		0,	/* Defer constraints checking to the member type */
		0,	/* No PER visible constraints */
		0,
		"proposedTGSN"
		},
	{ ATF_POINTER, 3, offsetof(struct CellMeasuredResults_r9__modeSpecificInfo__tdd, primaryCCPCH_RSCP),
		(ASN_TAG_CLASS_CONTEXT | (2 << 2)),
		-1,	/* IMPLICIT tag at current level */
		&asn_DEF_PrimaryCCPCH_RSCP,
		0,	/* Defer constraints checking to the member type */
		0,	/* No PER visible constraints */
		0,
		"primaryCCPCH-RSCP"
		},
	{ ATF_POINTER, 2, offsetof(struct CellMeasuredResults_r9__modeSpecificInfo__tdd, pathloss),
		(ASN_TAG_CLASS_CONTEXT | (3 << 2)),
		-1,	/* IMPLICIT tag at current level */
		&asn_DEF_Pathloss,
		0,	/* Defer constraints checking to the member type */
		0,	/* No PER visible constraints */
		0,
		"pathloss"
		},
	{ ATF_POINTER, 1, offsetof(struct CellMeasuredResults_r9__modeSpecificInfo__tdd, timeslotISCP_List),
		(ASN_TAG_CLASS_CONTEXT | (4 << 2)),
		-1,	/* IMPLICIT tag at current level */
		&asn_DEF_TimeslotISCP_List,
		0,	/* Defer constraints checking to the member type */
		0,	/* No PER visible constraints */
		0,
		"timeslotISCP-List"
		},
};
static int asn_MAP_tdd_oms_11[] = { 1, 2, 3, 4 };
static ber_tlv_tag_t asn_DEF_tdd_tags_11[] = {
	(ASN_TAG_CLASS_CONTEXT | (1 << 2)),
	(ASN_TAG_CLASS_UNIVERSAL | (16 << 2))
};
static asn_TYPE_tag2member_t asn_MAP_tdd_tag2el_11[] = {
    { (ASN_TAG_CLASS_CONTEXT | (0 << 2)), 0, 0, 0 }, /* cellParametersID at 13303 */
    { (ASN_TAG_CLASS_CONTEXT | (1 << 2)), 1, 0, 0 }, /* proposedTGSN at 13304 */
    { (ASN_TAG_CLASS_CONTEXT | (2 << 2)), 2, 0, 0 }, /* primaryCCPCH-RSCP at 13305 */
    { (ASN_TAG_CLASS_CONTEXT | (3 << 2)), 3, 0, 0 }, /* pathloss at 13306 */
    { (ASN_TAG_CLASS_CONTEXT | (4 << 2)), 4, 0, 0 } /* timeslotISCP-List at 13307 */
};
static asn_SEQUENCE_specifics_t asn_SPC_tdd_specs_11 = {
	sizeof(struct CellMeasuredResults_r9__modeSpecificInfo__tdd),
	offsetof(struct CellMeasuredResults_r9__modeSpecificInfo__tdd, _asn_ctx),
	asn_MAP_tdd_tag2el_11,
	5,	/* Count of tags in the map */
	asn_MAP_tdd_oms_11,	/* Optional members */
	4, 0,	/* Root/Additions */
	-1,	/* Start extensions */
	-1	/* Stop extensions */
};
static /* Use -fall-defs-global to expose */
asn_TYPE_descriptor_t asn_DEF_tdd_11 = {
	"tdd",
	"tdd",
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
	asn_DEF_tdd_tags_11,
	sizeof(asn_DEF_tdd_tags_11)
		/sizeof(asn_DEF_tdd_tags_11[0]) - 1, /* 1 */
	asn_DEF_tdd_tags_11,	/* Same as above */
	sizeof(asn_DEF_tdd_tags_11)
		/sizeof(asn_DEF_tdd_tags_11[0]), /* 2 */
	0,	/* No PER visible constraints */
	asn_MBR_tdd_11,
	5,	/* Elements count */
	&asn_SPC_tdd_specs_11	/* Additional specs */
};

static asn_TYPE_member_t asn_MBR_modeSpecificInfo_4[] = {
	{ ATF_NOFLAGS, 0, offsetof(struct CellMeasuredResults_r9__modeSpecificInfo, choice.fdd),
		(ASN_TAG_CLASS_CONTEXT | (0 << 2)),
		0,
		&asn_DEF_fdd_5,
		0,	/* Defer constraints checking to the member type */
		0,	/* No PER visible constraints */
		0,
		"fdd"
		},
	{ ATF_NOFLAGS, 0, offsetof(struct CellMeasuredResults_r9__modeSpecificInfo, choice.tdd),
		(ASN_TAG_CLASS_CONTEXT | (1 << 2)),
		0,
		&asn_DEF_tdd_11,
		0,	/* Defer constraints checking to the member type */
		0,	/* No PER visible constraints */
		0,
		"tdd"
		},
};
static asn_TYPE_tag2member_t asn_MAP_modeSpecificInfo_tag2el_4[] = {
    { (ASN_TAG_CLASS_CONTEXT | (0 << 2)), 0, 0, 0 }, /* fdd at 13296 */
    { (ASN_TAG_CLASS_CONTEXT | (1 << 2)), 1, 0, 0 } /* tdd at 13303 */
};
static asn_CHOICE_specifics_t asn_SPC_modeSpecificInfo_specs_4 = {
	sizeof(struct CellMeasuredResults_r9__modeSpecificInfo),
	offsetof(struct CellMeasuredResults_r9__modeSpecificInfo, _asn_ctx),
	offsetof(struct CellMeasuredResults_r9__modeSpecificInfo, present),
	sizeof(((struct CellMeasuredResults_r9__modeSpecificInfo *)0)->present),
	asn_MAP_modeSpecificInfo_tag2el_4,
	2,	/* Count of tags in the map */
	0,
	-1	/* Extensions start */
};
static /* Use -fall-defs-global to expose */
asn_TYPE_descriptor_t asn_DEF_modeSpecificInfo_4 = {
	"modeSpecificInfo",
	"modeSpecificInfo",
	CHOICE_free,
	CHOICE_print,
	CHOICE_constraint,
	CHOICE_decode_ber,
	CHOICE_encode_der,
	CHOICE_decode_xer,
	CHOICE_encode_xer,
	CHOICE_decode_uper,
	CHOICE_encode_uper,
	CHOICE_outmost_tag,
	0,	/* No effective tags (pointer) */
	0,	/* No effective tags (count) */
	0,	/* No tags (pointer) */
	0,	/* No tags (count) */
	&asn_PER_type_modeSpecificInfo_constr_4,
	asn_MBR_modeSpecificInfo_4,
	2,	/* Elements count */
	&asn_SPC_modeSpecificInfo_specs_4	/* Additional specs */
};

static asn_TYPE_member_t asn_MBR_CellMeasuredResults_r9_1[] = {
	{ ATF_POINTER, 2, offsetof(struct CellMeasuredResults_r9, cellIdentity),
		(ASN_TAG_CLASS_CONTEXT | (0 << 2)),
		-1,	/* IMPLICIT tag at current level */
		&asn_DEF_CellIdentity,
		0,	/* Defer constraints checking to the member type */
		0,	/* No PER visible constraints */
		0,
		"cellIdentity"
		},
	{ ATF_POINTER, 1, offsetof(struct CellMeasuredResults_r9, cellSynchronisationInfo),
		(ASN_TAG_CLASS_CONTEXT | (1 << 2)),
		-1,	/* IMPLICIT tag at current level */
		&asn_DEF_CellSynchronisationInfo,
		0,	/* Defer constraints checking to the member type */
		0,	/* No PER visible constraints */
		0,
		"cellSynchronisationInfo"
		},
	{ ATF_NOFLAGS, 0, offsetof(struct CellMeasuredResults_r9, modeSpecificInfo),
		(ASN_TAG_CLASS_CONTEXT | (2 << 2)),
		+1,	/* EXPLICIT tag at current level */
		&asn_DEF_modeSpecificInfo_4,
		0,	/* Defer constraints checking to the member type */
		0,	/* No PER visible constraints */
		0,
		"modeSpecificInfo"
		},
};
static int asn_MAP_CellMeasuredResults_r9_oms_1[] = { 0, 1 };
static ber_tlv_tag_t asn_DEF_CellMeasuredResults_r9_tags_1[] = {
	(ASN_TAG_CLASS_UNIVERSAL | (16 << 2))
};
static asn_TYPE_tag2member_t asn_MAP_CellMeasuredResults_r9_tag2el_1[] = {
    { (ASN_TAG_CLASS_CONTEXT | (0 << 2)), 0, 0, 0 }, /* cellIdentity at 13292 */
    { (ASN_TAG_CLASS_CONTEXT | (1 << 2)), 1, 0, 0 }, /* cellSynchronisationInfo at 13293 */
    { (ASN_TAG_CLASS_CONTEXT | (2 << 2)), 2, 0, 0 } /* modeSpecificInfo at 13301 */
};
static asn_SEQUENCE_specifics_t asn_SPC_CellMeasuredResults_r9_specs_1 = {
	sizeof(struct CellMeasuredResults_r9),
	offsetof(struct CellMeasuredResults_r9, _asn_ctx),
	asn_MAP_CellMeasuredResults_r9_tag2el_1,
	3,	/* Count of tags in the map */
	asn_MAP_CellMeasuredResults_r9_oms_1,	/* Optional members */
	2, 0,	/* Root/Additions */
	-1,	/* Start extensions */
	-1	/* Stop extensions */
};
asn_TYPE_descriptor_t asn_DEF_CellMeasuredResults_r9 = {
	"CellMeasuredResults-r9",
	"CellMeasuredResults-r9",
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
	asn_DEF_CellMeasuredResults_r9_tags_1,
	sizeof(asn_DEF_CellMeasuredResults_r9_tags_1)
		/sizeof(asn_DEF_CellMeasuredResults_r9_tags_1[0]), /* 1 */
	asn_DEF_CellMeasuredResults_r9_tags_1,	/* Same as above */
	sizeof(asn_DEF_CellMeasuredResults_r9_tags_1)
		/sizeof(asn_DEF_CellMeasuredResults_r9_tags_1[0]), /* 1 */
	0,	/* No PER visible constraints */
	asn_MBR_CellMeasuredResults_r9_1,
	3,	/* Elements count */
	&asn_SPC_CellMeasuredResults_r9_specs_1	/* Additional specs */
};
