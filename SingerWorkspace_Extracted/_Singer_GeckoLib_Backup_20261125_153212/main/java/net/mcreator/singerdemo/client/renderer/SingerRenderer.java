package net.mcreator.singerdemo.client.renderer;

import com.geckolib.renderer.GeoEntityRenderer;
import net.mcreator.singerdemo.entity.SingerEntity;
import net.mcreator.singerdemo.init.SingerdemoModEntities;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

/** GeckoLib 5 renderer. Asset paths are inferred from the singer entity id. */
public class SingerRenderer extends GeoEntityRenderer<SingerEntity, LivingEntityRenderState> {
    public SingerRenderer(EntityRendererProvider.Context context) {
        super(context, SingerdemoModEntities.SINGER.get());
    }
}
