package com.ipl.dashboard.batchprocessor;

import com.ipl.dashboard.entity.MatchInput;
import com.ipl.dashboard.model.MatchData;
import com.ipl.dashboard.processor.MatchDataProcessor;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;


@Configuration
@EnableBatchProcessing
public class BatchProcessor {

    private final String[] FIELDS = {
            "id", "city", "date", "player_of_match", "venue", "neutral_venue", "team1", "team2", "toss_winner", "toss_decision", "winner", "result", "result_margin", "eliminator", "method", "umpire1", "umpire2"
    };

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;


    @Bean
    public FlatFileItemReader<MatchInput> reader() {
        return new FlatFileItemReaderBuilder<MatchInput>()
                .name("personItemReader")
                .resource(new ClassPathResource("matches_data.csv"))
                .delimited()
                .names(FIELDS)
                .fieldSetMapper(new BeanWrapperFieldSetMapper<MatchInput>() {{
                    setTargetType(MatchInput.class);
                }})
                .build();
    }

    @Bean
    public MatchDataProcessor processor() {
        return new MatchDataProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<MatchData> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<MatchData>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO matches " +
                        "(id, city, date, player_of_match, venue, team1, team2, toss_winner, toss_decision, winner, result, result_margin, umpire1, umpire2) " +
                        "VALUES " +
                        "(:id, :city, :date, :playerOfMatch, :venue, :team1, :team2, :tossWinner, :tossDecision, :matchWinner, :result, :resultMargin, :umpire1, :umpire2 )")
                .dataSource(dataSource)
                .build();
    }

}