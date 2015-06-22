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