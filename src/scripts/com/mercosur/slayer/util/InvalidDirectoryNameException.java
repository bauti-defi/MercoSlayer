package scripts.com.mercosur.slayer.util;

public class InvalidDirectoryNameException extends InvalidNameException {

	public InvalidDirectoryNameException(String name) {
		super(name + " is not a valid directory name.");
	}

}