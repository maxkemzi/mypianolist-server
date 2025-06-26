package com.maxkemzi.mypianolist.user.piece.service;

import com.maxkemzi.mypianolist.user.piece.model.UserPieceStatus;

public class UserPieceStatusStat {
	private UserPieceStatus status;
	private long count;

	protected UserPieceStatusStat() {
	}

	public UserPieceStatusStat(UserPieceStatus status, long count) {
		this.status = status;
		this.count = count;
	}

	public UserPieceStatus getStatus() {
		return status;
	}

	public long getCount() {
		return count;
	}
}
