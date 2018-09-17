package dna.machine;

import java.util.List;

public interface InstructionPool {
	// * convert byte(s)in memory at/from instructionPointer to Instruction */
	Instruction decode(int instructionPointer, List<Integer> memory);
}
