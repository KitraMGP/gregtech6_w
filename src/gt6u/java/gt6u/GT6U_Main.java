/**
 * Copyright (c) 2026 GregTech 6 NeoForge port, after GregTech6-Unofficial by the GT6U-Team
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

import gregapi.GT_API;
import gregapi.api.Abstract_Mod;
import gregapi.api.Abstract_Proxy;
import gregapi.api.FMLInitializationEvent;
import gregapi.api.FMLPostInitializationEvent;
import gregapi.api.FMLPreInitializationEvent;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.code.ModData;
import gt6u.data.*;
import gt6u.loaders.Loader_MultiTileEntities;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;

import static gregapi.data.CS.*;

/** Content of GregTech6-Unofficial as an addon of GregTech 6, built on the GT API the way 1.7.10 addons were. */
@Mod(value = GT6U_Main.MOD_ID, depends = {ModIDs.GT})
public class GT6U_Main extends Abstract_Mod {
	public static final String MOD_ID = "gt6u", MOD_NAME = "GT6U Addon";
	public static final ModData MOD_DATA = new ModData(MOD_ID, MOD_NAME);
	public static final String REGISTRY = "gt6u.multitileentity";

	public GT6U_Main(IEventBus aModBus) {
		subscribePhases(aModBus);
	}

	/** Registry id (MultiTileEntityRegistry.currentID) of a multiblock part GT6U names by its id: GT6U kept one registry, the addon
	 *  has its own beside GT6's, and every part not in the addon's is GT6's. */
	public static int partRegistry(int aPartID) {
		MultiTileEntityRegistry tAddon = MultiTileEntityRegistry.getRegistry(REGISTRY);
		return (tAddon != null && tAddon.getClassContainer(aPartID) != null ? tAddon : MultiTileEntityRegistry.getRegistry("gt.multitileentity")).currentID();
	}
	
	@Override public String getModID() {return MOD_ID;}
	@Override public String getModName() {return MOD_NAME;}
	@Override public String getModNameForLog() {return "GT6U_Addon";}
	@Override public Abstract_Proxy getProxy() {return null;}

	// Before GregTech's PreInit, which generates items, fluids and recipes from the materials, prefixes and tags defined here.
	@Override public void onModPreInit2(FMLPreInitializationEvent aEvent) {
		// A class literal does not run static definitions; forName does, in this order: tags before the prefixes that name them.
		for (Class<?> tClass : new Class<?>[] {TD_GT6U.class, MT_GT6U.class, OP_GT6U.class, FL_GT6U.class, IL_GT6U.class, RM_GT6U.class, BI_GT6U.class}) {
			try {Class.forName(tClass.getName(), true, tClass.getClassLoader());} catch (ClassNotFoundException e) {throw new IllegalStateException(e);}
		}
		MT_GT6U.changeGT6();
		TD_GT6U.addToGT6();
		// Its own MultiTileEntity registry, built where GT6 builds its own: in the deferred block queue, which runs on RegisterEvent.
		GT_API.deferBlockInit(() -> new MultiTileEntityRegistry(REGISTRY));
	}
	@Override public void onModInit2(FMLInitializationEvent aEvent) {/**/}
	// GregTech's Init has queued its MultiTileEntity loader by now, so GT6U's runs after it and finds GT6's entries to change.
	@Override public void onModFinalInit(FMLInitializationEvent aEvent) {GT_API.deferItemInit(() -> new Loader_MultiTileEntities().run());}
	@Override public void onModPostInit2(FMLPostInitializationEvent aEvent) {/**/}
	@Override public void onModServerStarting2(ServerStartingEvent aEvent) {/**/}
	@Override public void onModServerStarted2(ServerStartedEvent aEvent) {/**/}
	@Override public void onModServerStopping2(ServerStoppingEvent aEvent) {/**/}
	@Override public void onModServerStopped2(ServerStoppedEvent aEvent) {/**/}
}
