package net.mcreator.singerdemo.client.renderer;

import net.minecraft.resources.Identifier;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.mcreator.singerdemo.entity.model.SingerModel;
import net.mcreator.singerdemo.entity.SingerEntity;

import com.geckolib.renderer.base.RenderPassInfo;
import com.geckolib.renderer.base.BoneSnapshots;
import com.geckolib.renderer.GeoEntityRenderer;
import com.geckolib.constant.dataticket.DataTicket;

public class SingerRenderer extends GeoEntityRenderer<SingerEntity, LivingEntityRenderState> {
	public static final DataTicket<Float> VISUAL_SCALE = DataTicket.create("singerdemo_singer_visual_scale", Float.class);

	public SingerRenderer(EntityRendererProvider.Context context) {
		super(context, new SingerModel());
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
}