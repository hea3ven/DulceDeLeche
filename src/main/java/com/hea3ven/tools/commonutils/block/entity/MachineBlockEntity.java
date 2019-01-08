package com.hea3ven.tools.commonutils.block.entity;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LockableContainerBlockEntity;

public abstract class MachineBlockEntity extends LockableContainerBlockEntity {

	public MachineBlockEntity(BlockEntityType<?> blockEntityType) {
		super(blockEntityType);
	}

}
