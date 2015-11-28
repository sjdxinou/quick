package cn.sjd.it.quick.modules.user.services.impl;

import javax.inject.Inject;
import javax.inject.Named;

import cn.sjd.it.quick.modules.user.dao.IUserDao;
import cn.sjd.it.quick.modules.user.services.IUserService;
import cn.sjd.it.quick.modules.user.vo.table.TUser;

@Named
public class UserServiceImpl implements IUserService {

	@Inject
	private IUserDao userDao;
	
	@Override
	public void insertUser() throws Exception {
		TUser tUser = new TUser();
		tUser.setDealPwd("123");
		tUser.setEmail("123@mail.com");
		tUser.setGender("F");
		tUser.setIdCard("123456");
		tUser.setMobile("183");
		tUser.setRealName("OX");
		tUser.setUserName("ADMON");
		tUser.setUserPwd("TT");
		userDao.insertUser(tUser);
	}

}
