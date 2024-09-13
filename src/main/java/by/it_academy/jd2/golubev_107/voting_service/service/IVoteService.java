package by.it_academy.jd2.golubev_107.voting_service.service;


import by.it_academy.jd2.golubev_107.voting_service.service.dto.vote.VoteInptDto;
import by.it_academy.jd2.golubev_107.voting_service.service.dto.vote.VotesResult;

public interface IVoteService {

    VotesResult calculate(VoteInptDto inDto);

}
