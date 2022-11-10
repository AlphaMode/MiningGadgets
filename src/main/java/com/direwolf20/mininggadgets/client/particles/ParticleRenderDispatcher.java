package com.direwolf20.mininggadgets.client.particles;

import com.direwolf20.mininggadgets.client.particles.laserparticle.LaserParticle;
import com.direwolf20.mininggadgets.client.particles.lightparticle.LightParticleType;
import com.direwolf20.mininggadgets.client.particles.playerparticle.PlayerParticleType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;

@Environment(EnvType.CLIENT)
public class ParticleRenderDispatcher {

    public static void registerProviders() {
        ParticleFactoryRegistry.getInstance().register(ModParticles.LASERPARTICLE.get(), LaserParticle.FACTORY);
        ParticleFactoryRegistry.getInstance().register(ModParticles.PLAYERPARTICLE.get(), PlayerParticleType.FACTORY::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.LIGHT_PARTICLE.get(), LightParticleType.LightParticleFactory::new);
    }
}
