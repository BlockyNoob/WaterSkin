package blockynoob.waterskin.common.main;

import blockynoob.waterskin.common.networking.CommonNetworkManager;
import blockynoob.waterskin.common.networking.ThirstPacket;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import toughasnails.api.TANCapabilities;
import toughasnails.thirst.ThirstHandler;

public class CommonProxy {

	public void subscribeHandler() {
		MinecraftForge.EVENT_BUS.register(this);
	}

	public void updateHydration(EntityPlayer player) {
		ThirstHandler thirstStats = (ThirstHandler) player.getCapability(TANCapabilities.THIRST, null);
		int thirstLevel = thirstStats.getThirst();
		float thirstHydrationLevel = thirstStats.getHydration();
		float thirstExhaustionLevel = thirstStats.getExhaustion();
		while (thirstExhaustionLevel > 4) {
			if (thirstHydrationLevel > 0) {
				thirstHydrationLevel = Math.max(0, thirstHydrationLevel - 1);
				thirstExhaustionLevel -= 4;
			} else if (thirstLevel > 0) {
				thirstLevel = Math.max(0, thirstLevel - 1);
				thirstExhaustionLevel -= 4;
			} else
				break;
		}
		thirstStats.setThirst(thirstLevel);
		thirstStats.setHydration(thirstHydrationLevel);
		thirstStats.setExhaustion(thirstExhaustionLevel);

	}

	@SubscribeEvent
	public void onPlayerUpdate(PlayerTickEvent event) {

		if (!event.player.world.isRemote) {
			updateHydration(event.player);
			CommonNetworkManager.sendToClient(new ThirstPacket(event.player), (EntityPlayerMP) event.player);
		}
	}
}
