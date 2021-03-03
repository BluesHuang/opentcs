/*
 * openTCS copyright information:
 * Copyright (c) 2014 Fraunhofer IML
 *
 * This program is free software and subject to the MIT license. (For details,
 * see the licensing information (LICENSE.txt) you should have received with
 * this copy of the software.)
 */
package org.opentcs.access;

import java.io.Serializable;

/**
 * Thrown when there are insufficient user permissions to perform an operation.
 *
 * @author Stefan Walter (Fraunhofer IML)
 */
public class UnsupportedKernelOpException
    extends KernelRuntimeException
    implements Serializable {

  /**
   * Constructs a new instance with no detail message.
   */
  public UnsupportedKernelOpException() {
    super();
  }

  /**
   * Constructs a new instance with the specified detail message.
   *
   * @param message The detail message.
   */
  public UnsupportedKernelOpException(String message) {
    super(message);
  }

  /**
   * Constructs a new instance with the specified detail message and
   * cause.
   *
   * @param message The detail message.
   * @param cause The exception's cause.
   */
  public UnsupportedKernelOpException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructs a new instance with the specified cause and a detail
   * message of <code>(cause == null ? null : cause.toString())</code> (which
   * typically contains the class and detail message of <code>cause</code>).
   *
   * @param cause The exception's cause.
   */
  public UnsupportedKernelOpException(Throwable cause) {
    super(cause);
  }
}