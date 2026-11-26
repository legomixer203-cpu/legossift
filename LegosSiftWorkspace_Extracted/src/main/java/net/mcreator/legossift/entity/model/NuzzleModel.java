package net.mcreator.legossift.entity.model;

import net.minecraft.resources.Identifier;

import net.mcreator.legossift.entity.NuzzleEntity;

import com.geckolib.renderer.base.GeoRenderState;
import com.geckolib.model.DefaultedEntityGeoModel;
import com.geckolib.constant.dataticket.DataTicket;
import com.geckolib.cache.GeckoLibResources;

public class NuzzleModel extends DefaultedEntityGeoModel<NuzzleEntity> {
	public static final DataTicket<String> TEXTURE = DataTicket.create("legos_sift_nuzzle_texture", String.class);
	/** Flat layout fallback when assets were imported without entity/ subfolders. */
	private static final Identifier MODEL_FLAT = Identifier.fromNamespaceAndPath("legos_sift", "nuzzle_-_sift_mob");
	private static final Identifier ANIM_FLAT = Identifier.fromNamespaceAndPath("legos_sift", "nuzzle_-_sift_mob");

	public NuzzleModel() {
		// DefaultedEntityGeoModel prefixes model/anim keys with entity/
		// matching: geckolib/models/entity/<path> and geckolib/animations/entity/<path>
		super(Identifier.fromNamespaceAndPath("legos_sift", "nuzzle_-_sift_mob"));
	}

	@Override
	public Identifier getModelResource(GeoRenderState renderState) {
		Identifier preferred = super.getModelResource(renderState);
		if (GeckoLibResources.getBakedModels().cache().containsKey(preferred))
			return preferred;
		return MODEL_FLAT;
	}

	@Override
	public Identifier getAnimationResource(NuzzleEntity animatable) {
		Identifier preferred = super.getAnimationResource(animatable);
		if (GeckoLibResources.getBakedAnimations().cache().containsKey(preferred))
			return preferred;
		return ANIM_FLAT;
	}

	@Override
	public Identifier[] getAnimationResourceFallbacks(NuzzleEntity animatable) {
		return new Identifier[]{ANIM_FLAT};
	}

	/**
	 * MCreator stores entity textures under textures/entities/ (plural),
	 * not GeckoLib DefaultedEntityGeoModel's textures/entity/.
	 */
	@Override
	public Identifier getTextureResource(GeoRenderState renderState) {
		String texture = renderState.getOrDefaultGeckolibData(TEXTURE, "nuzzle");
		return Identifier.fromNamespaceAndPath("legos_sift", "textures/entities/" + texture + ".png");
	}

	@Override
	public void addAdditionalStateData(NuzzleEntity animatable, Object relatedObject, GeoRenderState renderState) {
		renderState.addGeckolibData(TEXTURE, animatable.getTexture());
	}
}