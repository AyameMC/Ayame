package org.ayamemc.ayame.mixins;


import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.util.GeckoLibUtil;

@Mixin(Player.class)
public abstract class PlayerMixin implements GeoEntity {
    @Unique
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        // TODO 完善默认动画，支持自定义动画
        controllers.add(new AnimationController<>(this, 40, state -> {
            // 没有移动，则播放idle动画
            if (!state.isMoving()){
                state.getController().setAnimation(DefaultAnimations.IDLE);
            }else if (state.isMoving()){
//                if (this.getDeltaMovement().lengthSqr()< 0.01){
//                    state.getController().setAnimation(DefaultAnimations.WALK);
//                }else if (this.getDeltaMovement().lengthSqr() >= 0.01){
//                    state.getController().setAnimation(DefaultAnimations.RUN);
//                }
                state.getController().setAnimation(DefaultAnimations.WALK);
            }
            return PlayState.CONTINUE;
        }));
        // TODO 添加events
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return geoCache;
    }

}
