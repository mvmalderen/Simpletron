// Class offering static utility methods for working with
// Simpletron words.
public class SimpletronWord
{
  
  // Return the operation code part of this word.
  public static int getOperationCode(int stWord)
  {
    int totalDigits = 1 + (int) Math.log10(stWord);
    if (totalDigits <= DIGITS_PER_OPCODE) return 0;
    int exponent = totalDigits - DIGITS_PER_OPCODE;
    int powerOfTen = (int) Math.pow(10, exponent);
    int operationCode = stWord / powerOfTen;
    return operationCode;
  }
  
  // Return the operand part of this word.
  public static int getOperand(int stWord)
  {
    int totalDigits = 1 + (int) Math.log10(stWord);
    if (totalDigits <= DIGITS_PER_OPCODE) return stWord;
    int exponent = totalDigits - DIGITS_PER_OPCODE;
    int powerOfTen = (int) Math.pow(10, exponent);
    int operand = stWord % powerOfTen;
    return operand;
  }
  
  // Amount of digits that an opcode takes in a word.
  // Only set this to positive values! (zero included)
  public static final int DIGITS_PER_OPCODE = 2;
}