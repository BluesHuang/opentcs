/**
 * Copyright (c) The openTCS Authors.
 *
 * This program is free software and subject to the MIT license. (For details,
 * see the licensing information (LICENSE.txt) you should have received with
 * this copy of the software.)
 */
package org.opentcs.customizations;

import com.google.inject.AbstractModule;

/**
 * A base class for Guice modules adding or customizing bindings for the kernel application and the
 * plant overview application.
 * 为内核和工厂监控程序提供Guice模块新增或自定义绑定的基类
 * @author Martin Grzenia (Fraunhofer IML)
 */
public abstract class ConfigurableInjectionModule
    extends AbstractModule {

  /**
   * A provider for configuration bindings.
   * 配置绑定
   */
  private org.opentcs.configuration.ConfigurationBindingProvider configBindingProvider;

  /**
   * Returns the configuration bindung provider.
   *
   * @return The configuration binding provider.
   */
  public org.opentcs.configuration.ConfigurationBindingProvider getConfigBindingProvider() {
    return configBindingProvider;
  }

  /**
   * Sets the configuration binding provider.
   *
   * @param configBindingProvider The new configuration binding provider.
   */
  public void setConfigBindingProvider(
      org.opentcs.configuration.ConfigurationBindingProvider configBindingProvider) {
    this.configBindingProvider = configBindingProvider;
  }
}
