package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import UnitConversion.MassUnitConverter;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import UnitConversion.MassVolumeConverter;

class MassUnitConverterTest 
{
  @Test
  void testConvert() 
  {
      // Convert Pounds to Ounces
    assertEquals(192, MassUnitConverter.convert(12, MassVolumeConverter.Unit.POUNDS, 
        MassVolumeConverter.Unit.OUNCES), 0.001);
    
    // Convert Ounces to Pounds
    assertEquals(0.640625, MassUnitConverter.convert(10.25, MassVolumeConverter.Unit.OUNCES, 
        MassVolumeConverter.Unit.POUNDS), 0.001);
    
    // Convert Grams to Drams 
    assertEquals(0.564373897707231, MassUnitConverter.convert(1, MassVolumeConverter.Unit.GRAMS, 
        MassVolumeConverter.Unit.DRAMS), 0.001);
    
    // Convert Drams to Grams
    assertEquals(1.77184519375, MassUnitConverter.convert(1, MassVolumeConverter.Unit.DRAMS, 
        MassVolumeConverter.Unit.GRAMS), 0.001);
    
    // Convert Ounces to Pounds
    assertEquals(6.25, MassUnitConverter.convert(100, MassVolumeConverter.Unit.OUNCES, 
        MassVolumeConverter.Unit.POUNDS), 0.001);
    
    // Convert Ounces to Ounces
    assertEquals(100, MassUnitConverter.convert(100, MassVolumeConverter.Unit.OUNCES, 
        MassVolumeConverter.Unit.OUNCES), 0.001);
  }
}
