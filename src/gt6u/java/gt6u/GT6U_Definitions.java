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
 * the GregTech6-Unofficial redefinitions of four recipe maps of gregapi/data/RM.java, given to GT6 as an addon service.
 */

package gt6u;

import java.util.function.Supplier;

import gregapi.api.IGT6Definitions;
import gregapi.recipes.Recipe.RecipeMap;
import gregapi.recipes.maps.RecipeMapFormingPress;

import static gregapi.data.CS.*;

/** GregTech6-Unofficial's changes to GT6's static definitions, asked by GT6 while it builds them (META-INF/services). */
public class GT6U_Definitions implements IGT6Definitions {
	@Override
	public RecipeMap recipeMap(String aNameInternal, Supplier<RecipeMap> aGT6) {
		switch (aNameInternal) {
		case "gt.recipe.heatmixer": return heatMixer();
		case "gt.recipe.fermenter": return fermenter();
		case "gt.recipe.press"    : return press();
		case "gt.recipe.assembler": return assembler();
		default: return null;
		}
	}
	
	/** GT6U's definitions of four GT6 maps (GT6U RM.java:106-150); they differ from GT6's only where GT6U changed them. */
	private static RecipeMap heatMixer() {return new RecipeMap                     (null, "gt.recipe.heatmixer"                    , "Heat Mixer"                      , null, 0, 1, RES_PATH_GUI+"machines/HeatMixer"                 ,/*IN-OUT-MIN-ITEM=*/ 6, 6, 0,/*IN-OUT-MIN-FLUID=*/ 6, 6, 0,/*MIN*/ 2,/*AMP=*/ 1, ""                    ,    1, ""      , T, T, T, T, F);}
	private static RecipeMap fermenter() {return new RecipeMap                     (null, "gt.recipe.fermenter"                    , "Fermenter"                       , null, 0, 1, RES_PATH_GUI+"machines/Fermenter"                 ,/*IN-OUT-MIN-ITEM=*/ 2, 1, 1,/*IN-OUT-MIN-FLUID=*/ 2, 1, 0,/*MIN*/ 1,/*AMP=*/ 1, ""                    ,    1, ""      , T, T, T, T, F, T, T);}
	private static RecipeMap press    () {return new RecipeMapFormingPress         (null, "gt.recipe.press"                        , "Press"                           , null, 0, 1, RES_PATH_GUI+"machines/Press"                     ,/*IN-OUT-MIN-ITEM=*/ 3, 1, 2,/*IN-OUT-MIN-FLUID=*/ 1, 1, 0,/*MIN*/ 0,/*AMP=*/ 1, ""                    ,    1, ""      , T, T, T, T, F, T, T);}
	private static RecipeMap assembler() {return new RecipeMap                     (null, "gt.recipe.assembler"                    , "Circuit Assembler"               , null, 0, 1, RES_PATH_GUI+"machines/Assembler"                 ,/*IN-OUT-MIN-ITEM=*/ 9, 1, 1,/*IN-OUT-MIN-FLUID=*/ 1, 0, 0,/*MIN*/ 1,/*AMP=*/ 1, ""                    ,    1, ""      , T, T, T, T);}
}
