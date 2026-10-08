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

package gregapi.recipes;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import gregapi.recipes.Recipe.RecipeMap;

/** Restating GT6 statements without their effect on GT6's Recipes: Recipe Maps (Recipes and Recipe Handlers), the crafting list and
 *  OreDict listeners hand what they would add to the capture instead. An addon takes back a GT6 Recipe this way; GT6 never opens it. */
public final class RecipeCapture {
	private RecipeCapture() {}

	private static List<Map.Entry<RecipeMap, Recipe>> sMachine = null;
	private static List<ICraftingRecipeGT> sCrafting = null;
	private static List<Map.Entry<RecipeMap, IRecipeMapHandler>> sHandlers = null;

	/** Recipes a restatement made, none of them added. */
	public static final class Captured {
		public final List<Map.Entry<RecipeMap, Recipe>> mMachine = new ArrayList<>();
		public final List<ICraftingRecipeGT> mCrafting = new ArrayList<>();
		public final List<Map.Entry<RecipeMap, IRecipeMapHandler>> mHandlers = new ArrayList<>();
	}

	public static boolean open() {return sMachine != null;}

	public static Captured capture(Runnable aRestatement) {
		Captured rCaptured = new Captured();
		List<Map.Entry<RecipeMap, Recipe>> tMachine = sMachine;
		List<ICraftingRecipeGT> tCrafting = sCrafting;
		List<Map.Entry<RecipeMap, IRecipeMapHandler>> tHandlers = sHandlers;
		sMachine = rCaptured.mMachine; sCrafting = rCaptured.mCrafting; sHandlers = rCaptured.mHandlers;
		try {aRestatement.run();} finally {sMachine = tMachine; sCrafting = tCrafting; sHandlers = tHandlers;}
		return rCaptured;
	}

	/** Called by a Recipe Map about to add a Recipe: true when the capture took it instead. */
	public static boolean take(RecipeMap aMap, Recipe aRecipe) {
		if (sMachine == null) return false;
		sMachine.add(new AbstractMap.SimpleEntry<>(aMap, aRecipe));
		return true;
	}

	/** Called by a Recipe Map about to add a Recipe Handler: true when the capture took it instead. */
	public static boolean take(RecipeMap aMap, IRecipeMapHandler aHandler) {
		if (sHandlers == null) return false;
		sHandlers.add(new AbstractMap.SimpleEntry<>(aMap, aHandler));
		return true;
	}

	/** Called by the crafting list about to add a Recipe: true when the capture took it instead. */
	public static boolean take(ICraftingRecipeGT aRecipe) {
		if (sCrafting == null) return false;
		sCrafting.add(aRecipe);
		return true;
	}
}
