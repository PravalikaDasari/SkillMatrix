package com.feuji.referenceservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.feuji.referenceservice.bean.CommonReferenceDetailsBean;
import com.feuji.referenceservice.bean.TechnicalSkillsBean;
import com.feuji.referenceservice.constants.CommonConstants;
import com.feuji.referenceservice.exception.CategoryNotFoundException;
import com.feuji.referenceservice.exception.EmptyBeanException;
import com.feuji.referenceservice.exception.InvalidInputException;
import com.feuji.referenceservice.exception.NameNotFoundException;
import com.feuji.referenceservice.exception.NoRecordFoundException;
import com.feuji.referenceservice.exception.RecordNotFoundException;
import com.feuji.referenceservice.exception.ReferenceNotFoundException;
import com.feuji.referenceservice.exception.TechnicalSkillsNotFoundException;
import com.feuji.referenceservice.repository.CommonReferenceDetailsRepo;
import com.feuji.referenceservice.service.CommonReferenceDetailsService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/referencedetails")
@CrossOrigin("*")
@Slf4j
public class CommonReferenceDetailsController {

	@Autowired
	CommonReferenceDetailsService commonReferenceDetailsService;

	@Autowired
	CommonReferenceDetailsRepo commonReferenceDetailsRepo2;
	/**
	 * Retrieves a list of technical skills beans based on the specified type name.
	 *
	 * @param typeName The name of the type to retrieve technical skills for.
	 * @return ResponseEntity<List<TechnicalSkillsBean>> A ResponseEntity containing the list of TechnicalSkillsBean objects.
	 *         If successful, HttpStatus.OK is returned with the list of technical skills beans.
	 *         If the specified technical skills are not found, HttpStatus.NOT_FOUND is returned with an empty list.
	 */
	@GetMapping("/getref/{typeName}")
	public ResponseEntity<List<TechnicalSkillsBean>> getReferenceTypeByName(@PathVariable String typeName) {
		List<TechnicalSkillsBean> getbyreferenceType=null;
		try {
			log.info("Enterd into getReferenceTypeByName method of commomreferenceDetailsController  " + typeName);
			getbyreferenceType = commonReferenceDetailsService.getDetailsByTypeId(typeName);
			return new ResponseEntity<>(getbyreferenceType, HttpStatus.OK);
		} catch (TechnicalSkillsNotFoundException e) {
			log.info("Getting TechnicalSkillsNotFoundException in commomreferenceDetailsController");
			return new ResponseEntity<>(getbyreferenceType, HttpStatus.OK);
		}
	}

	/**
	 * Retrieves a list of categories based on the provided category.
	 * 
	 * This method is used to fetch sub-categories for the given category.
	 * 
	 * @param category The category for which to retrieve sub-categories.
	 * @return ResponseEntity<List<String>> A ResponseEntity containing the list of categories if found, 
	 *         or an empty list if no categories are found for the provided category.
	 *         Returns HttpStatus.OK if categories are found, HttpStatus.NOT_FOUND if the provided category is not found.
	 * @throws CategoryNotFoundException If the provided category is not found.
	 */
	@GetMapping("/getCategories/{category}")
	public ResponseEntity<List<String>> getCategories(@PathVariable String category) {
		log.info("getCategories start");
		List<String> categories = null;
		try {
			categories = commonReferenceDetailsService.getCategories(category);
			log.info("getCategories end");
			return new ResponseEntity<>(categories, HttpStatus.OK);
		} catch (CategoryNotFoundException e) {
			log.info(e.getMessage());
			return new ResponseEntity<>(categories, HttpStatus.NOT_FOUND);
		}

	}
	
	
	/**
	 * Retrieves an identifier by the provided name.
	 * 
	 * This method is used to fetch an identifier associated with the provided name.
	 * 
	 * @param name The name for which to retrieve an identifier.
	 * @return ResponseEntity<String> A ResponseEntity containing the identifier if found, 
	 *         or null if no identifier is found for the provided name.
	 *         Returns HttpStatus.OK if an identifier is found, HttpStatus.NOT_FOUND if the provided name is not found.
	 * @throws javax.naming.NameNotFoundException 
	 * @throws NullPointerException If the identifier retrieved by the provided name is null.
	 */
	@GetMapping("/getByName/{name}")
	public ResponseEntity<Integer> getByName(@PathVariable String name) throws NameNotFoundException, javax.naming.NameNotFoundException {
		log.info("controller-getByName() started");
		try {
			log.info("Entered into getIdByName method of service in controller");
			int idByName = commonReferenceDetailsService.getIdByName(name);
			log.info("Coming out from getIdByName method of service in controller");
			return new ResponseEntity<Integer>(idByName, HttpStatus.OK);
		} catch (NameNotFoundException e) {
			log.info("NameNotFoundException occured in commomreferenceDetailsController");
			throw new NameNotFoundException("Name not found: " + name);
		}

	}

	
	/**
	 * Retrieves a name by the provided identifier.
	 * 
	 * This method is used to fetch a name associated with the provided identifier.
	 * 
	 * @param id The identifier for which to retrieve a name.
	 * @return ResponseEntity<String> A ResponseEntity containing the name if found, 
	 *         or null if no name is found for the provided identifier.
	 *         Returns HttpStatus.OK if a name is found, HttpStatus.NOT_FOUND if the provided identifier is not found.
	 * @throws ReferenceNotFoundException If the name retrieved by the provided identifier is not found.
	 */
	@GetMapping("/getById/{id}")
	public ResponseEntity<String> getById(@PathVariable int id) {
		log.info("getById() started");
		String name = null;
		try {
			name = commonReferenceDetailsService.getByid(id);
			log.info("getById() ended");
			return new ResponseEntity<String>(name, HttpStatus.OK);
		} catch (ReferenceNotFoundException e) {
			log.info(e.getMessage());
			return new ResponseEntity<String>(name, HttpStatus.NOT_FOUND);

		}
	}
	
	@PostMapping("/addSubSkill")
	public ResponseEntity<CommonReferenceDetailsBean> addingSubSkillCategory(
			@RequestBody CommonReferenceDetailsBean bean) {
		log.info("Entered into addsubskill commomreferenceDetailsController");
		CommonReferenceDetailsBean addSubSkillcategory=null;
		try {
			log.info("Entered into the saving add subskill in commomreferenceDetailsController");
			
			addSubSkillcategory = commonReferenceDetailsService.addSubSkillcategory(bean);
			log.info("Completed the saving add subskill in commomreferenceDetailsController");
			return new ResponseEntity<CommonReferenceDetailsBean>(addSubSkillcategory, HttpStatus.ACCEPTED);
		} catch (ReferenceNotFoundException e) {
			log.info("ReferenceNotFoundException occured in commomreferenceDetailsController");
			return new ResponseEntity<CommonReferenceDetailsBean>(addSubSkillcategory, HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/getSubSkills")
	public ResponseEntity<List<String>> getsubSkills() {
		log.info("Entered into getsubskills commomreferenceDetailsController");
		try {
			log.info("Entered into getSubSkillCategory method of service in commomreferenceDetailsController");
			List<String> subSkillCategory = commonReferenceDetailsService.getSubSkillCategory();
			log.info("Coming out from  getSubSkillCategory method of service in commomreferenceDetailsController");
			return ResponseEntity.ok(subSkillCategory);
		} catch (EmptyBeanException e) {
			log.info("EmptyBeanException oocured in commomreferenceDetailsController");
			throw new EmptyBeanException("No records found in this table");
		}
	}
	
	@PostMapping("/save")
    public ResponseEntity<CommonReferenceDetailsBean> saveReferenceDetails(@RequestBody CommonReferenceDetailsBean referenceDetailsBean) {
		commonReferenceDetailsService.saveReferenceDetails(referenceDetailsBean);
		return ResponseEntity.status(HttpStatus.CREATED).build();
    }
	
	
	@PutMapping("/updateIsDeleted")
    public void updateIsDeleted(@RequestBody CommonReferenceDetailsBean commonReferenceDetailsBean) 
    {
		commonReferenceDetailsService.updateIsDeleted(commonReferenceDetailsBean);
    }

	@PutMapping("/deleteSubskill/{referenceDetailId}")
    public ResponseEntity<CommonReferenceDetailsBean> deleteSubSkill(@PathVariable Long referenceDetailId) {
		CommonReferenceDetailsBean deletedBean=null;
		try {
			deletedBean = commonReferenceDetailsService.deleteSubSkill(referenceDetailId, true);
            return new ResponseEntity<CommonReferenceDetailsBean>(deletedBean, HttpStatus.OK);
		
		}catch(RecordNotFoundException | NoRecordFoundException | InvalidInputException e)	
		{
			log.info(e.getMessage());
			return new ResponseEntity<CommonReferenceDetailsBean>(deletedBean,HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/deleteSkillCategory/{skillCategory}")
	public ResponseEntity<CommonReferenceDetailsBean> deleteSkillcategory(@PathVariable String skillCategory)
	{
		CommonReferenceDetailsBean deletedBean=null;
		try {
			deletedBean = commonReferenceDetailsService.deleteSkillCategory(skillCategory, CommonConstants.TRUE);
            return new ResponseEntity<CommonReferenceDetailsBean>(deletedBean, HttpStatus.OK);
		
		}catch(RecordNotFoundException | NoRecordFoundException | InvalidInputException e)
		{
			log.info(e.getMessage());
			return new ResponseEntity<CommonReferenceDetailsBean>(deletedBean,HttpStatus.NOT_FOUND);
		}
	}
}
