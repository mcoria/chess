package moveexecutors;

import java.util.Map;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Map.Entry;

import chess.BoardState;
import chess.DummyBoard;
import chess.Pieza;
import chess.Square;

public class EnroqueNegroReynaMove extends AbstractMove {

	public static final Map.Entry<Square, Pieza> FROM = new SimpleImmutableEntry<Square, Pieza>(Square.e8, Pieza.REY_NEGRO);
	
	public static final Map.Entry<Square, Pieza> TO = new SimpleImmutableEntry<Square, Pieza>(Square.c8, null);
	
	public EnroqueNegroReynaMove() {
		super(FROM, TO);
	}

	@Override
	public void execute(DummyBoard board) {
		this.execute(board, from, to);
		BoardState boardState = board.getBoardState();	
		boardState.setEnroqueNegroReyPermitido(false);
		boardState.setEnroqueNegroReinaPermitido(false);
		boardState.setPeonPasanteSquare(null);		
		boardState.rollTurno();		
	}

	@Override
	public void undo(DummyBoard board) {
		this.undo(board, from, to);
		BoardState boardState = board.getBoardState();		
		boardState.restoreState();		
	}

	@Override
	protected String getType() {
		return "EnroqueNegroReynaMove";
	}
	
	public void execute(DummyBoard board, Map.Entry<Square, Pieza> from, Map.Entry<Square, Pieza> to) {
		board.setEmptySquare(Square.e8);
		board.setEmptySquare(Square.a8);
		board.setPieza(Square.c8, Pieza.REY_NEGRO);
		board.setPieza(Square.d8, Pieza.TORRE_NEGRO);
	}

	public void undo(DummyBoard board, Entry<Square, Pieza> from, Entry<Square, Pieza> to) {
		board.setEmptySquare(Square.c8);
		board.setEmptySquare(Square.d8);
		board.setPieza(Square.e8, Pieza.REY_NEGRO);
		board.setPieza(Square.a8, Pieza.TORRE_NEGRO);		
	}

}
