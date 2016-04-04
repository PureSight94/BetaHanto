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

package hanto.studentNMNK.gamma;

import hanto.common.*;
import hanto.studentNMNK.common.HantoBoard;
import hanto.studentNMNK.common.HantoCoordinateImpl;
import hanto.studentNMNK.common.HantoPieceImpl;
import hanto.studentNMNK.common.validator.Validator;

import static hanto.common.MoveResult.*;
import static hanto.common.HantoPieceType.*;
import static hanto.common.HantoPlayerColor.*;

/**
 * Gamma Hanto Game class that is responsible for all moves made during a Gamma Hanto game
 * 
 * @version Mar 16, 2016
 */
public class GammaHantoGame implements HantoGame {

	private boolean firstMove = true;
	private boolean redPlayedButterfly = false;
	private boolean bluePlayedButterfly = false;
	private int moveNum = 1;
	private HantoBoard hantoBoard = new HantoBoard();
	private final Validator validator;
	
	/*
	 * @see hanto.common.HantoGame#makeMove(hanto.common.HantoPieceType,
	 * hanto.common.HantoCoordinate, hanto.common.HantoCoordinate)
	 */
	public GammaHantoGame(Validator validator) {
		this.validator = validator;
		validator.updateGame(moveNum, hantoBoard);
	}

	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to)
			throws HantoException {
		HantoPlayerColor pieceColor;
		HantoCoordinateImpl newTo = new HantoCoordinateImpl(to);
		HantoCoordinateImpl newFrom = null;
		if(from != null) {
			newFrom = new HantoCoordinateImpl(from);
		}
		pieceColor = validator.getPlayerColor();

		if (!validator.isValidMove(pieceType, pieceColor, newFrom, newTo)) {
			throw new HantoException("Invalid move");
		}

		hantoBoard.AddPieceToBoard(new HantoPieceImpl(pieceColor, pieceType), newTo);
		moveNum++;
		validator.updateGame(moveNum, hantoBoard);
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
