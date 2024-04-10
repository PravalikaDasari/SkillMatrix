package com.feuji.referenceservice.service;

import java.util.List;

import javax.naming.NameNotFoundException;

import com.feuji.referenceservice.bean.CommonReferenceDetailsBean;
import com.feuji.referenceservice.bean.CommonReferenceTypeBean;
import com.feuji.referenceservice.bean.TechnicalSkillsBean;
import com.feuji.referenceservice.exception.CategoryNotFoundException;
import com.feuji.referenceservice.exception.InvalidInputException;
import com.feuji.referenceservice.exception.NoRecordFoundException;
import com.feuji.referenceservice.exception.RecordNotFoundException;
import com.feuji.referenceservice.exception.ReferenceNotFoundException;
import com.feuji.referenceservice.exception.TechnicalSkillsNotFoundException;

public interface CommonReferenceDetailsService {

	public List<TechnicalSkillsBean> getDetailsByTypeId(String typeName) throws TechnicalSkillsNotFoundException;

	public int getIdByName(String name) throws NameNotFoundException;

	public List<String> getCategories(String category) throws CategoryNotFoundException;

	public CommonReferenceTypeBean saveReferenceDetails(CommonReferenceDetailsBean referenceDetailsBean);

	public CommonReferenceDetailsBean addSubSkillcategory(CommonReferenceDetailsBean bean)
			throws ReferenceNotFoundException;

	public CommonReferenceDetailsBean updateIsDeleted(CommonReferenceDetailsBean commonReferenceDetailsBean)
			throws InvalidInputException;

	public String getByid(int id);

	public CommonReferenceDetailsBean deleteSubSkill(Long referenceDetailId, Boolean isDeleted)
			throws RecordNotFoundException, NoRecordFoundException, InvalidInputException;

	public CommonReferenceDetailsBean deleteSkillCategory(String skillCategory, Boolean true1)
			throws RecordNotFoundException, NoRecordFoundException, InvalidInputException;
}
