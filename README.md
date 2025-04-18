# VillagerUnknown's Villager Coin

Villager Coin is a currency library that adds customizable coin currencies and optionally adds the currencies to loot tables, mob drops, and merchant trades. 
Coins can be converted between different types through the crafting table.

Villager Coin, on its own, consists of 5 types of coins each with their own value: Copper, Iron, Gold, Emerald, and Netherite. 
More coins can be added, like edible, collectable, cursed, and lucky coins, with addon mods. 

Each type of coin stacks up to 5,000 coins by default to reduce inventory clutter and can be converted between the different coins through the crafting table.
The stack size can be configured, but it's limited to a maximum of 1,073,741,822.

Learn how to customize Villager Coin with addon mods and the developer wiki at the bottom of this page.

## Features

* Adds 5 core coin items with individual currency values: Copper, Iron, Gold, Emerald, and Netherite.
* Convert between the different coins with the crafting tables.
* Smelt down Copper, Iron, and Gold coins for their respective resources.
* Adds advancements for obtaining coins.
* Options for fully customizing the coin value, chances, and amounts.
* Optionally find coins in vanilla structures.
* Optionally earn coins by killing some vanilla mobs.
* Optionally earn coins by killing breedable mobs. (For easier coins.)
* Optionally change trades for Villagers, and Wandering Traders, and replace the Emerald trades with Coin trades.
* Optionally allow players to flip individual coins for a heads or tails result in chat.

### Coin Currency

The value of coins can be configured through a Currency Multiplier option, with a default multiplier of 100. 
Copper coins are used as the base value of 1 with each coin above it multiplying by the Currency Multipler.

By default, Villager Coin includes Copper, Iron, and Gold coins in the loot tables and mob drops with lower value coins being more common. 

_Unless configured otherwise, Emerald and Netherite coins are only available through currency conversion crafting due to their high values. 
This includes any Emerald or Netherite coins included by addon mods. Learn how to enable emerald and netherite coins at https://github.com/VillagerUnknown/VillagerCoin/wiki/Customize-Your-Coin-Economy_

#### Currency Values

With the default currency multiplier of 100:

* Copper Coin = 1 Copper Coin
* Iron Coin = 100 Copper Coins
* Gold Coin = 10,000 Copper Coins
* Emerald Coin = 1,000,000 Copper Coins
* Netherite Coin = 100,000,000 Copper Coins

Collectable coins can have modified values configurable through the options.

#### Currency Conversions

* 100 Copper Coins = 1 Iron Coin
* 100 Iron Coins = 1 Gold Coin
* 100 Gold Coins = 1 Emerald Coin
* 100 Emerald Coins = 1 Netherite Coin

The crafting tables will calculate the conversions for you.

#### Currency Conversion Crafting

Convert between coin currencies using the crafting table and auto-crafter.

* Insert a single stack of 99 or fewer of the same coin in a crafting table to convert down.
* Insert 100 or more of the same coin in a crafting table to convert up.

The crafting table will calculate the total value of the coins and allow you to craft the highest value coin while leaving the remainder. 

You can optionally configure the crafting table to craft the maximum amount of coins when placing multiple stacks of coins in the crafting table together.

Additionally, coins will only appear as a crafting result if registered as such. 
Meaning: Coins added by addon mods won't appear as a crafting result unless registered as a crafting result. 

Copper, Iron, and Gold Villager Coins can be smelted down to their respective resources using the Furnace or Blast Furnace.

_Due to technical limitations, quick-crafting (shift-clicking crafted items to craft the maximum) will only craft once. 
Use the auto-crafter to convert large amounts of coins._

### Loot Tables

Coins can be optionally included in the loot tables. 
The value of the coins will vary depending on the loot table rarity. 

Trial Chambers, Ancient Cities, Strongholds, and Nether Fortresses will reward players more than a Village, Mineshaft, or Fishing.
Nether and End structures are more likely to reward Iron and Gold Villager Coins. 

If Emerald or Netherite coins are configured to drop, they will only be included in End Cities and Boss mobs.

Loot table weights and rolls can be configured through the options.

### Mob Drops

Coins can optionally drop when a player kills Humanoid mobs, Bosses, and Shulkers. 

An additional option exists to allow coin drops from breedable mobs if you want coins to be more common.

The value of the coins will vary depending on the difficulty of the mob. 
The Ender Dragon, Warden, and Wither will reward players more than a Zombie, Skeleton, or Piglin.

Mob drop chances, minimums, and maximums can be configured through the options.

### Trade Modifications

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

### Additional Notes

* If you have a single coin in your hand and interact (right-click) you can optionally flip a coin, for a heads or tails result in chat.
* Trades are modified for both vanilla and modded Villagers.
* This mod will change the significance of farms and general pacing of the game. 
Iron and Gold farms will be more rewarding than a crop farm or melon and pumpkin farm.
* Exploring through structures is an easy way to find coins with rarer structures offering higher value coins.
* Nether mobs (Piglins and Wither Skeletons) and structures are more likely to reward Gold Villager Coins.
* Piglins might try to steal your Gold Villager Coins.
* Netherite Villager Coins, like Netherite, are immune to fire damage making them the best way to secure large fortunes.

## Configurable Options

**Coins**

These options apply to every Villager Coin.

* currencyConversionMultiplier - The conversion value between currencies in the following order: Copper <-> Iron <-> Gold <-> Emerald <-> Netherite. (Default: 100)
* maximumCoinStackSize - Maximum amount of coins in a single stack. (Default: 5000; Capped at: 1073741822)
* enableCraftingMultipleToMaxCount - Allow crafting the maximum count when multiple stacks of coins are placed in the crafting table. (Default: false)
* enableCoinFlipping - Allow players to flip a single coin for a heads or tails result. (Default: true)

**Effect Coins**

These options are for status effect coins added by addon mods.

* inventoryEffectChancePerTick - Chance per tick that a coin with an effect will apply the effect. (Default: 1)

**Collectable Coins**

These options are for Collectable coins added by addon mods.

* copperMaximumCollectables - Maximum amount of coins to drop for collectable Copper coins.
* ironMaximumCollectables - Maximum amount of coins to drop for collectable Iron coins.
* goldMaximumCollectables - Maximum amount of coins to drop for collectable Gold coins.
* emeraldMaximumCollectables - Maximum amount of coins to drop for collectable Emerald coins.
* netheriteMaximumCollectables - Maximum amount of coins to drop for collectable Netherite coins.


* copperCollectableValue - Value of collectable Copper coins.
* ironCollectableValue - Value of collectable Iron coins.
* goldCollectableValue - Value of collectable Gold coins.
* emeraldCollectableValue - Value of collectable Emerald coins.
* netheriteCollectableValue - Value of collectable Netherite coins.


* copperCollectableDropChance - Drop chance of collectable Copper coins.
* ironCollectableDropChance - Drop chance of collectable Iron coins.
* goldCollectableDropChance - Drop chance of collectable Gold coins.
* emeraldCollectableDropChance - Drop chance of collectable Emerald coins.
* netheriteCollectableDropChance - Drop chance of collectable Netherite coins.

**Loot Tables**

These options configure loot tables in structure generation.

* addCoinsToStructureLootTables - Adds Coins to Structure loot tables. (Default: true)


* copperLootTableRolls - Rolls for Copper coins.
* ironLootTableRolls - Rolls for Iron coins.
* goldLootTableRolls - Rolls for Gold coins.
* emeraldLootTableRolls - Rolls for Emerald coins.
* netheriteLootTableRolls - Rolls for Netherite coins.


* copperLootTableWeight - Weight for Copper coins.
* ironLootTableWeight - Weight for Iron coins.
* goldLootTableWeight - Weight for Gold coins.
* emeraldLootTableWeight - Weight for Emerald coins.
* netheriteLootTableWeight - Weight for Netherite coins.

**Mob Drops**

These options configure drops from mobs.

* addCoinsToMobDrops - Adds Villager Coins to Mob drops. (Default: true)
* enableBreedableMobDrops - Allows coins to drop from mobs with a breeding mechanic. (Default: false)
* lootingBonusPerLevel - Bonus chance per level of looting coins will drop from mobs. (Default: 0.1)


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


* copperDropMultiplier - Amount multiplier for Copper coins, if dropped.
* ironDropMultiplier - Amount multiplier for Iron coins, if dropped.
* goldDropMultiplier - Amount multiplier for Gold coins, if dropped.
* emeraldDropMultiplier - Amount multiplier for Emerald coins, if dropped.
* netheriteDropMultiplier - Amount multiplier for Netherite coins, if dropped.

**Trades**

These options configure trade modifications for villagers, wandering traders, and other merchants.

* enableTradeModifications - Modifies trades and replaces Emerald trades with Coin trades. (Default: true)


* goldCoinSellItemDivisor - Price divisor for Gold Coin trades. (Default: 2)
* goldCoinSellItemMaximum - Maximum amount of Gold Coins for Gold Coin trades.
* goldForDiamond - Amount of Gold Coins to reward for a single Diamond.
* goldForEmerald - Amount of Gold Coins to reward for a single Emerald.
* chanceDiamondBecomesEmeraldTrade - Chance that a Diamond trade becomes an Emerald trade.

## Official Addon Mods

Search for these addon mods to further customize your Villager Coin economy:

* Edible Coins - Adds edible Villager Coins you can eat.
* Collectable Coins - Adds rarer collectable Villager Coins worth more in value.
* Cursed Coins - Adds rarer cursed Villager Coins that apply _negative_ status effects when in your inventory.
* Lucky Coins - Adds rarer lucky Villager Coins that apply _positive_ status effects when in your inventory.

## Developer Wiki (WIP)

Villager Coin allows players, and communities, to design their own coin economy. 

Mod and resource pack developers can easily customize Villager Coin to make the coins look and function however they'd like. 

Villager Coin provides the core features while allowing users to choose and create the add-on features they want.

Learn more about customizing Villager Coin at https://github.com/VillagerUnknown/VillagerCoin/wiki

## Support

* Request features and report bugs at https://github.com/VillagerUnknown/VillagerCoin/issues
* View the changelog at https://github.com/VillagerUnknown/VillagerCoin/blob/main/CHANGELOG.md
