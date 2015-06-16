/*
 * This file is part of ArborianProtect for Bukkit/Spigot, licensed under the MIT License (MIT).
 *
 * Copyright (c) JCThePants (www.jcwhatever.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.jcwhatever.arborianprotect.listeners;

import com.jcwhatever.arborianprotect.ArborianProtect;
import com.jcwhatever.arborianprotect.IProtected;
import com.jcwhatever.arborianprotect.filters.FilterPermission;
import com.jcwhatever.nucleus.utils.materials.Materials;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;
import org.bukkit.event.block.BlockMultiPlaceEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Player event listener.
 */
public class PlayerListener implements Listener {

    private static final Location PVP_LOCATION = new Location(null, 0, 0, 0);
    private static final Location BLOCK_BREAK_LOCATION = new Location(null, 0, 0, 0);
    private static final Location BLOCK_PLACE_LOCATION = new Location(null, 0, 0, 0);
    private static final Location HANGING_BREAK_LOCATION = new Location(null, 0, 0, 0);
    private static final Location HANGING_PLACE_LOCATION = new Location(null, 0, 0, 0);
    private static final Location IGNITED_BLOCK_LOCATION = new Location(null, 0, 0, 0);
    private static final Location MACHINE_LOCATION = new Location(null, 0, 0, 0);
    private static final Location SWITCH_LOCATION = new Location(null, 0, 0, 0);
    private static final Location DOOR_LOCATION = new Location(null, 0, 0, 0);
    private static final Location GATE_LOCATION = new Location(null, 0, 0, 0);
    private static final Location TRAPDOOR_LOCATION = new Location(null, 0, 0, 0);
    private static final Location CHEST_LOCATION = new Location(null, 0, 0, 0);
    private static final Location CHAT_LOCATION = new Location(null, 0, 0, 0);
    private static final Location CROPS_LOCATION = new Location(null, 0, 0, 0);

    private static final EventProcessor<EntityDamageByEntityEvent> PVP =
            new EventProcessor<EntityDamageByEntityEvent>() {
                @Override
                public FilterPermission getPermission(IProtected target) {
                    return target.getPlayerEventFilter().getPVP();
                }
                @Override
                protected void setCancelled(EntityDamageByEntityEvent event, boolean isCancelled) {

                    if (isCancelled)
                        event.setDamage(0);

                    event.setCancelled(isCancelled);
                }
            };

    private static final EventProcessor<Cancellable> BREAK =
            new EventProcessor<Cancellable>() {
                @Override
                public FilterPermission getPermission(IProtected target) {
                    return target.getPlayerEventFilter().getBreak();
                }
            };

    private static final EventProcessor<Cancellable> PLACE =
            new EventProcessor<Cancellable>() {
                @Override
                public FilterPermission getPermission(IProtected target) {
                    return target.getPlayerEventFilter().getPlace();
                }
            };

    private static final EventProcessor<Cancellable> IGNITE =
            new EventProcessor<Cancellable>() {
                @Override
                public FilterPermission getPermission(IProtected target) {
                    return target.getPlayerEventFilter().getIgnite();
                }
            };

    private static final EventProcessor<Cancellable> USE_MACHINES =
            new EventProcessor<Cancellable>() {
                @Override
                public FilterPermission getPermission(IProtected target) {
                    return target.getPlayerEventFilter().getUseMachines();
                }
            };

    private static final EventProcessor<Cancellable> THROW_SWITCH =
            new EventProcessor<Cancellable>() {
                @Override
                public FilterPermission getPermission(IProtected target) {
                    return target.getPlayerEventFilter().getThrowSwitch();
                }
            };

    private static final EventProcessor<Cancellable> OPEN_DOOR =
            new EventProcessor<Cancellable>() {
                @Override
                public FilterPermission getPermission(IProtected target) {
                    return target.getPlayerEventFilter().getOpenDoor();
                }
            };

    private static final EventProcessor<Cancellable> OPEN_GATE =
            new EventProcessor<Cancellable>() {
                @Override
                public FilterPermission getPermission(IProtected target) {
                    return target.getPlayerEventFilter().getOpenGate();
                }
            };

    private static final EventProcessor<Cancellable> OPEN_TRAPDOOR =
            new EventProcessor<Cancellable>() {
                @Override
                public FilterPermission getPermission(IProtected target) {
                    return target.getPlayerEventFilter().getOpenTrapDoor();
                }
            };

    private static final EventProcessor<Cancellable> OPEN_CHEST =
            new EventProcessor<Cancellable>() {
                @Override
                public FilterPermission getPermission(IProtected target) {
                    return target.getPlayerEventFilter().getOpenChest();
                }
            };

    private static final EventProcessor<Cancellable> CHAT =
            new EventProcessor<Cancellable>() {
                @Override
                public FilterPermission getPermission(IProtected target) {
                    return target.getPlayerEventFilter().getChat();
                }
            };

    private static final EventProcessor<Cancellable> TRAMPLE_CROPS =
            new EventProcessor<Cancellable>() {
                @Override
                public FilterPermission getPermission(IProtected target) {
                    return target.getPlayerEventFilter().getTrampleCrops();
                }
            };



    @EventHandler(priority = EventPriority.LOW)
    private void onPvp(EntityDamageByEntityEvent event) {

        if (!(event.getEntity() instanceof Player ||
                !(event.getDamager() instanceof Player))) {
            return;
        }

        if (event.getDamager() instanceof Player &&
                ArborianProtect.isExempt((Player)event.getDamager())) {
            return;
        }

        Location location = event.getEntity().getLocation(PVP_LOCATION);

        PVP.processEvent(location, event);
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onPlayerBreakBlock(BlockBreakEvent event) {

        if (ArborianProtect.isExempt(event.getPlayer()))
            return;

        Location location = event.getBlock().getLocation(BLOCK_BREAK_LOCATION);
        BREAK.processEvent(location, event);
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onHangingBreak(HangingBreakByEntityEvent event) {

        if (!(event.getRemover() instanceof Player))
            return;

        if (ArborianProtect.isExempt((Player) event.getRemover()))
            return;

        Location location = event.getEntity().getLocation(HANGING_BREAK_LOCATION);
        BREAK.processEvent(location, event);
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onPlaceBlock(BlockPlaceEvent event) {

        if (ArborianProtect.isExempt(event.getPlayer()))
            return;

        Location location = event.getBlockPlaced().getLocation(BLOCK_PLACE_LOCATION);
        PLACE.processEvent(location, event);
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onPlaceMultiBlock(BlockMultiPlaceEvent event) {

        if (ArborianProtect.isExempt(event.getPlayer()))
            return;

        Location location = event.getBlockPlaced().getLocation(BLOCK_PLACE_LOCATION);
        PLACE.processEvent(location, event);
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onHangingPlaced(HangingPlaceEvent event) {

        if (ArborianProtect.isExempt(event.getPlayer()))
            return;

        Location location = event.getEntity().getLocation(HANGING_PLACE_LOCATION);
        PLACE.processEvent(location, event);
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onIgnite(BlockIgniteEvent event) {

        if (ArborianProtect.isExempt(event.getPlayer()))
            return;

        if (event.getCause() != IgniteCause.FLINT_AND_STEEL || event.getPlayer() == null)
            return;

        Location location = event.getBlock().getLocation(IGNITED_BLOCK_LOCATION);
        IGNITE.processEvent(location, event);
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onUseMachine(PlayerInteractEvent event) {

        if (ArborianProtect.isExempt(event.getPlayer()))
            return;

        if (!event.hasBlock())
            return;

        Material material = event.getClickedBlock().getType();
        if (!Materials.hasGUI(material))
            return;

        Location location = event.getClickedBlock().getLocation(MACHINE_LOCATION);
        USE_MACHINES.processEvent(location, event);
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onThrowSwitch(PlayerInteractEvent event) {

        if (ArborianProtect.isExempt(event.getPlayer()))
            return;

        if (!event.hasBlock())
            return;

        Material material = event.getClickedBlock().getType();
        if (!Materials.isRedstoneSwitch(material))
            return;

        Location location = event.getClickedBlock().getLocation(SWITCH_LOCATION);
        THROW_SWITCH.processEvent(location, event);
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onOpenDoor(PlayerInteractEvent event) {

        if (ArborianProtect.isExempt(event.getPlayer()))
            return;

        if (!event.hasBlock())
            return;

        Material material = event.getClickedBlock().getType();
        if (!Materials.isDoor(material))
            return;

        Location location = event.getClickedBlock().getLocation(DOOR_LOCATION);
        OPEN_DOOR.processEvent(location, event);
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onOpenGate(PlayerInteractEvent event) {

        if (ArborianProtect.isExempt(event.getPlayer()))
            return;

        if (!event.hasBlock())
            return;

        Material material = event.getClickedBlock().getType();
        if (!Materials.isFenceGate(material))
            return;

        Location location = event.getClickedBlock().getLocation(GATE_LOCATION);
        OPEN_GATE.processEvent(location, event);
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onOpenTrapDoor(PlayerInteractEvent event) {

        if (ArborianProtect.isExempt(event.getPlayer()))
            return;

        if (!event.hasBlock())
            return;

        Material material = event.getClickedBlock().getType();
        if (!Materials.isTrapDoor(material))
            return;

        Location location = event.getClickedBlock().getLocation(TRAPDOOR_LOCATION);
        OPEN_TRAPDOOR.processEvent(location, event);
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onOpenChest(PlayerInteractEvent event) {

        if (ArborianProtect.isExempt(event.getPlayer()))
            return;

        if (!event.hasBlock())
            return;

        Material material = event.getClickedBlock().getType();
        if (!Materials.hasInventory(material) ||
                !Materials.hasGUI(material))
            return;

        Location location = event.getClickedBlock().getLocation(CHEST_LOCATION);
        OPEN_CHEST.processEvent(location, event);
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onTrampleCrops(PlayerInteractEvent event) {

        if (ArborianProtect.isExempt(event.getPlayer()))
            return;

        if (!event.hasBlock() || event.getAction() != Action.PHYSICAL)
            return;

        Material material = event.getClickedBlock().getType();
        if (material != Material.SOIL)
            return;

        Location location = event.getClickedBlock().getLocation(CROPS_LOCATION);
        TRAMPLE_CROPS.processEvent(location, event);
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onChat(PlayerCommandPreprocessEvent event) {

        if (ArborianProtect.isExempt(event.getPlayer()))
            return;

        if (event.getMessage().isEmpty() ||
                event.getMessage().charAt(0) == '\\')
            return;

        Location location = event.getPlayer().getLocation(CHAT_LOCATION);
        CHAT.processEvent(location, event);
    }
}
