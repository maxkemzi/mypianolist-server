package com.maxkemzi.mypianolist.user.profile.service;

import com.maxkemzi.mypianolist.user.piece.model.UserPieceStatus;

public class UserProfileStats {
	private long total;
	private UserProfileStatusStat[] statuses;

	protected UserProfileStats() {
	}

	public UserProfileStats(long total, long learning, long completed, long dropped, long planToLearn) {
		this.total = total;
		this.statuses = new UserProfileStatusStat[] {
				new UserProfileStatusStat(UserPieceStatus.CURRENTLY_LEARNING, learning),
				new UserProfileStatusStat(UserPieceStatus.COMPLETED, completed),
				new UserProfileStatusStat(UserPieceStatus.DROPPED, dropped),
				new UserProfileStatusStat(UserPieceStatus.PLAN_TO_LEARN, planToLearn)
		};
	}

	public long getTotal() {
		return total;
	}

	public UserProfileStatusStat[] getStatuses() {
		return statuses;
	}
}
