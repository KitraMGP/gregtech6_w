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

import gregapi.recipes.Recipe.RecipeMap;

import static gregapi.data.CS.*;

/** Where GT6's static definitions ask the addons' IGT6Definitions; without addons every answer is GT6's own. */
public final class GT6Definitions {
	private GT6Definitions() {}
	
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
}
