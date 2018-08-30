package scripts.com.mercosur.slayer.util;

public class InvalidFileNameException extends InvalidNameException {

	public InvalidFileNameException(String name) {
		super(name + " is not a valid file name.");
	}

}
