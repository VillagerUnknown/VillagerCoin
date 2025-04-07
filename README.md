# VillagerUnknown's Villager Coin

Adds a custom currency, Villager Coin, and optionally adds the currency to loot tables, mob drops, and merchant trades.

Villager Coin consists of 5 types of coins each with their own value: Copper, Iron, Gold, Emerald, and Netherite. 
Each type of coin stacks up to 5,000 coins to reduce inventory clutter and can be converted between types through the crafting table.

**Features**

* Adds 5 custom coin items with individual currency values: Copper, Iron, Gold, Emerald, and Netherite.
* Convert between coins with the crafting table.
* Optionally find coins in structures.
* Optionally earn coins by killing mobs.
* Optionally change trades for Villagers, and Wandering Traders, and replace Emerald trades with Coin trades.

**Currency Values**

* Copper Coin = 1 Copper Coin
* Iron Coin = 100 Copper Coins
* Gold Coin = 10,000 Copper Coins
* Emerald Coin = 1,000,000 Copper Coins
* Netherite Coin = 100,000,000 Copper Coins

Emerald and Netherite coins are only available through currency conversion crafting due to their insane values. 
These coins are mostly here to help players who amass a large wealth in their world.

**Currency Conversions**

* 100 Copper Coins = 1 Iron Coin
* 100 Iron Coins = 1 Gold Coin
* 100 Gold Coins = 1 Emerald Coin
* 100 Emerald Coins = 1 Netherite Coin

**Currency Conversion Crafting**

Convert between currencies using the crafting table.

* Insert 99 or fewer of the same coin in a crafting table to convert down.
* Insert 100 or more of the same coin in a crafting table to convert up.

You can also sum up your coins by placing them all in the crafting table together. 
The crafting table will calculate the value of the coins and allow you to craft the highest value coin while leaving the remainder.

**Loot Tables**

Coins can be optionally included in the loot tables of structures. 
The value of the coins will vary depending on the difficulty of the structure. 
Trial Chambers, Ancient Cities, Strongholds, and Nether Fortresses will reward players more than a Village or Mineshaft.

**Mob Drops**

Coins can optionally drop when a player kills Humanoid mobs, Bosses, and Shulkers.
The value of the coins will vary depending on the difficulty of the mob. 
The Ender Dragon, Warden, and Wither will reward players more than a Zombie, Skeleton, or Piglin.

**Trade Modifications**

Coins can optionally be used for trading with Villagers by replacing Emerald trades with Coin trades. 

With the trade modifications enabled:

* Apprentice Villager trades typically trade in Copper Villager Coins.
* Novice to Expert Villager trades typically trade in Iron Villager Coins.
* Master Villager trades typically trade in Gold Villager Coins.
* Most trades rewarding Coins will reward Copper Villager Coins.
* Most enchanted books, armor, tools, and weapons will cost Gold Villager Coins.
* Iron trades reward Iron Villager Coins.
* Gold trades reward Gold Villager Coins.
* Diamond trades have a 50% chance of being an Emerald trade for 10 Gold Villager Coins. This is the most rewarding trade available.

**Options**

* addCoinsToStructureLootTables - Adds Coins to Structure loot tables. (Default: true)
* addCoinsToMobDrops - Adds Coins to Mob drops. (Default: true)
* enableTradeModifications - Modifies trades and replaces Emerald trades with Coin trades.
* lootingBonusPerLevel - Bonus chance per level of looting (Default: 0.1)

## Support

* Request features and report bugs at https://github.com/VillagerUnknown/VillagerCoin/issues
* View the changelog at https://github.com/VillagerUnknown/VillagerCoin/blob/main/CHANGELOG.md