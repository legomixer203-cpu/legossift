package net.mcreator.legossift.client.renderer;

import net.minecraft.resources.Identifier;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.mcreator.legossift.entity.model.BlubModel;
import net.mcreator.legossift.entity.BlubEntity;

import com.geckolib.renderer.base.RenderPassInfo;
import com.geckolib.renderer.base.BoneSnapshots;
import com.geckolib.renderer.GeoEntityRenderer;
import com.geckolib.constant.dataticket.DataTicket;

public class BlubRenderer extends GeoEntityRenderer<BlubEntity, LivingEntityRenderState> {
	public static final DataTicket<Float> VISUAL_SCALE = DataTicket.create("legos_sift_blub_visual_scale", Float.class);

	public BlubRenderer(EntityRendererProvider.Context context) {
		super(context, new BlubModel());
		this.shadowRadius = 0.5f;
	}

	@Override
	public RenderType getRenderType(LivingEntityRenderState renderState, Identifier texture) {
		if (!renderState.isInvisible)
			return RenderTypes.entityTranslucent(texture);
		return super.getRenderType(renderState, texture);
	}

	@Override
	public void adjustModelBonesForRender(RenderPassInfo<LivingEntityRenderState> renderPassInfo, BoneSnapshots snapshots) {
		// Hide Blockbench utility bones (same as before)
		snapshots.ifPresent("hitbox", snapshot -> snapshot.skipRender(true).skipChildrenRender(true));
		snapshots.ifPresent("tag_name", snapshot -> snapshot.skipRender(true).skipChildrenRender(true));
		// The head rotation is now done with this render helper to target the bone
	}

	@Override
	public void addRenderData(BlubEntity animatable, Void relatedObject, LivingEntityRenderState renderState, float partialTick) {
		float scale = 2f;
		renderState.addGeckolibData(VISUAL_SCALE, scale);
	}

	@Override
	public void scaleModelForRender(RenderPassInfo<LivingEntityRenderState> renderPassInfo, float widthScale, float heightScale) {
		float scale = renderPassInfo.renderState().getOrDefaultGeckolibData(VISUAL_SCALE, 1f);
		super.scaleModelForRender(renderPassInfo, widthScale * scale, heightScale * scale);
	}
}