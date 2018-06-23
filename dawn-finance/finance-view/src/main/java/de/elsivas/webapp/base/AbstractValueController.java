package de.elsivas.webapp.base;

public abstract class AbstractValueController<T> extends AbstractController {

	private T value;

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

}
