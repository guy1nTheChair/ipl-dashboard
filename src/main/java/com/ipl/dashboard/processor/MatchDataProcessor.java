package com.ipl.dashboard.processor;

import com.ipl.dashboard.entity.MatchInput;
import com.ipl.dashboard.model.Match;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDate;

public class MatchDataProcessor implements ItemProcessor<MatchInput, Match> {

    private static final Logger log = LoggerFactory.getLogger(MatchDataProcessor.class);

    @Override
    public Match process(final MatchInput matchInput) throws Exception {
        String firstInningsTeam, secondInningsTeam;

        Match matchRow = new Match();
        matchRow.setId(Long.parseLong(matchInput.getId()));
        matchRow.setCity(matchInput.getCity());
        matchRow.setDate(LocalDate.parse(matchInput.getDate()));
        matchRow.setPlayerOfMatch(matchInput.getPlayer_of_Match());
        matchRow.setVenue(matchInput.getVenue());
        matchRow.setTossWinner(matchInput.getToss_winner());
        matchRow.setTossDecision(matchInput.getToss_decision());
        matchRow.setResult(matchInput.getResult());
        matchRow.setResultMargin(matchInput.getResult_margin());
        matchRow.setUmpire1(matchInput.getUmpire1());
        matchRow.setUmpire2(matchInput.getUmpire2());
        if ("bat".equals(matchInput.getToss_decision())) {
            firstInningsTeam = matchInput.getToss_winner();
            secondInningsTeam = matchInput.getToss_winner().equals(matchInput.getTeam1())
                    ? matchInput.getTeam2() : matchInput.getTeam1();
        } else {
            secondInningsTeam = matchInput.getToss_winner();
            firstInningsTeam = matchInput.getToss_winner().equals(matchInput.getTeam1())
                    ? matchInput.getTeam2() : matchInput.getTeam1();
        }
        matchRow.setTeam1(firstInningsTeam);
        matchRow.setTeam2(secondInningsTeam);

        return matchRow;
    }

}