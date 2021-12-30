/**
 * Copyright (c) The openTCS Authors.
 *
 * This program is free software and subject to the MIT license. (For details,
 * see the licensing information (LICENSE.txt) you should have received with
 * this copy of the software.)
 */
package org.opentcs.common;

import org.opentcs.access.KernelServicePortal;
import org.opentcs.components.kernel.services.ServiceUnavailableException;

/**
 * Declares methods for managing a connection to a remote portal.
 * 声明一些方法，管理远程端的连接
 * @author Stefan Walter (Fraunhofer IML)
 */
public interface PortalManager {

  /**
   * Tries to establish a connection to the portal.
   * 尝试与远程端建立连接
   * @param mode The mode to use for the connection attempt.
   * @return {@code true} if, and only if, the connection was established successfully.
   */
  boolean connect(ConnectionMode mode);

  /**
   * Tells the portal manager the connection to the portal was lost.
   * 告知连接管理器，连接已经断开
   */
  void disconnect();

  /**
   * Checks whether a connection to the portal is established.
   * 检查连接是否已经建立
   * @return {@code true} if, and only if, a connection to the portal is established.
   */
  boolean isConnected();

  /**
   * Returns the remote kernel client portal the manager is working with.
   * 返回一个内核客户端的入口
   * @return The remote kernel client portal.
   */
  KernelServicePortal getPortal();

  /**
   * Returns a description for the current connection.
   * 当前连接的描述
   * @return A description for the current connection.
   */
  String getDescription();

  /**
   * Returns the host currently connected to.
   * 当前连接的主机地址
   * @return The host currently connected to, or {@code null}, if not connected.
   */
  String getHost();

  /**
   * Returns the port currently connected to.
   * 当前连接的进程端口
   * @return The port currently connected to, or {@code -1}, if not connected.
   */
  int getPort();

  /**
   * Defines the states in which a portal manager instance may be in.
   * 连接状态枚举
   */
  public enum ConnectionState {

    /**
     * Indicates the portal manager is trying to connect to the remote portal.
     * 入口管理器试图连接远端的入口，连接中
     */
    CONNECTING,
    /**
     * Indicates the portal is connected and logged in to a remote portal, thus in a usable state.
     * 入口站点已经连接上，并登录到入口站点
     */
    CONNECTED,
    /**
     * Indicates the portal is disconnecting from the remote portal.
     * 连接正在端断开
     */
    DISCONNECTING,
    /**
     * Indicates the portal is not connected to a remote portal.
     * 连接已断开
     * While in this state, calls to the portal's service methods will result in a
     * {@link ServiceUnavailableException}.
     */
    DISCONNECTED;
  }

  /**
   * Defines the modes a portal manager uses to establish a connection to a portal.
   * 定义一个枚举，表示远程连接的多种模式
   */
  public enum ConnectionMode {

    /**
     * Connect automatically by using a predefined set of connection parameters.
     * 通过一些预定义的连接参数，自动建立连接
     */
    AUTO,
    /**
     * Connect manually by showing a dialog allowing to enter connection parameters.
     * 通过一个参数对话框，手动设置参数，建立连接
     */
    MANUAL,
    /**
     * Connect to the portal we were previously connected to.
     * 重写连接到上一个连接的远程端
     */
    RECONNECT;
  }
}
