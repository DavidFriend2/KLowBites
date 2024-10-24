package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import UnitConversion.MassUnitConverter;

class MassUnitConverterTest
{

  @Test
  void testConvert()
  {
    //Convert Pounds to Ounces
    assertEquals(MassUnitConverter.convert(12, "pounds", "ounces"), 192);
    
    //Convert Ounces to Pounds
    assertEquals(MassUnitConverter.convert(10.25, "ounces", "pounds"), 0.640625);
    
    //Convert Grams to Drams 
    assertEquals(MassUnitConverter.convert(1, "grams", "drams"), 0.564373897707231);
    
    //Convert Drams to Grams
    assertEquals(MassUnitConverter.convert(1, "drams", "grams"), 1.77184519375);
    
    //Convert Ounces to Pounds
    assertEquals(MassUnitConverter.convert(100, "ounces", "pounds"), 6.25);
    
    //Convert Ounces to Ounces
    assertEquals(MassUnitConverter.convert(100, "ounces", "ounces"), 100);
  }

}
