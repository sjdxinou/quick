package cn.sjd.it.quick.modules.user.services.impl;

import javax.inject.Inject;
import javax.inject.Named;
import org.apache.commons.beanutils.BeanUtils;
import cn.sjd.it.quick.framework.services.impl.BaseServiceImpl;
import cn.sjd.it.quick.modules.user.dao.IUserDao;
import cn.sjd.it.quick.modules.user.services.IUserService;
import cn.sjd.it.quick.modules.user.vo.req.AddUserReq;
import cn.sjd.it.quick.modules.user.vo.table.TUser;

@Named
public class UserServiceImpl extends BaseServiceImpl implements IUserService {

	@Inject
	private IUserDao userDao;
	
	@Override
	public void insertUser(AddUserReq req) throws Exception {
		if(!super.valid(req)){
			return;
		}; 
		TUser tUser = new TUser();
		BeanUtils.copyProperties(tUser, req);
		userDao.insertUser(tUser);
	}

}
