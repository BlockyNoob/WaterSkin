package blockynoob.waterskin.common.main;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import toughasnails.api.TANPotions;
import toughasnails.api.item.ItemDrink;
import toughasnails.api.thirst.ThirstHelper;
import toughasnails.api.thirst.WaterType;
import toughasnails.config.json.DrinkData;
import toughasnails.config.json.ItemPredicate;
import toughasnails.init.ModConfig;
import toughasnails.item.ItemCanteen;
import toughasnails.thirst.ThirstHandler;

public class ThirstManager {

	public static DrinkData getDrinkData(ItemStack stack) {
		if (stack.getItem().equals(Items.POTIONITEM)) {
			if (PotionUtils.getFullEffectsFromItem(stack).isEmpty()) {
				return new DrinkData(new ItemPredicate(stack.getItem()), WaterType.NORMAL.getThirst(),
						WaterType.NORMAL.getHydration(), WaterType.NORMAL.getPoisonChance());
			} else {
				return new DrinkData(new ItemPredicate(stack.getItem()), 4, 0.3F, 0F);
			}
		}
		
		if(stack.getItem() instanceof ItemDrink) {
			if(stack.getItem() instanceof ItemCanteen && stack.getItemDamage() == 0) return null;
			return TANDrinkData.getDataByName(stack.getUnlocalizedName());
		}

		String registryName = stack.getItem().getRegistryName().toString();
		if (ModConfig.drinkData.containsKey(registryName)) {
			for (DrinkData drinkData : ModConfig.drinkData.get(registryName)) {
				if (drinkData.getPredicate().apply(stack)) {
					return drinkData;
				}
			}
		}
		return null;
	}

	private int lastThirst;
	private float lastHydration;

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onItemUseFinish(LivingEntityUseItemEvent.Finish event) {

		if (event.getEntityLiving() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			ThirstHandler thirstHandler = (ThirstHandler) ThirstHelper.getThirstData(player);
			lastThirst = thirstHandler.getThirst();
			lastHydration = thirstHandler.getHydration();
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onItemUseFinishAgain(LivingEntityUseItemEvent.Finish event) {
		if (event.getEntityLiving() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			ItemStack stack = player.getHeldItem(player.getActiveHand());
			ThirstHandler thirstHandler = (ThirstHandler) ThirstHelper.getThirstData(player);
			thirstHandler.setHydration(lastHydration);
			thirstHandler.setThirst(lastThirst);
			if (thirstHandler.isThirsty()) {
				boolean zeroStack = false;

				if (stack.getCount() <= 0) {
					stack.setCount(1);
					zeroStack = true;
				}
				DrinkData data = null;
				if ((data = getDrinkData(stack)) != null) {
					int thirst = Math.min(20, thirstHandler.getThirst() + data.getThirstRestored());
					float hydration = (float) Math.min(20, thirstHandler.getHydration() + data.getHydrationRestored());
					thirstHandler.setThirst(thirst);
					thirstHandler.setHydration(hydration);
					Random random = new Random();
					if (random.nextDouble() <= data.getPoisonChance()) {
						event.getEntityLiving().addPotionEffect(new PotionEffect(TANPotions.thirst, 600));
					}

				}

				if (zeroStack)
					stack.setCount(0);
			}
		}
	}

}
