package org.example.cpu_visual.DAO;

import org.example.cpu_visual.HibernateSessionFactoryUtil;
import org.example.cpu_visual.program.Command;
import org.example.cpu_visual.program.Task;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class DAO implements Iterable<Command> {
	ArrayList<Command> coms = new ArrayList<>();

	public DAO() {
		updateList();
	}

	// Методы для сбора статистики по командам
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

	public void showSortedComs() {
		System.out.println("Sorted ist of commands:");

		coms.stream()
				.collect(Collectors.groupingBy(Command::getTask, Collectors.counting()))
				.entrySet()
				.stream()
				.sorted((b, a)->a.getValue().compareTo(b.getValue()))
				.forEach(com -> System.out.println(com.getKey() + " -> " + com.getValue()));
	}

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


	// методы для работы с командами
	public void addCom(Command com) {
		Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
		Transaction tx1 = session.beginTransaction();
		session.persist(com);
		tx1.commit();
		session.close();

		updateList();
	}

	public void comRemove(Command com) {
		Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
		Transaction tx1 = session.beginTransaction();
		session.delete(com);
		tx1.commit();
		session.close();

		updateList();
	}

	public void comMoveUp(Command com) {
		int ind = coms.indexOf(com);

		if (ind == 0) return;

		Command tmp_1 = coms.get(ind - 1);	// if=77
		Command tmp_2 = coms.get(ind);		// id=78
		int tmp_id = tmp_1.getID();			// id=77
		tmp_1.setID(tmp_2.getID());
		tmp_2.setID(tmp_id);

		Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
		Transaction tx1 = session.beginTransaction();
		session.merge(tmp_1);
		session.merge(tmp_2);
		tx1.commit();
		session.close();

		updateList();
	}

	public void comMoveDown(Command com) {
		int ind = coms.indexOf(com);

		if (ind == coms.size() - 1) return;

		Command tmp_1 = coms.get(ind + 1);	// if=79
		Command tmp_2 = coms.get(ind);		// id=78
		int tmp_id = tmp_1.getID();			// id=79
		tmp_1.setID(tmp_2.getID());
		tmp_2.setID(tmp_id);

		Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
		Transaction tx1 = session.beginTransaction();
		session.merge(tmp_1);
		session.merge(tmp_2);
		tx1.commit();
		session.close();

		updateList();
	}

	private void updateList(){
		coms.clear();
		List<Command> com = (List<Command>) HibernateSessionFactoryUtil.
				getSessionFactory().openSession().createQuery("From Command").list();
		coms.addAll(com);
	}

	@Override
	public Iterator<Command> iterator() {
		return coms.iterator();
	}
}
