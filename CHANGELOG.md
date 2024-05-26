# Change Log

### 1.0.0 (in progress)
#### Features
- Complete overhaul for Multi-loader support and to allow for dynamic resources and registration
- Added support for Fabric Mod Loader
- Re-designed Arch Models
- Overhauled Arch Block placement/update logic
- Re-designed Balustrade Models
- Added new block states to Balustrade Block so they can connect like walls
- Re-designed Capital Models
- Added new block states to Capital Blocks so they can connect like stairs
- Added Arched top version of Window and Half Window models
- Added new block states to Window and Window Half Blocks so they can have an arched top

#### Bugfixes

---
### 0.1.6
#### Features
- Added STONE_VARIANTS_WITHOUT_SLABS to ModBlockHelper
- Added Slabs to Stripped Wood variants
- Refactored Variant Lists for easier management of BlockManager variants
- Added fixBlockTex for Chalk Pillar and Purpur Pillar _(Pillars have "top" texture on top and bottom)_
- Added fixBlockTex for Create Variants

#### Bugfixes
- Removed non BlockManager item model data generation _(this was causing item models to appear invisible in inventory)_
- Adjusted texture alignment for Pillar models _(textures were misaligned)_


---
### 0.1.5
- Downgraded Forge Version <- 47.1.3 to allow NeoForge 1.20.1 support
- Added Window Shutter Blocks
- Added LootTable Provider Mixin
- Made changes to BlockManager to allow for Mod Compatibility blocks
- Bugfix: fixed ArchBlock class and models so textures display properly
- Bugfix: fixed Tall Doors sounds not playing
- Bugfix: fixed Talls Doors occlusion issues when in open state

---
### 0.1.4
- Added Diagonal Beam Blocks

---
### 0.1.3
- Added Buttons, Signs, Hanging Signs, Doors, Tall Doors, and Trapdoors to BlockManager
- Added Block and Item Tag Data Generation
- Refactored Slabs and Vertical Slabs to Layers and Vertical Layers (this just makes sense to avoid complications with vanilla slabs)
- Added CornerSlabLayers and VerticalCornerSlabLayers to add layers functionality to Corner Slab variants
- Added EighthLayer blocks to add layers functionality to Eighth Blocks
- Bugfix: fixed QuarterLayerBlock and VerticalQuarterLayerBlock textures

---
### 0.1.2
- Removed Pressure Plate Variant from Planks variants as it exists in Vanilla Minecraft
- Added Mud Brick to BlockProps
- Fixed block naming and textures to account for Stripped Bamboo Block Variants
- Removed and consolidated redundant Enums and ModBlockStateProperties

---
### 0.1.1
- Added BlockProps to ensure block property consistency across mods
- Added README.md

---
### 0.1.0
- Initial Release
- This mod is in the early development phase
- All classes moved from CaliberMod for use in other mods
- Added BlockState/Model DataGen for Hanging Signs
- Added Missing layer_block model





















