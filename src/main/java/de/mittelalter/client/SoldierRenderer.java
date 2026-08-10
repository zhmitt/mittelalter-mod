package de.mittelalter.client;

import de.mittelalter.soldier.SoldierEntity;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.monster.zombie.ZombieModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.state.ZombieRenderState;
import net.minecraft.resources.Identifier;

public final class SoldierRenderer extends HumanoidMobRenderer<SoldierEntity, ZombieRenderState, ZombieModel<ZombieRenderState>> {
    private static final Identifier TEXTURE = Identifier.withDefaultNamespace("textures/entity/zombie/zombie.png");

    public SoldierRenderer(EntityRendererProvider.Context context) {
        super(context, new ZombieModel<>(context.bakeLayer(ModelLayers.ZOMBIE)), 0.5F);
    }

    @Override
    public ZombieRenderState createRenderState() { return new ZombieRenderState(); }

    @Override
    public Identifier getTextureLocation(ZombieRenderState state) { return TEXTURE; }
}
