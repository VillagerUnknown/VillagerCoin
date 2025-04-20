# Changelog

All notable changes to this project will be documented in this file.

## [1.0.3]

### Added

- Added `CoinBankBlocksFeature`, `AbstractCoinBankBlock`, and `AbstractCoinBankBlockEntity` to allow addon mods to 
provide "Coin Bank" blocks that can only hold coins, hold the value of the coins if broken with silk touch, 
and drop the total value in coins when broken without silk touch.
- Added additional item tags for the various types of coins.
- Added item tag for coin banks.

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