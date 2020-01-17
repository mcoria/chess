package moveexecutor;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import chess.BoardState;
import chess.DummyBoard;
import chess.Move;
import chess.Pieza;
import chess.Square;
import moveexecutors.CaptureMoveExecutor;

public class CaptureMoveExecutorTest {
	
	@Mock
	private DummyBoard board;
	
	@Mock
	private Move move;	
	
	@Mock
	private BoardState boardState;
	
	private CaptureMoveExecutor moveExecutor;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		moveExecutor = new CaptureMoveExecutor();
	}

	
	@Test
	public void testExecute() {
		Map.Entry<Square, Pieza> origen = new SimpleImmutableEntry<Square, Pieza>(Square.e5, Pieza.TORRE_BLANCO);
		Map.Entry<Square, Pieza> destino = new SimpleImmutableEntry<Square, Pieza>(Square.e7, Pieza.PEON_NEGRO);
		
		when(move.getFrom()).thenReturn(origen);
		when(move.getTo()).thenReturn(destino);		

		moveExecutor.execute(board, move, boardState);
		
		verify(board).setPieza(Square.e7, Pieza.TORRE_BLANCO);
		verify(board).setEmptySquare(Square.e5);
		
		verify(boardState).setCaptura(destino);
		verify(boardState).setPeonPasanteSquare(null);
		
	}
	
	
	@Test
	public void testUndo() {
		Map.Entry<Square, Pieza> origen = new SimpleImmutableEntry<Square, Pieza>(Square.e5, Pieza.TORRE_BLANCO);
		Map.Entry<Square, Pieza> destino = new SimpleImmutableEntry<Square, Pieza>(Square.e7, Pieza.PEON_NEGRO);
		
		when(move.getFrom()).thenReturn(origen);
		when(boardState.getCaptura()).thenReturn(destino);		

		moveExecutor.undo(board, move, boardState);
		
		verify(board).setPieza(origen);
		verify(board).setPieza(destino);
	}	

}
