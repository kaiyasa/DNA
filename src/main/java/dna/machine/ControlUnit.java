package dna.machine;

import java.util.List;

public class ControlUnit {
	private final ArithmeticLogicUnit alu = new ArithmeticLogicUnit();
	private final InstructionPool instructionPool;
	private final List<Integer> memory;
	private int instructionPointer;

	public ControlUnit(int initialInstructionPointer, List<Integer> memory, InstructionPool instructionPool) {
		this.memory = memory;
		this.instructionPool = instructionPool;
	}

	public void run(int initialInstructionPointer) {
		this.instructionPointer = initialInstructionPointer;
		while (instructionPointer >= 0 && instructionPointer < memory.size()) {
			Instruction instruction = instructionPool.decode(instructionPointer, memory);
			instructionPointer = instruction.execute(instructionPointer, memory, alu);
		}
	}
}