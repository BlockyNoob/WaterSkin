package blockynoob.waterskin.common.main;

import org.apache.logging.log4j.Logger;

import blockynoob.waterskin.common.networking.CommonNetworkManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = WaterSkin.MODID, name = WaterSkin.NAME, version = WaterSkin.VERSION)
public class WaterSkin
{
    public static final String MODID = "waterskin";
    public static final String NAME = "Water Skin";
    public static final String VERSION = "1.0.0";
    public static final String MC_VERSION = "[1.12.2]";

    private static Logger logger;

    @Instance
	public static WaterSkin instance;

	@SidedProxy(clientSide = "blockynoob.waterskin.client.ClientProxy", serverSide = "blockynoob.waterskin.server.ServerProxy")
	public static CommonProxy proxy;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        instance = this;
		proxy.subscribeHandler();
    }
    
	@EventHandler
	public void init(FMLInitializationEvent event)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		MinecraftForge.EVENT_BUS.register(new ThirstManager());
		CommonNetworkManager.init();
		logger.info(WaterSkin.NAME + " is in the Init phase.");
	}
}
