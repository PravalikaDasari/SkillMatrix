package com.feuji.referenceservice.bean;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CommonReferenceDetailsBean {
	
	private Long referenceDetailId;

	private String referenceDetailValue;

	private CommonReferenceTypeBean referenceType;

	private String uuid;

	private Boolean isDeleted;

	private String createdBy;

	private Timestamp createdOn;

	private String modifiedBy;

	private Timestamp modifiedOn;
	
}
