package by.it_academy.jd2.golubev_107.voting_service.service;


import by.it_academy.jd2.golubev_107.voting_service.service.dto.ArtistVotingDtoFull;
import by.it_academy.jd2.golubev_107.voting_service.service.dto.VoteInptDto;
import by.it_academy.jd2.golubev_107.voting_service.service.dto.VotesResult;

import java.util.List;

public interface IVoteService {

    void init(List<ArtistVotingDtoFull> artists);

    VotesResult calculate(VoteInptDto inDto);

}
