package org.example.cpu_visual.cpu;

import org.example.cpu_visual.program.Command;

public class LSHandler extends ComHandler {
    @Override
    void run(Command com, CPU cpu) throws Exception {
        switch (com.getTask()) {
            case ld:
                cpu.ld(com.getVal1(), com.getVal2());
                break;
            case st:
                cpu.st(com.getVal1(), com.getVal2());
                break;
            case mv:
                cpu.mv(com.getVal1(), com.getVal2());
                break;
            case init:
                cpu.init(com.getVal1(), com.getVal2());
                break;
            case print:
                cpu.print();
                break;
            default:
                super.run(com, cpu);
        }
    }
}