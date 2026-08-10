package de.mittelalter.soldier;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;

public final class SoldierMovementGoal extends Goal {
    private final SoldierEntity soldier;

    public SoldierMovementGoal(SoldierEntity soldier) {
        this.soldier = soldier;
    }

    @Override
    public boolean canUse() {
        return soldier.getTarget() == null && (soldier.order() == SoldierOrder.FOLLOW && soldier.resolveOwner() != null
                || soldier.order() == SoldierOrder.HOLD && soldier.holdPosition().isPresent());
    }

    @Override
    public void tick() {
        if (soldier.order() == SoldierOrder.FOLLOW) {
            var owner = soldier.resolveOwner();
            if (owner != null && soldier.distanceToSqr(owner) > 9.0) {
                soldier.getNavigation().moveTo(owner, 1.05);
            }
        } else {
            BlockPos hold = soldier.holdPosition().orElse(soldier.blockPosition());
            if (soldier.blockPosition().distSqr(hold) > 2.0) {
                soldier.getNavigation().moveTo(hold.getX() + 0.5, hold.getY(), hold.getZ() + 0.5, 1.0);
            } else {
                soldier.getNavigation().stop();
            }
        }
    }
}
