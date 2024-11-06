package org.example.cpu_visual;

import org.example.cpu_visual.cpu.BCPU;
import org.example.cpu_visual.cpu.ICPU;
import org.example.cpu_visual.program.Command;
import org.example.cpu_visual.program.Executer;
import org.example.cpu_visual.program.Program;

public class App
{
    public static void main( String[] args ) {
        Program prog = new Program();
        try {
            prog.addCom(new Command("init 10 20"));
            prog.addCom(new Command("init" ,"11", "25"));
            prog.addCom(new Command("ld", "a" ,"10"));
            prog.addCom(new Command("ld", "b" ,"11"));
            prog.addCom(new Command("ld", "c" ,"11"));
            prog.addCom(new Command("add"));
            prog.addCom(new Command("mv", "a" ,"d"));
            prog.addCom(new Command("add"));
            prog.addCom(new Command("print"));
        }
        catch (Exception err) {
            System.out.println(err.getMessage());
        }

        for(Command c: prog) System.out.println(c);

        System.out.println(prog.mostPopularInstruction()); // ld
        prog.showSortedComs();

        ICPU cpu = BCPU.build();
        Executer exec = new Executer(cpu);
        exec.run(prog);

        prog.showMemoryRange();
    }
}
