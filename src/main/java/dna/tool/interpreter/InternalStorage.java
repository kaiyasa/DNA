package dna.tool.interpreter;

public class InternalStorage<R> implements Storage<R> {
	private R data;

	public InternalStorage(R data) {
		super();
		this.data = data;
	}

	@Override
	public void accept(R item) {
		data = item;

	}

	@Override
	public R get() {
		return data;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> Storage<T> typeOf(Class<T> typeClass) {
		if (data != null)
			if (data.getClass() != typeClass)
				throw new IllegalStateException("incompatiable types requested on storage");

		return (Storage<T>) this;
	}
}