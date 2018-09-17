package dna.machine;

import java.util.List;

public interface Instruction {
	// * modify the ALU and calculate the next value of
	// <code>instructionPointer</code>*/
	int execute(int instructionPointer, List<Integer> memory, ArithmeticLogicUnit alu);
}
