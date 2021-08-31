package xyz.vppiet.hellu;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class Subject {

	protected final Set<Observer> observers;

	public Subject() {
		this.observers = Collections.synchronizedSet(new HashSet<>());
	}

	protected boolean hasSubscriber(Observer obs) {
		return this.observers.contains(obs);
	}

	protected void addSubscriber(Observer obs) {
		this.observers.add(obs);
	}

	protected void removeSubscriber(Observer obs) {
		this.observers.remove(obs);
	}

	protected void notifyObservers(Subject sub, Object obj) {
		synchronized (this.observers) {
			for (Observer obs : this.observers) {
				obs.onNext(sub, obj);
			}
		}
	}
}
