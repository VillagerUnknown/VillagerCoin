# Changelog

All notable changes to this project will be documented in this file.

## [1.0.7]

This update resolves an issue with modded containers causing a fatal crash when saving stacks greater than 99.

This update moves Receipts from Villager Coin to their own addon: Bookkeeping for Villager Coin. 
_If you have any Receipts in your world, it is advised that you wait to update until the Bookeeping addon is approved, 
and you can download it to keep your Receipts intact._

### Added

- Added missing player stats for inserting coins in Coin Banks, crafting Villager Coins, crafting Receipts, and crafting Coin Stacks.
- Added `LedgerCraftingFeature` to allow addons to provide craftable Ledgers that allows players to catalog their receipts.

### Changed

- Changed default `maximumCoinStackSize` to 1,000.
This is to be more inline with the Coin Stack recipes for full blocks. 
This also makes it clearer how many coins are in a stack rather than an obscured amount. ("1250" appears as "1k+") 
Additionally, this reduces the overall chances of amounts exceeding the maximum long value.
_Existing stacks remain capped at 5,000 and, when over 1,000, can be picked up but will only place 1,000 when trying to place back in an inventory slot. 
This value can still be controlled through the config. It will remain at 5000, per the config, for existing worlds._
- Changed Coin Bank crafting recipe to add the value of the ingredient coin to the crafted Coin Bank. 
The recipe will still only consume 1 Coin per Coin Bank. 
This makes it meaningful to use higher value coins in the recipe.
- The thank-you message on receipts can now be changed by renaming the paper ingredient with an anvil before crafting the receipt.
- Changed Warden mob drop from Netherite to Gold as Wardens can easily be farmed in an automated way.

### Removed

- Removed receipts from core and into their own addon: Bookkeeping for Villager Coin. 
This allows players to choose which receipts they want in their world, the official ones or other modded ones. 
The Bookkeeping addon also introduces related items, like a Ledger that allows players to catalog their receipts. 
_Villager Coin still provides the core shared `ReceiptFeature` and `ReceiptCraftingFeature` functionality of receipts for addons._

### Fixed

- Fixed an issue with modded containers, not implementing vanilla methods, causing a fatal crash when saving stacks greater than 99. 
This was resolved by forcing `Codecs.rangedInt` to set the max value to the `Integer.MAX_VALUE` if the max is greater than 99.

## [1.0.6]

### Added

- Added craftable Villager Coin Receipts. Place a piece of paper in a crafting table with a stack of coins to craft a receipt.
- Added coin specific subtitles.
- Added value tooltip to coin items with a currency component.
- Added option to allow coin entities to sink, instead of float, in water and lava.
- Added option to toggle Coin Stacks breaking on collision. 
_The default value prevents breaking on collision. 
This is to match the expected functionality of Minecraft blocks._
- Added `CoinStackCraftingFeature` to allow crafting Coin Stacks.

### Changed

- Changed Currency values to use a Long number type instead of an Integer. This to allow larger values. 
Safety measures have been put in place for instances where an integer is required. 
Coin Banks are still capped at the maximum Integer value.
- Players in Creative mode no longer break Coin Stacks by walking over them. 
_This change is to make it easier for mapmakers to work with the Coin Stacks._
- Flipping a coin now sends a signal to sculk sensors.
- Inserting coins in Coin Banks now sends a signal to sculk sensors.
- If collisions are enabled, Coin Stacks no longer drop coins when a player collides with them while sneaking/crouched.
- Coin Stacks now drop their item form when broken with Silk Touch.

## [1.0.5]

### Added

- Added `getSmallestCoin` utility method to `CoinCraftingFeature`. 
This allows developers to get an ItemStack with a total value in Copper Villager Coins.
- Added `CoinStackBlocksFeature`.
- Added `AbstractCoinStackBlock` and `AbstractCoinCollectionBlock`.

### Changed

- Coin Bank Blocks now accept coins from hoppers above them that are pointed down.
- Changed `AbstractCoinBankBlock` to extend `AbstractCoinCollectionBlock`.
- Changed `AbstractCoinCollectionBlock` to implement `Waterloggable`. Coin Banks and Coin Stacks can now be waterlogged.
- Changed Value tooltips for Coin Banks and Coin Stacks to a readable format.

## [1.0.4]

### Added

- Coin Bank Blocks now provide a comparator output relative to their value limit of 2,147,483,647. 
The output signal will be 1 if there is at least 1 coin, and it will be 15 when it reaches the maximum.

### Fixed

- Coin Bank Blocks now properly drop their coins when broken in various ways (pistons, water, arrows, etc.).
- Coin Bank Blocks now properly respect their value limit of 2,147,483,647.

## [1.0.3]

### Added

- Added `CoinBankBlocksFeature`, `AbstractCoinBankBlock`, and `AbstractCoinBankBlockEntity` to allow addon mods to 
provide "Coin Bank" blocks that can only hold coins, hold the value of the coins if broken with silk touch, 
and drop the total value in coins when broken without silk touch.
- Added additional item tags for the various types of coins.
- Added block tag for coin banks.

### Changed

- Changed location of components from `villagercoin.data.component` to `villagercoin.component`.

### Fixed

- Fixed issue with multiple coins in the crafting table where smaller coins were being consumed before larger coins.

## [1.0.2]

### Added

- Added options for Edible coins.

### Changed

- Changed `StructuresIncludeCoinsFeature` to use `LootTableComponent` data.
- Changed `MobsDropCoinsFeature` to use `DropComponent` data.
- Added `LootTableComponent` to hold information about a coins loot table.
- Added `DropComponent` to hold information about a coins mob drop chances.

### Removed

- Removed `JUNGLE_TEMPLE_DISPENSER_CHEST` loot table from the Iron coin loot table.

### Fixed

- Loot tables now work as intended.
- Mob drops now work as intended.

## [1.0.1]

Villager Coin has been rewritten as a coin currency library and now only provides the core currency coins in favor of addon mods providing additional coins. 
Many values have been exposed through the configuration file to create a customized coin economy. 
New features have been added to the code to provide a simpler experience for mod developers. 
More information about customizing Villager Coin can be found on the new Wiki at https://github.com/VillagerUnknown/VillagerCoin/wiki

### Added

- Added option to set currency conversion multiplier.
- Added option to set maximum number of coins in a stack. The default remains 5,000.
- Added options for managing Coin drop values.
- Added options for managing Gold Coin trades.
- Added the `EdibleCoinFeature` class to allow addon developers to easily add edible coins that players can eat.
- Added the `CollectableCoinFeature` class to allow addon developers to easily add collectable coins with a limited number in existence.
- Added the `InventoryEffectCoinFeature` class to allow addon developers to easily add coins with a limited number in existence that apply status effects when in a player's inventory.

### Changed

- Changed the code to make it friendlier for other developers.
- Changed item group for Villager Coin items.

### Removed

- Removed edible coin items and moved them to an addon mod: Edible Coins for Villager Coin.

## [1.0.0]

_Initial release for Minecraft 1.21.1_