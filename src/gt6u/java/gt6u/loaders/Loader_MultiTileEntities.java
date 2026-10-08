/**
 * Copyright (c) 2020 Gregtech6-Unofficial Team
 *
 * This file is part of GregTech.
 *
 * GregTech is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * GregTech is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with GregTech. If not, see <http://www.gnu.org/licenses/>.
 *
 * Modified in 2026 for the GregTech 6 NeoForge port (https://github.com/wolfram0108/gregtech6_w):
 * the GregTech6-Unofficial entries of gregtech/loaders/b/Loader_MultiTileEntities.java moved into an addon registry.
 */

package gt6u.loaders;

import gregapi.block.MaterialMachines;
import gregapi.block.multitileentity.MultiTileEntityBlock;
import gregapi.block.multitileentity.MultiTileEntityClassContainer;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.data.MD;
import gregapi.data.MT;
import gregapi.data.OP;
import gregapi.data.RM;
import gregapi.data.TD;
import gregapi.oredict.OreDictMaterial;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import gregapi.util.OM;
import gregapi.util.ST;
import gregapi.util.UT;
import gt6u.GT6U_Main;
import gt6u.tileentity.multiblocks.MultiTileEntityExtruder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.SoundType;

import static gregapi.data.CS.*;

public class Loader_MultiTileEntities implements Runnable {
	@Override
	public void run() {
		MultiTileEntityRegistry aRegistry = MultiTileEntityRegistry.getRegistry(GT6U_Main.REGISTRY);
		MultiTileEntityBlock aMachine = MultiTileEntityBlock.getOrCreate(MD.GT.mID, "machine", MaterialMachines.instance, SoundType.METAL, TOOL_wrench, 0, 0, 15, F, F);
		OreDictMaterial aMat;

		aMat = MT.TungstenCarbide;      aRegistry.add("Dense Tungsten Carbide Wall"                         , "Multiblock Machines", 18035, 17101, MultiTileEntityMultiBlockPart.class      , aMat.mToolQuality, 64, aMachine   , UT.NBT.make(NBT_MATERIAL, aMat, NBT_HARDNESS,  12.0F, NBT_RESISTANCE,  12.0F, NBT_COLOR, UT.Code.getRGBInt(aMat.fRGBaSolid), NBT_TEXTURE, "metalwalldense"          , NBT_DESIGNS, 7)); OM.data(aRegistry.getItem(), aMat, U*36); RM.Welder.addRecipe2(F, 64, 512, OP.plateDense.mat(aMat, 4), ST.tag(10), aRegistry.getItem());
		aMat = MT.TungstenCarbide;      aRegistry.add("Large Extruder"                                      , "Multiblock Machines", 17129, 17101, MultiTileEntityExtruder.class            , aMat.mToolQuality, 16, aMachine   , UT.NBT.make(NBT_MATERIAL, aMat, NBT_HARDNESS,  12.0F, NBT_RESISTANCE,  12.0F, NBT_COLOR, UT.Code.getRGBInt(aMat.fRGBaSolid), NBT_TEXTURE, "largeextruder"           , NBT_INPUT, 2048, NBT_INPUT_MIN,  512, NBT_INPUT_MAX,    32768, NBT_EFFICIENCY,  5000, NBT_ENERGY_ACCEPTED, TD.Energy.EU, NBT_RECIPEMAP, RM.Extruder             , NBT_INV_SIDE_AUTO_OUT, SIDE_BOTTOM, NBT_TANK_SIDE_AUTO_OUT, SIDE_BOTTOM, NBT_CHEAP_OVERCLOCKING, T, NBT_PARALLEL,  64, NBT_PARALLEL_DURATION, T                          ), "CMC", "PwP", "PPP", 'M', aRegistry.getItem(18035), 'C', OD_CIRCUITS[4], 'P', OP.plateDense.dat(aMat));

		// GT6U's Diesel Engines: twice the output, less efficiency on the higher tiers. Only the ids where GT6 and GT6U
		// register the same material; GT6U moved the Iridium engine to another id, and GT6 later added Arsenic ones.
		MultiTileEntityRegistry tGT6 = MultiTileEntityRegistry.getRegistry("gt.multitileentity");
		engine(tGT6, 9147,   32, 10000); // Bronze
		engine(tGT6, 9148,   64, 10000); // Steel
		engine(tGT6, 9149,  128,  9500); // Invar
		engine(tGT6, 9197,  256,  9000); // Titanium
		engine(tGT6, 9198,  512,  8000); // Tungstensteel
	}

	private static void engine(MultiTileEntityRegistry aRegistry, int aID, int aOutput, int aEfficiency) {
		MultiTileEntityClassContainer tOld = aRegistry.getClassContainer(aID);
		CompoundTag tParameters = tOld.mParameters.copy();
		tParameters.putInt(NBT_OUTPUT, aOutput);
		tParameters.putInt(NBT_EFFICIENCY, aEfficiency);
		aRegistry.override(null, "Engines", new MultiTileEntityClassContainer(aID, tOld.mCreativeTabID, tOld.mClass, tOld.mBlockMetaData, tOld.mStackSize, tOld.mBlock, tParameters));
	}
}
