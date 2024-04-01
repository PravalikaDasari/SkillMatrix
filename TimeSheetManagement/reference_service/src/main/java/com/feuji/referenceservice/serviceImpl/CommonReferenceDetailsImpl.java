package com.feuji.referenceservice.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.feuji.referenceservice.bean.CommonReferenceDetailsBean;
import com.feuji.referenceservice.bean.CommonReferenceTypeBean;
import com.feuji.referenceservice.bean.TechnicalSkillsBean;
import com.feuji.referenceservice.constants.CommonConstants;
import com.feuji.referenceservice.entity.CommonReferenceDetailsEntity;
import com.feuji.referenceservice.entity.CommonReferenceTypeEntity;
import com.feuji.referenceservice.entity.SkillEntity;
import com.feuji.referenceservice.exception.CategoryNotFoundException;
import com.feuji.referenceservice.exception.InvalidInputException;
import com.feuji.referenceservice.exception.NoRecordFoundException;
import com.feuji.referenceservice.exception.RecordAlreadyExistsException;
import com.feuji.referenceservice.exception.RecordNotFoundException;
import com.feuji.referenceservice.exception.ReferenceNotFoundException;
import com.feuji.referenceservice.exception.TechnicalSkillsNotFoundException;
import com.feuji.referenceservice.repository.CommonReferenceDetailsRepo;
import com.feuji.referenceservice.repository.CommonReferenceTypeRepo;
import com.feuji.referenceservice.service.CommonReferenceDetailsService;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CommonReferenceDetailsImpl implements CommonReferenceDetailsService {

	@Autowired
	private CommonReferenceDetailsRepo commonReferenceDetailsRepo;
	@Autowired
	private CommonReferenceTypeRepo commonReferenceTypeRepo;
	@Autowired
	private RestTemplate restTemplate;

	/*
	 * Converts a {@link CommonReferenceDetailsBean} to a {@link
	 * CommonReferenceDetailsEntity}.
	 * 
	 * @param bean The {@link CommonReferenceDetailsBean} to be converted.
	 * 
	 * @return The converted {@link CommonReferenceDetailsEntity}.
	 */
	public CommonReferenceDetailsEntity convertBeanToEntity(CommonReferenceDetailsBean bean) {
		System.out.println(bean);
		CommonReferenceTypeEntity commonReferenceTypeEntity = new CommonReferenceTypeEntity();
		commonReferenceTypeEntity.setReferenceTypeId(bean.getReferenceType().getReferenceTypeId());
		CommonReferenceDetailsEntity entity = new CommonReferenceDetailsEntity();
		entity.setReferenceDetailId(bean.getReferenceDetailId());
		entity.setReferenceDetailValue(bean.getReferenceDetailValue());
		entity.setReferenceType(commonReferenceTypeEntity);
		entity.setIsDeleted(bean.getIsDeleted());
		entity.setModifiedBy(bean.getModifiedBy());
		entity.setCreatedBy(bean.getCreatedBy());
		return entity;
	}

	@Override
	public List<TechnicalSkillsBean> getDetailsByTypeId(String typeName) throws TechnicalSkillsNotFoundException {
		log.info("result" + typeName);

		List<String> detailsByTypeName = commonReferenceDetailsRepo.getDetailsByTypeName(typeName);
		if (detailsByTypeName == null || detailsByTypeName.isEmpty()) {
			log.info("We are getting detailsByTypeName null");
			throw new TechnicalSkillsNotFoundException("No details found for type: " + typeName);
		}

		List<TechnicalSkillsBean> technicalSkillBeanList = new ArrayList<>();

		for (String item : detailsByTypeName) {
			TechnicalSkillsBean bean = new TechnicalSkillsBean();
			String[] split = item.split(",");
			bean.setReferenceDetailValue(split[0]);
			bean.setReferenceDetailId(Integer.parseInt(split[1]));
			bean.setReferenceTypeId(Integer.parseInt(split[2]));
			technicalSkillBeanList.add(bean);
		}
		return technicalSkillBeanList;
	}

	@Override
	public List<String> getCategories(String categoryName) {
		log.info("getCategories() start");
		List<String> categoryList = commonReferenceDetailsRepo.getCategories(categoryName);
		if (categoryList != null) {
			log.info("getCategories() end");
			return categoryList;
		} else {
			throw new CategoryNotFoundException("no categories found with this " + categoryName);
		}

	}

	@Override
	public int getIdByName(String name) {
		log.info("getByName() started");
		if (name != null) {
			int id = commonReferenceDetailsRepo.getByName(name);
			log.info("getByName() ended");
			return id;
		} else {
			throw new NullPointerException("name is null");
		}

	}

	@Override
	public String getByid(int id) {
		log.info("getByid() started");
		String nameById = commonReferenceDetailsRepo.getNameById(id);
		if (nameById != null) {
			log.info("getByid() ended");
			return nameById;
		} else {
			throw new ReferenceNotFoundException("no suitable name found for this id: " + id);
		}
	}

	@Override
	public void saveReferenceDetails(CommonReferenceDetailsBean referenceDetailsBean) {
		CommonReferenceDetailsEntity convertBeanToEntity = convertBeanToEntity(referenceDetailsBean);
		CommonReferenceDetailsEntity existingDetailEntity = commonReferenceDetailsRepo
				.findByReferenceDetailValue(convertBeanToEntity.getReferenceDetailValue());
		if (existingDetailEntity != null) {
			throw new RecordAlreadyExistsException("Record with referenceDetailValue "
					+ convertBeanToEntity.getReferenceDetailValue() + " already exists.");
		}
		convertBeanToEntity = commonReferenceDetailsRepo.save(convertBeanToEntity);
		CommonReferenceTypeEntity commonReferenceTypeEntity = new CommonReferenceTypeEntity();
		commonReferenceTypeEntity.setReferenceTypeName(convertBeanToEntity.getReferenceDetailValue());
		commonReferenceTypeEntity.setCreatedBy(convertBeanToEntity.getCreatedBy());
		commonReferenceTypeEntity.setIsDeleted(convertBeanToEntity.getIsDeleted());
		commonReferenceTypeEntity.setModifiedBy(convertBeanToEntity.getModifiedBy());
		commonReferenceTypeRepo.save(commonReferenceTypeEntity);
	}

	/**
	 * Adds a sub-skill category based on the information provided in the
	 * {@link CommonReferenceDetailsBean}.
	 *
	 * @param bean The {@link CommonReferenceDetailsBean} containing details for the
	 *             sub-skill category to be added.
	 * @throws ReferenceNotFoundException If the reference type in the provided bean
	 *                                    is null.
	 */
	@Override
	public void addSubSkillcategory(CommonReferenceDetailsBean bean) throws ReferenceNotFoundException {
		log.info("Entered into addSubSkillCategory method in service implementation");

		CommonReferenceDetailsEntity beanToEntity = convertBeanToEntity(bean);
		CommonReferenceDetailsEntity existingEntities = commonReferenceDetailsRepo
				.findByReferenceDetailValue(beanToEntity.getReferenceDetailValue());
		if (existingEntities != null && existingEntities.getIsDeleted() == CommonConstants.FALSE) {
			log.info("Record with referenceDetailValue " + beanToEntity.getReferenceDetailValue() + " already exists.");
			throw new RecordAlreadyExistsException(
					"Record with referenceDetailValue " + beanToEntity.getReferenceDetailValue() + " already exists.");
		} else if (existingEntities != null && existingEntities.getIsDeleted() == CommonConstants.TRUE) {
			existingEntities.setIsDeleted(CommonConstants.FALSE);
			commonReferenceDetailsRepo.save(existingEntities);
		} else {
			if (beanToEntity.getReferenceType() == null) {
				log.info("Getting referenceType null");
				throw new ReferenceNotFoundException("Reference type cannot be null");
			}

			if (beanToEntity.getIsDeleted() == null) {
				log.info("We are setting the isDeleted true here");
				beanToEntity.setIsDeleted(false);
			}

			log.info("Saving the add subSkill into referenceDetails entity");
			commonReferenceDetailsRepo.save(beanToEntity);
			log.info("Completed addSubSkillCategory method in service implementation");
		}
	}

	@Override
	public void updateIsDeleted(CommonReferenceDetailsBean commonReferenceDetailsBean) {
		CommonReferenceDetailsEntity convertBeanToEntity = convertBeanToEntity(commonReferenceDetailsBean);
		convertBeanToEntity = commonReferenceDetailsRepo.findById(commonReferenceDetailsBean.getReferenceDetailId())
				.get();
		convertBeanToEntity.setIsDeleted(commonReferenceDetailsBean.getIsDeleted());
		commonReferenceDetailsRepo.save(convertBeanToEntity);
		CommonReferenceTypeEntity byTypeName = commonReferenceTypeRepo
				.findByReferenceTypeName(commonReferenceDetailsBean.getReferenceDetailValue());
		byTypeName.setIsDeleted(convertBeanToEntity.getIsDeleted());
		commonReferenceTypeRepo.save(byTypeName);

	}

	@Override
	public List<String> getSubSkillCategory() throws EntityNotFoundException {
		return null;
	}

	public CommonReferenceDetailsBean convertEntityToBean(CommonReferenceDetailsEntity detailsEntity) {
		CommonReferenceTypeBean commonReferenceTypeBean = new CommonReferenceTypeBean();
		commonReferenceTypeBean.setReferenceTypeId(detailsEntity.getReferenceType().getReferenceTypeId());
		CommonReferenceDetailsBean bean = new CommonReferenceDetailsBean();
		bean.setReferenceDetailId(detailsEntity.getReferenceDetailId());
		bean.setReferenceDetailValue(detailsEntity.getReferenceDetailValue());
		bean.setReferenceType(commonReferenceTypeBean);
		bean.setIsDeleted(detailsEntity.getIsDeleted());
		bean.setModifiedBy(detailsEntity.getModifiedBy());
		bean.setCreatedBy(detailsEntity.getCreatedBy());
		return bean;

	}

	@Override
	public CommonReferenceDetailsBean deleteSubSkill(Long referenceDetailId, Boolean isDeleted)
			throws RecordNotFoundException, NoRecordFoundException, InvalidInputException {
		Optional<CommonReferenceDetailsEntity> optionalEntity = commonReferenceDetailsRepo.findById(referenceDetailId);
		if (optionalEntity.isPresent()) {
			CommonReferenceDetailsEntity entity = optionalEntity.get();
			entity.setIsDeleted(isDeleted);
			CommonReferenceDetailsEntity save = commonReferenceDetailsRepo.save(entity);
			SkillEntity skillEntity = deleteSkill(referenceDetailId);
			return convertEntityToBean(save);
		} else {
			throw new RecordNotFoundException("Record not found with this reference details id: " + referenceDetailId);
		}
	}

	public SkillEntity deleteSkill(Long referenceDetailId) throws NoRecordFoundException, InvalidInputException {
		if (referenceDetailId != null) {
			String url = "http://localhost:8087/api/skill/deleteSkill/" + referenceDetailId;
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<String> httpEntity = new HttpEntity<>(headers);
			ResponseEntity<SkillEntity> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, httpEntity,
					SkillEntity.class);
			if (responseEntity.getBody() != null) {
				return responseEntity.getBody();
			} else {
				throw new NoRecordFoundException("no records found with this id: " + referenceDetailId);
			}
		} else {
			throw new InvalidInputException("invalid input is entered:in EmployeeSkillServiceImpl");
		}

	}

//	public SkillBean getSkillbeanById(int skillId) throws InvalidInputException, NoRecordFoundException {
//		log.info("getSkillbeanById() started: in EmployeeSkillServiceImpl");
//		if (skillId != CommonConstants.FALSE) {
//			String url = "http://localhost:8087/api/skill/getBySkillId/" + skillId;
//
//			HttpHeaders headers = new HttpHeaders();
//			headers.setContentType(MediaType.APPLICATION_JSON);
//
//			HttpEntity<String> httpEntity = new HttpEntity<>(headers);
//
//			ResponseEntity<SkillBean> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity,
//					SkillBean.class);
//			log.info("getSkillbeanById() ended:in EmployeeSkillServiceImpl");
//			if (responseEntity.getBody() != null) {
//				return responseEntity.getBody();
//			} else {
//				throw new NoRecordFoundException("no records found with this id: " + skillId);
//			}
//		} else {
//			throw new InvalidInputException("invalid input is entered:in EmployeeSkillServiceImpl");
//		}
//	}
//
//

	@Override
	public CommonReferenceDetailsBean deleteSkillCategory(String skillCategory, Boolean isDeleted)
			throws RecordNotFoundException, NoRecordFoundException, InvalidInputException {
		int skillCategoryId = getIdByName(skillCategory);
		Optional<CommonReferenceDetailsEntity> optionalEntity = commonReferenceDetailsRepo
				.findById((long) skillCategoryId);
		CommonReferenceTypeEntity typeEntity = commonReferenceTypeRepo.findByReferenceTypeName(skillCategory);
		if (optionalEntity.isPresent() && typeEntity != null) {
			CommonReferenceDetailsEntity entity = optionalEntity.get();
			entity.setIsDeleted(isDeleted);
			typeEntity.setIsDeleted(isDeleted);
			CommonReferenceDetailsEntity save = commonReferenceDetailsRepo.save(entity);
			CommonReferenceTypeEntity referenceTypeEntity = commonReferenceTypeRepo.save(typeEntity);
			
			List<String> categories = commonReferenceDetailsRepo.getCategories(skillCategory);
			List<Long> subSkillCategoryIds= new ArrayList<>();
			for(String subCategory:categories)
			{
				Long id = (long) getIdByName(subCategory);
				subSkillCategoryIds.add(id);
			}
			for(Long id:subSkillCategoryIds)
			{
				deleteSubSkill(id, isDeleted);
			}
			return convertEntityToBean(save);
		} else {
			throw new RecordNotFoundException("Record not found with this reference details id: " + skillCategoryId);
		}
	}

}
