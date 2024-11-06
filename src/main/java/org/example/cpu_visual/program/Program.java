package org.example.cpu_visual.program;

import org.example.cpu_visual.controller.program.IObserver;
import org.example.cpu_visual.cpu.BCPU;
import org.example.cpu_visual.cpu.ICPU;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.*;

// новая реализация без executer
public class Program implements Iterable<Command> {
	ArrayList<Command> coms = new ArrayList<>();
	ArrayList<IObserver> allObserver = new ArrayList<>();

	ICPU cpu = BCPU.build();

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
	Iterator<Command> iter;
	private int currComExec = -1;

	public int getCurrComInd() {
		return currComExec;
	}

	public void reset() {
		newRegs = true;
		currComExec = -1;
		cpu.reset();
		eventCall();
	}

	// =========================================
	// Команды визуальной части
	boolean newRegs = true;
	public boolean isNewRegs() { return newRegs; }
	public void setNewRegs(boolean newRegs) {
		this.newRegs = newRegs;
	}

	public int[] getMemStatus() {
		return cpu.getMem();
	}

	public int[] getRegsStatus() {
		return cpu.getRegs();
	}

	public void runNext() throws Exception {
		// начало исполнения команд
		if (currComExec == -1) {
			iter = this.iterator();
		}
		if (iter.hasNext()) {
			currComExec++;

			Command nextCom = iter.next();
			System.out.println(nextCom);
			cpu.run(nextCom);

			eventCall();
		}
	}

	public void addCom(Command com) {
		coms.add(com);
		this.reset();
		eventCall();
	}

	public void comRemove(int ind) {
		coms.remove(ind);
		this.reset();
		eventCall();
	}

	public void comMoveUp(int ind) {
		if (ind == 0) return;

		Command tmp = coms.get(ind - 1);
		coms.set(ind - 1, coms.get(ind));
		coms.set(ind, tmp);

		this.reset();
		eventCall();
	}

	public void comMoveDown(int ind) {
		if (ind == coms.size() - 1) return;

		Command tmp = coms.get(ind + 1);
		coms.set(ind + 1, coms.get(ind));
		coms.set(ind, tmp);

		this.reset();
		eventCall();
	}

	public Map<Task, Long> getSortedComs() {
		return coms.stream()
				.collect(Collectors.groupingBy(Command::getTask, Collectors.counting()))
				.entrySet()
				.stream()
				.sorted((b, a)->a.getValue().compareTo(b.getValue()))
				.collect(Collectors.toMap(
						Map.Entry::getKey,
						Map.Entry::getValue,
						(e1, e2) -> e1,
						LinkedHashMap::new
				));
	}

	// =========================================
	// Отображение статистики в cmd

	public String mostPopularInstruction() {
		Map.Entry<Task, Long> mapComs = coms
				.stream()
				.collect(Collectors.groupingBy(Command::getTask, Collectors.counting()))
				.entrySet()
				.stream()
				.max(Map.Entry.comparingByValue())
				.orElse(null);

		if (mapComs == null) {
			return "No popular operation";
		}
		return "Most popular operation: " + mapComs.getKey() + " -> " + mapComs.getValue();
	}

	public void showSortedComs() {
		System.out.println("Sorted ist of commands:");

		coms.stream()
				.collect(Collectors.groupingBy(Command::getTask, Collectors.counting()))
				.entrySet()
				.stream()
				.sorted((b, a)->a.getValue().compareTo(b.getValue()))
				.forEach(com -> System.out.println(com.getKey() + " -> " + com.getValue()));
	}

	public void showMemoryRange() {
		int mi = Integer.MAX_VALUE, ma = Integer.MIN_VALUE;
		for (Command com: coms) {
			switch (com.getTask()) {
				case init:
					mi = Math.min(mi, com.getVal1());
					ma = Math.max(ma, com.getVal1());
					break;
				case st:
					mi = Math.min(mi, com.getVal2());
					ma = Math.max(ma, com.getVal2());
					break;
			}
		}
		System.out.println("Memory range: " + mi + " --> " + ma);
	}

	// =========================================

	@Override
	public Iterator<Command> iterator() {
		return coms.iterator();
	}
}
