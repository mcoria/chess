package movegenerators;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;

import chess.Color;
import chess.DummyBoard;
import chess.Move;
import chess.Pieza;
import chess.Square;
import iterators.CardinalSquareIterator.Cardinal;
import moveexecutors.CaptureMove;
import moveexecutors.SimpleMove;
import parsers.FENBoarBuilder;

public class CardinalMoveGeneratorOesteTest {
	
	private FENBoarBuilder builder;

	@Before
	public void setUp() throws Exception {
		builder = new FENBoarBuilder();
	}
	
	@Test
	public void testOeste() {
		DummyBoard tablero = builder.withTablero("8/8/8/4R3/8/8/8/8").buildDummyBoard();
		
		Square from = Square.e5;
		assertEquals(Pieza.TORRE_BLANCO, tablero.getPieza(from));
		
		Map.Entry<Square, Pieza> origen = new SimpleImmutableEntry<Square, Pieza>(from, Pieza.TORRE_BLANCO);	
	
		CardinalMoveGenerator moveGenerator = new CardinalMoveGenerator(Color.BLANCO, new Cardinal[] {Cardinal.Oeste});
		moveGenerator.setTablero(tablero);
		
		Collection<Move> moves = moveGenerator.generateMoves(origen);
		
		assertEquals(4, moves.size());
		
		assertTrue(moves.contains( createSimpleMove(origen, Square.d5) ));
		assertTrue(moves.contains( createSimpleMove(origen, Square.c5) ));
		assertTrue(moves.contains( createSimpleMove(origen, Square.b5) ));
		assertTrue(moves.contains( createSimpleMove(origen, Square.a5) ));
	}
	
	@Test
	public void testOeste01() {
		DummyBoard tablero = builder.withTablero("8/8/8/B3R3/8/8/8/8").buildDummyBoard();
		
		Square from = Square.e5;
		assertEquals(Pieza.TORRE_BLANCO, tablero.getPieza(from));
		assertEquals(Pieza.ALFIL_BLANCO, tablero.getPieza(Square.a5));
		
		Map.Entry<Square, Pieza> origen = new SimpleImmutableEntry<Square, Pieza>(from, Pieza.TORRE_BLANCO);	
	
		CardinalMoveGenerator moveGenerator = new CardinalMoveGenerator(Color.BLANCO, new Cardinal[] {Cardinal.Oeste});
		moveGenerator.setTablero(tablero);
		
		Collection<Move> moves = moveGenerator.generateMoves(origen);
		
		assertEquals(3, moves.size());
		
		assertTrue(moves.contains( createSimpleMove(origen, Square.d5) ));
		assertTrue(moves.contains( createSimpleMove(origen, Square.c5) ));
		assertTrue(moves.contains( createSimpleMove(origen, Square.b5) ));
	}	
	
	@Test
	public void testOeste02() {
		DummyBoard tablero = builder.withTablero("8/8/8/b3R3/8/8/8/8").buildDummyBoard();
		
		Square from = Square.e5;
		assertEquals(Pieza.TORRE_BLANCO, tablero.getPieza(from));
		assertEquals(Pieza.ALFIL_NEGRO, tablero.getPieza(Square.a5));
		
		Map.Entry<Square, Pieza> origen = new SimpleImmutableEntry<Square, Pieza>(from, Pieza.TORRE_BLANCO);	
	
		CardinalMoveGenerator moveGenerator = new CardinalMoveGenerator(Color.BLANCO, new Cardinal[] {Cardinal.Oeste});
		moveGenerator.setTablero(tablero);
		
		Collection<Move> moves = moveGenerator.generateMoves(origen);
		
		assertEquals(4, moves.size());
		
		assertTrue(moves.contains( createSimpleMove(origen, Square.d5) ));
		assertTrue(moves.contains( createSimpleMove(origen, Square.c5) ));
		assertTrue(moves.contains( createSimpleMove(origen, Square.b5) ));
		assertTrue(moves.contains( createCaptureMove(origen, Square.a5, Pieza.ALFIL_NEGRO) ));
	}
		
	private Move createSimpleMove(Entry<Square, Pieza> origen, Square destinoSquare) {
		return new SimpleMove(origen, new SimpleImmutableEntry<Square, Pieza>(destinoSquare, null));
	}
	
	private Move createCaptureMove(Entry<Square, Pieza> origen, Square destinoSquare, Pieza destinoPieza) {
		return new CaptureMove(origen, new SimpleImmutableEntry<Square, Pieza>(destinoSquare, destinoPieza));
	}
}
