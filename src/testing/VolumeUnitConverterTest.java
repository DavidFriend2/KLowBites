package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import UnitConversion.VolumeUnitConverter;
import UnitConversion.MassVolumeConverter;

class VolumeUnitConverterTest 
{

  @Test
  void testToFluidOunce() 
  {
    assertEquals(1.0, VolumeUnitConverter.toFluidOunce(1, 
        MassVolumeConverter.Unit.FLUID_OUNCES), 0.0001);
    assertEquals(48.0, VolumeUnitConverter.toFluidOunce(1, 
        MassVolumeConverter.Unit.PINCHES), 0.0001);
    assertEquals(6.0, VolumeUnitConverter.toFluidOunce(1, 
        MassVolumeConverter.Unit.TEASPOONS), 0.0001);
    assertEquals(2.0, VolumeUnitConverter.toFluidOunce(1, 
        MassVolumeConverter.Unit.TABLESPOONS), 0.0001);
    assertEquals(8.0, VolumeUnitConverter.toFluidOunce(1, 
        MassVolumeConverter.Unit.CUPS), 0.0001);
    assertEquals(16.0, VolumeUnitConverter.toFluidOunce(1, 
        MassVolumeConverter.Unit.PINTS), 0.0001);
    assertEquals(32.0, VolumeUnitConverter.toFluidOunce(1, 
        MassVolumeConverter.Unit.QUARTS), 0.0001);
    assertEquals(128.0, VolumeUnitConverter.toFluidOunce(1, 
        MassVolumeConverter.Unit.GALLONS), 0.0001);
    assertEquals(29.57353, VolumeUnitConverter.toFluidOunce(1, 
        MassVolumeConverter.Unit.MILLILITERS), 0.00001);
  }

  @Test
  void testFromFluidOunce() 
  {
    assertEquals(48.0, VolumeUnitConverter.fromFluidOunce(1, 
        MassVolumeConverter.Unit.PINCHES), 0.0001);
    assertEquals(6.0, VolumeUnitConverter.fromFluidOunce(1, 
        MassVolumeConverter.Unit.TEASPOONS), 0.0001);
    assertEquals(2.0, VolumeUnitConverter.fromFluidOunce(1, 
        MassVolumeConverter.Unit.TABLESPOONS), 0.0001);
    assertEquals(0.125, VolumeUnitConverter.fromFluidOunce(1, 
        MassVolumeConverter.Unit.CUPS), 0.0001);
    assertEquals(0.0625, VolumeUnitConverter.fromFluidOunce(1, 
        MassVolumeConverter.Unit.PINTS), 0.0001);
    assertEquals(0.03125, VolumeUnitConverter.fromFluidOunce(1, 
        MassVolumeConverter.Unit.QUARTS), 0.0001);
    assertEquals(0.0078125, VolumeUnitConverter.fromFluidOunce(1, 
        MassVolumeConverter.Unit.GALLONS), 0.0001);
    assertEquals(29.57353, VolumeUnitConverter.fromFluidOunce(1, 
        MassVolumeConverter.Unit.MILLILITERS), 0.00001);
  }
}
