package com.trivago.cluecumber.engine.dagger;

import com.trivago.cluecumber.engine.CluecumberEngine;
import dagger.Component;

import javax.inject.Singleton;

@Component
@Singleton
public interface CluecumberCoreGraph {
    /**
     * Factory method contract.
     *
     * @return The {@link CluecumberEngine} instance.
     */
    CluecumberEngine getCluecumberEngine();
}
