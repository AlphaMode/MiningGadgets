package com.direwolf20.mininggadgets.client.particles;

import com.direwolf20.mininggadgets.client.particles.laserparticle.LaserParticle;
import com.direwolf20.mininggadgets.client.particles.lightparticle.LightParticleType;
import com.direwolf20.mininggadgets.client.particles.playerparticle.PlayerParticleType;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;

public class ParticleRenderDispatcher {

    public static void registerFactories() {
        ParticleFactoryRegistry.getInstance().register(ModParticles.LASERPARTICLE.get(), LaserParticle.FACTORY);
        ParticleFactoryRegistry.getInstance().register(ModParticles.PLAYERPARTICLE.get(), PlayerParticleType.FACTORY::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.LIGHT_PARTICLE.get(), LightParticleType.LightParticleFactory::new);
    }
}
