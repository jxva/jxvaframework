package demo.cache;

import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ConcurrentLinkedList {
	
	private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);
	private final Lock readLock = readWriteLock.readLock();
	private final Lock writeLock = readWriteLock.writeLock();

	public final LinkedList<Object> keyLRUList = new LinkedList<Object>();

	public void add(Object o) {
		try {
			writeLock.lock();
			keyLRUList.add(o);
		} finally {
			writeLock.unlock();
		}
	}

	public void addFirst(Object key) {
		try {
			writeLock.lock();
			keyLRUList.addFirst(key);
		} finally {
			writeLock.unlock();
		}
	}

	public void moveFirst(Object key) {
		try {
			writeLock.lock();
			keyLRUList.remove(key);
			keyLRUList.addFirst(key);
		} catch (Exception e) {
		} finally {
			writeLock.unlock();
		}
	}

	public Object getLast() {
		try {
			readLock.lock();
			return keyLRUList.getLast();
		} finally {
			readLock.unlock();
		}
	}

	public int size() {
		try {
			readLock.lock();
			return keyLRUList.size();
		} finally {
			readLock.unlock();
		}
	}

	public void remove(Object key) {
		try {
			writeLock.lock();
			keyLRUList.remove(key);
		} finally {
			writeLock.unlock();
		}
	}

	public void clear() {
		try {
			writeLock.lock();
			keyLRUList.clear();
		} finally {
			writeLock.unlock();
		}
	}

}
