package com.ipl.dashboard.processor;

import com.ipl.dashboard.entity.MatchInput;
import com.ipl.dashboard.model.MatchData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDate;

public class MatchDataProcessor implements ItemProcessor<MatchInput, MatchData> {

    private static final Logger log = LoggerFactory.getLogger(MatchDataProcessor.class);

    @Override
    public MatchData process(final MatchInput matchInput) throws Exception {
        String firstInningsTeam, secondInningsTeam;

        MatchData matchDataRow = new MatchData();
        matchDataRow.setId(Long.parseLong(matchInput.getId()));
        matchDataRow.setCity(matchInput.getCity());
        matchDataRow.setDate(LocalDate.parse(matchInput.getDate()));
        matchDataRow.setPlayerOfMatch(matchInput.getPlayer_of_Match());
        matchDataRow.setVenue(matchInput.getVenue());
        matchDataRow.setTossWinner(matchInput.getToss_winner());
        matchDataRow.setTossDecision(matchInput.getToss_decision());
        matchDataRow.setResult(matchInput.getResult());
        matchDataRow.setResultMargin(matchInput.getResult_margin());
        matchDataRow.setUmpire1(matchInput.getUmpire1());
        matchDataRow.setUmpire2(matchInput.getUmpire2());
        if ("bat".equals(matchInput.getToss_decision())) {
            firstInningsTeam = matchInput.getToss_winner();
            secondInningsTeam = matchInput.getToss_winner().equals(matchInput.getTeam1())
                    ? matchInput.getTeam2() : matchInput.getTeam1();
        } else {
            secondInningsTeam = matchInput.getToss_winner();
            firstInningsTeam = matchInput.getToss_winner().equals(matchInput.getTeam1())
                    ? matchInput.getTeam2() : matchInput.getTeam1();
        }
        matchDataRow.setTeam1(firstInningsTeam);
        matchDataRow.setTeam2(secondInningsTeam);

        return matchDataRow;
    }

}