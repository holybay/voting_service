package by.it_academy.jd2.golubev_107.voting_service.service;


import by.it_academy.jd2.golubev_107.voting_service.service.dto.artist.ArtistVotingDtoFull;
import by.it_academy.jd2.golubev_107.voting_service.service.dto.vote.VoteInptDto;
import by.it_academy.jd2.golubev_107.voting_service.service.dto.vote.VotesResult;

import java.util.List;

public interface IVoteService {

    void init(List<ArtistVotingDtoFull> artists);

    VotesResult calculate(VoteInptDto inDto);

}
