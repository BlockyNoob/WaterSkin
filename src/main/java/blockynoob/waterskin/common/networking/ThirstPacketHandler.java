package blockynoob.waterskin.common.networking;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import toughasnails.api.thirst.ThirstHelper;
import toughasnails.thirst.ThirstHandler;

public class ThirstPacketHandler implements IMessageHandler<ThirstPacket, IMessage> {

	@Override
	public IMessage onMessage(ThirstPacket message, MessageContext ctx) {
		try {
			ThirstHandler data = (ThirstHandler) ThirstHelper.getThirstData(Minecraft.getMinecraft().player);
			data.setExhaustion(message.exhaustion);
			data.setHydration(message.hydration);
			data.setThirst(message.thirst);
		} catch (NullPointerException e) {
		}
		return null;
	}

}
