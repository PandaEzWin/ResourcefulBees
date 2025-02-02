package com.teamresourceful.resourcefulbees.api.beedata.spawning;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.resourcefulbees.api.beedata.CodecUtils;
import com.teamresourceful.resourcefulbees.common.config.CommonConfig;
import com.teamresourceful.resourcefulbees.common.lib.enums.LightLevel;
import com.teamresourceful.resourcefulbees.common.registry.custom.BiomeDictionary;
import com.teamresourceful.resourcefulbees.common.utils.MathUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.MobSpawnSettings;
import org.apache.commons.lang3.text.WordUtils;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;

@Unmodifiable
public class SpawnData {
    public static final SpawnData DEFAULT = new SpawnData(false, 0, 0, 0, Collections.emptySet(), Collections.emptySet(), LightLevel.ANY, 0, 0);
    private static final Set<ResourceLocation> DEFAULT_WHITELIST = Collections.singleton(new ResourceLocation("tag:overworld"));
    private static final Set<ResourceLocation> DEFAULT_BLACKLIST = Collections.singleton(new ResourceLocation("tag:ocean"));

    /**
     * A {@link Codec<SpawnData>} that can be parsed to create a
     * {@link SpawnData} object.
     */
    public static final Codec<SpawnData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.BOOL.fieldOf("canSpawnInWorld").orElse(false).forGetter(SpawnData::canSpawnInWorld),
            Codec.intRange(0, 1000).fieldOf("spawnWeight").orElse(8).forGetter(SpawnData::getSpawnWeight),
            Codec.intRange(0, 8).fieldOf("minGroupSize").orElse(0).forGetter(SpawnData::getMinGroupSize),
            Codec.intRange(0, 8).fieldOf("maxGroupSize").orElse(3).forGetter(SpawnData::getMaxGroupSize),
            CodecUtils.createSetCodec(ResourceLocation.CODEC).fieldOf("biomeWhitelist").orElse(DEFAULT_WHITELIST).forGetter(SpawnData::getBiomeWhitelist),
            CodecUtils.createSetCodec(ResourceLocation.CODEC).fieldOf("biomeBlacklist").orElse(DEFAULT_BLACKLIST).forGetter(SpawnData::getBiomeBlacklist),
            LightLevel.CODEC.fieldOf("lightLevel").orElse(LightLevel.ANY).forGetter(SpawnData::getLightLevel),
            Codec.intRange(0, 256).fieldOf("minYLevel").orElse(50).forGetter(SpawnData::getMinYLevel),
            Codec.intRange(0, 256).fieldOf("maxYLevel").orElse(256).forGetter(SpawnData::getMaxYLevel)
            ).apply(instance, SpawnData::new)
    );

    protected boolean canSpawnInWorld;
    protected int spawnWeight;
    protected int minGroupSize;
    protected int maxGroupSize;
    protected Set<ResourceLocation> biomeWhitelist;
    protected Set<ResourceLocation> biomeBlacklist;
    protected LightLevel lightLevel;
    protected int minYLevel;
    protected int maxYLevel;
    private final Set<ResourceLocation> spawnableBiomes = new HashSet<>();

    private SpawnData(boolean canSpawnInWorld, int spawnWeight, int minGroupSize, int maxGroupSize, Set<ResourceLocation> biomeWhitelist, Set<ResourceLocation> biomeBlacklist, LightLevel lightLevel, int minYLevel, int maxYLevel) {
        this.canSpawnInWorld = canSpawnInWorld;
        this.spawnWeight = spawnWeight;
        this.minGroupSize = minGroupSize;
        this.maxGroupSize = maxGroupSize;
        this.biomeWhitelist = biomeWhitelist;
        this.biomeBlacklist = biomeBlacklist;
        this.lightLevel = lightLevel;
        this.minYLevel = minYLevel;
        this.maxYLevel = maxYLevel;
        if (!biomeWhitelist.isEmpty()) buildSpawnableBiomes();
    }

    /**
     *
     * @return Returns <tt>true</tt> if the associated bee can spawn naturally in the world.
     */
    public boolean canSpawnInWorld() { return canSpawnInWorld; }

    /**
     * The spawn weight for most passive mobs such as sheep and cows is <tt>8</tt>. Resourceful Bees
     * default to the same weight value if one is not supplied in the json file. This value gets
     * calculated against the total weight of all mobs that can spawn in the biome for which the mobs
     * are trying to spawn in. Formula: <tt>chance = weight/total_weight</tt>.
     * <br>Example:<br>
     * Four bees are spawning in a biome and they are the only mobs that can spawn in the biome.
     * The weights of the four bees are as follows: 15, 15, 10, 10. The percent chance that bee
     * number one would spawn is 15/50 or 30%.
     *
     * @return Returns the spawn weight for the associated bee.
     */
    public int getSpawnWeight() {
        return spawnWeight;
    }

    /**
     * This method is a helper method used for getting a {@link MobSpawnSettings.SpawnerData} object
     * that can be passed into the biome's spawner.
     *
     * @param entityType The entity type spawner data is being created for.
     * @param isFlowerForest Is the biome this is being used for a flower forest biome?
     * @return Returns a {@link MobSpawnSettings.SpawnerData} object to be used for adding
     * the associated bees spawn to biomes.
     */
    public MobSpawnSettings.SpawnerData getSpawnerData(EntityType<?> entityType, boolean isFlowerForest) {
        return new MobSpawnSettings.SpawnerData(entityType, isFlowerForest ? spawnWeight + CommonConfig.BEE_FLOWER_FOREST_MULTIPLIER.get() : spawnWeight, minGroupSize, maxGroupSize);
    }

    /**
     *
     * @return Returns the minimum quantity of the associated bee type that will spawn
     * in a single spawn event.
     */
    public int getMinGroupSize() { return minGroupSize; }

    /**
     *
     * @return Returns the maximum quantity of the associated bee type that can spawn in a single
     *  spawn event.
     */
    public int getMaxGroupSize() { return maxGroupSize; }

    /**
     * Bee spawns are established after applying the biome blacklist to this list. The default
     * implementation sets the biome whitelist to <tt>"tag:overworld"</tt> and the biome
     * blacklist to <tt>"tag:ocean"</tt> which would make the bees spawn in any overworld biome
     * <b>except</b> biomes in the ocean tag.
     *
     * @return Returns a {@link Set} of {@link ResourceLocation}s representing the biomes
     * the associated bee can spawn in <b>before</b> modifications are made.
     */
    public Set<ResourceLocation> getBiomeWhitelist() { return biomeWhitelist; }

    /**
     * Bee spawns are established after applying this list to the biome whitelist. The default
     * implementation sets the biome whitelist to <tt>"tag:overworld"</tt> and the biome
     * blacklist to <tt>"tag:ocean"</tt> which would make the bees spawn in any overworld biome
     * <b>except</b> biomes in the ocean tag.
     *
     * @return Returns a {@link Set} of {@link ResourceLocation}s representing the biomes
     * the associated bee <b>cannot</b> spawn in.
     */
    public Set<ResourceLocation> getBiomeBlacklist() { return biomeBlacklist; }

    /**
     * TODO finish this after integrating light level with honeycomb production
     * @return Returns the light level the bee can spawn in.
     */
    public LightLevel getLightLevel() { return lightLevel; }

    /**
     * y-level spawn restrictions can seem a bit buggy when set for underground spawns
     * bc they are exceedingly rare. We have not yet determined why they are rarer than
     * hostile mobs other than the different {@link net.minecraft.world.entity.MobCategory}s.
     *
     * @return Returns the minimum y-level the associated bee will spawn at.
     */
    public int getMinYLevel() { return minYLevel; }

    /**
     * y-level spawn restrictions can seem a bit buggy when set for underground spawns
     * bc they are exceedingly rare. We have not yet determined why they are rarer than
     * hostile mobs other than the different {@link net.minecraft.world.entity.MobCategory}s.
     *
     * @return Returns the maximum y-level the associated bee will spawn at.
     */
    public int getMaxYLevel() { return maxYLevel; }

    /**
     * Bee spawns are established after applying the biome blacklist to the biome whitelist. The
     * default implementation sets the biome whitelist to <tt>"tag:overworld"</tt> and the biome
     * blacklist to <tt>"tag:ocean"</tt> which would make the bees spawn in any overworld biome
     * <b>except</b> biomes in the ocean tag.
     *
     * @return Returns a {@link Set} of {@link ResourceLocation}s representing the biomes
     * the associated bee can spawn in <b>after</b> modifications are made.
     */
    public Set<ResourceLocation> getSpawnableBiomes() {
        return spawnableBiomes;
    }

    /**
     *
     * @return Returns the modified spawnable biome list in a {@link String} format.
     */
    public String getSpawnableBiomesAsString() {
        StringJoiner returnList = new StringJoiner(", ");
        spawnableBiomes.forEach(resourceLocation -> returnList.add(WordUtils.capitalize(resourceLocation.getPath().replace("_", " "))));
        return returnList.toString();
    }

    public SpawnData toImmutable() {
        return this;
    }

    //region Setup
    private void buildSpawnableBiomes() {
        biomeWhitelist.stream()
                .filter(resourceLocation -> resourceLocation.getNamespace().equals("tag"))
                .forEach(this::addBiomesFromTag);
        biomeWhitelist.stream()
                .filter(resourceLocation -> !resourceLocation.getNamespace().equals("tag"))
                .forEach(spawnableBiomes::add);
        biomeBlacklist.stream()
                .filter(resourceLocation -> resourceLocation.getNamespace().equals("tag"))
                .forEach(this::removeBiomesFromTag);
        biomeBlacklist.stream()
                .filter(resourceLocation -> resourceLocation.getNamespace().equals("tag"))
                .forEach(spawnableBiomes::remove);
    }

    private void addBiomesFromTag(ResourceLocation resourceLocation) {
        if (Boolean.TRUE.equals(CommonConfig.USE_FORGE_DICTIONARIES.get())) {
            net.minecraftforge.common.BiomeDictionary.Type type = BiomeDictionary.getForgeType(resourceLocation);
            if (type != null) {
                spawnableBiomes.addAll(BiomeDictionary.getForgeBiomeLocations(type));
            }
        }  else {
            if (BiomeDictionary.get().containsKey(resourceLocation.getPath())) {
                spawnableBiomes.addAll(BiomeDictionary.get().get(resourceLocation.getPath()));
            }
        }
    }

    private void removeBiomesFromTag(ResourceLocation resourceLocation) {
        if (Boolean.TRUE.equals(CommonConfig.USE_FORGE_DICTIONARIES.get())) {
            net.minecraftforge.common.BiomeDictionary.Type type = BiomeDictionary.getForgeType(resourceLocation);
            if (type != null) {
                spawnableBiomes.removeAll(BiomeDictionary.getForgeBiomeLocations(type));
            }
        }  else {
            if (BiomeDictionary.get().containsKey(resourceLocation.getPath())) {
                spawnableBiomes.removeAll(BiomeDictionary.get().get(resourceLocation.getPath()));
            }
        }
    }

    public boolean canSpawnAtYLevel(BlockPos nestPos) {
        return MathUtils.inRangeInclusive(nestPos.getY(), minYLevel, maxYLevel);
    }
    //endregion

    public static class Mutable extends SpawnData {
        public Mutable(boolean canSpawnInWorld, int spawnWeight, int minGroupSize, int maxGroupSize, Set<ResourceLocation> biomeWhitelist, Set<ResourceLocation> biomeBlacklist, LightLevel lightLevel, int minYLevel, int maxYLevel) {
            super(canSpawnInWorld, spawnWeight, minGroupSize, maxGroupSize, biomeWhitelist, biomeBlacklist, lightLevel, minYLevel, maxYLevel);
        }

        public Mutable() {
            super(false, 8, 0, 3, DEFAULT_WHITELIST, DEFAULT_BLACKLIST, LightLevel.ANY, 50, 256);
        }

        public Mutable setCanSpawnInWorld(boolean canSpawnInWorld) {
            this.canSpawnInWorld = canSpawnInWorld;
            return this;
        }

        public Mutable setSpawnWeight(int spawnWeight) {
            this.spawnWeight = spawnWeight;
            return this;
        }

        public Mutable setMinGroupSize(int minGroupSize) {
            this.minGroupSize = minGroupSize;
            return this;
        }

        public Mutable setMaxGroupSize(int maxGroupSize) {
            this.maxGroupSize = maxGroupSize;
            return this;
        }

        public Mutable setBiomeWhitelist(Set<ResourceLocation> biomeWhitelist) {
            this.biomeWhitelist = biomeWhitelist;
            return this;
        }

        public Mutable setBiomeBlacklist(Set<ResourceLocation> biomeBlacklist) {
            this.biomeBlacklist = biomeBlacklist;
            return this;
        }

        public Mutable setLightLevel(LightLevel lightLevel) {
            this.lightLevel = lightLevel;
            return this;
        }

        public Mutable setMinYLevel(int minYLevel) {
            this.minYLevel = minYLevel;
            return this;
        }

        public Mutable setMaxYLevel(int maxYLevel) {
            this.maxYLevel = maxYLevel;
            return this;
        }

        @Override
        public SpawnData toImmutable() {
            return new SpawnData(this.canSpawnInWorld, this.spawnWeight, this.minGroupSize, this.maxGroupSize, this.biomeWhitelist, this.biomeBlacklist, this.lightLevel, this.minYLevel, this.maxYLevel);
        }
    }
}
