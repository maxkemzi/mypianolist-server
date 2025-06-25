package com.maxkemzi.mypianolist.user.profile.service;

import com.maxkemzi.mypianolist.user.piece.model.UserPieceStatus;

public class UserProfileStatusStat {
	private UserPieceStatus status;
	private long count;

	protected UserProfileStatusStat() {
	}

	public UserProfileStatusStat(UserPieceStatus status, long count) {
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
