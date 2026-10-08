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

package gt6u;

import java.util.Map;

import gregapi.recipes.ICraftingRecipeGT;
import gregapi.recipes.IRecipeMapHandler;
import gregapi.recipes.Recipe;
import gregapi.recipes.RecipeCapture;
import gregapi.recipes.Recipe.RecipeMap;
import gregapi.util.CR;

import static gregapi.data.CS.*;

/** Takes back GT6 Recipes GregTech6-Unofficial dropped: GT6's own statement runs with the recipe capture open, and the Recipes it
 *  would make are taken out of GT6's maps and crafting list again. */
public class GT6U_Recipes {
	public static void takeBack(Runnable aGT6Statements) {
		RecipeCapture.Captured tCaptured = RecipeCapture.capture(aGT6Statements);
		int tMissing = 0;
		for (Map.Entry<RecipeMap, Recipe> tEntry : tCaptured.mMachine) if (tEntry.getKey().removeSame(tEntry.getValue()) <= 0) tMissing++;
		for (ICraftingRecipeGT tRecipe : tCaptured.mCrafting) if (CR.removeSame(tRecipe) <= 0) tMissing++;
		for (Map.Entry<RecipeMap, IRecipeMapHandler> tEntry : tCaptured.mHandlers) if (tEntry.getKey().removeSameHandler(tEntry.getValue()) <= 0) tMissing++;
		if (tMissing > 0) ERR.println("GT6U: " + tMissing + " of " + (tCaptured.mMachine.size() + tCaptured.mCrafting.size() + tCaptured.mHandlers.size()) + " GT6 Recipes to take back were not found");
	}
}
