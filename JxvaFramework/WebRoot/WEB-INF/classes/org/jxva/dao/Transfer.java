package org.jxva.dao;

import java.sql.SQLException;
import java.util.List;

import org.jxva.dao.transfer.model.SsoUser;
import org.jxva.dao.transfer.model.User;

import com.jxva.dao.DAO;
import com.jxva.dao.DAOFactory;
import com.jxva.entity.MD5;

public class Transfer{
	private static final DAOFactory factory=DAOFactory.getInstance();
	
	public static void main(String[] args) throws SQLException {
		DAO dao=factory.createDAO();
		List<User> list=dao.find(User.class);
		for(User user:list){
			short gender=(short)((user.getGender()==null||user.getGender()==0)?2:user.getGender());
			String nickname=user.getNickname()==null||user.getNickname().trim().length()==0?user.getUsername():user.getNickname();
			String realname=user.getRealname()==null||user.getRealname().trim().length()==0?user.getUsername():user.getRealname();
			SsoUser ssoUser=(SsoUser)dao.get("from SsoUser s where s.username=?",user.getUsername());
			if(ssoUser==null){
				ssoUser=new SsoUser();
				ssoUser.setAddress(user.getAddress());
				ssoUser.setCity(user.getCity());
				ssoUser.setEmail(user.getEmail());
				ssoUser.setGender(gender);
				ssoUser.setMobileNo(user.getMobile());
				ssoUser.setUsername(user.getUsername());
				ssoUser.setNickname(nickname);
				ssoUser.setRealname(realname);
				ssoUser.setPostcode(user.getPostCode());
				ssoUser.setProvince(user.getCity());
				ssoUser.setRemainCurrency(0);
				ssoUser.setTelephone(user.getPhone());
				ssoUser.setPassword(MD5.encrypt(user.getUsername()));
				dao.save(ssoUser);
			}else{
				Object[] objs=new Object[]{nickname,realname,user.getEmail(),user.getCity(),gender,user.getMobile(),user.getUsername()};
				dao.update("update SsoUser set nickname=?,realname=?,email=?,city=?,gender=?,mobileNo=? where username=?",objs);
			}
		}
		dao.close();
	}

}
