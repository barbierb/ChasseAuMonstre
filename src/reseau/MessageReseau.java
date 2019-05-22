package reseau;

public enum MessageReseau {
	
	READY("ready");
	
	private final String message;
	
	private MessageReseau(String data) {
		message = data;
	}
	
	@Override
	public String toString() {
		return message;
	}
}
