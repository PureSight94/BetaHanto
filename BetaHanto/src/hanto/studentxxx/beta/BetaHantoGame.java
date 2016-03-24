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

import java.util.HashMap;

/**
 * <<Fill this in>>
 * 
 * @version Mar 16, 2016
 */
public class BetaHantoGame implements HantoGame {

	private boolean firstMove = true;
	private int moveNum = 1;

	/*
	 * @see hanto.common.HantoGame#makeMove(hanto.common.HantoPieceType,
	 * hanto.common.HantoCoordinate, hanto.common.HantoCoordinate)
	 */
	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to)
			throws HantoException {
		HantoPlayerColor pieceColor = (moveNum % 2 == 0 ? HantoPlayerColor.RED : HantoPlayerColor.BLUE);
		HantoCoordinate newTo = new HantoCoordinateImpl(to);

		if (from != null) {
			throw new HantoException("Beta Hanto does not support the moving of pieces");
		}

		if (!pieceType.equals(HantoPieceType.SPARROW) && !pieceType.equals(HantoPieceType.BUTTERFLY)) {
			throw new HantoException("Can only use Sparrow and Butterfly in Beta Hanto");
		}

		if (!isValidMove(pieceType, to)) {
			throw new HantoException("Invalid move");
		}
		HantoBoard.AddPieceToBoard(new HantoPieceImpl(pieceColor, pieceType), to);
		moveNum++;
		return OK;
	}

	public boolean isValidMove(HantoPieceType pieceType, HantoCoordinate to) throws HantoException {
		HantoCoordinateImpl newTo = new HantoCoordinateImpl(to);
		if (firstMove && newTo.equals(new HantoCoordinateImpl(0, 0))) {
			firstMove = false;
			return true;
		} else if ((!HantoBoard.isAdjacentToAny(newTo) || HantoBoard.isTaken(newTo))) {
			return false;
		}
		return true;
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
		// TODO Auto-generated method stub
		return null;
	}

}
