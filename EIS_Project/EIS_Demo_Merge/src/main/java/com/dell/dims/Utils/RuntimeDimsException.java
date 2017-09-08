
package com.dell.dims.Utils;

/**
 * Base class for all Dims unchecked exceptions.
 */
public class RuntimeDimsException extends RuntimeException {
    private static final long serialVersionUID = 8046489554418284257L;

    public RuntimeDimsException() {
    }

    public RuntimeDimsException(String message) {
        super(message);
    }

    public RuntimeDimsException(String message, Throwable cause) {
        super(message, cause);
    }

    public RuntimeDimsException(Throwable cause) {
        super(cause);
    }
}
