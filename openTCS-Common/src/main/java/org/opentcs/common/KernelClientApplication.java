/**
 * Copyright (c) The openTCS Authors.
 *
 * This program is free software and subject to the MIT license. (For details,
 * see the licensing information (LICENSE.txt) you should have received with
 * this copy of the software.)
 */
package org.opentcs.common;

import org.opentcs.components.Lifecycle;

/**
 * Provides methods used in a kernel client application's context.
 * 内核客户端接口，提供一些内核客户端应用上下文需要使用的方法
 * @author Martin Grzenia (Fraunhofer IML)
 */
public interface KernelClientApplication
    extends Lifecycle {

  /**
   * Tells the application to switch its state to online.
   * 将客户端状态改为在线
   * @param autoConnect Whether to connect automatically to the kernel or to show a connect dialog
   * when going online.
   */
  public void online(boolean autoConnect);

  /**
   * Tells the application to switch its state to offline.
   * 将客户端状态改为离线
   */
  public void offline();

  /**
   * Checks whether the application's state is online.
   * 检查客户端状态是否为在线
   * @return Whether the application's state is online.
   */
  public boolean isOnline();

}
