/**
 * Copyright (c) The openTCS Authors.
 *
 * This program is free software and subject to the MIT license. (For details,
 * see the licensing information (LICENSE.txt) you should have received with
 * this copy of the software.)
 */
package org.opentcs.kernelcontrolcenter;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.util.Modules;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.ServiceLoader;
import org.opentcs.configuration.ConfigurationBindingProvider;
import org.opentcs.configuration.cfg4j.Cfg4jConfigurationBindingProvider;
import org.opentcs.customizations.ConfigurableInjectionModule;
import org.opentcs.customizations.controlcenter.ControlCenterInjectionModule;
import org.opentcs.util.Environment;
import org.opentcs.util.logging.UncaughtExceptionLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The kernel control center process's default entry point.
 * 内核控制器进程的默认程序入口
 * @author Martin Grzenia (Fraunhofer IML)
 */
public class RunKernelControlCenter {

  /**
   * This class' logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(RunKernelControlCenter.class);

  /**
   * Prevents external instantiation.
   * 单例，不允许实例化
   */
  private RunKernelControlCenter() {
  }

  /**
   * The kernel control center client's main entry point.
   * 内核控制器的主程序入口
   * @param args the command line arguments
   */
  public static void main(final String[] args) {
    System.setSecurityManager(new SecurityManager());//启用Java安全管理器
    Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionLogger(false));//记录未捕获的异常

    Environment.logSystemInfo();//输出系统基础信息日志

    Injector injector = Guice.createInjector(customConfigurationModule());//Guice框架入口，创建一个IOC容器
    injector.getInstance(KernelControlCenterApplication.class).initialize();//应用初始化，启动
  }

  /**
   * Builds and returns a Guice module containing the custom configuration for the kernel control
   * center application, including additions and overrides by the user.
   * 构建并返回一个Guice Module，包括应用的用户自定义配置
   * @return The custom configuration module.
   */
  private static Module customConfigurationModule() {
    ConfigurationBindingProvider bindingProvider = configurationBindingProvider();
    ConfigurableInjectionModule kernelControlCenterInjectionModule
        = new DefaultKernelControlCenterInjectionModule();
    kernelControlCenterInjectionModule.setConfigBindingProvider(bindingProvider);
    return Modules.override(kernelControlCenterInjectionModule)
        .with(findRegisteredModules(bindingProvider));
  }

  /**
   * Finds and returns all Guice modules registered via ServiceLoader.
   * 查找所有注入的模块
   * @return The registered/found modules.
   */
  private static List<ControlCenterInjectionModule> findRegisteredModules(
      ConfigurationBindingProvider bindingProvider) {
    List<ControlCenterInjectionModule> registeredModules = new LinkedList<>();
    for (ControlCenterInjectionModule module
             : ServiceLoader.load(ControlCenterInjectionModule.class)) {
      LOG.info("Integrating injection module {}", module.getClass().getName());
      module.setConfigBindingProvider(bindingProvider);//每个模块绑定配置提供者
      registeredModules.add(module);
    }
    return registeredModules;
  }

  //配置提供者，使用Cfg4j配置框架，多层级配置系统，基线、默认、自定义
  private static ConfigurationBindingProvider configurationBindingProvider() {
    return new Cfg4jConfigurationBindingProvider(
        Paths.get(System.getProperty("opentcs.base", "."),
                  "config",
                  "opentcs-kernelcontrolcenter-defaults-baseline.properties")
            .toAbsolutePath(),
        Paths.get(System.getProperty("opentcs.base", "."),
                  "config",
                  "opentcs-kernelcontrolcenter-defaults-custom.properties")
            .toAbsolutePath(),
        Paths.get(System.getProperty("opentcs.home", "."),
                  "config",
                  "opentcs-kernelcontrolcenter.properties")
            .toAbsolutePath()
    );
  }
}
