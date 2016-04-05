/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.  
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentNMNK.common.validator;

import static hanto.common.HantoPieceType.BUTTERFLY;
import static hanto.common.HantoPlayerColor.BLUE;
import static hanto.common.HantoPlayerColor.RED;
import static hanto.common.MoveResult.BLUE_WINS;
import static hanto.common.MoveResult.DRAW;
import static hanto.common.MoveResult.OK;
import static hanto.common.MoveResult.RED_WINS;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentNMNK.common.HantoBoard;
import hanto.studentNMNK.common.HantoCoordinateImpl;

/**
 * This class contains the specific validation rules to the Beta version of Hanto 
 * while still containing the common rules defined in AbsValidator
 * @author Nicholas Muesch & Nicholas Kalamvokis
 *
 */
public class BetaHantoRules extends AbsValidator {

	private final int BUTTERFLY_MUST_BE_PLAYED_MOVE_NUM = 4;
	private final int MAX_MOVES = 12;

	/**
	 * Checks if the player is making a valid move under the rule set defined in
	 * the BetaHanto Game
	 * 
	 * @param pieceType
	 * @param pieceColor
	 * @param to
	 * @return whether the given move is valid
	 * @throws HantoException
	 */
	@Override
	public boolean isValidMove(HantoPieceType pieceType, HantoPlayerColor pieceColor, HantoCoordinate from, HantoCoordinate to)
			throws HantoException {
		
		if (isGameOver) {
			throw new HantoException("Player attempted to make a move after the game ended");
		}

		if (!pieceType.equals(HantoPieceType.SPARROW) && !pieceType.equals(HantoPieceType.BUTTERFLY)) {
			throw new HantoException("Can only use Sparrow and Butterfly in Beta Hanto");
		}
		
		if ((moveNum == 1) && !isValidFirstMove(to)) {
			return false;
		}
		
		if ((moveNum == 2) && !isValidSecondMove(to)){
			return false;
		}
		
		if ((moveNum > 2) && !isValidSubsequentMove(to, from)){
			return false;
		}
		
		isButterflyPlayedTwice(pieceType, pieceColor);
		isButterflyPlayedByMaxTurn(bluePlayedButterfly, redPlayedButterfly, BUTTERFLY_MUST_BE_PLAYED_MOVE_NUM);
		
		if (pieceType == BUTTERFLY) {
			if (pieceColor == BLUE) {
				bluePlayedButterfly = true;
			} else if (pieceColor == RED) {
				redPlayedButterfly = true;
			}
		}
		return true;
	}

	/**
	 * Checks if the player is making a valid move after the second move
	 * under the rule set defined in the BetaHanto Game
	 * 
	 * @param to
	 * @return whether the given move is valid
	 */
	public boolean isValidSubsequentMove(HantoCoordinate to, HantoCoordinate from) {
		HantoCoordinateImpl newTo = new HantoCoordinateImpl(to);
		if (from != null) {
			return false;
		}
		if ((board.isAdjacentToAny(newTo) && !isOccupied(newTo)))
		{
			return true;
		}
		return false;
	}

	/**
	 * Updates board state and move number for the validator
	 */
	@Override
	public void updateGame(int moveNumber, HantoBoard currentBoard) {
		moveNum = moveNumber;
		board = currentBoard;
	}

	/**
	 * Gets the current state of the Hanto Board
	 */
	@Override
	public MoveResult getBoardState() {
		if (board.isButterflySurrounded(RED) && (board.isButterflySurrounded(BLUE))) {
			isGameOver = true;
			return DRAW;
		} else if (board.isButterflySurrounded(RED)) {
			isGameOver = true;
			return BLUE_WINS;
		} else if (board.isButterflySurrounded(BLUE)) {
			isGameOver = true;
			return RED_WINS;
		}
		
		if(moveNum > MAX_MOVES)
		{
			isGameOver = true;
			return DRAW;
		}
		return OK;
	}
	
	
}
