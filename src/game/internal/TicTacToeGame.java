package game.internal;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import game.Game;

public class TicTacToeGame implements Game {
	
	private static final int NUMBER_OF_PLAYERS=2;
	private final Random random=new Random();
	private final List<Sign> playerSigns=Arrays.asList(Sign.X,Sign.Y);
    private final BoardPrinter printer;
    private final Board board;
    private final List<Player> players;
	@Override
	public void startGame() {
		Sign winner;
		int nextPlayerIndex=random.nextInt(NUMBER_OF_PLAYERS);
		Player player;
		printer.print(board);
		do {
		nextPlayerIndex=NUMBER_OF_PLAYERS-nextPlayerIndex-1;
		player=players.get(nextPlayerIndex);
		Sign playerSign=playerSigns.get(nextPlayerIndex);
		player.play(board, playerSign);
		winner=board.checkWinner();
		printer.print(board);
		}while(!board.isBoardFull() && winner==Sign.EMPTY);
		if(winner!=Sign.EMPTY) {
			System.out.println(String.format("Winner is :%s", player.getPlayerName()));
		}else {
			System.out.println("Game over | Nobody won.");
		}

	}
	TicTacToeGame(BoardPrinter printer,Board board,HumanPlayer humanPlayer,ComputerPlayer computerPlayer) {
		this.printer = printer;
		this.board=board;
		this.players=Arrays.asList(humanPlayer,computerPlayer);
		
	}
	

}
