/**
 * Copyright (c) The openTCS Authors.
 *
 * This program is free software and subject to the MIT license. (For details,
 * see the licensing information (LICENSE.txt) you should have received with
 * this copy of the software.)
 */
package org.opentcs.customizations.controlcenter;

import com.google.inject.multibindings.Multibinder;
import org.opentcs.components.kernelcontrolcenter.ControlCenterPanel;
import org.opentcs.customizations.ConfigurableInjectionModule;
import org.opentcs.drivers.peripherals.management.PeripheralCommAdapterPanelFactory;
import org.opentcs.drivers.vehicle.management.VehicleCommAdapterPanelFactory;

/**
 * A base class for Guice modules adding or customizing bindings for the kernel control center
 * application.
 * 为内核控制中心添加或自定义绑定的Guice模块基类
 * @author Martin Grzenia (Fraunhofer IML)
 */
public abstract class ControlCenterInjectionModule
    extends ConfigurableInjectionModule {

  /**
   * Returns a multibinder that can be used to register {@link ControlCenterPanel} implementations
   * for the kernel's modelling mode.
   * 注册控制中心面板，在内核建模模态下
   * @return The multibinder.
   */
  protected Multibinder<ControlCenterPanel> controlCenterPanelBinderModelling() {
    return Multibinder.newSetBinder(binder(),
                                    ControlCenterPanel.class,
                                    ActiveInModellingMode.class);
  }

  /**
   * Returns a multibinder that can be used to register {@link ControlCenterPanel} implementations
   * for the kernel's operating mode.
   * 注册控制中心面板，在内核操作模态下
   * @return The multibinder.
   */
  protected Multibinder<ControlCenterPanel> controlCenterPanelBinderOperating() {
    return Multibinder.newSetBinder(binder(),
                                    ControlCenterPanel.class,
                                    ActiveInOperatingMode.class);
  }

  /**
   * Returns a multibinder that can be used to register {@link VehicleCommAdapterPanelFactory}
   * implementations.
   * 注册小车通信适配器面板工厂
   * @return The multibinder.
   */
  protected Multibinder<VehicleCommAdapterPanelFactory> commAdapterPanelFactoryBinder() {
    return Multibinder.newSetBinder(binder(), VehicleCommAdapterPanelFactory.class);
  }

  /**
   * Returns a multibinder that can be used to register {@link PeripheralCommAdapterPanelFactory}
   * implementations.
   * 注册小车次要参数的通信适配器面板工厂
   * @return The multibinder.
   */
  protected Multibinder<PeripheralCommAdapterPanelFactory> peripheralCommAdapterPanelFactoryBinder() {
    return Multibinder.newSetBinder(binder(), PeripheralCommAdapterPanelFactory.class);
  }
}
