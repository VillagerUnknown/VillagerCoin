# VillagerUnknown's Villager Coin

Villager Coin is a lightweight currency library that adds customizable coin currencies and optionally adds the currencies to loot tables, mob drops, and merchant trades. 
Coins can be converted between different types through the crafting table.

Villager Coin, on its own, consists of craftable receipts and 5 types of coins with their own value: Copper, Iron, Gold, Emerald, and Netherite. 
More coins can be added, like edible, collectable, cursed, and lucky coins, with addon mods. 

Each type of coin stacks up to 5,000 coins by default to reduce inventory clutter and can be converted between the different coins through the crafting table.
The stack size can be configured, but it's limited to a maximum of 1,073,741,822.

Learn how to customize Villager Coin with addon mods and the developer wiki at the bottom of this page.

## Features

* Adds 5 core coin items with individual currency values: Copper, Iron, Gold, Emerald, and Netherite.
* Convert between the different coins with the crafting tables.
* Smelt down Copper, Iron, and Gold coins for their respective resources.
* Adds advancements for obtaining coins.
* Adds a craftable Receipt from an item with a currency value, like a stack of coins, coin stack, or coin bank, and a piece of paper. _Only the paper will be consumed. The currency items will not be consumed._
* Options for fully customizing the coin value, chances, and amounts.
* Optionally find coins in vanilla structures.
* Optionally earn coins by killing some vanilla mobs.
* Optionally earn coins by killing breedable mobs. (For easier coins.)
* Optionally change trades for Villagers, and Wandering Traders, and replace the Emerald trades with Coin trades.
* Optionally allow players to flip individual coins for a heads or tails result in chat.
* Additional features can be added with addons like Coin Banks, Coin Stacks, custom structures, 
and loot integrations with mods like the Graveyards & Ghosts and Better Archeology mods.

## Coin Currency

The value of coins can be configured through a Currency Multiplier option, with a default multiplier of 100. 
Copper coins are used as the base value of 1 with each coin above it multiplying by the Currency Multipler.

By default, Villager Coin includes Copper, Iron, and Gold coins in the loot tables and mob drops with lower value coins being more common. 

_Unless configured otherwise, Emerald and Netherite coins are only available through currency conversion crafting due to their high values. 
This includes any Emerald or Netherite coins included by addon mods. Learn how to enable emerald and netherite coins 
at [https://github.com/VillagerUnknown/VillagerCoin/wiki/Customize-Your-Coin-Economy](https://github.com/VillagerUnknown/VillagerCoin/wiki/Customize-Your-Coin-Economy)_

#### Currency Values

With the default currency multiplier of 100:

* Copper Coin = 1 Copper Coin
* Iron Coin = 100 Copper Coins
* Gold Coin = 10,000 Copper Coins
* Emerald Coin = 1,000,000 Copper Coins
* Netherite Coin = 100,000,000 Copper Coins

Collectable coins can have modified values configurable through the options.

#### Currency Conversions

With the default currency multiplier of 100:

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
Meaning: Coins added by addon mods won't appear as a crafting result unless registered as a crafting result by the addon.

Copper, Iron, and Gold Villager Coins can be smelted down to their respective resources using the Furnace or Blast Furnace.

_Due to technical limitations, quick-crafting (shift-clicking crafted items to craft the maximum) will only craft once. 
Use the auto-crafter to convert large amounts of coins._

## Receipts

Players can craft receipts for transactions by placing the payment amount of coins in the crafting table with paper. When a receipt is crafted, the date, amount, and player name will be added to the receipt. Receipts from the same player and date will stack and sum the totals. This is a fun and easy way for multiplayer servers to make transactions feel more realistic.

_Quick-crafting (shift-clicking crafted items to craft the maximum) will work as expected with receipts._

## Loot Tables

Coins can be optionally included in the loot tables. 
The value of the coins will vary depending on the loot table rarity. 

Trial Chambers, Ancient Cities, Strongholds, and Nether Fortresses will reward players more than a Village, Mineshaft, or Fishing.
Nether and End structures are more likely to reward Iron and Gold Villager Coins. 

If Emerald or Netherite coins are configured to drop, they will only be included in End Cities and Boss mobs.

Loot table weights and rolls can be configured through the options.

_While some mods may include vanilla loot tables, coins will only be included in structures added by mods if they have included coins in the loot tables. 
You can find data packs that provide compatibility at [https://github.com/VillagerUnknown/VillagerCoin/wiki/Villager-Coin-Addons](https://github.com/VillagerUnknown/VillagerCoin/wiki/Villager-Coin-Addons)_

## Mob Drops

Coins can optionally drop when a player kills Humanoid mobs, Bosses, and Shulkers. 

An additional option exists to allow coin drops from breedable mobs if you want coins to be more common.

The value of the coins will vary depending on the difficulty of the mob. 
The Ender Dragon, Warden, and Wither will reward players more than a Zombie, Skeleton, or Piglin.

Mob drop chances, minimums, and maximums can be configured through the options.

_Coins will only drop from mobs added by mods if they have included coins in the loot tables. You can find data packs that provide compatibility at [https://github.com/VillagerUnknown/VillagerCoin/wiki/Villager-Coin-Addons#compatibility](https://github.com/VillagerUnknown/VillagerCoin/wiki/Villager-Coin-Addons#compatibility)_

## Trade Modifications

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

_Trade modifications scale appropriately with the Vanilla trading values for professions. Some mods may use exaggerated values resulting in trades offering high value coins at a lower level. It is important for Villager Coin economies to use modded Villagers with appropriate trade values for the appropriate profession. If you are a fellow mod-maker and would like your custom Villagers to be compatible with Villager Coin please make sure you use the appropriate values for the appropriate levels._

## Additional Notes

* I recommend grabbing the Coin Banks and Coin Stacks addons for Villager Coin as they allow players to store their wealth physically in the world. This not only allows players to create cool looking treasure stashes and vaults but it also offers the opportunity for heists on multiplayer servers.
* If you have a single coin in your hand and interact (right-click) you can optionally flip a coin, for a heads or tails result in chat.
* Trades are modified for both vanilla and modded Villagers.
* This mod will change the significance of farms and general pacing of the game. 
Iron and Gold farms will be more rewarding than a crop farm or melon and pumpkin farm.
* Exploring through structures is an easy way to find coins with rarer structures offering higher value coins.
* Nether mobs (Piglins and Wither Skeletons) and structures are more likely to reward Gold Villager Coins.
* Piglins might try to steal your Gold Villager Coins.
* Netherite Villager Coins, like Netherite, are immune to fire damage making them the best way to secure large fortunes.

## Configurable Options

The list of configurable options, and their default values, can be found on the Wiki at https://github.com/VillagerUnknown/VillagerCoin/wiki/Configurable-Options

## Add-ons

Check out our list of add-ons for Villager Coin at https://github.com/VillagerUnknown/VillagerCoin/wiki/Villager-Coin-Addons

## Developer Wiki (WIP)

Villager Coin allows players, and communities, to design their own coin economy. 

Mod and resource pack developers can easily customize Villager Coin to make the coins look and function however they'd like. 

Villager Coin provides the core features while allowing users to choose and create the add-on features they want.

Learn more about customizing Villager Coin at https://github.com/VillagerUnknown/VillagerCoin/wiki

## Support

* Request features and report bugs at https://github.com/VillagerUnknown/VillagerCoin/issues
* View the changelog at https://github.com/VillagerUnknown/VillagerCoin/blob/main/CHANGELOG.md

### Compatibility

Compatibility data packs for the following mods can be found at https://github.com/VillagerUnknown/VillagerCoin/wiki/Villager-Coin-Addons#compatibility

* VillagerUnknown's Graveyards & Ghosts
* Pandarix's Better Archeology
