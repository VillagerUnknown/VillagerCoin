# VillagerUnknown's Villager Coin

Adds a custom currency, Villager Coin, and optionally adds the currency to loot tables, mob drops, and merchant trades.

Villager Coin consists of 5 types of coins each with their own value: Copper, Iron, Gold, Emerald, and Netherite. 
Each type of coin stacks up to 5,000 coins to reduce inventory clutter and can be converted between the different coins through the crafting table.

**Features**

* Adds 5 custom coin items with individual currency values: Copper, Iron, Gold, Emerald, and Netherite.
* Adds 3 edible coin items with no currency values: Gold, Emerald, and Netherite.
* Convert between the different coins with the crafting table.
* Optionally find coins in vanilla structures.
* Optionally earn coins by killing vanilla mobs.
* Optionally change trades for Villagers, and Wandering Traders, and replace the Emerald trades with Coin trades.

**Currency Values**

* Copper Coin = 1 Copper Coin
* Iron Coin = 100 Copper Coins
* Gold Coin = 10,000 Copper Coins
* Emerald Coin = 1,000,000 Copper Coins
* Netherite Coin = 100,000,000 Copper Coins

Emerald and Netherite coins are only available through currency conversion crafting due to their high values. 
These coins are mostly here to help players who amass a large wealth in their world.

**Currency Conversions**

* 100 Copper Coins = 1 Iron Coin
* 100 Iron Coins = 1 Gold Coin
* 100 Gold Coins = 1 Emerald Coin
* 100 Emerald Coins = 1 Netherite Coin

**Currency Conversion Crafting**

Convert between currencies using the crafting table and auto-crafter.

* Insert 99 or fewer of the same coin in a crafting table to convert down.
* Insert 100 or more of the same coin in a crafting table to convert up.

You can also sum up your coins by placing them all in the crafting table together. 
The crafting table will calculate the total value of the coins and allow you to craft the highest value coin while leaving the remainder.

Due to technical limitations, quick-crafting (shift-clicking crafted items to craft the maximum) 
will still automatically stack with the same items in your inventory, but it will only craft once. 
Use the auto-crafter to convert large amounts of coins.

**Edible Coins**

Edible coins, found in common structures and dropped by common mobs, trick players into thinking they've struck rich when they've really just found a bit of chocolate. 
They're only available in Gold, Emerald, and Netherite. They cannot be crafted and have the same food values as cookies. 

**Loot Tables**

Coins can be optionally included in the loot tables. 
The value of the coins will vary depending on the loot table rarity. 
Trial Chambers, Ancient Cities, Strongholds, and Nether Fortresses will reward players more than a Village, Mineshaft, or Fishing.

**Mob Drops**

Coins can optionally drop when a player kills Humanoid mobs, Bosses, and Shulkers.
The value of the coins will vary depending on the difficulty of the mob. 
The Ender Dragon, Warden, and Wither will reward players more than a Zombie, Skeleton, or Piglin.

**Trade Modifications**

Coins can optionally be used for trading with Villagers and Wandering Traders by replacing Emerald trades with Coin trades. 
_This will not change any existing villager trades in worlds. Only new villager trades will use the coins, if enabled._

With the trade modifications enabled:

* Apprentice Villager trades typically trade in Copper Villager Coins.
* Novice to Expert Villager trades typically trade in Iron Villager Coins.
* Master Villager trades typically trade in Gold Villager Coins.
* Most trades rewarding Coins will reward Copper Villager Coins.
* Most enchanted books, armor, tools, and weapons will cost Gold Villager Coins.
* Iron trades reward Iron Villager Coins.
* Gold trades reward Gold Villager Coins.
* Diamond trades have a 50% chance of being an Emerald trade for 10 Gold Villager Coins. This is the most rewarding trade available.

**Additional Notes**

* You can flip a coin, for a heads or tails result, if you have a single coin in your hand and interact (right-click).
* Trades are modified for both vanilla and modded Villagers.
* Bundles have been changed to display their progress as "N/5000" in addition to the vanilla "N/64". 1 stack of coins essentially equals 1 item in the bundle.
* This mod will change the significance of farms. Iron and Gold farms will be more rewarding than a crop farm or melon and pumpkin farm.
* Manually mining for diamonds and emeralds, or exploring through structures, may be more profitable than any farm.
* Nether mobs (Piglins and Wither Skeletons) and structures are more likely to reward Gold Villager Coins.
* Piglins might try to steal your Gold Villager Coins.

**Options**

* addCoinsToStructureLootTables - Adds Coins to Structure loot tables. (Default: true)
* addCoinsToMobDrops - Adds Coins to Mob drops. (Default: true)
* enableTradeModifications - Modifies trades and replaces Emerald trades with Coin trades. (Default: true)
* lootingBonusPerLevel - Bonus chance per level of looting (Default: 0.1)

## Support

* Request features and report bugs at https://github.com/VillagerUnknown/VillagerCoin/issues
* View the changelog at https://github.com/VillagerUnknown/VillagerCoin/blob/main/CHANGELOG.md