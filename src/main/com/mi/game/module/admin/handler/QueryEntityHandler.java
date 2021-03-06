package com.mi.game.module.admin.handler;

import java.util.List;

import com.mi.core.engine.IOMessage;
import com.mi.core.engine.ModuleManager;
import com.mi.core.engine.annotation.HandlerType;
import com.mi.core.pojo.BaseEntity;
import com.mi.game.defines.HandlerIds;
import com.mi.game.defines.ModuleNames;
import com.mi.game.module.abstrac.BaseEntityManager;
import com.mi.game.module.abstrac.EntityManagerFoctory;
import com.mi.game.module.abstrac.ResponseResult;
import com.mi.game.module.admin.protocol.BaseAdminProtocol;
import com.mi.game.module.admin.userManage.AdminModule;
import com.mi.game.module.admin.userManage.pojo.AdminRoleEntity;
import com.mi.game.module.admin.userManage.pojo.AdminUserEntity;
import com.mi.game.module.base.handler.BaseHandler;

@HandlerType(type = HandlerIds.adminQueryEntity, order = 2)
public class QueryEntityHandler extends BaseHandler {
	@Override
	public void execute(IOMessage ioMessage) {
		BaseAdminProtocol protocol = new BaseAdminProtocol();
		String permission = (String) ioMessage.getInputParse("permission");
		String username = (String) ioMessage.getInputParse("username");

		AdminModule adminModule = ModuleManager.getModule(ModuleNames.AdminModule, AdminModule.class);
		AdminUserEntity userEntity = adminModule.getAdminUserEntityByName(username);

		int result = ResponseResult.PERMISSION;
		List<AdminRoleEntity> roleList = userEntity.getRoles();
		for (AdminRoleEntity role : roleList) {
			String roleUrl = role.getRoleUrl().toLowerCase();
			if (permission.equalsIgnoreCase(roleUrl)) {
				if (role.getPermission().contains("R")) {
					result = ResponseResult.OK;
				}
				break;
			}
		}

		if (result == ResponseResult.OK) {
			String entityName = (String) ioMessage.getInputParse("entity");
			BaseEntity entity;
			BaseEntityManager<?> entityManager = EntityManagerFoctory.getEntityManager(entityName);
			entity = entityManager.queryEntity(ioMessage);
			protocol.put("entity", entity);
		}
		protocol.put("result", result);
		protocol.put("code", 1);
		ioMessage.setOutputResult(protocol);
	}
}
