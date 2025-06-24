package com.maxkemzi.mypianolist.user.profile.service;

public class UserProfileStats {
	private long total;
	private long learning;
	private long completed;
	private long dropped;
	private long planToLearn;

	protected UserProfileStats() {
	}

	public UserProfileStats(long total, long learning, long completed, long dropped, long planToLearn) {
		this.total = total;
		this.learning = learning;
		this.completed = completed;
		this.dropped = dropped;
		this.planToLearn = planToLearn;
	}

	public long getTotal() {
		return total;
	}

	public long getLearning() {
		return learning;
	}

	public long getCompleted() {
		return completed;
	}

	public long getDropped() {
		return dropped;
	}

	public long getPlanToLearn() {
		return planToLearn;
	}
}
