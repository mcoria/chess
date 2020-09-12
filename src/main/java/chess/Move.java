package chess;

import layers.ColorBoard;
import layers.DummyBoard;

//TOOD: Implementar en terminos de Square a Square
public interface Move {

	//TOOD: Y si en vez de PosicionPieza utilizamos Square ?
	PosicionPieza getFrom();
	PosicionPieza getTo();

	void executeMove(DummyBoard board);
	void undoMove(DummyBoard board);
	
	void executeMove(BoardState boardState);
	void undoMove(BoardState boardState);

	void executeMove(ColorBoard boardCache);
	void undoMove(ColorBoard boardCache);
	
	void executeMove(MoveCache moveCache);
	void undoMove(MoveCache moveCache);	
}