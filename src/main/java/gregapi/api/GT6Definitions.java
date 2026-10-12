/**
 * Copyright (c) 2026 wolfram0108
 *
 * Written in 2026 for the GregTech 6 NeoForge port
 * (https://github.com/wolfram0108/gregtech6_w). Not part of the original GregTech 6
 * by Gregorius Techneticies; distributed under the same licence as the work it extends.
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
 */

package gregapi.api;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.function.Supplier;

import gregapi.oredict.OreDictItemData;
import gregapi.oredict.OreDictMaterial;
import gregapi.recipes.Recipe.RecipeMap;
import net.minecraft.world.item.ItemStack;

import static gregapi.data.CS.*;

/** Where GT6's static definitions and recipe helpers ask the addons' IGT6Definitions; without addons every answer is GT6's own. */
public final class GT6Definitions {
	private GT6Definitions() {}
	
	/** The names of what GT6 asks addons about, written here only: GT6 asks and an addon answers by these constants. Recipe Maps go
	 *  by their own internal name. */
	public static final String
	  MATERIAL_SAND                = "definition.material.Sand"
	, MATERIAL_SLUICE_SAND         = "definition.material.SluiceSand"
	, MATERIAL_NEUTRONIUM          = "definition.material.Neutronium"
	, MATERIAL_TRITANIUM           = "definition.material.Tn"
	, MATERIAL_DURANIUM            = "definition.material.Dn"
	, UNKNOWN_ELEMENTS             = "definition.material.unknown"
	, STEAM_GAS                    = "definition.material.Steam.gas"
	, FLUID_KEROSENE               = "definition.fluid.kerosene"
	, REPLICATOR_POWER_PER_NUCLEON = "definition.replicator.powerPerNucleon";
	
	public static final List<IGT6Definitions> ADDONS = new ArrayList<>();
	static {
		for (IGT6Definitions tAddon : ServiceLoader.load(IGT6Definitions.class, GT6Definitions.class.getClassLoader())) {
			ADDONS.add(tAddon);
			OUT.println("GT6 definitions are changed by the addon " + tAddon.getClass().getName());
		}
	}
	
	public static RecipeMap recipeMap(String aNameInternal, Supplier<RecipeMap> aGT6) {
		for (IGT6Definitions tAddon : ADDONS) {RecipeMap rMap = tAddon.recipeMap(aNameInternal, aGT6); if (rMap != null) return rMap;}
		return aGT6.get();
	}
	
	public static OreDictMaterial material(String aName, Supplier<OreDictMaterial> aGT6) {
		for (IGT6Definitions tAddon : ADDONS) {OreDictMaterial rMaterial = tAddon.material(aName, aGT6); if (rMaterial != null) return rMaterial;}
		return aGT6.get();
	}

	public static boolean drops(String aName) {
		for (IGT6Definitions tAddon : ADDONS) if (tAddon.drops(aName)) return T;
		return F;
	}

	public static long number(String aName, long aGT6) {
		for (IGT6Definitions tAddon : ADDONS) {Long rNumber = tAddon.number(aName, aGT6); if (rNumber != null) return rNumber;}
		return aGT6;
	}
	
	/** The addon's result of making these biomass Recipes, or null when no addon makes them. */
	public static Boolean biomass(ItemStack aBiomass, long aSpeed) {
		for (IGT6Definitions tAddon : ADDONS) {Boolean rResult = tAddon.biomass(aBiomass, aSpeed); if (rResult != null) return rResult;}
		return null;
	}
	
	/** The data MultiItemRandom.addItem registers for this item: an addon's in place of GT6's, as the listeners read it at once. */
	public static OreDictItemData itemData(ItemStack aStack, OreDictItemData aGT6) {
		for (IGT6Definitions tAddon : ADDONS) {OreDictItemData rData = tAddon.itemData(aStack, aGT6); if (rData != null) return rData;}
		return aGT6;
	}
}
