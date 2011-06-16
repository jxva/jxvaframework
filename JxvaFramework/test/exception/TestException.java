package exception;
import com.jxva.exception.NestableRuntimeException;

public class TestException extends NestableRuntimeException {
	
	private static final long serialVersionUID = 6998187778572667684L;
	
	public TestException() {
		super();
	}
	
	public TestException(Throwable root) {
		super(root);
	}

	public TestException(String string, Throwable root) {
		super(string, root);
	}

	public TestException(String s) {
		super(s);
	}
}
