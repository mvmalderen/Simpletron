import java.util.Scanner;

public class SimpletronMachine
{
  public SimpletronMachine()
  {
    stMemory = new SimpletronMemory(MEMORY_SLOTS);
    stProcessor = new SimpletronProcessor(stMemory);
  }
  
  // Let the user write values to the memory of this Simpletron.
  public void inputProgram()
  {
    // Display instructions on how to enter a program into 
    // the Simpletron's memory.
    System.out.println("*** Welcome to Simpletron! ***");
    System.out.println("*** Please enter your program one instruction   ***");
    System.out.println("*** (or data word) at a time. I will display    ***");
    System.out.println("*** the location number and a question mark (?) ***");
    System.out.println("*** You then type the word for that location.   ***");
    System.out.println("*** Type -99999 to stop entering your program.  ***");
    System.out.println();
    
    Scanner scan = new Scanner(System.in);
    
    // Sentinel value that denotes the user is finished 
    // with entering the program.
    final int END_OF_INPUT = -99999;
    
    for (int memorySlot = 0; memorySlot < stMemory.getSize(); memorySlot++)
    {
      // Prompt for data to load this memory slot with.
      System.out.printf("%02d ? ", memorySlot);
      
      // Get data and write it to memory.
      int data = scan.nextInt();
      if (data == END_OF_INPUT) break;
      else stMemory.write(memorySlot, data);
    }
    
    System.out.println("*** Program loading completed ***");
    System.out.println();
  }
  
  public void computerDump()
  {
    System.out.println();
    
    System.out.println("REGISTERS:");
    stProcessor.dump();
    
    System.out.println();
    
    System.out.println("MEMORY:");
    stMemory.dump();
  }
  
  // Execute program in Simpletron's memory.
  public void executeProgram()
  {
    System.out.println("*** Program execution begins  ***");
    
    // Keep executing as long as no HALT instruction
    // is encountered, and as long as no error has occurred.
    while (stProcessor.executeNextInstruction()) ;
    
    computerDump();
  }
  
  // Random access memory (RAM).
  private SimpletronMemory stMemory;
  
  // Central processing unit (CPU).
  private SimpletronProcessor stProcessor;
  
  // Amount of memory slots for Simpletron machines.
  public static final int MEMORY_SLOTS = 100;
}