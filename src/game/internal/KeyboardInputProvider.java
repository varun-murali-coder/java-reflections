package game.internal;

import java.util.Scanner;

class KeyboardInputProvider implements InputProvider {
	private final Scanner sc=new Scanner(System.in);
	private final BoardDimensions dimensions;
	
	

	KeyboardInputProvider(BoardDimensions dimensions) {
		this.dimensions = dimensions;
	}



	@Override
	public BoardLocation provideNextMove(Board board) {
int row;
int column;
do {
	System.out.print("Please choose row:-");
	row=sc.nextInt();
	System.out.print("Please choose column:-");
	column=sc.nextInt();
}while(row<0||row>=dimensions.getNumberOfRows()||column<0||column>=dimensions.getNumberOfColums()||
		!board.isCellEmpty(row,column));
return new BoardLocation(row,column);
	}

}
