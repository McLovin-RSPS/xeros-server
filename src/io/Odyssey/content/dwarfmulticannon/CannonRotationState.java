package io.Odyssey.content.dwarfmulticannon;

import io.Odyssey.model.Direction;

/**
 * The eight directions a cannon can face, in chronological order (note: do
 * not change order, order is crucial!).
 */
enum CannonRotationState {
    NORTH(Direction.NORTH, 9255),
    NORTH_EAST(Direction.NORTH_EAST, 9255),
    EAST(Direction.EAST, 9255),
    SOUTH_EAST(Direction.SOUTH_EAST, 9255),
    SOUTH(Direction.SOUTH, 9255),
    SOUTH_WEST(Direction.SOUTH_WEST, 9255),
    WEST(Direction.WEST, 9255),
    NORTH_WEST(Direction.NORTH_WEST, 9255);

    private final Direction direction;
    private final int animationId;


    CannonRotationState(Direction direction, int animationId) {
        this.direction = direction;
        this.animationId = animationId;
    }

    public int getAnimationId() {
        return animationId;
    }
}

