package com.edestria.engine.display.menus.rows;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.BiFunction;

@AllArgsConstructor
@Getter
public enum Rows {

    ONE(9),
    TWO(18),
    THREE(27),
    FOUR(36),
    FIVE(45),
    SIX(54);

    private final BiFunction<Rows, Integer, Integer> slotPosition = (rows1, slot) -> (rows1.getSlots() / 9) * 9 + slot - 9 - 1;

    private final int slots;

    public int getRelativeSlot(Rows rows, int slot) {
        return this.slotPosition.apply(rows, slot);
    }
}