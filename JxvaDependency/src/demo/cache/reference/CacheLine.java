package demo.cache.reference;

import java.lang.ref.SoftReference;

public class CacheLine {
	private Object valueRef = null;

    private long loadTime = 0;

    private boolean useSoftReference = false;

    public CacheLine(Object value, boolean useSoftReference) {
    	this.useSoftReference = useSoftReference;
        if (this.useSoftReference)
            this.valueRef = new SoftReference<Object>(value);
         else 
            this.valueRef = value;
              
    }

    public CacheLine(Object value, boolean useSoftReference, long loadTime) {
        this(value, useSoftReference);
        this.loadTime = loadTime;
    }

    @SuppressWarnings("unchecked")
	public Object getValue() {
        if (valueRef == null)
            return null;
        if (useSoftReference) {
            return ((SoftReference<Object>) valueRef).get();
        } else {
            return valueRef;
        }
    }

	public long getLoadTime() {
		return loadTime;
	}

	public void setLoadTime(long loadTime) {
		this.loadTime = loadTime;
	}

	public boolean isUseSoftReference() {
		return useSoftReference;
	}

	public void setUseSoftReference(boolean useSoftReference) {
		this.useSoftReference = useSoftReference;
	}

}
