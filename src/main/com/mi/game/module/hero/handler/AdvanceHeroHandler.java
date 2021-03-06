package com.mi.game.module.hero.handler;

import com.mi.core.engine.IOMessage;
import com.mi.core.engine.ModuleManager;
import com.mi.core.engine.annotation.HandlerType;
import com.mi.game.defines.HandlerIds;
import com.mi.game.defines.ModuleNames;
import com.mi.game.module.base.handler.BaseHandler;
import com.mi.game.module.hero.HeroModule;
import com.mi.game.module.hero.protocol.HeroInfoProtocol;
@HandlerType(type = HandlerIds.AdvanceHero)
public class AdvanceHeroHandler extends BaseHandler{
	@Override
	public void execute(IOMessage ioMessage){
		String playerID = ioMessage.getPlayerId();
		String heroID = "-1";
		if(ioMessage.getInputParse("heroID") != null){
			heroID = ioMessage.getInputParse("heroID").toString();
		}
		HeroInfoProtocol protocol = new HeroInfoProtocol();
		HeroModule heroModule = ModuleManager.getModule(ModuleNames.HeroModule,HeroModule.class);
		heroModule.advanceHero(playerID, heroID, protocol, ioMessage);
		ioMessage.setProtocol(protocol);
	}
}
