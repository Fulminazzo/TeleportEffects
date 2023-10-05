# TeleportEffects
**TeleportEffects** is a **Bukkit** plugin that **intercepts** every **teleport** and **re-schedule it** while creating some **particle effects** on the **start** and **destination points**.
<br>
This is a page presentation for the plugin, if you are looking for the [Wiki](https://github.com/Fulminazzo/TeleportEffects/wiki/home) 
you should go [here](https://github.com/Fulminazzo/TeleportEffects/wiki/home).

## Why?
The reason why I created this plugin is that I have seen various custom projects that create these type of effects, but not for every teleportation:
some only implement this for teleport between players (```/tpa```) while others only for movement to certain destinations (```/warp```).
<br>
**TeleportEffects** tries to generify by **piping** every **teleportation** through its **filter**. 
This gives a more **uniform look** to your server, making the audience assume that **all kind of teleports** are **handled** by the **same plugin**.

## How to use
Since **TeleportEffects** does not know if the **cause** of a **teleportation** is a **command**, and even so **it cannot know** what **messages** the **command** is going to **send** to the **player**, 
I suggest that you **disable any message** sent from teleportation plugin.
If, for example, a ```/spawn``` plugin sends a message to the user when they reach destination, you should make the **message blank** ("") and **leave the text part** only to **TeleportEffects**
(check out the [messages.yml file](https://github.com/Fulminazzo/TeleportEffects/blob/master/src/main/resources/messages.yml)).
<br>
Do keep in mind that using **TeleportEffects** is **not 100% secure**, and it **might cause some problems**.
Check the [dedicated page in the Wiki](https://github.com/Fulminazzo/TeleportEffects/wiki/Compatibility) for more.