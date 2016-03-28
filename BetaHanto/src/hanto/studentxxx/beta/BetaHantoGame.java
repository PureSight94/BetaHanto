/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Copyright ©2016 Gary F. Pollice
 *******************************************************************************/

package hanto.studentxxx.beta;

import hanto.common.*;
import hanto.studentxxx.common.HantoBoard;
import hanto.studentxxx.common.HantoCoordinateImpl;
import hanto.studentxxx.common.HantoPieceImpl;

import static hanto.common.MoveResult.*;
import static hanto.common.HantoPieceType.*;
import static hanto.common.HantoPlayerColor.*;

/**
 * <<Fill this in>>
 * 
 * @version Mar 16, 2016
 */
public class BetaHantoGame implements HantoGame {

	private boolean firstMove = true;
	private boolean redPlayedButterfly = false;
	private boolean bluePlayedButterfly = false;
	private int moveNum = 1;
	private boolean isGameOver = false;
	private HantoBoard hantoBoard = new HantoBoard();

	/*
	 * @see hanto.common.HantoGame#makeMove(hanto.common.HantoPieceType,
	 * hanto.common.HantoCoordinate, hanto.common.HantoCoordinate)
	 */
	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to)
			throws HantoException {
		HantoPlayerColor pieceColor = (moveNum % 2 == 0 ? RED : BLUE);
		HantoCoordinate newTo = new HantoCoordinateImpl(to);

		if (isGameOver) {
			throw new HantoException("Player attempted to make a move after the game ended");
		}

		if (from != null) {
			throw new HantoException("Beta Hanto does not support the moving of pieces");
		}

		if (!pieceType.equals(HantoPieceType.SPARROW) && !pieceType.equals(HantoPieceType.BUTTERFLY)) {
			throw new HantoException("Can only use Sparrow and Butterfly in Beta Hanto");
		}

		if (!isValidMove(pieceType, pieceColor, to)) {
			throw new HantoException("Invalid move");
		}

		hantoBoard.AddPieceToBoard(new HantoPieceImpl(pieceColor, pieceType), to);

		if (pieceType == BUTTERFLY) {
			if (pieceColor == BLUE) {
				bluePlayedButterfly = true;
			} else if (pieceColor == RED) {
				redPlayedButterfly = true;
			}
		}

		if ((moveNum >= 12) || (hantoBoard.isButterflySurrounded(RED) && (hantoBoard.isButterflySurrounded(BLUE)))) {
			isGameOver = true;
			return DRAW;
		} else if (hantoBoard.isButterflySurrounded(RED)) {
			isGameOver = true;
			return BLUE_WINS;
		} else if (hantoBoard.isButterflySurrounded(BLUE)) {
			isGameOver = true;
			return RED_WINS;
		}

		moveNum++;
		return OK;
	}

	/**
	 * Checks if the player is making a valid move under the rule set defined in
	 * the BetaHnato Game
	 * 
	 * @param pieceType
	 * @param pieceColor
	 * @param to
	 * @return whether the given move is valid
	 * @throws HantoException
	 */
	public boolean isValidMove(HantoPieceType pieceType, HantoPlayerColor pieceColor, HantoCoordinate to)
			throws HantoException {
		HantoCoordinateImpl newTo = new HantoCoordinateImpl(to);

		if (!isValidFirstMove(newTo)) {
			return false;
		}
		isButterflyPlayedTwice(pieceType, pieceColor);
		isButterflyPlayedByFourthTurn(bluePlayedButterfly, redPlayedButterfly);
		return true;
	}

	/**
	 * Checks if the first piece is placed at coordinate 0,0 as specified by the
	 * BetaHanto rule set
	 * 
	 * @param newTo
	 * @return whether the first move of the game is valid
	 */
	public boolean isValidFirstMove(HantoCoordinateImpl newTo) {
		if (firstMove && newTo.equals(new HantoCoordinateImpl(0, 0))) {
			firstMove = false;
			return true;
		} else if ((!hantoBoard.isAdjacentToAny(newTo) || hantoBoard.isTaken(newTo))) {
			return false;
		}
		return true;
	}

	/**
	 * Checks that the Butterfly piece hasnt been played more than once per
	 * player per game. If a player places a Butterfly piece more than once per
	 * game, an exception is thrown
	 * 
	 * @param pieceType
	 * @param pieceColor
	 * @throws HantoException
	 */
	public void isButterflyPlayedTwice(HantoPieceType pieceType, HantoPlayerColor pieceColor) throws HantoException {
		if ((pieceColor == RED) && (pieceType == BUTTERFLY) && (redPlayedButterfly)) {
			throw new HantoException("Red played butterfly twice");
		} else if ((pieceColor == BLUE) && (pieceType == BUTTERFLY) && (bluePlayedButterfly)) {
			throw new HantoException("Blue played butterfly twice");
		}
	}

	/**
	 * Checks that the player has played the Butterfly piece by the fourth turn,
	 * if not an Exception is thrown
	 * 
	 * @param bluePlayed
	 * @param redPlayed
	 * @throws HantoException
	 */
	public void isButterflyPlayedByFourthTurn(boolean bluePlayed, boolean redPlayed) throws HantoException {
		if ((!redPlayed || !bluePlayed) && (moveNum / 2 > 4)) {
			throw new HantoException("Fourth turn and butterfly has not been played yet");
		}
	}

	/*
	 * @see hanto.common.HantoGame#getPieceAt(hanto.common.HantoCoordinate)
	 */
	@Override
	public HantoPiece getPieceAt(HantoCoordinate where) {
		HantoCoordinate newWhere = new HantoCoordinateImpl(where);
		if (newWhere.equals(new HantoCoordinateImpl(0, 0))) {
			return new HantoPieceImpl(HantoPlayerColor.BLUE, HantoPieceType.BUTTERFLY);
		}
		return null;
	}

	/*
	 * @see hanto.common.HantoGame#getPrintableBoard()
	 */
	@Override
	public String getPrintableBoard() {
		return hantoBoard.printBoard();
	}

}
