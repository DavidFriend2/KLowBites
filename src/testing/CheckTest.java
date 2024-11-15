package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import utilities.Check;

class CheckTest
{
  // test
  @Test
  void forContainsTest()
  {
    String[] haystack = {"Horse","Cow","Dog","Cat"};
    assertTrue(Check.forContains(haystack, "Horse"));
    assertTrue(Check.forContains(haystack, "Cow"));
    assertTrue(Check.forContains(haystack, "Dog"));
    assertTrue(Check.forContains(haystack, "Cat"));
    
    assertFalse(Check.forContains(haystack, "Aardvark"));
  }

  @Test
  void forContainsTestEmpty()
  {
    String[] haystack = {};
    assertFalse(Check.forContains(haystack, "Aardvark"));
  }

  @Test
  void forContainsTestNull()
  {
    assertFalse(Check.forContains(null, "Aardvark"));
  }
}
