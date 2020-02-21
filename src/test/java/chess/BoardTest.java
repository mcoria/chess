package chess;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import parsers.FENBoarBuilder;

public class BoardTest {
	
	private FENBoarBuilder builder;

	@Before
	public void setUp() throws Exception {
		builder = new FENBoarBuilder();
	}
	
	@Test
	public void testPosicionInicial() {
		Board board = builder.withDefaultBoard().buildBoard();
		
		assertEquals(Color.BLANCO, board.getTurnoActual());
		assertEquals(GameStatus.IN_PROGRESS, board.getGameStatus());
		assertEquals(20, board.getMovimientosPosibles().size());
	}

	@Test
	public void testJuegoJaqueMate() {
		Board board = builder.withDefaultBoard().buildBoard();
		assertEquals(20, board.getMovimientosPosibles().size());
		assertEquals(Color.BLANCO, board.getTurnoActual());
		
		board.executeMove(Square.e2, Square.e4);
		board.executeMove(Square.e7, Square.e5);
		board.executeMove(Square.f1, Square.c4);	
		board.executeMove(Square.b8, Square.c6);
		board.executeMove(Square.d1, Square.f3);
		board.executeMove(Square.f8, Square.c5);
		board.executeMove(Square.f3, Square.f7);
		
		assertEquals(Color.NEGRO, board.getTurnoActual());
		assertEquals(GameStatus.JAQUE_MATE, board.getGameStatus());
		assertTrue(board.getMovimientosPosibles().isEmpty());
	}

	
	@Test
	public void testJuegoJaque() {
		Board board = builder.withDefaultBoard().buildBoard();
		
		assertEquals(20, board.getMovimientosPosibles().size());
		assertEquals(Color.BLANCO, board.getTurnoActual());
		
		board.executeMove(Square.e2, Square.e4);
		board.executeMove(Square.e7, Square.e5);
		board.executeMove(Square.f1, Square.c4);
		board.executeMove(Square.b8, Square.c6);
		board.executeMove(Square.d1, Square.f3);
		board.executeMove(Square.g8, Square.h6);
		board.executeMove(Square.f3, Square.f7);
		
		assertEquals(Color.NEGRO, board.getTurnoActual());
		assertEquals(GameStatus.IN_PROGRESS, board.getGameStatus());
		assertEquals(1, board.getMovimientosPosibles().size());
	}
	
	@Test
	public void testJuegoTablas() {
		Board board = builder.withFEN("k7/7Q/K7/8/8/8/8/8 w KQkq - 0 1").buildBoard();
		
		assertEquals(Color.BLANCO, board.getTurnoActual());
		
		board.executeMove(Square.h7, Square.c7);

		assertEquals(Color.NEGRO, board.getTurnoActual());
		assertEquals(GameStatus.TABLAS, board.getGameStatus());
		assertEquals(0, board.getMovimientosPosibles().size());
	}
	
	@Test
	public void testJuegoUndo() {
		Board board = builder.withDefaultBoard().buildBoard();
		
		assertEquals(20, board.getMovimientosPosibles().size());
		assertEquals(Color.BLANCO, board.getTurnoActual());
		
		board.executeMove(Square.e2, Square.e4);
		
		board.undoMove();
		assertEquals(20, board.getMovimientosPosibles().size());
		assertEquals(Color.BLANCO, board.getTurnoActual());
	}
	
	@Test
	public void testJuegoNoPeonPasante() {
		Board board = builder.withFEN("rnbqkbnr/p1pppppp/1p6/P7/8/8/1PPPPPPP/RNBQKBNR b KQkq - 0 2").buildBoard();
		
		board.executeMove(Square.b6, Square.b5);
		
		assertEquals(22, board.getMovimientosPosibles().size());
	}
	
	@Test
	public void testJuegoPeonPasante() {
		Board board = builder.withFEN("rnbqkbnr/1ppppppp/8/pP6/8/8/P1PPPPPP/RNBQKBNR b KQkq - 0 2").buildBoard();
		
		board.executeMove(Square.c7, Square.c5);
		
		assertNotNull(board.getMovimiento(Square.b5, Square.c6));
		assertEquals(22, board.getMovimientosPosibles().size());
	}	
}
