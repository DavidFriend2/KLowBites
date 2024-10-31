package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import UnitConversion.VolumeUnitConverter;

class VolumeUnitConverterTest
{

  @Test
  void testConvert()
  {
    assertEquals(VolumeUnitConverter.convert(0, "pinches", "fluid ounces"), 0.0);
    
    assertEquals(VolumeUnitConverter.convert(3, "Cups", "cups"), 3.0);
    
    assertEquals(VolumeUnitConverter.convert(1, "fluid ounces", "milliliters"), 29.57353);
    
    assertEquals(VolumeUnitConverter.convert(1, "gallons", "pinches"), 12288.0);
  }

}
