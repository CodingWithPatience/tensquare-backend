/**
 * 
 */
package com.zhihao.tensquare.service;

import java.util.List;
import java.util.Map;

import com.zhihao.tensquare.entity.Admin;

/**
 * @author zzh
 * 2018年11月20日
 */
public interface AdminService {

	List<Admin> findAll();
	
	Admin findById(String id);
	
	Admin findByNameAndPass(String loginName, String password);

	List<Admin> search(Admin admin);

	void delete(Admin admin);
	
	void deleteById(String id);
	
	Long total();
	
	void create(Admin admin);
	
	void update(Admin admin);

	List<Admin> searchPageable(Admin admin, int pageNum);

	Map<String, Object> getInfo();

}
