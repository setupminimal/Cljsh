Cljsh
=====

This is a mod for Minecraft, using Minecraft Forge, that allows other mods to be written in Clojure.
It requires that a clojure jar also be in the mods folder. Clojure can be found here:
http://clojure.org/downloads

Making Contributions
--------------------

Please do! I am fairly new to contributing to open-source projects, so I don't have a good grasp of how to organize things.
With that in mind, just send a pull request, and I will probably accept it. Thanks!

Support
-------

If you have trouble using this mod, or just need something explained, please feel free to email me at setupminimal@gmail.com.
I will eventually have documentation and a tutorial, but only eventually.

Using Cljsh
-----------

Cljsh mods are clojure files, located in (Minecraft folder)/mods/cljsh-mods/, ending in .mod.clj

Here is an example Cljsh mod:

```
(import net.minecraft.creativetab.CreativeTabs
        net.minecraft.item.Item 
        net.minecraft.block.Block 
        net.minecraft.block.material.Material
        net.minecraftforge.fml.common.registry.GameRegistry)

(defn registerExampleItem [x]
  (def exampleItem
    (doto (Item.)
      (.setUnlocalizedName "exampleItem")
      (.setCreativeTab CreativeTabs/tabRedstone)))
  (GameRegistry/registerItem exampleItem (. exampleItem getUnlocalizedName)))

(defn registerExampleBlock [x]
  (def exampleBlock
    (doto (proxy [Block] [Material/rock])
      (.setUnlocalizedName "exampleBlock")
      (.setCreativeTab CreativeTabs/tabBrewing)))
  (GameRegistry/registerBlock exampleBlock (. exampleBlock getUnlocalizedName)))

(defn main [instance]
  (. instance addInitializationCallback registerExampleItem)
  (. instance addInitializationCallback registerExampleBlock))

; Function to be called on pre-initialization
(fn [x] (main x))
```

This is equivalent to this java mod:

```
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
```

Files that don't end in .mod.clj will simply be ignored.

Why Cljsh?
----------

Cljsh is a mixture between clj, or Clojure, and Clash, as in a sound a Forge makes.

Licence
-------

Cljsh is released under the GPL. https://www.gnu.org/licenses/gpl-3.0.txt
