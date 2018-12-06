package com.hntyhb.detection.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.hntyhb.detection.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(com.hntyhb.detection.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(com.hntyhb.detection.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.hntyhb.detection.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.hntyhb.detection.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.hntyhb.detection.domain.Project.class.getName(), jcacheConfiguration);
            cm.createCache(com.hntyhb.detection.domain.Project.class.getName() + ".names", jcacheConfiguration);
            cm.createCache(com.hntyhb.detection.domain.Task.class.getName(), jcacheConfiguration);
            cm.createCache(com.hntyhb.detection.domain.Task.class.getName() + ".points", jcacheConfiguration);
            cm.createCache(com.hntyhb.detection.domain.Point.class.getName(), jcacheConfiguration);
            cm.createCache(com.hntyhb.detection.domain.Point.class.getName() + ".samples", jcacheConfiguration);
            cm.createCache(com.hntyhb.detection.domain.SampleUser.class.getName(), jcacheConfiguration);
            cm.createCache(com.hntyhb.detection.domain.SampleUser.class.getName() + ".sample1S", jcacheConfiguration);
            cm.createCache(com.hntyhb.detection.domain.SampleUser.class.getName() + ".sample2S", jcacheConfiguration);
            cm.createCache(com.hntyhb.detection.domain.Sample.class.getName(), jcacheConfiguration);
            cm.createCache(com.hntyhb.detection.domain.Sample.class.getName() + ".params", jcacheConfiguration);
            cm.createCache(com.hntyhb.detection.domain.Params.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
