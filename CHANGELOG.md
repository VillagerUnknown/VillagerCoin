# Changelog

All notable changes to this project will be documented in this file.

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