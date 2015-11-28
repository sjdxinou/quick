package cn.sjd.it.quick.modules.user.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

public interface IUserService {
	
	@GET
	@Path("/add")
	@Produces({ MediaType.APPLICATION_JSON })
	public void insertUser() throws Exception;
}
