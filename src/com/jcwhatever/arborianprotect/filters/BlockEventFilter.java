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

import com.jcwhatever.nucleus.storage.serialize.DeserializeException;
import com.jcwhatever.nucleus.storage.IDataNode;
import com.jcwhatever.nucleus.storage.serialize.IDataNodeSerializable;
import com.jcwhatever.nucleus.utils.PreCon;

/**
 * Block event filter settings.
 */
public class BlockEventFilter implements IDataNodeSerializable {

    private IDataNode _dataNode;

    private FilterPermission _leafDecay = FilterPermission.DEFAULT;
    private FilterPermission _iceMelt = FilterPermission.DEFAULT;
    private FilterPermission _snowMelt = FilterPermission.DEFAULT;
    private FilterPermission _soilDehydrate = FilterPermission.DEFAULT;
    private FilterPermission _vineGrowth = FilterPermission.DEFAULT;
    private FilterPermission _mushroomGrowth = FilterPermission.DEFAULT;
    private FilterPermission _grassGrowth = FilterPermission.DEFAULT;
    private FilterPermission _myceliumGrowth = FilterPermission.DEFAULT;
    private FilterPermission _fireSpread = FilterPermission.DEFAULT;
    private FilterPermission _explosionDamage = FilterPermission.DEFAULT;
    private FilterPermission _entityChangeBlock = FilterPermission.DEFAULT;

    private BlockEventFilter() {}

    /**
     * Constructor.
     *
     * @param dataNode  The filters data node.
     */
    public BlockEventFilter(IDataNode dataNode) {
        PreCon.notNull(dataNode);

        _dataNode = dataNode;
    }

    /**
     * Get leaf decay permission.
     */
    public FilterPermission getLeafDecay() {
        return _leafDecay;
    }

    /**
     * Set leaf decay permission.
     *
     * @param permission  The {@link FilterPermission}.
     */
    public void setLeafDecay(FilterPermission permission) {
        PreCon.notNull(permission);

        _leafDecay = permission;
        _dataNode.set("leaf-decay", permission);
        _dataNode.save();
    }

    /**
     * Get ice melt permission.
     */
    public FilterPermission getIceMelt() {
        return _iceMelt;
    }

    /**
     * Set ice melt permission.
     *
     * @param permission  The {@link FilterPermission}.
     */
    public void setIceMelt(FilterPermission permission) {
        PreCon.notNull(permission);

        _iceMelt = permission;
        _dataNode.set("ice-melt", permission);
        _dataNode.save();
    }

    /**
     * Get snow melt permission.
     */
    public FilterPermission getSnowMelt() {
        return _snowMelt;
    }

    /**
     * Set snow melt permission.
     *
     * @param permission  The {@link FilterPermission}.
     */
    public void setSnowMelt(FilterPermission permission) {
        PreCon.notNull(permission);

        _snowMelt = permission;
        _dataNode.set("snow-melt", permission);
        _dataNode.save();
    }

    /**
     * Get soil dehydration permission.
     */
    public FilterPermission getSoilDehydrate() {
        return _soilDehydrate;
    }

    /**
     * Set soil dehydration permission.
     *
     * @param permission  The {@link FilterPermission}.
     */
    public void setSoilDehydrate(FilterPermission permission) {
        PreCon.notNull(permission);

        _soilDehydrate = permission;
        _dataNode.set("soil-dehydrate", permission);
        _dataNode.save();
    }

    /**
     * Get vine growth permission.
     */
    public FilterPermission getVineGrowth() {
        return _vineGrowth;
    }

    /**
     * Set vine growth permission.
     *
     * @param permission  The {@link FilterPermission}.
     */
    public void setVineGrowth(FilterPermission permission) {
        PreCon.notNull(permission);

        _vineGrowth = permission;
        _dataNode.set("vine-growth", permission);
        _dataNode.save();
    }

    /**
     * Get mushroom growth permission.
     */
    public FilterPermission getMushroomGrowth() {
        return _mushroomGrowth;
    }

    /**
     * Set mushroom growth permission.
     *
     * @param permission  The {@link FilterPermission}.
     */
    public void setMushroomGrowth(FilterPermission permission) {
        PreCon.notNull(permission);

        _mushroomGrowth = permission;
        _dataNode.set("mushroom-growth", permission);
        _dataNode.save();
    }

    /**
     * Get grass growth permission.
     *
     * @return  The {@link FilterPermission}.
     */
    public FilterPermission getGrassGrowth() {
        return _grassGrowth;
    }

    /**
     * Set grass growth permission.
     *
     * @param permission  The {@link FilterPermission}.
     */
    public void setGrassGrowth(FilterPermission permission) {
        PreCon.notNull(permission);

        _grassGrowth = permission;
        _dataNode.set("grass-growth", permission);
        _dataNode.save();
    }

    /**
     * Get mycelium growth permission.
     */
    public FilterPermission getMyceliumGrowth() {
        return _myceliumGrowth;
    }

    /**
     * Set mycelium growth permission.
     *
     * @param permission  The {@link FilterPermission}.
     */
    public void setMyceliumGrowth(FilterPermission permission) {
        PreCon.notNull(permission);

        _myceliumGrowth = permission;
        _dataNode.set("mycelium-growth", permission);
        _dataNode.save();
    }

    /**
     * Get fire spread permission.
     */
    public FilterPermission getFireSpread() {
        return _fireSpread;
    }

    /**
     * Set fire spread permission.
     *
     * @param permission  The {@link FilterPermission}.
     */
    public void setFireSpread(FilterPermission permission) {
        PreCon.notNull(permission);

        _fireSpread = permission;
        _dataNode.set("fire-spread", permission);
        _dataNode.save();
    }

    /**
     * Get explosion block damage permission.
     */
    public FilterPermission getExplosionDamage() {
        return _explosionDamage;
    }

    /**
     * Set explosion block damage permission.
     *
     * @param permission  The {@link FilterPermission}.
     */
    public void setExplosionDamage(FilterPermission permission) {
        PreCon.notNull(permission);

        _explosionDamage = permission;
        _dataNode.set("explosion-damage", _explosionDamage);
        _dataNode.save();
    }

    /**
     * Get the entity change block permission.
     */
    public FilterPermission getEntityChangeBlock() {
        return _entityChangeBlock;
    }

    /**
     * Set the entity change block permission.
     *
     * @param permission  The {@link FilterPermission}.
     */
    public void setEntityChangeBlock(FilterPermission permission) {
        PreCon.notNull(permission);

        _entityChangeBlock = permission;
        _dataNode.set("entity-change-block", _entityChangeBlock);
        _dataNode.save();
    }

    @Override
    public void serialize(IDataNode dataNode) {
        dataNode.set("leaf-decay", _leafDecay);
        dataNode.set("ice-melt", _iceMelt);
        dataNode.set("snow-melt", _snowMelt);
        dataNode.set("soil-dehydrate", _soilDehydrate);
        dataNode.set("vine-growth", _vineGrowth);
        dataNode.set("mushroom-growth", _mushroomGrowth);
        dataNode.set("grass-growth", _grassGrowth);
        dataNode.set("mycelium-growth", _myceliumGrowth);
        dataNode.set("fire-spread", _fireSpread);
        dataNode.set("explosion-damage", _explosionDamage);
        dataNode.set("entity-change-block", _entityChangeBlock);
    }

    @Override
    public void deserialize(IDataNode dataNode) throws DeserializeException {

        _dataNode = dataNode;

        _leafDecay = dataNode.getEnum("leaf-decay",
                FilterPermission.DEFAULT, FilterPermission.class);

        _iceMelt = dataNode.getEnum("ice-melt",
                FilterPermission.DEFAULT, FilterPermission.class);

        _snowMelt = dataNode.getEnum("snow-melt",
                FilterPermission.DEFAULT, FilterPermission.class);

        _soilDehydrate = dataNode.getEnum("soil-dehydrate",
                FilterPermission.DEFAULT, FilterPermission.class);

        _vineGrowth = dataNode.getEnum("vine-growth",
                FilterPermission.DEFAULT, FilterPermission.class);

        _mushroomGrowth = dataNode.getEnum("mushroom-growth",
                FilterPermission.DEFAULT, FilterPermission.class);

        _grassGrowth = dataNode.getEnum("grass-growth",
                FilterPermission.DEFAULT, FilterPermission.class);

        _myceliumGrowth = dataNode.getEnum("mycelium-growth",
                FilterPermission.DEFAULT, FilterPermission.class);

        _fireSpread = dataNode.getEnum("fire-spread",
                FilterPermission.DEFAULT, FilterPermission.class);

        _explosionDamage = dataNode.getEnum("explosion-damage",
                FilterPermission.DEFAULT, FilterPermission.class);

        _entityChangeBlock = dataNode.getEnum("entity-change-block",
                FilterPermission.DEFAULT, FilterPermission.class);
    }
}
