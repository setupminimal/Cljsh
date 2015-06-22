(import net.minecraftforge.fml.common.event.FMLInitializationEvent net.minecraftforge.event.entity.player.PlayerSleepInBedEvent)

(defn say-hello [x]
  (println "Hello from clojure!"))

(defn marks [x] 
  (println "!!!!!!!!!!!!!!!!!!!!"))

(defn main [instance]
  (. instance addInitializationCallback
    say-hello)
  (. instance addRegularCallback
    PlayerSleepInBedEvent
    marks))

(fn [x] (main x))
