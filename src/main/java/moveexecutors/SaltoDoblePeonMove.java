package moveexecutors;

import java.util.Map;
import java.util.Map.Entry;

import chess.BoardState;
import chess.DummyBoard;
import chess.Pieza;
import chess.Square;

public class SaltoDoblePeonMove extends AbstractMove {
	
	private final Square peonPasanteSquare;

	public SaltoDoblePeonMove(Entry<Square, Pieza> from, Entry<Square, Pieza> to, Square peonPasanteSquare) {
		super(from, to);
		this.peonPasanteSquare = peonPasanteSquare;
	}

	@Override
	public void execute(DummyBoard board) {
		this.execute(board, from, to);
		BoardState boardState = board.getBoardState();
		boardState.setPeonPasanteSquare(peonPasanteSquare);
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
		return "SaltoDoblePeonMove";
	}
	
	public void execute(DummyBoard board, Map.Entry<Square, Pieza> from, Map.Entry<Square, Pieza> to) {
		board.setEmptySquare(from.getKey());								//Dejamos origen
		board.setPieza(to.getKey(), from.getValue());						//Vamos a destino
	}

	public void undo(DummyBoard board, Entry<Square, Pieza> from, Entry<Square, Pieza> to) {
		board.setPosicion(to);							//Reestablecemos destino
		board.setPosicion(from);						//Volvemos a origen		
	}

}
