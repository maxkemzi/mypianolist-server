package com.maxkemzi.mypianolist.user.profile.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.maxkemzi.mypianolist.user.model.User;
import com.maxkemzi.mypianolist.user.piece.model.UserPiece;
import com.maxkemzi.mypianolist.user.piece.model.UserPieceStatus;
import com.maxkemzi.mypianolist.user.piece.repository.UserPieceRepository;
import com.maxkemzi.mypianolist.user.repository.UserRepository;
import com.maxkemzi.mypianolist.user.service.UserNotFoundException;

@Service
public class UserProfileService {
	private final UserRepository userRepository;
	private final UserPieceRepository userPieceRepository;

	public UserProfileService(UserRepository userRepository, UserPieceRepository userPieceRepository) {
		this.userRepository = userRepository;
		this.userPieceRepository = userPieceRepository;
	}

	public UserProfile findByUsername(String username) {
		Optional<User> user = this.userRepository.findByUsername(username);
		if (user.isEmpty()) {
			throw new UserNotFoundException();
		}

		UserProfileStats stats = this.getStats(username);

		return new UserProfile(user.get(), stats);
	}

	private UserProfileStats getStats(String username) {
		List<UserPiece> pieces = this.userPieceRepository.findByUserUsername(username);

		long total = pieces.size();
		long learning = this.countPiecesByStatus(pieces, UserPieceStatus.CURRENTLY_LEARNING);
		long completed = this.countPiecesByStatus(pieces, UserPieceStatus.COMPLETED);
		long dropped = this.countPiecesByStatus(pieces, UserPieceStatus.DROPPED);
		long planToLearn = this.countPiecesByStatus(pieces, UserPieceStatus.PLAN_TO_LEARN);

		return new UserProfileStats(total, learning, completed, dropped, planToLearn);
	}

	private long countPiecesByStatus(List<UserPiece> pieces, UserPieceStatus status) {
		return pieces.stream().filter(p -> p.getStatus() == status).count();
	}
}
