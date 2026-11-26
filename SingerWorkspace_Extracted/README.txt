SINGER SIFT DEMO — MCreator 2026.2 / NeoForge 26.1.2

This is an MCreator shareable-workspace ZIP. Import it using:
  File > Import workspace from shareable ZIP

The workspace contains three MCreator elements:
  1. Singer — GeckoLib animated living entity; registry ID singerdemo:singer
  2. SoulBlock — the Soul Block
  3. SingerTick — Singer's server-side tick procedure

RENAMING
- The MCreator element is named Singer (not SingerAnimatedTest).
- The Java entity class is SingerEntity.
- Its renderer is SingerRenderer and its model adapter is SingerModel.
- Its registry name remains singer, so the summon command and spawn egg use:
    /summon singerdemo:singer

CUSTOM CODE
The Singer element is code-locked in the workspace manifest so MCreator does not overwrite its custom GeckoLib entity, renderer, or model source when the element is opened. Keep it locked when changing ordinary MCreator values. Edit the Java source directly for custom behavior and attributes.

NATURAL SPAWNING
- No default biome spawn modifier is included. Singer is not added to any biome by this workspace.
- Add your own NeoForge biome modifier under:
    src/main/resources/data/<your_namespace>/neoforge/biome_modifier/
- Add `singerdemo:singer` only to the custom biome(s) where you want it to spawn.
- The Java spawn-placement registration is retained; it defines valid spawn position checks, not biome locations.

RESOURCES
- GeckoLib model: assets/singerdemo/geckolib/models/entity/singer_-_sift_mob.geo.json
- GeckoLib animation: assets/singerdemo/geckolib/animations/entity/singer_-_sift_mob.animation.json
- Singer textures: assets/singerdemo/textures/entities/
- Sound definitions: assets/singerdemo/sounds.json

This is a source workspace ZIP, not a prebuilt mod JAR. Import it into the matching MCreator 2026.2 / NeoForge 26.1.2 setup and let MCreator prepare the Gradle project. The archive has not been launched or compiled in this environment.
