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

package hanto.studentNMNK.beta;

import hanto.common.*;
import hanto.studentNMNK.common.HantoBoard;
import hanto.studentNMNK.common.HantoCoordinateImpl;
import hanto.studentNMNK.common.HantoPieceImpl;
import hanto.studentNMNK.common.validator.Validator;

import static hanto.common.HantoPlayerColor.*;

/**
 * Beta Hanto Game class that is responsible for all moves made during a Beta Hanto game
 * 
 * @version Mar 16, 2016
 */
public class BetaHantoGame implements HantoGame {

	private int moveNum = 1;
	private HantoBoard hantoBoard = new HantoBoard();
	private boolean blueFirst = true;
	private final Validator validator;

	/**
	 * Constructor for the BetaHantoGame class where the color of the player is
	 * specified This defaults to blue if there is nothing specified
	 * 
	 * @param movesFirst
	 * @param validator
	 */
	public BetaHantoGame(HantoPlayerColor movesFirst, Validator validator) {
		if (movesFirst == RED) {
			blueFirst = false;
		}
		this.validator = validator;
		validator.updateGame(moveNum, hantoBoard);
	}

	/*
	 * @see hanto.common.HantoGame#makeMove(hanto.common.HantoPieceType,
	 * hanto.common.HantoCoordinate, hanto.common.HantoCoordinate)
	 */
	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to)
			throws HantoException {
		HantoPlayerColor pieceColor;
		HantoCoordinateImpl newTo = new HantoCoordinateImpl(to);
		if (blueFirst) {
			pieceColor = (moveNum % 2 == 0 ? RED : BLUE);
		} else {
			pieceColor = (moveNum % 2 == 0 ? BLUE : RED);
		}
		
		if (!validator.isValidMove(pieceType, pieceColor, from, newTo)) {
			throw new HantoException("Invalid move");
		}

		hantoBoard.AddPieceToBoard(new HantoPieceImpl(pieceColor, pieceType), newTo);
		validator.updateGame(++moveNum, hantoBoard);
		
		return validator.getBoardState();
	}

	/*
	 * @see hanto.common.HantoGame#getPieceAt(hanto.common.HantoCoordinate)
	 */
	@Override
	public HantoPiece getPieceAt(HantoCoordinate where) {
		HantoCoordinate newWhere = new HantoCoordinateImpl(where);
		return hantoBoard.getPieceAtLocation(newWhere);
	}

	/*
	 * @see hanto.common.HantoGame#getPrintableBoard()
	 */
	@Override
	public String getPrintableBoard() {
		return hantoBoard.printBoard();
	}

}
