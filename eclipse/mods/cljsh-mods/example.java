package io.github.setupminimal.cljsh.reference;

import net.minecraft.block.Block;

/* Bunches of imports */

@Mod(modid = "example")
public class ReferenceMod {
        @Instance
        public static ReferenceMod instance;
       
        @EventHandler
        public void onInit(FMLInitializationEvent event) {
                Item exampleItem = new Item();
                exampleItem.setUnlocalizedName("exampleItem");
                exampleBlock.setCreativeTab(CreativeTabs.tabRedstone);
                GameRegistry.registerItem(exampleItem, exampleItem.getUnlocalizedName());
                
                Block exampleBlock = new Block(Material.rock) {};
                exampleBlock.setUnlocalizedName("exampleBlock");
                exampleBlock.setCreativeTab(CreativeTabs.tabBrewing);
                GameRegistry.registerBlock(exampleBlock, exampleBlock.getUnlocalizedName());
        }
}