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

import com.jcwhatever.arborianprotect.IProtected;
import com.jcwhatever.arborianprotect.filters.FilterPermission;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.List;

/**
 * Block event filter listener.
 */
public class BlockListener implements Listener {

    private static final Location LEAF_DECAY_LOCATION = new Location(null, 0, 0, 0);
    private static final Location BLOCK_FADE_LOCATION = new Location(null, 0, 0, 0);
    private static final Location BLOCK_SPREAD_LOCATION = new Location(null, 0, 0, 0);
    private static final Location BLOCK_IGNITE_LOCATION = new Location(null, 0, 0, 0);
    private static final Location EXPLOSION_LOCATION = new Location(null, 0, 0, 0);
    private static final Location ENTITY_CHANGE_BLOCK_LOCATION = new Location(null, 0, 0, 0);

    private static final EventProcessor<Cancellable> LEAF_DECAY =
            new EventProcessor<Cancellable>() {
                @Override
                public FilterPermission getPermission(IProtected target) {
                    return target.getBlockEventFilter().getLeafDecay();
                }
            };

    private static final EventProcessor<Cancellable> ICE_MELT =
            new EventProcessor<Cancellable>() {
                @Override
                public FilterPermission getPermission(IProtected target) {
                    return target.getBlockEventFilter().getIceMelt();
                }
            };

    private static final EventProcessor<Cancellable> SNOW_MELT =
            new EventProcessor<Cancellable>() {
                @Override
                public FilterPermission getPermission(IProtected target) {
                    return target.getBlockEventFilter().getSnowMelt();
                }
            };

    private static final EventProcessor<Cancellable> SOIL_DEHYDRATE =
            new EventProcessor<Cancellable>() {
                @Override
                public FilterPermission getPermission(IProtected target) {
                    return target.getBlockEventFilter().getSoilDehydrate();
                }
            };

    private static final EventProcessor<Cancellable> VINE_GROWTH =
            new EventProcessor<Cancellable>() {
                @Override
                public FilterPermission getPermission(IProtected target) {
                    return target.getBlockEventFilter().getVineGrowth();
                }
            };

    private static final EventProcessor<Cancellable> MUSHROOM_GROWTH =
            new EventProcessor<Cancellable>() {
                @Override
                public FilterPermission getPermission(IProtected target) {
                    return target.getBlockEventFilter().getMushroomGrowth();
                }
            };

    private static final EventProcessor<Cancellable> GRASS_GROWTH =
            new EventProcessor<Cancellable>() {
                @Override
                public FilterPermission getPermission(IProtected target) {
                    return target.getBlockEventFilter().getGrassGrowth();
                }
            };

    private static final EventProcessor<Cancellable> MYCEL_GROWTH =
            new EventProcessor<Cancellable>() {
                @Override
                public FilterPermission getPermission(IProtected target) {
                    return target.getBlockEventFilter().getMyceliumGrowth();
                }
            };

    private static final EventProcessor<Cancellable> FIRE_SPREAD =
            new EventProcessor<Cancellable>() {
                @Override
                public FilterPermission getPermission(IProtected target) {
                    return target.getBlockEventFilter().getFireSpread();
                }
            };

    private static final EventProcessor<EntityExplodeEvent> TNT_DAMAGE =
            new EventProcessor<EntityExplodeEvent>() {
                @Override
                public FilterPermission getPermission(IProtected target) {
                    return target.getBlockEventFilter().getTntDamage();
                }
                @Override
                protected void setCancelled(EntityExplodeEvent event, boolean isCancelled) {
                    if (isCancelled)
                        event.blockList().clear();
                }
            };

    private static final EventProcessor<EntityExplodeEvent> CREEPER_DAMAGE =
            new EventProcessor<EntityExplodeEvent>() {
                @Override
                public FilterPermission getPermission(IProtected target) {
                    return target.getBlockEventFilter().getCreeperDamage();
                }
                @Override
                protected void setCancelled(EntityExplodeEvent event, boolean isCancelled) {
                    if (isCancelled)
                        event.blockList().clear();
                }
            };

    private static final EventProcessor<EntityExplodeEvent> FIREBALL_DAMAGE =
            new EventProcessor<EntityExplodeEvent>() {
                @Override
                public FilterPermission getPermission(IProtected target) {
                    return target.getBlockEventFilter().getFireballDamage();
                }
                @Override
                protected void setCancelled(EntityExplodeEvent event, boolean isCancelled) {
                    if (isCancelled)
                        event.blockList().clear();
                }
            };

    private static final EventProcessor<EntityChangeBlockEvent> MOB_CHANGE_BLOCK_EVENT =
            new EventProcessor<EntityChangeBlockEvent>() {
                @Override
                public FilterPermission getPermission(IProtected target) {
                    return target.getBlockEventFilter().getMobChangeBlock();
                }
            };

    @EventHandler(priority = EventPriority.LOW)
    private void onLeafDecay(LeavesDecayEvent event) {

        Location location = event.getBlock().getLocation(LEAF_DECAY_LOCATION);
        LEAF_DECAY.processEvent(location, event);
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onBlockFade(BlockFadeEvent event) {

        Location location = event.getBlock().getLocation(BLOCK_FADE_LOCATION);
        Material material = event.getBlock().getType();

        if (material != Material.ICE &&
                material != Material.SNOW &&
                material != Material.SOIL)
            return;

        switch (material) {
            case ICE:
                ICE_MELT.processEvent(location, event);
                break;
            case SNOW:
                SNOW_MELT.processEvent(location, event);
                break;
            case SOIL:
                SOIL_DEHYDRATE.processEvent(location, event);
                break;
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onBlockSpread(BlockSpreadEvent event) {

        Location location = event.getBlock().getLocation(BLOCK_SPREAD_LOCATION);
        Material material = event.getBlock().getType();

        switch (material) {
            case VINE:
                VINE_GROWTH.processEvent(location, event);
                break;
            case RED_MUSHROOM:
                // fall through
            case BROWN_MUSHROOM:
                MUSHROOM_GROWTH.processEvent(location, event);
                break;
            case GRASS:
                GRASS_GROWTH.processEvent(location, event);
                break;
            case MYCEL:
                MYCEL_GROWTH.processEvent(location, event);
                break;
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onBlockGrow(BlockGrowEvent event) {

        Location location = event.getBlock().getLocation(BLOCK_SPREAD_LOCATION);
        Material material = event.getBlock().getType();

        switch (material) {
            case VINE:
                VINE_GROWTH.processEvent(location, event);
                break;
            case RED_MUSHROOM:
                // fall through
            case BROWN_MUSHROOM:
                MUSHROOM_GROWTH.processEvent(location, event);
                break;
            case GRASS:
                GRASS_GROWTH.processEvent(location, event);
                break;
            case MYCEL:
                MYCEL_GROWTH.processEvent(location, event);
                break;
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onBlockIgnite(BlockIgniteEvent event) {

        Location location = event.getBlock().getLocation(BLOCK_IGNITE_LOCATION);
        IgniteCause cause = event.getCause();

        if (cause != IgniteCause.SPREAD)
            return;

        FIRE_SPREAD.processEvent(location, event);
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onExplosion(EntityExplodeEvent event) {

        EntityType type = event.getEntityType();

        if (type != EntityType.PRIMED_TNT &&
                type != EntityType.CREEPER &&
                type != EntityType.FIREBALL &&
                type != EntityType.SMALL_FIREBALL) {
            return;
        }

        List<Block> blocks = event.blockList();

        for (Block block : blocks) {

            Location location = block.getLocation(EXPLOSION_LOCATION);

            switch (type) {
                case PRIMED_TNT:
                    if (TNT_DAMAGE.processEvent(location, event))
                        return;
                    break;
                case CREEPER:
                    if (CREEPER_DAMAGE.processEvent(location, event))
                        return;
                    break;
                case FIREBALL:
                    // fallthrough
                case SMALL_FIREBALL:
                    if (FIREBALL_DAMAGE.processEvent(location, event))
                        return;
                    break;
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onMobChangeBlock(EntityChangeBlockEvent event) {

        if (!event.getEntity().getType().isAlive())
            return;

        Location location = event.getBlock().getLocation(ENTITY_CHANGE_BLOCK_LOCATION);

        MOB_CHANGE_BLOCK_EVENT.processEvent(location, event);
    }
}
