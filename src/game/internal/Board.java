package game.internal;

class Board {
	
	private Cell[][] cells;
	private BoardDimensions dimensions;
	
	

	public Board(BoardDimensions dimensions) {
		this.dimensions = dimensions;
		this.cells=new Cell[dimensions.getNumberOfColums()][dimensions.getNumberOfRows()];
		initAllCells();
	}

	private void initAllCells() {
for(int r=0;r<dimensions.getNumberOfRows();r++) {
	for(int c=0;c<dimensions.getNumberOfColums();c++) {
	this.cells[c][r]=new Cell();
	}

}		
	}

	public char getPrintableCellSign(int r, int c) {
		return this.cells[c][r].getSign().getValue();
	}

	public boolean isCellEmpty(int row, int column) {
		// TODO Auto-generated method stub
		return this.cells[column][row].isEmpty();
	}

	public void updateCell(int row, int column, Sign sign) {

		this.cells[column][row].setSign(sign);
	}

	public Sign checkWinner() {
		//Check rows
for(int r=0;r<dimensions.getNumberOfRows();r++) {
	Sign sign=getRowWinner(r);
	if(sign!=Sign.EMPTY) {
		return sign;
	}
}
//Check columns

for(int c=0;c<dimensions.getNumberOfColums();c++) {
	Sign sign=getColumnWinner(c);
	if(sign!=Sign.EMPTY) {
		return sign;
	}
}
//Check diagonals
Sign sign=getDiagonalWinner(0,0,1,1);
if(sign!=Sign.EMPTY) {
	return sign;
}

// Check diagonal
return getDiagonalWinner(0, dimensions.getNumberOfColums() - 1, -1, 1);
	}

	private Sign getDiagonalWinner(int startRow, int startColumn, int horizontalStep, int verticalStep) {
		Sign initialSign=this.cells[startColumn][startRow].getSign();
		if(initialSign==Sign.EMPTY) {
			return initialSign;
		}
		int r=startRow+verticalStep;
		int c=startColumn+horizontalStep;
		while(r<dimensions.getNumberOfRows()&&c<dimensions.getNumberOfColums()) {
			if(this.cells[c][r].getSign()!=initialSign) {
				return Sign.EMPTY;
			}
			r+=verticalStep;
			c+=horizontalStep;
		}
				return initialSign;
	}

	private Sign getColumnWinner(int currentColumn) {
		Sign initialSign=this.cells[currentColumn][0].getSign();
		if(initialSign==Sign.EMPTY) {
			return initialSign;
		}
		for(int r=1;r<dimensions.getNumberOfRows();r++) {
			if(this.cells[currentColumn][r].getSign()!=initialSign) {
				return Sign.EMPTY;
			}
		}
				return initialSign;
	}

	private Sign getRowWinner(int currentRow) {
Sign initialSign=this.cells[0][currentRow].getSign();
if(initialSign==Sign.EMPTY) {
	return initialSign;
}
for(int c=1;c<dimensions.getNumberOfColums();c++) {
	if(this.cells[c][currentRow].getSign()!=initialSign) {
		return Sign.EMPTY;
	}
}
		return initialSign;
	}

	public boolean isBoardFull() {
		for(int r=0;r<dimensions.getNumberOfRows();r++) {
			for(int c=0;c<dimensions.getNumberOfColums();c++) {
				if(this.cells[c][r].isEmpty()) {
				return false;
				}
			}
			}
		return true;

	}

}
