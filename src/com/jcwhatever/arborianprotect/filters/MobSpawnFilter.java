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

package com.jcwhatever.arborianprotect.filters;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.jcwhatever.nucleus.storage.DeserializeException;
import com.jcwhatever.nucleus.storage.IDataNode;
import com.jcwhatever.nucleus.storage.IDataNodeSerializable;
import com.jcwhatever.nucleus.utils.CollectionUtils;
import com.jcwhatever.nucleus.utils.EnumUtils;
import com.jcwhatever.nucleus.utils.PreCon;
import com.jcwhatever.nucleus.utils.entity.EntityTypes;

import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;

/**
 * Creature spawn filter settings.
 */
public class MobSpawnFilter implements IFilterPolicy, IDataNodeSerializable {

    private IDataNode _dataNode;
    private FilterPolicy _policy = FilterPolicy.BLACKLIST;
    private Multimap<EntityType, SpawnReason> _reasonMap;

    private MobSpawnFilter() {}

    /**
     * Constructor.
     *
     * @param dataNode  The filters data node.
     */
    public MobSpawnFilter(IDataNode dataNode) {
        PreCon.notNull(dataNode);
        _dataNode = dataNode;
    }

    @Override
    public FilterPolicy getPolicy() {
        return _policy;
    }

    @Override
    public void setPolicy(FilterPolicy policy) {
        _policy = policy;
        _dataNode.set("policy", policy);
        _dataNode.save();
    }

    /**
     * Get the {@link org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason}'s filtered
     * for the specified entity type.
     *
     * @param type  The {@link org.bukkit.entity.EntityType} to check.
     */
    public Collection<SpawnReason> getReasons(EntityType type) {
        PreCon.notNull(type);

        if (_reasonMap == null)
            return CollectionUtils.unmodifiableList(SpawnReason.class);

        return Collections.unmodifiableCollection(
                _reasonMap.get(type)
        );
    }

    /**
     * Determine if an entity type is allowed to spawn.
     *
     * @param type    The {@link org.bukkit.entity.EntityType}.
     * @param reason  The reason the entity is being spawned.
     */
    public boolean canSpawn(EntityType type, SpawnReason reason) {
        PreCon.notNull(type);

        // prevent skeleton from spawning when spider is removed for natural spawn (Spider Jockey)
        // prevent chicken from spawning when zombie is removed for natural spawn (Chicken Jockey)
        // caveat: Cant spawn jockeys if skeleton or chicken is denied natural spawn.
        if ((type == EntityType.SKELETON && reason == SpawnReason.JOCKEY) ||
                (type == EntityType.CHICKEN && reason == SpawnReason.MOUNT)) {
            if (!canSpawn(type, SpawnReason.NATURAL))
                return false;
        }

        if (_reasonMap != null && _reasonMap.get(type).contains(reason))
            return _policy == FilterPolicy.WHITELIST;

        return _policy == FilterPolicy.BLACKLIST;
    }

    /**
     * Add a filter.
     *
     * @param type  The {@link EntityType} to filter.
     *
     * @return  True if the filter was changed.
     */
    public boolean addFilter(EntityType type) {
        return addFilter(type, null);
    }

    /**
     * Adda filter based on a specific {@link org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason}.
     *
     * @param type    The {@link org.bukkit.entity.EntityType} to filter.
     * @param reason  The {@link org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason} to filter.
     *
     * @return  True if the filter was changed.
     */
    public boolean addFilter(EntityType type, @Nullable SpawnReason reason) {
        PreCon.notNull(type);

        // add all types
        if (reason == null) {
            boolean isChanged = false;
            for (SpawnReason r : SpawnReason.values()) {
                isChanged = addFilter(type, r) || isChanged;
            }
            return isChanged;
        }

        // add type and reason
        if (_reasonMap == null)
            _reasonMap = MultimapBuilder.enumKeys(EntityType.class)
                    .enumSetValues(SpawnReason.class).build();

        if (_reasonMap.put(type, reason)) {
            save();
            return true;
        }
        return false;
    }

    /**
     * Remove an {@link org.bukkit.entity.EntityType} filter.
     *
     * @param type  The type to remove.
     *
     * @return  True if the filter was changed.
     */
    public boolean removeFilter(EntityType type) {
        return removeFilter(type, null);
    }

    /**
     * Remove an {@link org.bukkit.entity.EntityType} and
     * {@link org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason} filter.
     *
     * @param type    The {@link org.bukkit.entity.EntityType} of the filter.
     * @param reason  The {@link org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason} of the filter.
     *
     * @return  True if the filter was changed.
     */
    public boolean removeFilter(EntityType type, @Nullable SpawnReason reason) {
        PreCon.notNull(type);

        // remove type only
        if (reason == null) {
            boolean isChanged = false;
            for (SpawnReason r : SpawnReason.values()) {
                isChanged = removeFilter(type, r) || isChanged;
            }
            return isChanged;
        }

        // remove type and reason
        if (_reasonMap != null && _reasonMap.get(type).remove(reason)) {
            save();
            return true;
        }
        return false;
    }

    /**
     * Add all animal types to the filter.
     */
    public void addAnimals() {
        addAnimals(null);
    }

    /**
     * Add all animal types to the filter with the specified
     * {@link org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason}.
     *
     * @param reason  The reason to filter.
     */
    public void addAnimals(@Nullable SpawnReason reason) {

        for (EntityType type : EntityType.values()) {
            if (isAnimal(type))
                addFilter(type, reason);
        }
    }

    /**
     * Remove all animal types from the filter.
     */
    public void removeAnimals() {
        removeAnimals(null);
    }

    /**
     * Remove all animal types from the filter with the specified
     * {@link org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason}.
     *
     * @param reason  The reason to filter.
     */
    public void removeAnimals(@Nullable SpawnReason reason) {

        for (EntityType type : EntityType.values()) {
            if (isAnimal(type))
                removeFilter(type, reason);
        }
    }

    /**
     * Add all monster types to the filter.
     */
    public void addMonsters() {
        addMonsters(null);
    }

    /**
     * Add all monster types to the filter with the specified
     * {@link org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason}.
     *
     * @param reason  The reason to filter.
     */
    public void addMonsters(@Nullable SpawnReason reason) {

        for (EntityType type : EntityType.values()) {
            if (isMonster(type))
                addFilter(type, reason);
        }
    }

    /**
     * Remove all monster types from the filter.
     */
    public void removeMonsters() {
        removeMonsters(null);
    }

    /**
     * Remove all monster types from the filter with the specified
     * {@link org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason}.
     *
     * @param reason  The reason to filter.
     */
    public void removeMonsters(@Nullable SpawnReason reason) {

        for (EntityType type : EntityType.values()) {
            if (isMonster(type))
                removeFilter(type, reason);
        }
    }

    @Override
    public void serialize(IDataNode dataNode) {

        _dataNode = dataNode;

        dataNode.set("policy", _policy);

        if (_reasonMap != null) {

            IDataNode reasonsNode = dataNode.getNode("reasons");

            Set<EntityType> types = _reasonMap.keySet();

            for (EntityType type : types) {

                Collection<SpawnReason> reasons = _reasonMap.get(type);

                List<String> reasonNames = new ArrayList<>(reasons.size());

                for (SpawnReason reason : reasons) {
                    reasonNames.add(reason.name());
                }

                reasonsNode.set(type.name(), reasonNames);
            }
        }
    }

    @Override
    public void deserialize(IDataNode dataNode) throws DeserializeException {

        _dataNode = dataNode;

        _policy = dataNode.getEnum("policy", FilterPolicy.BLACKLIST, FilterPolicy.class);

        IDataNode reasonsNode = dataNode.getNode("reasons");
        if (reasonsNode.size() > 0) {
            _reasonMap = MultimapBuilder.enumKeys(EntityType.class)
                    .enumSetValues(SpawnReason.class).build();

            for (IDataNode node : reasonsNode) {

                EntityType type = EnumUtils.searchEnum(node.getName(), EntityType.class);

                Set<SpawnReason> reasons = EnumSet.noneOf(SpawnReason.class);
                List<String> reasonNames = node.getStringList("", null);

                if (reasonNames == null)
                    continue;

                for (String reasonName : reasonNames) {
                    SpawnReason reason = EnumUtils.searchEnum(reasonName, SpawnReason.class);
                    if (reason == null)
                        continue;

                    reasons.add(reason);
                }

                _reasonMap.putAll(type, reasons);
            }
        }
    }

    private void save() {
        _dataNode.set("", this);
        _dataNode.save();
    }

    private boolean isAnimal(EntityType type) {
        return EntityTypes.isAnimal(type);
    }

    private boolean isMonster(EntityType type) {
        return !isAnimal(type) &&
                (EntityTypes.isHostile(type) ||
                EntityTypes.isMonster(type));
    }
}
