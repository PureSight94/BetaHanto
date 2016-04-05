/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.  
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/package hanto.studentNMNK.common.validator;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentNMNK.common.HantoBoard;

/**
 * This interface outlines all the methods needed to validate a game of any sort. This will be chosen
 * during the game factory creation. 
 * @author Nicholas Muesch & Nicholas Kalamvokis
 *
 */
public interface Validator {

	/**
	 * Checks if the player is making a valid move under the rule set defined in
	 * the GammaHnato Game
	 * 
	 * @param pieceType
	 * @param pieceColor
	 * @param from
	 * @param to
	 * @return whether the given move is valid
	 * @throws HantoException
	 */
	boolean isValidMove(HantoPieceType pieceType, HantoPlayerColor pieceColor, HantoCoordinate from, HantoCoordinate to)
			throws HantoException;

	/**
	 * Checks if the first piece is placed at coordinate 0,0 as specified by the
	 * GammaHanto rule set
	 * 
	 * @param newTo
	 * @return whether the first move of the game is valid
	 */
	boolean isValidFirstMove(HantoCoordinate newTo);

	/**
	 * Checks if the second piece is placed correctly GammaHanto rule set
	 * 
	 * @param to
	 * @return whether the first move of the game is valid
	 */
	boolean isValidSecondMove(HantoCoordinate to);
	
	/**
	 * Checks if the move after move 2 is a valid one according to specific game rules
	 * @param to
	 * @param from
	 * @return true if the 3rd or greater move is valid, false otherwise
	 */
	boolean isValidSubsequentMove(HantoCoordinate to, HantoCoordinate from);

	/**
	 * Checks that the player has played the Butterfly piece by the specified turn number,
	 * if not an Exception is thrown
	 * 
	 * @param bluePlayed
	 * @param redPlayed
	 * @param butterflyMustBePlayedTurn
	 * @throws HantoException
	 */
	void isButterflyPlayedByMaxTurn(boolean bluePlayed, boolean redPlayed, int butterflyMustBePlayedTurn)
			throws HantoException;

	/**
	 * Checks that the Butterfly piece hasnt been played more than once per
	 * player per game. If a player places a Butterfly piece more than once per
	 * game, an exception is thrown
	 * 
	 * @param pieceType
	 * @param pieceColor
	 * @throws HantoException
	 */
	void isButterflyPlayedTwice(HantoPieceType pieceType, HantoPlayerColor pieceColor) throws HantoException;

	/**
	 * Updates the validator with the current status of the game
	 * @param moveNumber
	 * @param currentBoard
	 */
	void updateGame(int moveNumber, HantoBoard currentBoard);
	
	/**
	 * Checks whether the given location is currently occupied by another piece
	 * @param coordinate
	 * @return true if occupied, false otherwise
	 */
	boolean isOccupied(HantoCoordinate coordinate);

	/**
	 * Retrieves the Move Result based on the current board state (different between specific game versions)
	 * @return the MoveResult corresponding to the current board state
	 */
	MoveResult getBoardState();
	
	/**
	 * Gets the current player color 
	 * @return the HantoPlayerColor corresponding to the current player
	 */
	HantoPlayerColor getPlayerColor();

	/**
	 * Sets the Piece Type to be the given pieceType
	 * @param pieceType
	 */
	void setPieceType(HantoPieceType pieceType);
}
