package game.internal;

enum Sign {
	
	X('X'),EMPTY(' '),Y('Y');
	

	private char value;
	
Sign(char c) {
		this.value=c;
	}
public char getValue() {
	
	return this.value;
}

}
