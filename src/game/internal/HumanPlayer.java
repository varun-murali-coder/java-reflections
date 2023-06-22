package game.internal;

class HumanPlayer implements Player {

	private static final String NAME="You";
	private final KeyboardInputProvider inputProvider;
	
	
	HumanPlayer(KeyboardInputProvider inputProvider) {
		this.inputProvider = inputProvider;
	}

	@Override
	public void play(Board board, Sign sign) {
BoardLocation nextMoveLocation=inputProvider.provideNextMove(board);
board.updateCell(nextMoveLocation.getRow(),nextMoveLocation.getColumn(),sign);

		
	}

	@Override
	public String getPlayerName() {
		// TODO Auto-generated method stub
		return NAME;
	}

}
