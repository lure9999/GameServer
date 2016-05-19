package com.mi.game.module.tower.handler;

import com.mi.core.engine.IOMessage;
import com.mi.core.engine.ModuleManager;
import com.mi.core.engine.annotation.HandlerType;
import com.mi.game.defines.HandlerIds;
import com.mi.game.defines.ModuleNames;
import com.mi.game.module.base.handler.BaseHandler;
import com.mi.game.module.tower.TowerModule;
import com.mi.game.module.tower.protocol.TowerProtocol;
@HandlerType(type = HandlerIds.towerQuicklyComplete)
public class TowerQuicklyCompleteHandler extends BaseHandler{
	@Override
	public void execute(IOMessage ioMessage){
		String playerID = ioMessage.getPlayerId();
		TowerProtocol protocol = new TowerProtocol();
		TowerModule towerModule = ModuleManager.getModule(ModuleNames.TowerModule, TowerModule.class);
		try{
			towerModule.quicklyCompleteTower(playerID, protocol, ioMessage);
		}catch(IllegalArgumentException ex){
			protocol.setCode(Integer.parseInt(ex.getMessage()));
		}

		ioMessage.setProtocol(protocol);
	}
}
