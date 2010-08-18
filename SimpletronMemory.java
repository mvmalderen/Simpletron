public class SimpletronMemory
{
  public SimpletronMemory(int amountOfMemorySlots)
  {
    memory = new int[amountOfMemorySlots];
    error = NO_ERROR;
  }
  
  // Write a value to memory.
  public void write(int slot, int value)
  {    
    if (slot < 0 || slot >= memory.length)
    {
      System.out.println("*** Attempt to write to a non-existing"
                         + " memory location ***");
      error = WRITE_ERROR;
    }
    else memory[slot] = value;
  }
  
  // Read a value from memory.
  public int read(int slot)
  {
    int value = 0;
    
    if (slot < 0 || slot >= memory.length)
    {
      System.out.println("*** Attempt to read from a non-existing"
                         + " memory location ***");
      error = READ_ERROR;
    }
    else value = memory[slot];
    
    return value;
  }
  
  // Dump memory contents to screen.
  public void dump()
  {
    final int SLOTS_PER_LINE = 10;
    
    // Print table header.
    System.out.print("\t");
    for (int i = 0; i < SLOTS_PER_LINE; i++)
      System.out.printf("%5d ", i);
    
    // Print newline.
    System.out.println();
    
    // Print rest of memory map, SLOTS_PER_LINE memory slots at a time.
    for (int i = 0; i < memory.length; i += SLOTS_PER_LINE)
    {
      System.out.printf("%7d ", i);
      for (int j = 0; j < SLOTS_PER_LINE && i+j < memory.length; j++)
        System.out.printf("%+05d ", memory[i + j]);
      
      System.out.println();
    }
  }
  
  // Return true if a memory error has occured.
  public boolean hasErrorOccurred()
  {
    return (error != NO_ERROR);
  }
  
  // Return the error type.
  public int getError()
  {
    return error;
  }
  
  // Return the amount of memory slots.
  public int getSize()
  {
    return (memory.length);
  }
  
  private int[] memory;
  private int error;
  
  // Memory error types that can occur.
  public final int NO_ERROR = 0;
  public final int READ_ERROR = 1;
  public final int WRITE_ERROR = 2;
}