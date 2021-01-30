package blockynoob.waterskin.common.networking;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import toughasnails.api.thirst.ThirstHelper;
import toughasnails.thirst.ThirstHandler;

public class ThirstPacket implements IMessage {

	public float hydration;
	public float exhaustion;
	public short thirst;

	@Override
	public void fromBytes(ByteBuf buf) {
		hydration = buf.readFloat();
		exhaustion = buf.readFloat();
		thirst = buf.readShort();

	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeFloat(hydration);
		buf.writeFloat(exhaustion);
		buf.writeShort(thirst);
	}

	public ThirstPacket() {

	}

	public ThirstPacket(EntityPlayer player) {
		ThirstHandler data = (ThirstHandler) ThirstHelper.getThirstData(player);
		hydration = data.getHydration();
		exhaustion = data.getExhaustion();
		thirst = (short) data.getThirst();
	}

}
