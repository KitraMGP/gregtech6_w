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

import gregapi.util.ST;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

/** Typed engine dispatcher; recipe data, experience and showcase belong to FurnaceRecipes. */
public final class GT6SmeltingDispatcher extends SmeltingRecipe {
	public static final com.mojang.serialization.MapCodec<GT6SmeltingDispatcher> CODEC = com.mojang.serialization.codecs.RecordCodecBuilder.mapCodec(i -> i.group(
		com.mojang.serialization.Codec.FLOAT.optionalFieldOf("xp", 0.0F).forGetter(AbstractCookingRecipe::experience)).apply(i, GT6SmeltingDispatcher::new));
	public static final net.minecraft.network.codec.StreamCodec<net.minecraft.network.RegistryFriendlyByteBuf, GT6SmeltingDispatcher> STREAM_CODEC = new net.minecraft.network.codec.StreamCodec<>() {
		@Override public GT6SmeltingDispatcher decode(net.minecraft.network.RegistryFriendlyByteBuf aBuf) {return new GT6SmeltingDispatcher(aBuf.readFloat());}
		@Override public void encode(net.minecraft.network.RegistryFriendlyByteBuf aBuf, GT6SmeltingDispatcher aRecipe) {aBuf.writeFloat(aRecipe.experience());}
	};
	public static final RecipeSerializer<GT6SmeltingDispatcher> SERIALIZER = new RecipeSerializer<>(CODEC, STREAM_CODEC);
	private final FurnaceRecipes.Dispatch mDispatch;
	public GT6SmeltingDispatcher() {this(0);}
	public GT6SmeltingDispatcher(float aXP) {
		super(new net.minecraft.world.item.crafting.Recipe.CommonInfo(false), new AbstractCookingRecipe.CookingBookInfo(CookingBookCategory.MISC, ""), Ingredient.of(Items.BARRIER), new net.minecraft.world.item.ItemStackTemplate(Items.FURNACE), aXP, 200);
		mDispatch = FurnaceRecipes.smelting().new Dispatch(aXP);
	}
	@Override public boolean matches(SingleRecipeInput aInput, Level aLevel) {return mDispatch.matches(aInput.item());}
	@Override public ItemStack assemble(SingleRecipeInput aInput) {return mDispatch.assemble(aInput.item());}
	@Override public boolean isSpecial() {return true;}
	@Override public Ingredient input() {
		java.util.List<ItemStack> tInputs = mDispatch.showcase();
		if (tInputs.isEmpty()) return Ingredient.of(Items.BARRIER);
		return Ingredient.of(tInputs.stream().map(ItemStack::getItem).distinct());
	}
	@Override public java.util.List<net.minecraft.world.item.crafting.display.RecipeDisplay> display() {
		java.util.List<net.minecraft.world.item.crafting.display.SlotDisplay> tInputs = new java.util.ArrayList<>();
		for (ItemStack tInput : mDispatch.showcase()) tInputs.add(new net.minecraft.world.item.crafting.display.SlotDisplay.ItemStackSlotDisplay(net.minecraft.world.item.ItemStackTemplate.fromNonEmptyStack(tInput)));
		return java.util.List.of(new net.minecraft.world.item.crafting.display.FurnaceRecipeDisplay(
			tInputs.isEmpty() ? net.minecraft.world.item.crafting.display.SlotDisplay.Empty.INSTANCE : new net.minecraft.world.item.crafting.display.SlotDisplay.Composite(tInputs),
			net.minecraft.world.item.crafting.display.SlotDisplay.AnyFuel.INSTANCE, new net.minecraft.world.item.crafting.display.SlotDisplay.ItemStackSlotDisplay(result()),
			new net.minecraft.world.item.crafting.display.SlotDisplay.ItemSlotDisplay(Items.FURNACE), cookingTime(), experience()));
	}
	@SuppressWarnings("unchecked")
	@Override public RecipeSerializer<SmeltingRecipe> getSerializer() {return (RecipeSerializer<SmeltingRecipe>)(RecipeSerializer<?>)SERIALIZER;}
}
