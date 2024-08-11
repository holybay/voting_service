package by.it_academy.jd2.golubev_107.voting_service.service;


import by.it_academy.jd2.golubev_107.voting_service.service.dto.VoteInptDto;
import by.it_academy.jd2.golubev_107.voting_service.service.dto.VotesResult;

public interface IVoteService {

    void init();

    VotesResult calculate(VoteInptDto inDto);

}
