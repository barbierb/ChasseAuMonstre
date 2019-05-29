package reseau;

public enum MessageReseau {

	ESTMONSTRE("monstre"),
	ESTCHASSEUR("chasseur"),
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
