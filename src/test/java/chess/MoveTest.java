package chess;

import static org.junit.Assert.*;

import org.junit.Test;

import moveexecutors.CaptureMoveExecutor;
import moveexecutors.SimpleMoveExecutor;
import parsers.FENParser;

public class MoveTest {

	@Test
	public void testEquals01() {
		assertEquals(new Move(Square.e5, Square.e7, new SimpleMoveExecutor(Pieza.TORRE_BLANCO)), new Move(Square.e5, Square.e7, new SimpleMoveExecutor(Pieza.TORRE_BLANCO)));
	}
	
	@Test
	public void testToString01() {
		Move move = new Move(Square.e5, Square.e7, new SimpleMoveExecutor(Pieza.TORRE_BLANCO));
		assertEquals("e5 e7; Simple: TORRE_BLANCO", move.toString());
	}
	
	@Test
	public void testToString02() {
		Move move = new Move(Square.e5, Square.e7, null);
		assertEquals("e5 e7; ERROR", move.toString());
	}	
	
	@Test
	public void testSimple() {
		FENParser parser = new FENParser();
		DummyBoard tablero = parser.parsePiecePlacement("8/8/8/4R3/8/8/8/8");
		
		assertEquals(tablero.getPieza(Square.e5), Pieza.TORRE_BLANCO);
		
		Move move = new Move(Square.e5, Square.e7, new SimpleMoveExecutor(Pieza.TORRE_BLANCO));
		
		move.execute(tablero.getMediator());
		assertEquals(tablero.getPieza(Square.e7), Pieza.TORRE_BLANCO);
		assertTrue(tablero.isEmtpy(Square.e5));
		
		move.undo(tablero.getMediator());
		assertEquals(tablero.getPieza(Square.e5), Pieza.TORRE_BLANCO);
		assertTrue(tablero.isEmtpy(Square.e7));
		
	}
	
	@Test
	public void testCapture() {
		FENParser parser = new FENParser();
		DummyBoard tablero = parser.parsePiecePlacement("8/4p3/8/4R3/8/8/8/8");
		
		assertEquals(tablero.getPieza(Square.e5), Pieza.TORRE_BLANCO);
		assertEquals(tablero.getPieza(Square.e7), Pieza.PEON_NEGRO);
		
		Move move = new Move(Square.e5, Square.e7, new CaptureMoveExecutor(Pieza.TORRE_BLANCO, Pieza.PEON_NEGRO));
		
		move.execute(tablero.getMediator());
		assertEquals(tablero.getPieza(Square.e7), Pieza.TORRE_BLANCO);
		assertTrue(tablero.isEmtpy(Square.e5));
		
		move.undo(tablero.getMediator());
		assertEquals(tablero.getPieza(Square.e5), Pieza.TORRE_BLANCO);
		assertEquals(tablero.getPieza(Square.e7), Pieza.PEON_NEGRO);
		
	}

}
