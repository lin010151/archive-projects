/**
 * 
 */
package edu.gdei.jobmessage.util;

/**
 * @author dragonzhu
 *
 */
public class PushMessageExistsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PushMessageExistsException() {
        super();
    }

    public PushMessageExistsException(String message) {
        super(message);
    }

    public PushMessageExistsException(Throwable cause) {
        super(cause);
    }

    public PushMessageExistsException(String message, Throwable cause) {
        super(message, cause);
    }

}
