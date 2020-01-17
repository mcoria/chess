package moveexecutors;

import java.util.Objects;

import chess.BoardState;
import chess.DummyBoard;
import chess.Move;
import chess.Pieza;

public class CaptureMoveExecutor implements MoveExecutor {

	private Pieza origen;
	private Pieza capturada;

	public CaptureMoveExecutor(Pieza origen, Pieza capturada) {
		this.origen = origen;
		this.capturada = capturada;
	}
	
	@Override
	public void execute(DummyBoard board, Move move, BoardState boardState) {
		board.setPieza(move.getTo().getKey(), origen);
		board.setEmptySquare(move.getFrom().getKey());
	}

	@Override
	public void undo(DummyBoard board, Move move, BoardState boardState) {
		board.setPieza(move.getFrom().getKey(), origen);
		board.setPieza(move.getTo().getKey(), capturada);
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof CaptureMoveExecutor){
			CaptureMoveExecutor theOther = (CaptureMoveExecutor) obj;
			return Objects.equals(origen, theOther.origen) && Objects.equals(capturada, theOther.capturada);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return origen.hashCode();
	}	
	
	
	@Override
	public String toString() {
		return "Captura: " + origen.toString() + " - " + capturada.toString(); 
	}	
}
