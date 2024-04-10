package com.feuji.referenceservice.service;

import com.feuji.referenceservice.bean.CommonReferenceTypeBean;
import com.feuji.referenceservice.entity.CommonReferenceTypeEntity;

public interface CommonReferenceTypeService {

	public String getNameById(int id);
	
	public CommonReferenceTypeBean getByTypeName(String typeName);

	public CommonReferenceTypeEntity saveTimesheetWeek(CommonReferenceTypeBean commonReferenceTypeBean);

}
