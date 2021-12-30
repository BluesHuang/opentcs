/**
 * Copyright (c) The openTCS Authors.
 *
 * This program is free software and subject to the MIT license. (For details,
 * see the licensing information (LICENSE.txt) you should have received with
 * this copy of the software.)
 */
package org.opentcs.virtualvehicle;

import com.google.inject.assistedinject.FactoryModuleBuilder;
import org.opentcs.customizations.kernel.KernelInjectionModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Configures/binds the loopback communication adapters of the openTCS kernel.
 * 向内核配置一个本地虚拟小车的驱动器
 * @author Stefan Walter (Fraunhofer IML)
 */
public class LoopbackCommAdapterModule
    extends KernelInjectionModule {

  /**
   * This class's logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(LoopbackCommAdapterModule.class);

  @Override
  protected void configure() {
    VirtualVehicleConfiguration configuration
        = getConfigBindingProvider().get(VirtualVehicleConfiguration.PREFIX,
                                         VirtualVehicleConfiguration.class);

    if (!configuration.enable()) {
      LOG.info("Loopback driver disabled by configuration.");
      return;
    }

    bind(VirtualVehicleConfiguration.class)
        .toInstance(configuration);
    //这种注入方式非常特别，只需要定义接口，实现由Guice自动生成，生成实现的规则是——创建一个接口中方法的返回类型
    install(new FactoryModuleBuilder().build(LoopbackAdapterComponentsFactory.class));

    // tag::documentation_createCommAdapterModule[]
    //添加一个小车驱动器，本地虚拟小车
    vehicleCommAdaptersBinder().addBinding().to(LoopbackCommunicationAdapterFactory.class);
    // end::documentation_createCommAdapterModule[]
  }

}
