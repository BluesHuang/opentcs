/**
 * Copyright (c) The openTCS Authors.
 *
 * This program is free software and subject to the MIT license. (For details,
 * see the licensing information (LICENSE.txt) you should have received with
 * this copy of the software.)
 */
package com.example.commadapter.vehicle;

import com.google.inject.assistedinject.FactoryModuleBuilder;
import org.opentcs.customizations.kernel.KernelInjectionModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExampleKernelInjectionModule
    extends KernelInjectionModule {

  private static final Logger LOG = LoggerFactory.getLogger(ExampleKernelInjectionModule.class);

  @Override
  protected void configure() {

    ExampleCommAdapterConfiguration configuration
        = getConfigBindingProvider().get(ExampleCommAdapterConfiguration.PREFIX,
                                         ExampleCommAdapterConfiguration.class);

    if (!configuration.enable()) {
      LOG.info("Example communication adapter disabled by configuration.");
      return;
    }

    //通过FactoryModuleBuilder实现辅助注入
    install(new FactoryModuleBuilder().build(ExampleAdapterComponentsFactory.class));
    vehicleCommAdaptersBinder().addBinding().to(ExampleCommAdapterFactory.class);
  }
}
