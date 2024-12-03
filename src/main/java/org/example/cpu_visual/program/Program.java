package org.example.cpu_visual.program;

import org.example.cpu_visual.DAO.DAO;
import org.example.cpu_visual.DAO.BDAO;
import org.example.cpu_visual.controller.program.IObserver;
import org.example.cpu_visual.cpu.BCPU;
import org.example.cpu_visual.cpu.ICPU;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.*;


public class Program implements Iterable<Command> {
//	ArrayList<Command> coms = new ArrayList<>();
	DAO dao = BDAO.getDAO();
	ArrayList<IObserver> allObserver = new ArrayList<>();
	Executer_visual exec = new Executer_visual();

	HashMap<Integer, Integer> regs = new HashMap<>();
	public void regsAdd(int ind, int val) {
		regs.put(ind, val);
	}

	// =========================================
	// Часть для наблюдателей
	void eventCall() {
		allObserver.forEach(action->action.event(this));
	}

	public void addObserver(IObserver e){
		allObserver.add(e);
		newRegs = true;	// fix later
		eventCall();
	}

	// =========================================
	// Работа с текущей командой
	public final Command getCurrCom() { return exec.getCurrCom(); }

	// =========================================
	// Команды визуальной части
	boolean newRegs = true;
	public boolean isNewRegs() { return newRegs; }
	public void setNewRegs(boolean newRegs) {
		this.newRegs = newRegs;
	}

	// =========================================
	// работа с CPU
	public int[] getMemStatus() { return exec.getMemStatus(); }
	public int[] getRegsStatus() {
		return exec.getRegsStatus();
	}
	public void runNext() throws Exception {
		exec.runNext();
		eventCall();
	}

	public void addCom(Command com) {
		dao.addCom(com);
		this.reset();
		eventCall();
	}

	public void comRemove(Command com) {
		dao.comRemove(com);
		this.reset();
		eventCall();
	}

	public void comMoveUp(Command com) {
		dao.comMoveUp(com);
		this.reset();
		eventCall();
	}

	public void comMoveDown(Command com) {
		dao.comMoveDown(com);
		this.reset();
		eventCall();
	}

	// =========================================
	// Методы для сбора статистики по командам
	public Map<Task, Long> getSortedComs() { return dao.getSortedComs(); }
	public String mostPopularInstruction() { return dao.mostPopularInstruction(); }
	public void showSortedComs() { dao.showSortedComs(); }
	public void showMemoryRange() { dao.showMemoryRange(); }

	// =========================================
	public void reset() {
		newRegs = true;
		exec.reset();
		eventCall();
	}

	@Override
	public Iterator<Command> iterator() {
		return dao.iterator();
	}
}