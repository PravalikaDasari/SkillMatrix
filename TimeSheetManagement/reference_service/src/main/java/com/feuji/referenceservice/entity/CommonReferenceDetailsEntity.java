package com.feuji.referenceservice.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@DynamicInsert
@DynamicUpdate
@ToString
@Table(name="common_reference_details")
public class CommonReferenceDetailsEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "reference_details_id")
	private Long referenceDetailId;
	
	@Column(name = "reference_details_values")
	private String referenceDetailValue;
	
	@OneToOne
    @JoinColumn(name = "reference_type_id" ,referencedColumnName = "reference_type_id")
    private CommonReferenceTypeEntity referenceType;
	
	@Column(name = "uuid")
	private String uuid;
	
	@Column(name = "is_deleted")
	private Boolean isDeleted;
	
	@Column(name = "created_by")
	private String createdBy;
	
	@CreationTimestamp
	@Column(name = "created_on")
	private Timestamp createdOn;
	
	@Column(name = "modified_by")
	private String modifiedBy;
	@CreationTimestamp
	@Column(name = "modified_on")
	private Timestamp modifiedOn;

}
