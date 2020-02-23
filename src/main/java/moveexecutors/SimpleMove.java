package moveexecutors;

import java.util.Map.Entry;

import chess.BoardState;
import chess.DummyBoard;
import chess.Pieza;
import chess.Square;

public class SimpleMove extends AbstractMove {
	
	public SimpleMove(Entry<Square, Pieza> from, Entry<Square, Pieza> to) {
		super(from, to);
	}
	
	@Override
	public void executeMove(DummyBoard board) {
		board.setEmptySquare(from.getKey());					//Dejamos origen
		board.setPieza(to.getKey(), from.getValue()) ;			//Vamos a destino
	}

	@Override
	public void undoMove(DummyBoard board) {
		board.setPosicion(to);							//Reestablecemos destino
		board.setPosicion(from);						//Volvemos a origen	
	}
	
	@Override
	public void executeState(BoardState boardState) {
		boardState.pushState();
		boardState.setPeonPasanteSquare(null);
		boardState.rollTurno();
	}
	
	@Override
	protected String getType() {
		return "SimpleMove";
	}	
}
