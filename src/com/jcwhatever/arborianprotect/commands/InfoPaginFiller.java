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

package com.jcwhatever.arborianprotect.commands;

import com.jcwhatever.arborianprotect.IProtected;
import com.jcwhatever.arborianprotect.Lang;
import com.jcwhatever.arborianprotect.filters.BlockEventFilter;
import com.jcwhatever.arborianprotect.filters.FilterPermission;
import com.jcwhatever.arborianprotect.filters.FilterPolicy;
import com.jcwhatever.arborianprotect.filters.MobSpawnFilter;
import com.jcwhatever.arborianprotect.filters.PlayerEventFilter;
import com.jcwhatever.nucleus.managed.messaging.ChatPaginator;
import com.jcwhatever.nucleus.managed.language.Localizable;
import com.jcwhatever.nucleus.utils.text.TextUtils;
import com.jcwhatever.nucleus.utils.text.TextUtils.TitleCaseOption;

import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Utilities to fill paginators with info about filter settings.
 */
public class InfoPaginFiller {

    @Localizable static final String _BLOCK_BREAK = "Block Break";
    @Localizable static final String _BLOCK_PLACE = "Block Place";
    @Localizable static final String _CHAT = "Chat";
    @Localizable static final String _IGNITE = "Ignite";
    @Localizable static final String _PVP = "PVP";
    @Localizable static final String _HUNGER = "Hunger";
    @Localizable static final String _OPEN_CHEST = "Open Chest";
    @Localizable static final String _OPEN_DOOR = "Open Door";
    @Localizable static final String _OPEN_GATE = "Open Gate";
    @Localizable static final String _OPEN_TRAPDOOR = "Open Trapdoor";
    @Localizable static final String _THROW_SWITCH = "Throw Switch";
    @Localizable static final String _TRAMPLE_CROPS = "Trample Crops";
    @Localizable static final String _USE_MACHINES = "Use Machines";
    @Localizable static final String _TNT_DAMAGE = "TNT Explosion Damage";
    @Localizable static final String _CREEPER_DAMAGE = "Creeper Explosion Damage";
    @Localizable static final String _FIREBALL_DAMAGE = "Fireball Explosion Damage";
    @Localizable static final String _FIRE_SPREAD = "Fire Spread";
    @Localizable static final String _GRASS_GROWTH = "Grass Growth";
    @Localizable static final String _ICE_MELT = "Ice Melt";
    @Localizable static final String _LEAF_DECAY = "Leaf Decay";
    @Localizable static final String _MUSHROOM_GROWTH = "Mushroom Growth";
    @Localizable static final String _MYCEL_GROWTH = "Mycel Growth";
    @Localizable static final String _SNOW_MELT = "Snow Melt";
    @Localizable static final String _SOIL_DEHYDRATE = "Soil Dehydrate";
    @Localizable static final String _VINE_GROWTH = "Vine Growth";
    @Localizable static final String _MOB_CHANGE_BLOCK = "Mob Change Block";
    @Localizable static final String _ALLOW = "{GREEN}Allow";
    @Localizable static final String _DENY = "{RED}Deny";
    @Localizable static final String _DEFAULT = "Default";
    @Localizable static final String _BLACKLIST_ITEM = "{RED}{0: item name}{GRAY}";
    @Localizable static final String _WHITELIST_ITEM = "{GREEN}{0: item name}{GRAY}";
    @Localizable static final String _FILTER_POLICY = "Filter Policy";

    public static void playerFilter(ChatPaginator pagin, IProtected target) {

        PlayerEventFilter filter = target.getPlayerEventFilter();

        addPagin(pagin, _BLOCK_BREAK, filter.getBreak());
        addPagin(pagin, _BLOCK_PLACE, filter.getPlace());
        addPagin(pagin, _CHAT, filter.getChat());
        addPagin(pagin, _IGNITE, filter.getIgnite());
        addPagin(pagin, _PVP, filter.getPVP());
        addPagin(pagin, _OPEN_CHEST, filter.getOpenChest());
        addPagin(pagin, _OPEN_DOOR, filter.getOpenDoor());
        addPagin(pagin, _OPEN_GATE, filter.getOpenGate());
        addPagin(pagin, _OPEN_TRAPDOOR, filter.getOpenTrapDoor());
        addPagin(pagin, _THROW_SWITCH, filter.getThrowSwitch());
        addPagin(pagin, _TRAMPLE_CROPS, filter.getTrampleCrops());
        addPagin(pagin, _USE_MACHINES, filter.getUseMachines());
        addPagin(pagin, _HUNGER, filter.getHunger());
    }

    public static void blockFilter(ChatPaginator pagin, IProtected target) {

        BlockEventFilter filter = target.getBlockEventFilter();

        addPagin(pagin, _TNT_DAMAGE, filter.getTntDamage());
        addPagin(pagin, _CREEPER_DAMAGE, filter.getCreeperDamage());
        addPagin(pagin, _FIREBALL_DAMAGE, filter.getFireballDamage());
        addPagin(pagin, _FIRE_SPREAD, filter.getFireSpread());
        addPagin(pagin, _GRASS_GROWTH, filter.getGrassGrowth());
        addPagin(pagin, _ICE_MELT, filter.getIceMelt());
        addPagin(pagin, _LEAF_DECAY, filter.getLeafDecay());
        addPagin(pagin, _MUSHROOM_GROWTH, filter.getMushroomGrowth());
        addPagin(pagin, _MYCEL_GROWTH, filter.getMyceliumGrowth());
        addPagin(pagin, _SNOW_MELT, filter.getSnowMelt());
        addPagin(pagin, _SOIL_DEHYDRATE, filter.getSoilDehydrate());
        addPagin(pagin, _VINE_GROWTH, filter.getVineGrowth());
        addPagin(pagin, _MOB_CHANGE_BLOCK, filter.getMobChangeBlock());
    }

    public static void mobSpawns(ChatPaginator pagin, IProtected target) {

        MobSpawnFilter filter = target.getMobSpawnFilter();

        pagin.add(Lang.get(_FILTER_POLICY), filter.getPolicy());

        for (EntityType type : EntityType.values()) {
            if (!type.isAlive())
                continue;

            Collection<SpawnReason> reasons = filter.getReasons(type);
            if (reasons.isEmpty())
                continue;

            List<String> reasonNames= new ArrayList<>(reasons.size());

            for (SpawnReason reason : reasons) {
                reasonNames.add(reason.name());
            }

            filter.getReasons(type);

            pagin.add(TextUtils.titleCase(type.name(), TitleCaseOption.ALL),
                    getText(filter.getPolicy(), reasonNames));
        }
    }

    private static String getText(FilterPolicy policy, List<String> names) {

        List<String> modified = new ArrayList<>(names.size());

        for (String name : names) {

            name = TextUtils.titleCase(name.replace("_", " "), TitleCaseOption.ALL);

            switch (policy) {
                case BLACKLIST:
                    modified.add(Lang.get(_BLACKLIST_ITEM, name));
                    break;
                case WHITELIST:
                    modified.add(Lang.get(_WHITELIST_ITEM, name));
                    break;
            }
        }

        return TextUtils.concat(modified, ", ");
    }

    private static String getText(FilterPermission permission) {
        switch (permission) {
            case ALLOW:
                return Lang.get(_ALLOW);
            case DENY:
                return Lang.get(_DENY);
            default:
                return Lang.get(_DEFAULT);
        }
    }

    private static void addPagin(ChatPaginator pagin, String title, FilterPermission permission) {
        pagin.add(Lang.get(title), getText(permission));
    }
}
