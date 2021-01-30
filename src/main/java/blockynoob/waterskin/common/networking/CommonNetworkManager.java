package blockynoob.waterskin.common.networking;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class CommonNetworkManager {

	private static String channelName = "blk_waterskin";

	private static SimpleNetworkWrapper gaddonsChannel;

	private static int registryIndex = 899;

	public static void init() {
		gaddonsChannel = NetworkRegistry.INSTANCE.newSimpleChannel(channelName);
		registerMessages();
	}

	public static <REQ extends IMessage, REP extends IMessage> void registerMessage(
			Class<? extends IMessageHandler<REQ, REP>> handlerClass, Class<REQ> messageClass, Side side) {
		gaddonsChannel.registerMessage(handlerClass, messageClass, registryIndex++, side);
	}

	private static void registerMessages() {
		registerMessage(ThirstPacketHandler.class, ThirstPacket.class, Side.CLIENT);
	}

	public static void sendToClient(IMessage msg, EntityPlayerMP player) {
		gaddonsChannel.sendTo(msg, player);
	}

	public static void sendToClients(IMessage msg) {
		gaddonsChannel.sendToAll(msg);
	}

	public static void sendToDimension(IMessage msg, int dimId) {
		gaddonsChannel.sendToDimension(msg, dimId);
	}

}
