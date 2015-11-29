package cn.sjd.it.quick.modules.user.services;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import cn.sjd.it.quick.modules.user.vo.req.AddUserReq;

public interface IUserService {
	@POST
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	public void insertUser(AddUserReq req) throws Exception; 
}
