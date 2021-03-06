package moveexecutors;

import chess.BoardState;
import chess.Pieza;
import chess.PosicionPieza;
import chess.Square;

public class EnroqueBlancoReyMove extends EnroqueMove {

	public static final PosicionPieza FROM = new PosicionPieza(Square.e1, Pieza.REY_BLANCO);
	public static final PosicionPieza TO = new PosicionPieza(Square.g1, null);
	
	public static final PosicionPieza TORRE_FROM = new PosicionPieza(Square.h1, Pieza.TORRE_BLANCO);
	public static final PosicionPieza TORRE_TO = new PosicionPieza(Square.f1, null);
	
	private static final SimpleReyMove REY_MOVE = new SimpleReyMove(FROM, TO);
	private static final SimpleMove TORRE_MOVE = new SimpleMove(TORRE_FROM, TORRE_TO);
	
	public EnroqueBlancoReyMove() {
		super(FROM, TO);
	}
	
	@Override
	public void executeMove(BoardState boardState) {
		super.executeMove(boardState);
		boardState.setEnroqueBlancoReyPermitido(false);
		boardState.setEnroqueBlancoReinaPermitido(false);
	}

	@Override
	protected SimpleReyMove getReyMove() {
		return REY_MOVE;
	}

	@Override
	protected SimpleMove getTorreMove() {
		return TORRE_MOVE;
	}
	
	protected String getType() {
		return "EnroqueBlancoReyMove";
	}

}
