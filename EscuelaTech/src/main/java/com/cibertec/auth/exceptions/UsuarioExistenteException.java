package com.cibertec.auth.exceptions;

public class UsuarioExistenteException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UsuarioExistenteException(String mensaje) {
        super(mensaje);
    }
}
