package chess;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;

import gui.ASCIIOutput;
import iterators.BoardIterator;
import iterators.DummyBoardIterator;
import iterators.SquareIterator;
import movegenerators.MoveFilter;
import movegenerators.MoveGenerator;
import movegenerators.MoveGeneratorStrategy;
import movegenerators.PeonAbstractMoveGenerator;
import movegenerators.ReyAbstractMoveGenerator;

public class Board implements DummyBoard {
	
	private MoveFilter defaultFilter = (Move move) -> filterMove(move);
	
	private MoveGeneratorStrategy strategy = null; 
	
	private BoardState boardState = null;
	
	private BoardCache boardCache = null;

	public Board(Pieza[][] tablero, BoardState boardState) {
		crearTablero(tablero);
		this.boardState = boardState;
		this.boardCache = new BoardCache(this);
		this.strategy = new MoveGeneratorStrategy(this);
	}
	

	///////////////////////////// START positioning logic /////////////////////////////
	// Quizas podria encapsular estas operaciones en su propia clase.
	// Bitboard podria ser mas rapido? Un word por tipo de ficha
	// Las primitivas de tablero son muy basicas!? En vez de descomponer una movimiento en operaciones simples, proporcionar un solo metodo
	//
	private PosicionPieza[] tablero = new PosicionPieza[64];
	private final CachePosiciones cachePosiciones = new CachePosiciones();
	
	/* (non-Javadoc)
	 * @see chess.DummyBoard#getPosicion(chess.Square)
	 */
	@Override
	public PosicionPieza getPosicion(Square square) {
		return tablero[square.toIdx()];
	}

	/* (non-Javadoc)
	 * @see chess.DummyBoard#setPosicion(chess.PosicionPieza)
	 */
	@Override
	public void setPosicion(PosicionPieza entry) {
		Square square = entry.getKey();
		tablero[square.toIdx()] = entry;
	}

	/* (non-Javadoc)
	 * @see chess.DummyBoard#getPieza(chess.Square)
	 */
	@Override
	public Pieza getPieza(Square square) {
		return tablero[square.toIdx()].getValue();
	}

	/* (non-Javadoc)
	 * @see chess.DummyBoard#setPieza(chess.Square, chess.Pieza)
	 */
	@Override
	public void setPieza(Square square, Pieza pieza) {
		tablero[square.toIdx()] =  cachePosiciones.getPosicion(square, pieza);
	}

	/* (non-Javadoc)
	 * @see chess.DummyBoard#setEmptySquare(chess.Square)
	 */
	@Override
	public void setEmptySquare(Square square) {
		tablero[square.toIdx()] =  cachePosiciones.getPosicion(square, null);
	}

	/* (non-Javadoc)
	 * @see chess.DummyBoard#isEmtpy(chess.Square)
	 */
	@Override
	public boolean isEmtpy(Square square) {
		return getPieza(square) == null;
	}
	///////////////////////////// END positioning logic /////////////////////////////
	
	public Collection<Move> getLegalMoves(){
		Collection<Move> moves = createMoveContainer();
		Color turnoActual = boardState.getTurnoActual();
		for (SquareIterator iterator = boardCache.iteratorSquare(turnoActual); iterator.hasNext();) {
			PosicionPieza origen = this.getPosicion(iterator.next());
			Pieza currentPieza = origen.getValue();
			MoveGenerator moveGenerator = strategy.getMoveGenerator(currentPieza);
			moveGenerator.generateMoves(origen, moves);
		}
		return moves;
	}

	public boolean isKingInCheck() {
		Color turno = boardState.getTurnoActual();
		Square kingSquare = boardCache.getKingSquare(turno);
		return positionCaptured(turno.opositeColor(), kingSquare);
	}
	
	
	// Habria que preguntar si aquellos para los cuales su situacion cambi� pueden ahora pueden capturar al rey. 
	/* (non-Javadoc)
	 * @see chess.PositionCaptured#sepuedeCapturarReyEnSquare(chess.Color, chess.Square)
	 */
	protected boolean positionCaptured(Color color, Square square){
		for (SquareIterator iterator = boardCache.iteratorSquare(color); iterator.hasNext();) {
			PosicionPieza origen = this.getPosicion(iterator.next());
			Pieza currentPieza = origen.getValue();
			if(currentPieza != null){
				if(color.equals(currentPieza.getColor())){
					MoveGenerator moveGenerator = this.strategy.getMoveGenerator(currentPieza);
					if(moveGenerator.puedeCapturarRey(origen, square)){
						return true;
					}
				}
			}
		}
		return false;		
	}
	
	/*
	 * NO HACE FALA UTILIZAR ESTE FILTRO CUANDO ES MOVIMEINTO DE REY
	 */
	private boolean filterMove(Move move) {
		boolean result = false;
				
		move.executeMove(this);
		
		/*
		if(move instanceof MoveKing){
			((MoveKing) move).executetSquareKingCache(this.boardCache);
		}*/
		
		// Habria que preguntar si aquellos para los cuales su situacion cambi� pueden ahora pueden capturar al rey. 
		if(! this.isKingInCheck() ) {
			result = true;
		}
		
		move.undoMove(this);
		
		/*
		if(move instanceof MoveKing){
			((MoveKing) move).undoSquareKingCache(this.boardCache);
		}*/
		
		return result;
	}
	
	/*
	 * NO HACE FALA UTILIZAR ESTE FILTRO CUANDO ES MOVIMEINTO DE REY
	 */
	private boolean filterMoveKing(MoveKing move) {
		boolean result = false;
				
		move.executeMove(this);
		
		move.executetSquareKingCache(this.boardCache);
		
		// Habria que preguntar si aquellos para los cuales su situacion cambi� pueden ahora pueden capturar al rey. 
		if(! this.isKingInCheck() ) {
			result = true;
		}
		
		move.undoMove(this);
		
		move.undoSquareKingCache(this.boardCache);		
		
		return result;
	}

	///////////////////////////// START Board Iteration Logic /////////////////////////////
	/* (non-Javadoc)
	 * @see chess.DummyBoard#iterator()
	 */
	@Override
	public BoardIterator iterator() {
		return new DummyBoardIterator(this);
	}

	/* (non-Javadoc)
	 * @see chess.DummyBoard#iterator(iterators.SquareIterator)
	 */
	@Override
	public BoardIterator iterator(SquareIterator squareIterator){
		return new BoardIterator(){
			@Override
			public boolean hasNext() {
				return squareIterator.hasNext();
			}
			
			@Override
			public PosicionPieza next() {
				Square currentSquare = squareIterator.next();
				return getPosicion(currentSquare);
			}
		};
	}
	///////////////////////////// END Board Iteration Logic /////////////////////////////	

	///////////////////////////// START Move execution Logic /////////////////////////////		
	public void execute(Move move) {
		move.executeMove(this);

		move.executeMove(boardCache);
		
		//boardCache.validarCacheSqueare(this);

		if(move instanceof MoveKing){
			((MoveKing) move).executetSquareKingCache(this.boardCache);
		}
		
		move.executeMove(boardState);
		
		//assert validarSquares(squareBlancos, Color.BLANCO) && validarSquares(squareNegros, Color.NEGRO);
	}


	public void undo(Move move) {
		move.undoMove(this);

		move.undoMove(boardCache);	
		
		//boardCache.validarCacheSqueare(this);
		
		if(move instanceof MoveKing){
			((MoveKing) move).undoSquareKingCache(this.boardCache);
		}
		
		move.undoMove(boardState);
		
		//assert validarSquares(squareBlancos, Color.BLANCO) && validarSquares(squareNegros, Color.NEGRO);
	}
	///////////////////////////// END Move execution Logic /////////////////////////////
	
	
	public BoardState getBoardState() {
		return boardState;
	}
	
	public MoveFilter getDefaultFilter(){
		return defaultFilter;
	}
	
	public void settupMoveGenerator(MoveGenerator moveGenerator){
		moveGenerator.setTablero(this);
		moveGenerator.setFilter(defaultFilter);
		
		if(moveGenerator instanceof PeonAbstractMoveGenerator){
			PeonAbstractMoveGenerator generator = (PeonAbstractMoveGenerator) moveGenerator;
			generator.setBoardState(boardState);
		}
		
		if(moveGenerator instanceof ReyAbstractMoveGenerator){
			ReyAbstractMoveGenerator generator = (ReyAbstractMoveGenerator) moveGenerator;
			generator.setBoardState(boardState);
			generator.setPositionCaptured((Color color, Square square) -> positionCaptured(color, square));
			generator.setFilter((Move move) -> filterMoveKing((MoveKing) move));
		}		
	}
	
	
	@Override
	public String toString() {
	    final ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    try (PrintStream ps = new PrintStream(baos)) {
	    	ASCIIOutput output = new ASCIIOutput(ps);
	    	output.printDummyBoard(this);
	    	ps.flush();
	    }
	    return new String(baos.toByteArray());
	}
	
	private static Collection<Move> createMoveContainer(){
		return new ArrayList<Move>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 2237718042714336104L;

			@Override
			public String toString() {
				StringBuffer buffer = new StringBuffer(); 
				for (Move move : this) {
					buffer.append(move.toString() + "\n");
				}
				return buffer.toString();
			}
		};
	}	
	
	private void crearTablero(Pieza[][] sourceTablero) {
		for (int file = 0; file < 8; file++) {
			for (int rank = 0; rank < 8; rank++) {
				PosicionPieza posicion = cachePosiciones.getPosicion(Square.getSquare(file, rank),
						sourceTablero[file][rank]);
				tablero[Square.getSquare(file, rank).toIdx()] = posicion;
			}
		}
	}

	public BoardCache getBoardCache() {
		return boardCache;
	}

	public void setBoardCache(BoardCache boardCache) {
		this.boardCache = boardCache;
	}	

}
