package com.easybusiness.usermanagement.services.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.easybusiness.usermanagement.DTO.UserDTO;
import com.easybusiness.usermanagement.dao.UserDao;
import com.easybusiness.usermanagement.entity.User;
import com.easybusiness.usermanagement.repository.UserRepository;
import com.easybusiness.usermanagement.services.UserService;


/*
 * Service and RestController class for User entity
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;
    
    @Autowired
    UserRepository userRepo;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    
    /*
     * (non-Javadoc)
     * @see com.easybusiness.usermanagement.services.user.UserService#getUser(java.lang.String)
     * fetching user by username
     * GET method with param "username"
     */
    @Override
    public UserDTO getUser(String userName) {
	System.out.println("in get user");
	User userEntity = userDao.findByUserName(userName);	//fetching user entity by username
	return prepareUserDTO(userEntity);
    }

    //preparing user DTO for fetching user details
    private UserDTO prepareUserDTO(User userEntity) {
	UserDTO userDTO = new UserDTO();	//creating blank user DTO object
	
	userDTO.setAddress(userEntity.getAddress());
	userDTO.setContactno(userEntity.getContactno());
	userDTO.setEmail(userEntity.getEmail());
	userDTO.setId(userEntity.getId());
	userDTO.setLoginid(userEntity.getLoginid());
	userDTO.setName(userEntity.getName());
	userDTO.setPassword(userEntity.getPassword());
	

	return userDTO;
	
    }

    
    
    /*
     * (non-Javadoc)
     * @see com.easybusiness.usermanagement.services.user.UserService#persistUser(com.easybusiness.usermanagement.DTO.UserDTO)
     * saving user entity to DB
     * POST method with request body "UserDTO"
     */
    @Override
    public ResponseEntity<UserDTO> persistUser(UserDTO userDTO) {

	System.out.println(
		"user dto in persistence layer to be set is " + userDTO.toString() );

	User userEntity = new User();	//creating blank user object
	
	userEntity.setAddress(userDTO.getAddress());
	userEntity.setEmail(userDTO.getEmail());
	userEntity.setContactno(userDTO.getContactno());
	userEntity.setLoginid(userDTO.getLoginid());
	userEntity.setName(userDTO.getName());
	userEntity.setPassword(userDTO.getPassword());
	
	userDao.addUser(userEntity);
	return new ResponseEntity<UserDTO>(userDTO, HttpStatus.CREATED);

    }

    /*
     * peparing unique username using first character of firstname and last name in lower case
     */
    private String prepareUniqueUserName(char firstCharOfFirstName, String lastName) {
	int countOfAlreadyPresentSimilarUserName = userDao.findCountOfUserName(firstCharOfFirstName + lastName);
	return countOfAlreadyPresentSimilarUserName == 0 ? (firstCharOfFirstName + lastName)
		: (firstCharOfFirstName + lastName + (countOfAlreadyPresentSimilarUserName + 1));
    }

    
    
    
    /*
     * (non-Javadoc)
     * @see com.easybusiness.usermanagement.services.user.UserService#updateUser(com.easybusiness.usermanagement.DTO.UserDTO)
     * updating user entity
     * POST method with request body of "UserDTO"
     */
    @SuppressWarnings("unused")
	@Override
    public ResponseEntity<UserDTO> updateUser(UserDTO userDTO) {

	System.out.println(
		"user dto in persistence layer to be updated is " + userDTO.toString() );
//	User userGetEntity = userDao.findByUserName(userDTO.getUserName()).get();
	if(userDTO.getId()!= 0) {
		
	User userEntity = userDao.findUserById(userDTO.getId());
	
	userEntity.setAddress(userDTO.getAddress());
	userEntity.setContactno(userDTO.getContactno());
	userEntity.setLoginid(userDTO.getLoginid());
	userEntity.setName(userDTO.getName());
	userEntity.setPassword(userDTO.getPassword());
	userEntity.setEmail(userDTO.getEmail());
	
	userDao.addUser(userEntity);
	}else {
		User userEntity = new User();	//creating blank user object
		
		userEntity.setAddress(userDTO.getAddress());
		userEntity.setContactno(userDTO.getContactno());
		userEntity.setLoginid(userDTO.getLoginid());
		userEntity.setName(userDTO.getName());
		userEntity.setPassword(userDTO.getPassword());
		userEntity.setEmail(userDTO.getEmail());
		
		userDao.addUser(userEntity);
		System.out.println("inside if "+userDTO.getId());
		
	}
	
	return new ResponseEntity<UserDTO>(userDTO, HttpStatus.CREATED);

    }

    
    /*
     * (non-Javadoc)
     * @see com.easybusiness.usermanagement.services.user.UserService#populateUserList()
     * fetching all users
     * GET method for user_details table
     */
    @Override
    public List<UserDTO> populateUserList() {
	List<UserDTO> userDTOList = new ArrayList<UserDTO>();
	try {
	    List<User> userEntityList = userDao.findAll();
//		List<User> userEntityList = userDao.findAllOrdered();

	    userEntityList.forEach(userEntity -> {
//		if (userEntity.getIsEnabled() == 1) {
		    userDTOList.add(prepareUserDTO(userEntity));
//		}
	    });

	} catch (Exception e) {
	    e.printStackTrace();
	}
	return userDTOList;
    }

    
    /*
     * (non-Javadoc)
     * @see com.easybusiness.usermanagement.services.user.UserService#populateOneUserDetails(java.lang.Long)
     * fetching user by user id
     * GET method for table user_detials with param userId
     */
    @Override
    public UserDTO populateOneUserDetails(Long userId) {
	User userEntity = userDao.findUserById(userId);
	return prepareUserDTO(userEntity);
    }

    
    /*
     * (non-Javadoc)
     * @see com.easybusiness.usermanagement.services.user.UserService#destroyUser(java.lang.Long)
     * deleting user
     * DELETE method for user_details table with param userId
     */
    @Override
    public void destroyUser(Long userId) {
    	userDao.deleteUser(userId);
    }

    @Override
    public List<UserDTO> getFieldEq(Class<UserDTO> type, String propertyName, Object value, int offset, int size) {
	return null;
    }

    @Override
    public void persistUser(UserDTO loggedUser, boolean changePassword) {

    }

    
    /*
     * (non-Javadoc)
     * @see com.easybusiness.usermanagement.services.user.UserService#activateUser(java.lang.Long)
     * activating user 
     * POST method for user_details table with param userId
     * this method updates isEnabled field of user_details table to value 1
     */
    @Override
    public void activateUser(Long userId) {

    	User user = userDao.findUserById(userId);
//    	user.setIsEnabled((long) 1);
    	
    	userDao.updateUser(user);
    	
    }

    
    /*
     * (non-Javadoc)
     * @see com.easybusiness.usermanagement.services.user.UserService#deActivateUser(java.lang.Long)
     * deactivating user
     *  POST method for user_details table with param userId
     *  this method updates isEnabled field of user_details table to value 0
     */
    @Override
    public void deActivateUser(Long userId) {

    	User user = userDao.findUserById(userId);
//    	user.setIsEnabled((long) 0);
    	
    	userDao.updateUser(user);
    	
    }

    @Override
    public UserDTO getActiveUser(String email) {
	return null;
    }

	@Override
	public UserDTO getByEmailAndPassword(String email, String password) {
		return prepareUserDTO(userDao.getUserByEmailAndPassword(email, password));
	}

    
    
    
    
	
	
    /*private User prepareUserProfessionEntity(UserProfessionDTO userProfessionDTO) {
	User userEntity = new User();
	userEntity.setAlternateEmail(userProfessionDTO.getUser().getAlternateEmail());
	userEntity.setDateOfBirth(userProfessionDTO.getUser().getDateOfBirth());
	Department dept = new Department();
	dept.setDeptName(userProfessionDTO.getUser().getDepartment().getDeptName());
	dept.setId(userProfessionDTO.getUser().getDepartment().getId());
	Organization org = new Organization();
	org.setId(userProfessionDTO.getUser().getDepartment().getOrganization().getId());
	org.setOrgLocation(userProfessionDTO.getUser().getDepartment().getOrganization().getOrgLocation());
	org.setOrgName(userProfessionDTO.getUser().getDepartment().getOrganization().getOrgName());
	dept.setOrganization(org);
	userEntity.setDepartment(dept);
	Designation desg = new Designation();
	desg.setDesig(userProfessionDTO.getUser().getDesignation().getDesig());
	desg.setId(userProfessionDTO.getUser().getDesignation().getId());
	userEntity.setDesignation(desg);
	userEntity.setEmail(userProfessionDTO.getUser().getEmail());
	userEntity.setEndDate(userProfessionDTO.getUser().getEndDate());
	userEntity.setFirstName(userProfessionDTO.getUser().getFirstName());
	userEntity.setFromDate(userProfessionDTO.getUser().getFromDate());
	userEntity.setGender(userProfessionDTO.getUser().getGender());
	userEntity.setIsEnabled(userProfessionDTO.getUser().getIsEnabled());
	userEntity.setLastName(userProfessionDTO.getUser().getLastName());
	userEntity.setMobile(userProfessionDTO.getUser().getMobile());
	userEntity.setModifiedBy(userProfessionDTO.getUser().getModifiedBy());
	userEntity.setModifiedOn(userProfessionDTO.getUser().getModifiedOn());
	userEntity.setOrganization(org);
	userEntity.setPassword(userProfessionDTO.getUser().getPassword().toString());
	userEntity.setTypeOfEmployment(userProfessionDTO.getUser().getTypeOfEmployment());
	userEntity.setUserName(userProfessionDTO.getUser().getUserName());
	userEntity.setId(userProfessionDTO.getUser().getId());

	userEntity.setPermAddr(userProfessionDTO.getUser().getPermAddr());
	userEntity.setState(userProfessionDTO.getUser().getState());
	userEntity.setCity(userProfessionDTO.getUser().getCity());
	userEntity.setCountry(userProfessionDTO.getUser().getCountry());
	userEntity.setZip(userProfessionDTO.getUser().getZip());
	userEntity.setFatherName(userProfessionDTO.getUser().getFatherName());
	userEntity.setSpouseName(userProfessionDTO.getUser().getSpouseName());
	userEntity.setPassport(userProfessionDTO.getUser().getPassport());
	userEntity.setLocation(null == userProfessionDTO.getUser().getLocation() ? null
		: prepareLocationEntity(userProfessionDTO.getUser().getLocation()));
	userEntity.setUnitId(userProfessionDTO.getUser().getUnitId());

	return userEntity;
    }*/

    
    

    
    

    
    
    /*private User prepareUserAcademicsEntity(UserAcademicsDTO userAcademicsDTO) {
	User userEntity = new User();
	userEntity.setAlternateEmail(userAcademicsDTO.getUser().getAlternateEmail());
	userEntity.setDateOfBirth(userAcademicsDTO.getUser().getDateOfBirth());
	Department dept = new Department();
	dept.setDeptName(userAcademicsDTO.getUser().getDepartment().getDeptName());
	dept.setId(userAcademicsDTO.getUser().getDepartment().getId());
	Organization org = new Organization();
	org.setId(userAcademicsDTO.getUser().getDepartment().getOrganization().getId());
	org.setOrgLocation(userAcademicsDTO.getUser().getDepartment().getOrganization().getOrgLocation());
	org.setOrgName(userAcademicsDTO.getUser().getDepartment().getOrganization().getOrgName());
	dept.setOrganization(org);
	userEntity.setDepartment(dept);
	Designation desg = new Designation();
	desg.setDesig(userAcademicsDTO.getUser().getDesignation().getDesig());
	desg.setId(userAcademicsDTO.getUser().getDesignation().getId());
	userEntity.setDesignation(desg);
	userEntity.setEmail(userAcademicsDTO.getUser().getEmail());
	userEntity.setEndDate(userAcademicsDTO.getUser().getEndDate());
	userEntity.setFirstName(userAcademicsDTO.getUser().getFirstName());
	userEntity.setFromDate(userAcademicsDTO.getUser().getFromDate());
	userEntity.setGender(userAcademicsDTO.getUser().getGender());
	userEntity.setIsEnabled(userAcademicsDTO.getUser().getIsEnabled());
	userEntity.setLastName(userAcademicsDTO.getUser().getLastName());
	userEntity.setMobile(userAcademicsDTO.getUser().getMobile());
	userEntity.setModifiedBy(userAcademicsDTO.getUser().getModifiedBy());
	userEntity.setModifiedOn(userAcademicsDTO.getUser().getModifiedOn());
	userEntity.setOrganization(org);
	userEntity.setPassword(userAcademicsDTO.getUser().getPassword().toString());
	userEntity.setTypeOfEmployment(userAcademicsDTO.getUser().getTypeOfEmployment());
	userEntity.setUserName(userAcademicsDTO.getUser().getUserName());
	userEntity.setId(userAcademicsDTO.getUser().getId());

	userEntity.setPermAddr(userAcademicsDTO.getUser().getPermAddr());
	userEntity.setState(userAcademicsDTO.getUser().getState());
	userEntity.setCity(userAcademicsDTO.getUser().getCity());
	userEntity.setCountry(userAcademicsDTO.getUser().getCountry());
	userEntity.setZip(userAcademicsDTO.getUser().getZip());
	userEntity.setFatherName(userAcademicsDTO.getUser().getFatherName());
	userEntity.setSpouseName(userAcademicsDTO.getUser().getSpouseName());
	userEntity.setPassport(userAcademicsDTO.getUser().getPassport());
	userEntity.setLocation(null == userAcademicsDTO.getUser().getLocation() ? null
		: prepareLocationEntity(userAcademicsDTO.getUser().getLocation()));
	userEntity.setUnitId(userAcademicsDTO.getUser().getUnitId());
	return userEntity;
    }*/

}
