package io.github.setupminimal.cljsh;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import org.apache.logging.log4j.Logger;

import clojure.lang.IFn;
import clojure.lang.RT;

@Mod(modid = Cljsh.modid, name = Cljsh.name, version = Cljsh.version)
public class Cljsh {

	public static final String modid = "cljsh";
	public static final String name = "Cljsh";
	public static final String version = "1.0";
	
	public ArrayList<IFn> initCalls = new ArrayList<IFn>();
	public ArrayList<IFn> postInitCalls = new ArrayList<IFn>();
	
	public static Logger logger;

	@Instance(value = modid)
	public static Cljsh instance;

	public Object execute(String contents) {
		contents = "(do " + contents + ")";
		Object form = RT.readString(contents);
		return clojure.lang.Compiler.eval(form);
	}

	@EventHandler
	public void onPreInitialization(FMLPreInitializationEvent event) {
		logger = event.getModLog();
		
		String path = "./mods/cljsh-mods/";
		File directory = new File(path);
		File[] fileListing = directory.listFiles();
		for (File file : fileListing) {
			if (file.isFile() && file.getName().endsWith(".mod.clj")) {
				String contents = "";
				try {
					contents = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				IFn mainFunction = (IFn) execute(contents);

				mainFunction.invoke(this);
				
				logger.info("Cljsh sucsessfully loaded " + file.getName());
			}
		}
	}
	
	@EventHandler
	public void onInitialization(FMLInitializationEvent event) {
		for (IFn fun : instance.initCalls) {
			fun.invoke(event);
		}
	}
	
	@EventHandler
	public void onPostInitialization(FMLPostInitializationEvent event) {
		for (IFn fun : instance.postInitCalls) {
			fun.invoke(event);
		}
	}
	
	public void addInitializationCallback(IFn fun) {
		instance.initCalls.add(fun);
	}
	
	public void addPostInitializationCallback(IFn fun) {
		instance.postInitCalls.add(fun);
	}

	public Object createCallerClass(final String eventClass, final IFn fun) {
		//System.out.println(eventClass);
		return new Object() {
			@SubscribeEvent
			public void onEvent(Event event) {
				if (event.getClass().getCanonicalName() == eventClass) {
					fun.invoke(event);
				}
			}
		};
	}

	public <K extends Event> void addOreGenCallback(Class<K> eventClass, final IFn fun) {
		Object callerClass = createCallerClass(eventClass.getCanonicalName(), fun);

		MinecraftForge.ORE_GEN_BUS.register(callerClass);
	}

	public <K extends Event> void addTerrainGenCallback(Class<K> eventClass, final IFn fun) {
		Object callerClass = createCallerClass(eventClass.getCanonicalName(), fun);

		MinecraftForge.TERRAIN_GEN_BUS.register(callerClass);
	}

	public <K extends Event> void addFMLCallback(Class<K> eventClass, final IFn fun) {
		Object callerClass = createCallerClass(eventClass.getCanonicalName(), fun);

		FMLCommonHandler.instance().bus().register(callerClass);
	}

	public <K extends Event> void addRegularCallback(Class<K> eventClass, final IFn fun) {
		Object callerClass = createCallerClass(eventClass.getCanonicalName(), fun);

		MinecraftForge.EVENT_BUS.register(callerClass);
	}

}
