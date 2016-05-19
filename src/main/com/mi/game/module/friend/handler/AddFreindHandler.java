package com.mi.game.module.friend.handler;

import com.mi.core.engine.IOMessage;
import com.mi.core.engine.ModuleManager;
import com.mi.core.engine.annotation.HandlerType;
import com.mi.game.defines.HandlerIds;
import com.mi.game.defines.ModuleNames;
import com.mi.game.module.base.handler.BaseHandler;
import com.mi.game.module.friend.FriendModule;
import com.mi.game.module.friend.protocol.FriendProtocol;
@HandlerType(type = HandlerIds.addFriend)
public class AddFreindHandler extends BaseHandler{
	@Override
	public void execute(IOMessage ioMessage){
		String playerID = ioMessage.getPlayerId();
		String friendID = null;
		String message = null;
		if(ioMessage.getInputParse("friendID") != null){
			friendID = ioMessage.getInputParse("friendID").toString();
		}
		if(ioMessage.getInputParse("msg") != null){
			message = ioMessage.getInputParse("msg").toString();
		}
		FriendModule module = ModuleManager.getModule(ModuleNames.FriendMoudle, FriendModule.class);
		FriendProtocol protocol = new FriendProtocol();
		try{
			module.applyFriend(playerID, friendID, message, protocol);
		}catch(IllegalArgumentException ex){
			protocol.setCode(Integer.parseInt(ex.getMessage()));
		}
		ioMessage.setProtocol(protocol);
	}
}
