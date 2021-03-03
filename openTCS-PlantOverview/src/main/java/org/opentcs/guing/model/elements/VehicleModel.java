/*
 * openTCS copyright information:
 * Copyright (c) 2005-2011 ifak e.V.
 * Copyright (c) 2012 Fraunhofer IML
 *
 * This program is free software and subject to the MIT license. (For details,
 * see the licensing information (LICENSE.txt) you should have received with
 * this copy of the software.)
 */
package org.opentcs.guing.model.elements;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.opentcs.data.model.Triple;
import org.opentcs.data.model.Vehicle;
import org.opentcs.data.order.TransportOrder;
import org.opentcs.guing.components.drawing.figures.VehicleFigure;
import org.opentcs.guing.components.properties.type.AngleProperty;
import org.opentcs.guing.components.properties.type.BooleanProperty;
import org.opentcs.guing.components.properties.type.CoursePointProperty;
import org.opentcs.guing.components.properties.type.KeyValueSetProperty;
import org.opentcs.guing.components.properties.type.LengthProperty;
import org.opentcs.guing.components.properties.type.PercentProperty;
import org.opentcs.guing.components.properties.type.SelectionProperty;
import org.opentcs.guing.components.properties.type.StringProperty;
import org.opentcs.guing.components.properties.type.TripleProperty;
import org.opentcs.guing.model.AbstractFigureComponent;
import org.opentcs.guing.model.FigureComponent;
import org.opentcs.guing.util.ResourceBundleUtil;

/**
 * Basic implementation of a vehicle. A vehicle has an unique number.
 *
 * @author Sebastian Naumann (ifak e.V. Magdeburg)
 * @author Stefan Walter (Fraunhofer IML)
 */
public class VehicleModel
    extends AbstractFigureComponent {

  public static final String LENGTH = "Length";
  public static final String ENERGY_LEVEL_CRITICAL = "EnergyLevelCritical";
  public static final String ENERGY_LEVEL_GOOD = "EnergyLevelGood";
  public static final String LOADED = "Loaded";
  public static final String STATE = "State";
  public static final String PROC_STATE = "ProcState";
  public static final String POINT = "Point";
  public static final String NEXT_POINT = "NextPoint";
  public static final String PRECISE_POSITION = "PrecisePosition";
  public static final String INITIAL_POSITION = "InitialPosition";
  public static final String ORIENTATION_ANGLE = "OrientationAngle";
  public static final String ENERGY_LEVEL = "EnergyLevel";
  public static final String ENERGY_STATE = "EnergyState";

  /**
   * The point the vehicle currently remains on.
   */
  private PointModel fPoint;
  /**
   * The point the vehicle will drive to next.
   */
  private PointModel fNextPoint;
  /**
   * The current position (x,y,z) the vehicle driver reported.
   */
  private Triple fPrecisePosition;
  /**
   * The current vehicle orientation.
   */
  private double fOrientationAngle;
  /**
   * The current drive order.
   */
  private List<FigureComponent> fDriveOrderComponents;
  /*
   * The color the drive order will be painted in.
   */
  private Color fDriveOrderColor = Color.BLACK;
  /**
   * Flag, whether the colors will get darker or brighter.
   */
  private boolean driveOrderColorDesc = false;
  /**
   * The state of the drive order.
   */
  private TransportOrder.State fDriveOrderState;
  /**
   * Flag whether the drive order will be displayed.
   */
  private boolean fDisplayDriveOrders;
  /**
   * Flag whether the view follows this vehicle as it drives.
   */
  private boolean fViewFollows;
  /**
   * A reference to the vehicle.
   */
  private Vehicle vehicle;

  /**
   * Creates a new instance.
   */
  public VehicleModel() {
    super();
    createProperties();
  }

  /**
   * Sets the point the vehicle currently remains on.
   *
   * @param point The point.
   */
  public void placeOnPoint(PointModel point) {
    fPoint = point;
  }

  @Override // AbstractFigureComponent
  public VehicleFigure getFigure() {
    return (VehicleFigure) super.getFigure();
  }

  /**
   * Returns the point the vehicle currently remains on.
   *
   * @return The current point.
   */
  public PointModel getPoint() {
    return fPoint;
  }

  /**
   * Returns the point the vehicle will drive to next.
   *
   * @return The next point.
   */
  public PointModel getNextPoint() {
    return fNextPoint;
  }

  /**
   * Sets the point the vehicle will drive to next.
   *
   * @param point The next point.
   */
  public void setNextPoint(PointModel point) {
    fNextPoint = point;
  }

  /**
   * Returns the current position.
   *
   * @return The position (x,y,z).
   */
  public Triple getPrecisePosition() {
    return fPrecisePosition;
  }

  /**
   * Sets the current position
   *
   * @param position A triple containing the position.
   */
  public void setPrecisePosition(Triple position) {
    fPrecisePosition = position;
  }

  /**
   * Returns the current orientation angle.
   *
   * @return The orientation angle.
   */
  public double getOrientationAngle() {
    return fOrientationAngle;
  }

  /**
   * Sets the orientation angle.
   *
   * @param angle The new angle.
   */
  public void setOrientationAngle(double angle) {
    fOrientationAngle = angle;
  }

  /**
   * Returns a list with all drive order components.
   *
   * @return The drive order components.
   */
  public List<FigureComponent> getDriveOrderComponents() {
    return fDriveOrderComponents;
  }

  /**
   * Sets the drive order components.
   *
   * @param driveOrderComponents A list with the components.
   */
  public void setDriveOrderComponents(List<FigureComponent> driveOrderComponents) {
    if (fDriveOrderComponents == null && driveOrderComponents != null) {
      updateDriveOrderColor();
    }
    fDriveOrderComponents = driveOrderComponents;
  }

  /**
   * Updates the drive order color by making it darker or brighter.
   * It depends on the current color and the flag <code>driveOrderColorDesc</code>.
   */
  private void updateDriveOrderColor() {
    int red = fDriveOrderColor.getRed();
    int green = fDriveOrderColor.getGreen();
    int blue = fDriveOrderColor.getBlue();

    if (red >= 240 || green >= 240 || blue >= 240) {
      driveOrderColorDesc = true;
    }
    else if (red < 30 && red > 0 || green < 30 && green > 0 || blue < 30 && blue > 0) {
      driveOrderColorDesc = false;
    }

    if (driveOrderColorDesc) {
      red *= 0.8;
      green *= 0.8;
      blue *= 0.8;
      fDriveOrderColor = new Color(red, green, blue);
    }
    else {
      red *= 1.2;
      green *= 1.2;
      blue *= 1.2;

      if (red > 240) {
        red = 240;
      }

      if (green > 240) {
        green = 240;
      }

      if (blue > 240) {
        blue = 240;
      }

      fDriveOrderColor = new Color(red, green, blue);
    }
  }

  /**
   * Returns the color the drive order is painted in.
   *
   * @return The color.
   */
  public Color getDriveOrderColor() {
    return fDriveOrderColor;
  }

  /**
   * Sets the drive order color.
   *
   * @param color The color.
   */
  public void setDriveOrderColor(Color color) {
    fDriveOrderColor = Objects.requireNonNull(color, "color is null");
  }

  /**
   * Returns the state of the drive order.
   *
   * @return The state.
   */
  public TransportOrder.State getDriveOrderState() {
    return fDriveOrderState;
  }

  /**
   * Sets the drive order state.
   *
   * @param driveOrderState The new state.
   */
  public void setDriveOrderState(TransportOrder.State driveOrderState) {
    fDriveOrderState = driveOrderState;
  }

  /**
   * Sets whether the drive order shall be displayed or not.
   *
   * @param state <code>true</code> to display the drive order.
   */
  public void setDisplayDriveOrders(boolean state) {
    fDisplayDriveOrders = state;
  }

  /**
   * Returns whether the drive order is displayed.
   *
   * @return <code>true</code>, if it displayed.
   */
  public boolean getDisplayDriveOrders() {
    return fDisplayDriveOrders;
  }

  /**
   * Returns whether the view follows this vehicle as it drives.
   *
   * @return <code>true</code> if it follows.
   */
  public boolean isViewFollows() {
    return fViewFollows;
  }

  /**
   * Sets whether the view follows this vehicle as it drives.
   *
   * @param viewFollows <code>true</code> if it follows.
   */
  public void setViewFollows(boolean viewFollows) {
    this.fViewFollows = viewFollows;
  }

  /**
   * Returns the kernel object.
   *
   * @return The kernel object.
   */
  public Vehicle getVehicle() {
    return vehicle;
  }

  /**
   * Sets the kernel object.
   *
   * @param vehicle The kernel object.
   */
  public void setVehicle(Vehicle vehicle) {
    this.vehicle = vehicle;
  }

  /**
   * Checks whether the last reported processing state of the vehicle would
   * allow it to be assigned an order.
   *
   * @return <code>true</code> if, and only if, the vehicle's processing state
   * is not UNAVAILABLE.
   */
  public boolean isAvailableForOrder() {
    return vehicle != null
        && !vehicle.hasProcState(Vehicle.ProcState.UNAVAILABLE);
  }

  @Override // AbstractModelComponent
  public String getTreeViewName() {
    String treeViewName = getDescription() + " " + getName();

    return treeViewName;
  }

  @Override // AbstractModelComponent
  public String getDescription() {
    return ResourceBundleUtil.getBundle().getString("vehicle.description");
  }

  private void createProperties() {
    ResourceBundleUtil bundle = ResourceBundleUtil.getBundle();
    // Name
    StringProperty pName = new StringProperty(this);
    pName.setDescription(bundle.getString("vehicle.name.text"));
    pName.setHelptext(bundle.getString("vehicle.name.helptext"));
    setProperty(NAME, pName);
    // Fahrzeuglänge
    LengthProperty pLength = new LengthProperty(this);
    pLength.setDescription(bundle.getString("vehicle.length.text"));
    pLength.setHelptext(bundle.getString("vehicle.length.helptext"));
    setProperty(LENGTH, pLength);
    // Unterer Schwellwert für "kritischen" Batterie-Ladezustand
    PercentProperty pEnergyLevelCritical = new PercentProperty(this, true);
    pEnergyLevelCritical.setDescription(bundle.getString("vehicle.energyLevelCritical.text"));
    pEnergyLevelCritical.setHelptext(bundle.getString("vehicle.energyLevelCritical.helptext"));
    setProperty(ENERGY_LEVEL_CRITICAL, pEnergyLevelCritical);
    // Obererer Schwellwert für "guten" Batterie-Ladezustand
    PercentProperty pEnergyLevelGood = new PercentProperty(this, true);
    pEnergyLevelGood.setDescription(bundle.getString("vehicle.energyLevelGood.text"));
    pEnergyLevelGood.setHelptext(bundle.getString("vehicle.energyLevelGood.helptext"));
    setProperty(ENERGY_LEVEL_GOOD, pEnergyLevelGood);
    // Initiale Fahrzeugposition
    CoursePointProperty pInitialPosition = new CoursePointProperty(this);
    pInitialPosition.setDescription(bundle.getString("vehicle.initialPosition.text"));
    pInitialPosition.setHelptext(bundle.getString("vehicle.initialPosition.helptext"));
    setProperty(INITIAL_POSITION, pInitialPosition);
    // Diese Größen werden vom Fahrzeugtreiber gesetzt und sind nicht editierbar
    // Aktueller Batterie-Ladezustand
    PercentProperty pEnergyLevel = new PercentProperty(this, true);
    pEnergyLevel.setDescription(bundle.getString("vehicle.energyLevel.text"));
    pEnergyLevel.setHelptext(bundle.getString("vehicle.energyLevel.helptext"));
    pEnergyLevel.setModellingEditable(false);
    setProperty(ENERGY_LEVEL, pEnergyLevel);
    // Bewertung: Gut, ausreichend, kritisch
    SelectionProperty<EnergyState> pEnergyState = 
        new SelectionProperty<>(this, Arrays.asList(EnergyState.values()), EnergyState.CRITICAL);
    pEnergyState.setDescription(bundle.getString("vehicle.energyState.text"));
    pEnergyState.setHelptext(bundle.getString("vehicle.energyState.helptext"));
    pEnergyState.setModellingEditable(false);
    setProperty(ENERGY_STATE, pEnergyState);
    // Ist mindestens ein LAM beladen?
    BooleanProperty pLoaded = new BooleanProperty(this);
    pLoaded.setDescription(bundle.getString("vehicle.loaded.text"));
    pLoaded.setHelptext(bundle.getString("vehicle.loaded.helptext"));
    pLoaded.setModellingEditable(false);
    setProperty(LOADED, pLoaded);
    // State
    SelectionProperty<Vehicle.State> pState = 
        new SelectionProperty<>(this, Arrays.asList(Vehicle.State.values()), Vehicle.State.UNKNOWN);
    pState.setDescription(bundle.getString("vehicle.state.text"));
    pState.setHelptext(bundle.getString("vehicle.state.helptext"));
    pState.setModellingEditable(false);
    setProperty(STATE, pState);
    // Process state
    SelectionProperty<Vehicle.ProcState> pProcState = 
        new SelectionProperty<>(this, Arrays.asList(Vehicle.ProcState.values()), Vehicle.ProcState.UNAVAILABLE);
    pProcState.setDescription(bundle.getString("vehicle.procState.text"));
    pProcState.setHelptext("vehicle.procState.helptext");
    pProcState.setModellingEditable(false);
    setProperty(PROC_STATE, pProcState);
    // Position: Current Point
    StringProperty pPoint = new StringProperty(this);
    pPoint.setDescription(bundle.getString("vehicle.point.text"));
    pPoint.setHelptext(bundle.getString("vehicle.point.helptext"));
    pPoint.setModellingEditable(false);
    setProperty(POINT, pPoint);
    // Position: Next Point
    StringProperty pNextPoint = new StringProperty(this);
    pNextPoint.setDescription(bundle.getString("vehicle.nextPoint.text"));
    pNextPoint.setHelptext(bundle.getString("vehicle.nextPoint.helptext"));
    pNextPoint.setModellingEditable(false);
    setProperty(NEXT_POINT, pNextPoint);
    // Precise position
    TripleProperty pPrecisePosition = new TripleProperty(this);
    pPrecisePosition.setDescription(bundle.getString("vehicle.precisePosition.text"));
    pPrecisePosition.setHelptext(bundle.getString("vehicle.precisePosition.helptext"));
    pPrecisePosition.setModellingEditable(false);
    setProperty(PRECISE_POSITION, pPrecisePosition);
    // Angle orientation
    AngleProperty pOrientationAngle = new AngleProperty(this);
    pOrientationAngle.setDescription(bundle.getString("vehicle.orientationAngle.text"));
    pOrientationAngle.setHelptext(bundle.getString("vehicle.orientationAngle.helptext"));
    pOrientationAngle.setModellingEditable(false);
    setProperty(ORIENTATION_ANGLE, pOrientationAngle);
    // Miscellaneous properties
    KeyValueSetProperty pMiscellaneous = new KeyValueSetProperty(this);
    pMiscellaneous.setDescription(bundle.getString("vehicle.miscellaneous.text"));
    pMiscellaneous.setHelptext(bundle.getString("vehicle.miscellaneous.helptext"));
    setProperty(MISCELLANEOUS, pMiscellaneous);
  }

  public enum EnergyState {

    CRITICAL,
    DEGRADED,
    GOOD
  }
}