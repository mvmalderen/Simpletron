import java.util.Scanner;

public class SimpletronProcessor
{
  public SimpletronProcessor(SimpletronMemory stMemory)
  {
    accumulator = 0;
    instructionCounter = 0;
    instructionRegister = 0;
    operationCode = 0;
    operand = 0;
    halted = false;
    abnormalTermination = false;
    memory = stMemory;
  }
  
  // Execute next instruction from memory.
  // True is returned if the instruction that was 
  // executed was not a HALT-instruction, and if 
  // no error did occur in the attempt to execute 
  // an instruction. False is returned otherwise.
  public boolean executeNextInstruction()
  {
    // Processor only accepts instructions in a 
    // state in which it isn't halted.
    if (halted) return false;
    
    // Read next instruction from memory.
    instructionRegister = memory.read(instructionCounter);
    halted = abnormalTermination = memory.hasErrorOccurred();
    if (abnormalTermination) return false;
        
    // Get opcode and operand.
    operationCode = instructionRegister / 100;
    operand = instructionRegister % 100;
    
    // We need to remember if a transfer-of-control has occurred.
    boolean transferOfControl = false;
    
    // Attempt to execute the instruction.
    switch (operationCode)
    {
      case READ:
        Scanner scan = new Scanner(System.in);
        
        // Read a word from the keyboard.
        System.out.print("Enter an integer: ");
        
        // Write word to memory.
        memory.write(operand, scan.nextInt());
        halted = abnormalTermination = memory.hasErrorOccurred();
        
        break;
      
      case WRITE:
        // Read a word from memory.
        int data = memory.read(operand);
        
        // Display it on the screen if no error has occurred.
        halted = abnormalTermination = memory.hasErrorOccurred();
        if (!halted) System.out.println(data);
        
        break;
        
      case LOAD:
        // Load a word from memory into the accumulator.
        accumulator = memory.read(operand);
        halted = abnormalTermination = memory.hasErrorOccurred();
        break;
        
      case STORE:
        // Store accumulator into memory.
        memory.write(operand, accumulator);
        halted = abnormalTermination = memory.hasErrorOccurred();
        break;
        
      case ADD:
        // Add word from memory to accumulator.
        accumulator += memory.read(operand);
        halted = abnormalTermination = memory.hasErrorOccurred();
        break;
        
      case SUBTRACT:
        // Subtract word from memory from accumulator.
        accumulator -= memory.read(operand);
        halted = abnormalTermination = memory.hasErrorOccurred();
        break;
      
      case DIVIDE:
        // Divide accumulator by a word loaded from memory.
        int divisor = memory.read(operand);
        halted = abnormalTermination = memory.hasErrorOccurred();
        if (!halted)
          if (divisor == 0)
          {
            System.out.println("*** Attempt to divide by zero ***");
            halted = abnormalTermination = true;
          }
          else
            accumulator /= divisor;
        
        break;
      
      case MULTIPLY:
        // Multiply accumulator by a word loaded from memory.
        accumulator *= memory.read(operand);
        halted = abnormalTermination = memory.hasErrorOccurred();
        break;
      
      case BRANCH:
        // Branch to location in memory.
        instructionCounter = operand;
        transferOfControl = true;
        break;
        
      case BRANCHNEG:
        // Branch to location in memory if accumulator is negative.
        if (accumulator < 0)
        {
          instructionCounter = operand;
          transferOfControl = true;
        }
        break;
        
      case BRANCHZERO:
        // Branch to location in memory if accumulator is zero.
        if (accumulator == 0)
        {
          instructionCounter = operand;
          transferOfControl = true;
        }
        break;
        
      case HALT:
        // Halt the CPU.
        System.out.println("*** Simpletron execution terminated ***");
        transferOfControl = true;
        halted = true;
        break;
      
      default:
        System.out.println("*** Unrecognized operation code ***");
        // A fatal error has occurred: the processor cannot execute 
        // that what must be an instruction. Halt the processor.
        halted = abnormalTermination = true;
    }
    
    if (!transferOfControl)  // only increment instructionCounter if not 
      instructionCounter++;  // a transfer-of-control operation.
    
    if (abnormalTermination)
      System.out.println("*** Simpletron execution abnormally terminated ***");
    
    return !halted;
  }
  
  // Dump CPU register contents to screen.
  public void dump()
  {
    System.out.printf("%-35s%+05d\n", "accumulator ", accumulator);
    System.out.printf("%-38s%02d\n", "instructionCounter ", instructionCounter);
    System.out.printf("%-35s%+05d\n", "instructionRegister ", instructionRegister);
    System.out.printf("%-38s%02d\n", "operationCode ", operationCode);
    System.out.printf("%-38s%02d\n", "operand ", operand);
  }
  
  private int accumulator;
  private int instructionCounter;
  private int instructionRegister;
  private int operationCode;
  private int operand;
  private boolean halted;
  private boolean abnormalTermination;
  private SimpletronMemory memory;
  
  // *****     Operation codes     *****
  
  // Input/Output operations.
  private final int READ = 10;
  private final int WRITE = 11;
  
  // Load/Store operations.
  private final int LOAD = 20;
  private final int STORE = 21;
  
  // Arithmetic operations.
  private final int ADD = 30;
  private final int SUBTRACT = 31;
  private final int DIVIDE = 32;
  private final int MULTIPLY = 33;
  
  // Transfer-of-control operations.
  private final int BRANCH = 40;
  private final int BRANCHNEG = 41;
  private final int BRANCHZERO = 42;
  private final int HALT = 43;
}