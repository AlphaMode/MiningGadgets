package com.direwolf20.mininggadgets.client.particles;

import com.direwolf20.mininggadgets.client.particles.laserparticle.LaserParticleData;
import com.direwolf20.mininggadgets.client.particles.laserparticle.LaserParticleType;
import com.direwolf20.mininggadgets.client.particles.lightparticle.LightParticleType;
import com.direwolf20.mininggadgets.client.particles.playerparticle.PlayerParticleData;
import com.direwolf20.mininggadgets.client.particles.playerparticle.PlayerParticleType;
import com.direwolf20.mininggadgets.common.MiningGadgets;
import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;

public class ModParticles {
    public static final LazyRegistrar<ParticleType<?>> PARTICLE_TYPES = LazyRegistrar.create(Registry.PARTICLE_TYPE, MiningGadgets.MOD_ID);

    public static final RegistryObject<ParticleType<LaserParticleData>> LASERPARTICLE = PARTICLE_TYPES.register("laserparticle", LaserParticleType::new);
    public static final RegistryObject<ParticleType<PlayerParticleData>> PLAYERPARTICLE = PARTICLE_TYPES.register("playerparticle", PlayerParticleType::new);
    public static final RegistryObject<ParticleType<SimpleParticleType>> LIGHT_PARTICLE = PARTICLE_TYPES.register("light_particle", LightParticleType::new);
}
