# VillagerUnknown's Villager Coin

Meet the last currency mod you'll ever need!

Villager Coin is a lightweight currency library that adds customizable coin currencies and optionally adds the currencies to loot tables, mob drops, and merchant trades.
Additional features can be enabled with add-on mods, like different types of coins, coin banks, and coin stacks.

On its own, Villager Coin consists of 5 types of coins with their own value: Copper, Iron, Gold, Emerald, and Netherite.
Coins can be converted between different types through the crafting table.

Each type of coin stacks up to 1,000 coins by default to reduce inventory clutter and can be converted between the different coins through the crafting table.
The stack size can be configured, but it's limited to a maximum of 1,073,741,822.

Learn how to customize Villager Coin with add-on mods and the developer wiki at the bottom of this page.

## Features

* Adds 5 core coin items with individual currency values: Copper, Iron, Gold, Emerald, and Netherite.
* Convert between the different coins with the crafting tables.
* Smelt down Copper, Iron, and Gold coins for their respective resources.
* Adds advancements for obtaining coins.
* Options for fully customizing the coin value, chances, and amounts.
* Optionally find coins in vanilla structures.
* Optionally find coins in modded structures.
* Optionally earn coins by killing vanilla mobs.
* Optionally earn coins by killing modded mobs.
* Optionally earn coins by killing breedable mobs. (For easier coins.)
* Optionally change trades for Villagers, and Wandering Traders, to replace the Emerald trades with Coin trades.
* Optionally allow players to flip individual coins for a heads or tails result in chat.
* Optionally allow coins to sink in liquids.
* _Additional features can be enabled with add-ons like Receipts, Ledgers, Coin Banks, and Coin Stacks._

## Coin Currency

The value of coins can be configured through a Currency Multiplier option, with a default multiplier of 100. 
Copper coins are used as the base value of 1 with each coin above it multiplying by the Currency Multipler.

By default, Villager Coin includes Copper, Iron, and Gold coins in the loot tables and mob drops with lower value coins being more common. 

_Unless configured otherwise, Emerald and Netherite coins are only available through currency conversion crafting due to their high values. 
This includes any Emerald or Netherite coins included by addon mods. Learn how to enable emerald and netherite coins 
at [https://github.com/VillagerUnknown/VillagerCoin/wiki/Customize-Your-Coin-Economy](https://github.com/VillagerUnknown/VillagerCoin/wiki/Customize-Your-Coin-Economy)_

### Currency Values

With the default currency multiplier of 100:

* Copper Coin = 1 Copper Coin
* Iron Coin = 100 Copper Coins
* Gold Coin = 10,000 Copper Coins
* Emerald Coin = 1,000,000 Copper Coins
* Netherite Coin = 100,000,000 Copper Coins

Collectable coins can have modified values configurable through the options.

### Currency Conversions

With the default currency multiplier of 100:

* 100 Copper Coins = 1 Iron Coin
* 100 Iron Coins = 1 Gold Coin
* 100 Gold Coins = 1 Emerald Coin
* 100 Emerald Coins = 1 Netherite Coin

The crafting tables will calculate the conversions for you.

### Currency Conversion Crafting

Convert between coin currencies using the crafting table and auto-crafter.

* Insert a single stack of 99 or fewer of the same coin in a crafting table to convert down.
* Insert 100 or more of the same coin in a crafting table to convert up.

The crafting table will calculate the total value of the coins and allow you to craft the highest value coin while leaving the remainder. 

You can also place multiple stacks of coins in the crafting table to combine them and craft the maximum amount of coins.

Additionally, coins will only appear as a crafting result if registered as such. 
Meaning: Coins added by addon mods won't appear as a crafting result unless registered as a crafting result by the addon.

Copper, Iron, and Gold Villager Coins can be smelted down to their respective resources using the Furnace or Blast Furnace.

_Due to technical limitations, quick-crafting (shift-clicking crafted items to craft the maximum) will only craft once. 
Use the auto-crafter to convert large amounts of coins._

## Loot Tables

Coins can be optionally included in the loot tables. 
The value of the coins will vary depending on the loot table rarity. 

Trial Chambers, Ancient Cities, Strongholds, and Nether Fortresses will reward players more than a Village, Mineshaft, or Fishing.
Nether and End structures are more likely to reward Iron and Gold Villager Coins. 

If Emerald or Netherite coins are configured to drop, they will only be included in End Cities and Boss mobs.

Loot table weights and rolls can be configured through the options.

_An additional option, and config file, exists to include, or exclude, coins from modded loot tables._

## Mob Drops

Coins can optionally drop when a player kills Humanoid mobs, Bosses, and Shulkers. 

An additional option exists to allow coin drops from breedable mobs if you want coins to be more common.

The value of the coins will vary depending on the difficulty of the mob. 
The Ender Dragon, Warden, and Wither will reward players more than a Zombie, Skeleton, or Piglin.

Mob drop chances, minimums, and maximums can be configured through the options.

_An additional option, and config files, exist to include, or exclude, coins from modded mob drops._

## Trade Modifications

Coins can optionally be used for trading with Villagers and Wandering Traders by replacing Emerald trades with Coin trades. 

_This will not change any existing villager trades in existing worlds. Only new villager trades will use the coins, if enabled._

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

_Trade modifications scale appropriately with the Vanilla trading values for professions. 
Some mods may use exaggerated values resulting in trades offering high value coins at a lower level. 
It is important for Villager Coin economies to use modded Villagers with appropriate trade values for the appropriate profession. 
If you are a fellow mod-maker and would like your custom Villagers to be compatible with Villager Coin, 
please make sure you use the appropriate values for the appropriate profession levels._

## Add-ons

Additional features can be enabled with addons like Receipts, Ledgers, Coin Banks, and Coin Stacks.

Check out the list of add-ons for Villager Coin at https://github.com/VillagerUnknown/VillagerCoin/wiki/Villager-Coin-Addons

### Receipts

With add-ons, you can add craftable Receipts from multiple add-ons to record transactions.

* The type of Receipt you can craft is randomly selected at the time of crafting from the added Receipts. 
_It is up to you to make sure that the Receipts you add with add-ons all provide the same functionality._
* Receipts can be added to Ledgers for bookkeeping purposes.
* Receipts are crafted with Paper and Coins.
* A custom footnote can be included on the Receipt by renaming the Paper ingredient before crafting the Receipt.

### Ledgers

With add-ons, you can add craftable Ledgers from multiple add-ons to catalog Receipts for bookkeeping purposes.

* The type of Ledger you can craft is randomly selected at the time of crafting from the added Ledgers.
_It is up to you to make sure that the Ledgers you add with add-ons all provide the same functionality._
* Ledgers are crafted with a Book and Quill and any number of Receipts.
* Receipts can be continuously added to Ledgers, up to 100 Receipts/pages.

### Coin Banks

With add-ons, you can add craftable Coin Banks from multiple add-ons to hold Coins.

* Coin Banks are crafted with a Coin and themed resources.
* Coin Banks always drop their crafting ingredients.
* If broken without Silk Touch, the Coin Banks drop their total amount in the highest valued Coins.
* Coin Banks can accept coins from hoppers pointed directly down into them.

### Coin Stacks

With add-ons, you can add craftable Coin Stacks from multiple add-ons to display coins. 

* Coin Stacks are crafted with a stack of Coins surrounded by 4 String in a 3x3 crafting table.
* If broken without Silk Touch, the Coin Stacks drop their total amount in the highest valued coins.

## Additional Notes

* I recommend installing a Bookkeeping addon if you're on a multiplayer server.
* I highly recommend installing Coin Bank and Coin Stack addons as they allow players to store their wealth physically in the world. This not only allows players to create cool looking treasure stashes and vaults but it also offers the opportunity for heists on multiplayer servers.
* If you have a single coin in your hand and interact (right-click) you can optionally flip a coin, for a heads or tails result in chat.
* Trades are modified for both vanilla and modded Villagers.
* This mod will change the significance of farms and general pacing of the game. 
Iron and Gold farms will be more rewarding than a crop farm or melon and pumpkin farm.
* Exploring through structures is an easy way to find coins with rarer structures offering higher value coins.
* Nether mobs (Piglins and Wither Skeletons) and structures are more likely to reward Gold Villager Coins.
* Piglins might try to steal your Gold Villager Coins.
* Netherite Villager Coins, like Netherite, are immune to fire damage making them the best way to secure large fortunes.

## Support

* Request features and report bugs at https://github.com/VillagerUnknown/VillagerCoin/issues
* View the changelog at https://github.com/VillagerUnknown/VillagerCoin/blob/main/CHANGELOG.md

### Supported Languages

* English _by VillagerUnknown_
* Pirate English
* Svenska (Swedish)
* Français (French)
* Italiano (Italian)
* Deutsch (German)
* Português do Brasil (Brazil Portuguese)
* Русский (Russian)
* 한국어 (Korean)
* 简体中文 (Simplified Chinese)
* 日本語 (Japanese)
* हिन्दी (Hindi)
* (Hebrew) עברית

_Disclaimer: Unless noted otherwise, translations have been provided by automated services._

### Configurable Options

The list of configurable options, and their default values, can be found on the Wiki at https://github.com/VillagerUnknown/VillagerCoin/wiki/Configurable-Options

### Compatibility

Villager Coin includes options, and additional configuration files, to include Copper, Iron, and Gold coins in modded loot tables and mob drops. 
In most cases, Villager Coin should "just work" with other mods. Additional config files exist if you want to explicitly exclude or include coins for a specific loot table or mob.

_If a loot table is modified by a data pack, Villager Coin will not inject coins into those loot tables. This does not apply to mob drops._

### Wiki & Developer Discussions

Villager Coin allows players, and communities, to design their own coin economy.

Mod and resource pack developers can easily customize Villager Coin to make the coins look and function however they'd like.

Villager Coin provides the core features while allowing users to choose and create the add-on features they want.

Learn more about customizing Villager Coin at https://github.com/VillagerUnknown/VillagerCoin/wiki