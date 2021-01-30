package blockynoob.waterskin.common.main;

import java.util.HashMap;
import java.util.Map;

import toughasnails.api.thirst.WaterType;
import toughasnails.api.item.TANItems;
import toughasnails.config.json.DrinkData;
import toughasnails.config.json.ItemPredicate;
import toughasnails.item.ItemFruitJuice.JuiceType;

public class TANDrinkData {

	private static Map<String, DrinkData> drinkValues = new HashMap<String, DrinkData>();
	
	private static void init() {
		drinkValues.put("item.purified_water_bottle", new DrinkData(new ItemPredicate(TANItems.purified_water_bottle), WaterType.PURIFIED.getThirst(), WaterType.PURIFIED.getHydration(), WaterType.PURIFIED.getPoisonChance()));
		drinkValues.put("item.juice_apple", new DrinkData(new ItemPredicate(TANItems.fruit_juice), JuiceType.APPLE.getThirst(), JuiceType.APPLE.getHydration(), JuiceType.APPLE.getPoisonChance()));
		drinkValues.put("item.juice_beetroot", new DrinkData(new ItemPredicate(TANItems.fruit_juice), JuiceType.BEETROOT.getThirst(), JuiceType.BEETROOT.getHydration(), JuiceType.BEETROOT.getPoisonChance()));
		drinkValues.put("item.juice_cactus", new DrinkData(new ItemPredicate(TANItems.fruit_juice), JuiceType.CACTUS.getThirst(), JuiceType.CACTUS.getHydration(), JuiceType.CACTUS.getPoisonChance()));
		drinkValues.put("item.juice_carrot", new DrinkData(new ItemPredicate(TANItems.fruit_juice), JuiceType.CARROT.getThirst(), JuiceType.CARROT.getHydration(), JuiceType.CARROT.getPoisonChance()));
		drinkValues.put("item.juice_chorus_fruit", new DrinkData(new ItemPredicate(TANItems.fruit_juice),JuiceType.CHORUS_FRUIT.getThirst(), JuiceType.CHORUS_FRUIT.getHydration(), JuiceType.CHORUS_FRUIT.getPoisonChance()));
		drinkValues.put("item.juice_glistering_melon", new DrinkData(new ItemPredicate(TANItems.fruit_juice), JuiceType.GLISTERING_MELON.getThirst(), JuiceType.GLISTERING_MELON.getHydration(), JuiceType.GLISTERING_MELON.getPoisonChance()));
		drinkValues.put("item.juice_golden_apple", new DrinkData(new ItemPredicate(TANItems.fruit_juice), JuiceType.GOLDEN_APPLE.getThirst(), JuiceType.GOLDEN_APPLE.getHydration(), JuiceType.GOLDEN_APPLE.getPoisonChance()));
		drinkValues.put("item.juice_golden_carrot", new DrinkData(new ItemPredicate(TANItems.fruit_juice), JuiceType.GOLDEN_CARROT.getThirst(), JuiceType.GOLDEN_CARROT.getHydration(), JuiceType.GOLDEN_CARROT.getPoisonChance()));
		drinkValues.put("item.juice_melon", new DrinkData(new ItemPredicate(TANItems.fruit_juice), JuiceType.MELON.getThirst(), JuiceType.MELON.getHydration(), JuiceType.MELON.getPoisonChance()));
		drinkValues.put("item.juice_pumpkin", new DrinkData(new ItemPredicate(TANItems.fruit_juice),JuiceType.PUMPKIN.getThirst(), JuiceType.PUMPKIN.getHydration(), JuiceType.PUMPKIN.getPoisonChance()));
		
		drinkValues.put("item.canteen", new DrinkData(new ItemPredicate(TANItems.canteen), WaterType.NORMAL.getThirst(), WaterType.NORMAL.getHydration(), WaterType.NORMAL.getPoisonChance()));
	}
	
	public static DrinkData getDataByName(String unlocalizedName) {
		if(drinkValues.isEmpty()) init();
		if(drinkValues.containsKey(unlocalizedName)) return drinkValues.get(unlocalizedName);
		return null;
	}
	
	public static boolean isEmpty() {
		return drinkValues.isEmpty();
	}
	
	public static Map<String, DrinkData> getDrinkValues() {
		return drinkValues;
	}
	
}
