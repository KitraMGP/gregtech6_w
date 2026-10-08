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

	@Override public String getModID() {return MOD_ID;}
	@Override public String getModName() {return MOD_NAME;}
	@Override public String getModNameForLog() {return "GT6U_Addon";}
	@Override public Abstract_Proxy getProxy() {return null;}

	// Its own MultiTileEntity registry, built where GT6 builds its own: in the deferred block queue, which runs on RegisterEvent.
	@Override public void onModPreInit2(FMLPreInitializationEvent aEvent) {GT_API.deferBlockInit(() -> new MultiTileEntityRegistry(REGISTRY));}
	// Queued after GT6's own MultiTileEntity loader (this mod's Init follows gregtech's), so GT6's entries exist to be overridden.
	@Override public void onModInit2(FMLInitializationEvent aEvent) {GT_API.deferItemInit(() -> new Loader_MultiTileEntities().run());}
	@Override public void onModPostInit2(FMLPostInitializationEvent aEvent) {/**/}
	@Override public void onModServerStarting2(ServerStartingEvent aEvent) {/**/}
	@Override public void onModServerStarted2(ServerStartedEvent aEvent) {/**/}
	@Override public void onModServerStopping2(ServerStoppingEvent aEvent) {/**/}
	@Override public void onModServerStopped2(ServerStoppedEvent aEvent) {/**/}
}
