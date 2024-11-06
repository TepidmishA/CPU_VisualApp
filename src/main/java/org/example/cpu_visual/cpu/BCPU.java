package org.example.cpu_visual.cpu;

// fabric
public class BCPU {
    public static ICPU build() {
        ComHandler h = new ComHandler();

        h.add(new LSHandler())
         .add(new MathHandler());

        return new CPU(h);
    }
}