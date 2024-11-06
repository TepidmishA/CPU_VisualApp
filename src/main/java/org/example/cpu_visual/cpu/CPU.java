package org.example.cpu_visual.cpu;

import org.example.cpu_visual.exception.div_zero;
import org.example.cpu_visual.program.Command;

import java.lang.reflect.Array;

public class CPU implements ICPU{
    private int[] mem = new int[1024];          // RAM
    private int[] regs = new int[4];            // REGISTERS

    ComHandler h;

    @Override
    public void reset() {
        mem = new int[1024];
        regs = new int[4];
    }

    private void checkMem(int memInd) {                                     // Check memory index
        if (!(memInd >= 0 && memInd < Array.getLength(mem)))
            throw new IndexOutOfBoundsException("Invalid mem index: " + memInd);
    }

    private void checkReg(int regInd) {
        if (!(regInd >= 0 && regInd < Array.getLength(regs)))
            throw new IndexOutOfBoundsException("Invalid reg index: " + regInd);
    }

    // ???
    @Override
    public int[] getMem() {
        return mem;
    }

    @Override
    public int[] getRegs() {
        return regs;
    }

    public CPU(ComHandler h) {
        this.h = h;
    }

    @Override
    public void run(Command command) throws Exception {
        h.run(command, this);
    }

    void ld(int reg, int address) {
        checkReg(reg);
        checkMem(address);

        regs[reg] = mem[address];
    }

    void st(int reg, int address) {
        checkReg(reg);
        checkMem(address);

        mem[address] = regs[reg];
    }

    void mv(int reg1, int reg2) {
        checkReg(reg1);
        checkReg(reg2);

        regs[reg1] = regs[reg2];
    }

    void init(int address, int val) {
        checkMem(address);

        mem[address] = val;
    }

    void print() {
        StringBuilder out = new StringBuilder("Reg values: ");
        for (int i = 0; i < Array.getLength(regs) - 1; i++) {
            out.append("R" + i + ":" + regs[i] + ", ");
        }
        out.append("R" + (Array.getLength(regs) - 1) + ":" + regs[Array.getLength(regs) - 1]);
        System.out.println(out);
    }

    void add() {
        regs[3] = regs[0] + regs[1];
    }

    void sub() {
        regs[3] = regs[0] - regs[1];
    }

    void mul() {
        regs[3] = regs[0] * regs[1];
    }

    void div() throws div_zero {
        if (regs[1] == 0) {
            throw new div_zero();
        }
        regs[3] = regs[0] / regs[1];
    }
}