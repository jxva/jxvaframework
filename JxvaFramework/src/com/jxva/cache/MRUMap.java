package com.jxva.cache;

import java.util.HashMap;
import java.util.LinkedList;

public final class MRUMap<K, V> extends HashMap<K, V> {

	private static final long serialVersionUID = 1L;
	private int maxSize = 100;
	private LinkedList<Object> list;

	public MRUMap(int maxobjects) {
		super((int) (maxobjects * 1.2));
		this.list = new LinkedList<Object>();
		this.maxSize = maxobjects;
	}


	public V get(Object key) {
		V tmpobject = super.get(key);
		if (tmpobject != null) {
			list.remove(key);
			list.addFirst(key);
			return tmpobject;
		}
		return null;
	}

	public V put(K key, V value) {
		while (list.size() >= maxSize) {
			if (super.size() > 0) {
				super.remove(list.getLast());
				list.removeLast();
			}
		}
		V obj = super.put(key, value);
		list.remove(key);
		list.addFirst(key);
		return obj;
	}

	public V remove(Object key) {
		list.remove(key);
		return super.remove(key);
	}
}
