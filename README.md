# VillagerUnknown's Villager Coin

Villager Coin adds a customizable coin currency, Villager Coin, and optionally adds the currency to loot tables, mob drops, and merchant trades. Coins can be converted between different types through the crafting table.

Villager Coin consists of 5 types of coins each with their own value: Copper, Iron, Gold, Emerald, and Netherite.

Each type of coin stacks up to 5,000 coins by default to reduce inventory clutter and can be converted between the different coins through the crafting table.
The stack size can be configured, but it's limited to a maximum of 1,073,741,822.

Learn how to customize Villager Coin from the Developer Wiki at the bottom of this page.

**Features**

* Adds 5 custom coin items with individual currency values: Copper, Iron, Gold, Emerald, and Netherite.
* Adds advancements for obtaining coins.
* Convert between the different coins with the crafting tables.
* Smelt down Copper, Iron, and Gold coins for their respective resources.
* Configurable drop chances.
* Optionally find coins in vanilla structures.
* Optionally earn coins by killing some vanilla mobs.
* Optionally earn coins by killing breedable mobs. (For easier coins.)
* Optionally change trades for Villagers, and Wandering Traders, and replace the Emerald trades with Coin trades.
* Optionally allow players to flip individual coins for a heads or tails result in chat.
* Configurable loot bonus per level chance for coins from mobs.

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

The crafting tables will calculate the conversions for you.

**Currency Conversion Crafting**

Convert between currencies using the crafting table and auto-crafter.

* Insert a single stack of 99 or fewer of the same coin in a crafting table to convert down.
* Insert 100 or more of the same coin in a crafting table to convert up.

You can sum up your coins by placing them all in the crafting table together. 
The crafting table will calculate the total value of the coins and allow you to craft the highest value coin while leaving the remainder. 

Copper, Iron, and Gold Villager Coins can be smelted down to their respective resources using the Furnace or Blast Furnace.

_Due to technical limitations, quick-crafting (shift-clicking crafted items to craft the maximum) 
will still automatically stack with the same items in your inventory, but it will only craft once. 
Use the auto-crafter to convert large amounts of coins._

**Loot Tables**

Coins can be optionally included in the loot tables. 
The value of the coins will vary depending on the loot table rarity. 
Trial Chambers, Ancient Cities, Strongholds, and Nether Fortresses will reward players more than a Village, Mineshaft, or Fishing.
Nether and End structures are more likely to reward Iron and Gold Villager Coins.

**Mob Drops**

Coins can optionally drop when a player kills Humanoid mobs, Bosses, and Shulkers. 

A separate option exists to drop coins from breedable mobs if you want coins to be more common.

The value of the coins will vary depending on the difficulty of the mob. 
The Ender Dragon, Warden, and Wither will reward players more than a Zombie, Skeleton, or Piglin.

**Trade Modifications**

Coins can optionally be used for trading with Villagers and Wandering Traders by replacing Emerald trades with Coin trades. 
_This will not change any existing villager trades in worlds. Only new villager trades will use the coins, if enabled._

With the trade modifications enabled:

* Apprentice Villager trades typically trade in Copper Villager Coins.
* Novice to Expert Villager trades typically trade in Iron Villager Coins.
* Master Villager trades typically trade in Gold Villager Coins.
* Most enchanted books, armor, tools, and weapons will cost Gold Villager Coins, 
but at half of the original emerald amount. These are the most expensive trades.
* Iron trades reward Iron Villager Coins.
* Gold trades reward Gold Villager Coins.
* Trades with Nether items typically trade in Gold Villager Coins.
* The Diamond trade, giving 16 Gold Villager Coins (by default), has a 50% chance of being an Emerald trade giving 8 Gold Villager Coins (by default). 
These are the most rewarding trades available and they're configurable.

**Additional Notes**

* If you have a single coin in your hand and interact (right-click) you can optionally flip a coin, for a heads or tails result in chat.
* Trades are modified for both vanilla and modded Villagers.
* This mod will change the significance of farms and general pacing of the game. 
Iron and Gold farms will be more rewarding than a crop farm or melon and pumpkin farm.
* Exploring through structures is an easy way to find coins with rarer structures offering higher value coins.
* Nether mobs (Piglins and Wither Skeletons) and structures are more likely to reward Gold Villager Coins.
* Piglins might try to steal your Gold Villager Coins.
* Netherite Villager Coins, like Netherite, are immune to fire damage making them the best way to secure large fortunes.

**Options**

_Coins_

* currencyConversionMultiplier - The conversion value between currencies in the following order: Copper <-> Iron <-> Gold <-> Emerald <-> Netherite. (Default: 100)
* maximumCoinStackSize - Maximum amount of coins in a single stack. (Default: 5000; Capped at: 1073741822)
* enableCoinFlipping - Allow players to flip a single coin for a heads or tails result.

* copperDropMinimum - Minimum amount to drop for Copper coins, if the chance is successful.
* ironDropMinimum - Minimum amount to drop for Iron coins, if the chance is successful.
* goldDropMinimum - Minimum amount to drop for Gold coins, if the chance is successful.
* emeraldDropMinimum - Minimum amount to drop for Emerald coins, if the chance is successful.
* netheriteDropMinimum - Minimum amount to drop for Netherite coins, if the chance is successful.

* copperDropMaximum - Maximum amount to drop for Copper coins, if the chance is successful.
* ironDropMaximum - Maximum amount to drop for Iron coins, if the chance is successful.
* goldDropMaximum - Maximum amount to drop for Gold coins, if the chance is successful.
* emeraldDropMaximum - Maximum amount to drop for Emerald coins, if the chance is successful.
* netheriteDropMaximum - Maximum amount to drop for Netherite coins, if the chance is successful.

* copperDropChance - Chance that Copper coins will drop on mob death or loot table generation.
* ironDropChance - Chance that Iron coins will drop on mob death or loot table generation.
* goldDropChance - Chance that Gold coins will drop on mob death or loot table generation.
* emeraldDropChance - Chance that Emerald coins will drop on mob death or loot table generation.
* netheriteDropChance - Chance that Netherite coins will drop on mob death or loot table generation.

_Effect Coins_

* inventoryEffectChancePerTick - Chance per tick that a coin with an effect will apply the effect. (Default: 1)

_Loot Tables_

* addCoinsToStructureLootTables - Adds Coins to Structure loot tables. (Default: true)

_Mob Drops_

* addCoinsToMobDrops - Adds Villager Coins to Mob drops. (Default: true)
* enableBreedableMobDrops - Allows coins to drop from mobs with a breeding mechanic. (Default: false)
* lootingBonusPerLevel - Bonus chance per level of looting coins will drop from mobs. (Default: 0.1)

_Trades_

* enableTradeModifications - Modifies trades and replaces Emerald trades with Coin trades. (Default: true)
* goldCoinSellItemDivisor - Price divisor for Gold Coin trades. (Default: 2)
* goldCoinSellItemMaximum - Maximum amount of Gold Coins for Gold Coin trades.
* goldForDiamond - Amount of Gold Coins to reward for a single Diamond.
* goldForEmerald - Amount of Gold Coins to reward for a single Emerald.
* chanceDiamondBecomesEmeraldTrade - Chance that a Diamond trade becomes an Emerald trade.

## Support

* Request features and report bugs at https://github.com/VillagerUnknown/VillagerCoin/issues
* View the changelog at https://github.com/VillagerUnknown/VillagerCoin/blob/main/CHANGELOG.md

## Developer Wiki

Villager Coin allows players, and communities, to design their own coin economy. 
Mod and resource pack developers can easily customize Villager Coin to make the coins look and function however they'd like. 
Villager Coin provides the core features while allowing users to choose and create the add-on features they want.

* Learn more about customizing Villager Coin at https://github.com/VillagerUnknown/VillagerCoin/wiki