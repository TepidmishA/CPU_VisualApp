package org.example.cpu_visual.program;

import org.example.cpu_visual.DAO.BDAO;
import org.example.cpu_visual.DAO.DAO;
import org.example.cpu_visual.cpu.BCPU;
import org.example.cpu_visual.cpu.ICPU;

import java.util.Iterator;

public class Executer_visual implements Iterable<Command> {
	Iterator<Command> iter;
	ICPU cpu = BCPU.build();
	DAO dao = BDAO.getDAO();

	private Command currCom = null;
	public final Command getCurrCom() { return currCom; }

	void reset() {
		currCom = null;
		cpu.reset();
	}

	public int[] getMemStatus() {
		return cpu.getMem();
	}

	public int[] getRegsStatus() {
		return cpu.getRegs();
	}

	public void runNext() throws Exception {
		// начало исполнения команд
		if (currCom == null) {
			iter = this.iterator();
		}
		if (iter.hasNext()) {
			Command nextCom = iter.next();
			currCom = nextCom;

			cpu.run(nextCom);
		}
	}

	@Override
	public Iterator<Command> iterator() {
		return dao.iterator();
	}
}
