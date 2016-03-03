package com.xuguruogu.auth.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.xuguruogu.auth.dal.dataobject.KssAdminDO;
import com.xuguruogu.auth.dal.enums.RoleType;
import com.xuguruogu.auth.service.AdminManager;
import com.xuguruogu.auth.util.AbstractConverter;

/**
 *
 *
 * @author benli.lbl
 * @version $Id: ApiUserDetailsConverter.java, v 0.1 Aug 10, 2015 11:31:07 PM
 *          benli.lbl Exp $
 */
@Component("adminUserDetailsConverter")
public class AdminUserDetailsConverter extends AbstractConverter<KssAdminDO, AdminUserDetails> {

	@Autowired
	private AdminManager adminManager;

	@Override
	protected AdminUserDetails doConvert(KssAdminDO kssAdminDO) {

		boolean accountNonLocked = !kssAdminDO.getIslock();

		Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
		Long adminid = kssAdminDO.getId();

		for (RoleType role : RoleType.values()) {
			if (kssAdminDO.getLevel() == role.getLevel()) {
				auths.add(new SimpleGrantedAuthority(role.getCode()));
			}
		}

		AdminUserDetails adminUserDetails = new AdminUserDetails(kssAdminDO.getUsername(), kssAdminDO.getPassword(),
				accountNonLocked, auths);

		adminUserDetails.setAdminid(adminid);

		return adminUserDetails;
	}
}