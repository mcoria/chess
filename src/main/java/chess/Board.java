package chess;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Board {
	private DummyBoard tablero;
	
	private Color turnoActual;
	
	private Deque<Move> stackMoves = new ArrayDeque<Move>();
	
	private Set<Move> movimientosPosibles;
	
	private GameStatus status;
	
	private BoardMediator mediator;
	
	public Board(DummyBoard tablero, Color turno){
		this.tablero = tablero;
		this.turnoActual = turno;
		this.mediator = createMediator();
		updateGameStatus();
	}

	public GameStatus executeMove(Square from, Square to) {
		if(GameStatus.IN_PROGRESS.equals(this.status)){
			Move move = getMovimiento(from, to);
			if(move != null) {
				executeMove(move);
			} else {
				throw new RuntimeException("Invalid move: " + from.toString() + " " + to.toString());
			}
		} else {
			throw new RuntimeException("Invalid game state");
		}
		return this.status;
	}
	

	protected GameStatus executeMove(Move move) {
		assert(movimientosPosibles.contains(move));
		move.execute(mediator);
		stackMoves.push(move);
		turnoActual = turnoActual.opositeColor();
		updateGameStatus();
		return this.status;
	}

	protected void updateGameStatus() {
		movimientosPosibles = getMoves(turnoActual);
		if(movimientosPosibles.isEmpty()){
			if( tablero.isKingInCheck(turnoActual) ){
				this.status = GameStatus.JAQUE_MATE;
			} else {
				this.status = GameStatus.TABLAS;
			}
		} else {
			this.status = GameStatus.IN_PROGRESS;
		}
	}

	protected Set<Move> getMoves(Color color){
		Set<Move> moves = new HashSet<Move>();
		for (Map.Entry<Square, Pieza> origen : tablero) {
			Pieza currentPieza = origen.getValue();
			if(currentPieza != null){
				if(color.equals(currentPieza.getColor())){
					moves.addAll(currentPieza.getLegalMoves(this, origen));
				}
			}
		}
		return moves;
	}
	
	private Move getMovimiento(Square from, Square to) {
		Move moveResult = null;
		for (Move move : movimientosPosibles) {
			if(from.equals(move.getFrom()) && to.equals(move.getTo())){
				moveResult = move;
			}
		}
		return moveResult;
	}	
	
	private BoardMediator createMediator() {
		return new BoardMediator(){

			@Override
			public Pieza getPieza(Square from) {
				return tablero.getPieza(from);
			}

			@Override
			public void setEmptySquare(Square from) {
				tablero.setEmptySquare(from);
			}

			@Override
			public void setPieza(Square to, Pieza pieza) {
				tablero.setPieza(to, pieza);
			}
			
		};
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(tablero.toString());
		buffer.append("Turno: " + this.turnoActual + "\n");
		return buffer.toString();
	}

	public final DummyBoard getTablero() {
		return tablero;
	}

	public final Set<Move> getMovimientosPosibles() {
		return movimientosPosibles;
	}

	public final Color getTurnoActual() {
		return turnoActual;
	}

	public final GameStatus getGameStatus() {
		return this.status;
	}

}
