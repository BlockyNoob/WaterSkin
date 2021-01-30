package blockynoob.waterskin.client;

import blockynoob.waterskin.common.main.WaterSkin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import toughasnails.api.TANCapabilities;
import toughasnails.api.config.GameplayOption;
import toughasnails.api.config.SyncedConfig;
import toughasnails.thirst.ThirstHandler;
import toughasnails.util.RenderUtils;

@SideOnly(Side.CLIENT)
public class HydrationHUD {
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onPostRenderOverlayLast(RenderGameOverlayEvent.Post event) {

		if (event.getType() == ElementType.EXPERIENCE && SyncedConfig.getBooleanValue(GameplayOption.ENABLE_THIRST)) {
			Minecraft mc = Minecraft.getMinecraft();
			ScaledResolution resolution = event.getResolution();
			int width = resolution.getScaledWidth();
			int height = resolution.getScaledHeight();
			EntityPlayerSP player = mc.player;

			WaterSkin.proxy.updateHydration(player);

			ThirstHandler thirstStats = (ThirstHandler) player.getCapability(TANCapabilities.THIRST, null);
			float thirstHydrationLevel = thirstStats.getHydration();
			float thirstHydration = thirstStats.getHydration();
			float thirstExhaustion = thirstStats.getExhaustion();

			thirstHydrationLevel = Math.max(0, thirstHydrationLevel - 0.25f * thirstExhaustion);
			if (thirstHydration > 0) {
				thirstExhaustion = (thirstExhaustion % 2f) * 2;
			}
			mc.getTextureManager()
					.bindTexture(new ResourceLocation("waterskin", "textures/gui/overlay/thirst_overlay.png"));

			if (mc.playerController.gameIsSurvivalOrAdventure()) {
				drawExhaustion(width, height, thirstExhaustion, thirstHydrationLevel);
				drawHydration(width, height, thirstHydrationLevel);
			}
		}
	}

	protected static final int BAR_WIDTH = 81;
	protected static final int BAR_HEIGHT = 9;
	protected static final float MAX_EXHAUSTION = 4;
	protected static final int LEFT_OFFSET = 91;
	protected static final int TOP_OFFSET = -49;
	protected static final int ATLAS_HEIGHT = 18;

	private static void drawExhaustion(int width, int height, float exhaustion, float hydration) {
		Minecraft mc = Minecraft.getMinecraft();
		float maxExhaustion = MAX_EXHAUSTION;
		int barWidth = BAR_WIDTH;
		int barHeight = BAR_HEIGHT;
		int left = (width >> 1) + LEFT_OFFSET;
		int top = height + TOP_OFFSET;
		float emptyRatio = (maxExhaustion - exhaustion) / maxExhaustion;
		float filledRatio = exhaustion / maxExhaustion;
		int emptyTex = (int) (barWidth * emptyRatio);
		int filledTex = (int) (barWidth * filledRatio);

		int x = left - filledTex;
		int y = top;

		mc.ingameGUI.drawTexturedModalRect(x, y, emptyTex, ATLAS_HEIGHT, filledTex, barHeight);
	}

	protected static final int DROPLET_WIDTH = 9;
	protected static final int DROPLET_HEIGHT = 9;

	private static void drawHydration(int width, int height, float thirstHydrationLevel) {
		int dropletWidth = DROPLET_WIDTH;
		int dropletHeight = DROPLET_HEIGHT;
		int left = (width >> 1) + LEFT_OFFSET;
		int top = height + TOP_OFFSET;
		int x = left - dropletWidth;
		int y = top;
		for (int i = 0; 2 * i < thirstHydrationLevel; i++) {
			float rem = thirstHydrationLevel - (2 * i);
			if (rem < 2) {
				RenderUtils.drawTexturedModalRect(x - (dropletWidth - 1) * i, y, dropletWidth * (int) (rem * 2 - 1), 0,
						dropletWidth, dropletHeight);
			} else {
				RenderUtils.drawTexturedModalRect(x - (dropletWidth - 1) * i, y, dropletWidth * 3, 0, dropletWidth, dropletHeight);
			}
		}
	}
}