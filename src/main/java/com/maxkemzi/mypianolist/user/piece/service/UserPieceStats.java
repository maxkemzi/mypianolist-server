package com.maxkemzi.mypianolist.user.piece.service;

import com.maxkemzi.mypianolist.user.piece.model.UserPieceStatus;

public class UserPieceStats {
	private long total;
	private UserPieceStatusStat[] statuses;

	protected UserPieceStats() {
	}

	public UserPieceStats(long total, long learning, long completed, long dropped, long planToLearn) {
		this.total = total;
		this.statuses = new UserPieceStatusStat[] {
				new UserPieceStatusStat(UserPieceStatus.CURRENTLY_LEARNING, learning),
				new UserPieceStatusStat(UserPieceStatus.COMPLETED, completed),
				new UserPieceStatusStat(UserPieceStatus.DROPPED, dropped),
				new UserPieceStatusStat(UserPieceStatus.PLAN_TO_LEARN, planToLearn)
		};
	}

	public long getTotal() {
		return total;
	}

	public UserPieceStatusStat[] getStatuses() {
		return statuses;
	}
}
