package net.mcreator.legossift.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.core.registries.Registries;

import net.mcreator.legossift.entity.SproutEntity;
import net.mcreator.legossift.entity.SlabberEntity;
import net.mcreator.legossift.entity.SeedlingEntity;
import net.mcreator.legossift.entity.NuzzleEntity;
import net.mcreator.legossift.entity.BlubEntity;
import net.mcreator.legossift.LegosSiftMod;

@EventBusSubscriber
public class LegosSiftModEntities {
	public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(Registries.ENTITY_TYPE, LegosSiftMod.MODID);
	public static final DeferredHolder<EntityType<?>, EntityType<BlubEntity>> BLUB = register("blub",
			EntityType.Builder.<BlubEntity>of(BlubEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)

					.sized(0.6f, 1.8f)

	);
	public static final DeferredHolder<EntityType<?>, EntityType<SlabberEntity>> SLABBER = register("slabber",
			EntityType.Builder.<SlabberEntity>of(SlabberEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)

					.sized(0.6f, 1.8f)

	);
	public static final DeferredHolder<EntityType<?>, EntityType<NuzzleEntity>> NUZZLE = register("nuzzle",
			EntityType.Builder.<NuzzleEntity>of(NuzzleEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)

					.sized(0.6f, 1.8f)

	);
	public static final DeferredHolder<EntityType<?>, EntityType<SeedlingEntity>> SEEDLING = register("seedling",
			EntityType.Builder.<SeedlingEntity>of(SeedlingEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)

					.notInPeaceful().sized(0.6f, 1.8f)

	);
	public static final DeferredHolder<EntityType<?>, EntityType<SproutEntity>> SPROUT = register("sprout",
			EntityType.Builder.<SproutEntity>of(SproutEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)

					.notInPeaceful().sized(0.6f, 1.8f)

	);

	// Start of user code block custom entities
	// End of user code block custom entities
	private static <T extends Entity> DeferredHolder<EntityType<?>, EntityType<T>> register(String registryname, EntityType.Builder<T> entityTypeBuilder) {
		return REGISTRY.register(registryname, () -> (EntityType<T>) entityTypeBuilder.build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(LegosSiftMod.MODID, registryname))));
	}

	@SubscribeEvent
	public static void init(RegisterSpawnPlacementsEvent event) {
		BlubEntity.init(event);
		SlabberEntity.init(event);
		NuzzleEntity.init(event);
		SeedlingEntity.init(event);
		SproutEntity.init(event);
	}

	@SubscribeEvent
	public static void registerAttributes(EntityAttributeCreationEvent event) {
		event.put(BLUB.get(), BlubEntity.createAttributes().build());
		event.put(SLABBER.get(), SlabberEntity.createAttributes().build());
		event.put(NUZZLE.get(), NuzzleEntity.createAttributes().build());
		event.put(SEEDLING.get(), SeedlingEntity.createAttributes().build());
		event.put(SPROUT.get(), SproutEntity.createAttributes().build());
	}
}