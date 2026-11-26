package net.mcreator.singerdemo.entity.model;

import net.minecraft.resources.Identifier;

import net.mcreator.singerdemo.entity.SingerEntity;

import com.geckolib.renderer.base.GeoRenderState;
import com.geckolib.model.DefaultedEntityGeoModel;
import com.geckolib.constant.dataticket.DataTicket;
import com.geckolib.cache.GeckoLibResources;

public class SingerModel extends DefaultedEntityGeoModel<SingerEntity> {
	public static final DataTicket<String> TEXTURE = DataTicket.create("singerdemo_singer_texture", String.class);
	/** Flat layout fallback when assets were imported without entity/ subfolders. */
	private static final Identifier MODEL_FLAT = Identifier.fromNamespaceAndPath("singerdemo", "singer_-_sift_mob");
	private static final Identifier ANIM_FLAT = Identifier.fromNamespaceAndPath("singerdemo", "singer_-_sift_mob");

	public SingerModel() {
		// DefaultedEntityGeoModel prefixes model/anim keys with entity/
		// matching: geckolib/models/entity/<path> and geckolib/animations/entity/<path>
		super(Identifier.fromNamespaceAndPath("singerdemo", "singer_-_sift_mob"));
	}

	@Override
	public Identifier getModelResource(GeoRenderState renderState) {
		Identifier preferred = super.getModelResource(renderState);
		if (GeckoLibResources.getBakedModels().cache().containsKey(preferred))
			return preferred;
		return MODEL_FLAT;
	}

	@Override
	public Identifier getAnimationResource(SingerEntity animatable) {
		Identifier preferred = super.getAnimationResource(animatable);
		if (GeckoLibResources.getBakedAnimations().cache().containsKey(preferred))
			return preferred;
		return ANIM_FLAT;
	}

	@Override
	public Identifier[] getAnimationResourceFallbacks(SingerEntity animatable) {
		return new Identifier[]{ANIM_FLAT};
	}

	/**
	 * MCreator stores entity textures under textures/entities/ (plural),
	 * not GeckoLib DefaultedEntityGeoModel's textures/entity/.
	 */
	@Override
	public Identifier getTextureResource(GeoRenderState renderState) {
		String texture = renderState.getOrDefaultGeckolibData(TEXTURE, "singer1");
		return Identifier.fromNamespaceAndPath("singerdemo", "textures/entities/" + texture + ".png");
	}

	@Override
	public void addAdditionalStateData(SingerEntity animatable, Object relatedObject, GeoRenderState renderState) {
		renderState.addGeckolibData(TEXTURE, animatable.getTexture());
	}
}