package com.guttery.madii.domain.notification.infrastructure;

import com.guttery.madii.domain.notification.application.dto.NotificationTokenDto;
import com.guttery.madii.domain.notification.domain.repository.NotificationTokensCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class NotificationTokensRepositoryImpl implements NotificationTokensCustomRepository {
    private final MongoOperations mongoOperations;

    @Override
    public List<String> getTokensByUserIds(List<String> userIds) {
        final MatchOperation matchStage = Aggregation.match(Criteria.where("_id").in(userIds));

        final ProjectionOperation projectStage = Aggregation.project()
                .andExclude("_id")
                .andExpression("{$objectToArray: '$tokenByDeviceId'}").as("tokenArray");

        final UnwindOperation unwindStage = Aggregation.unwind("tokenArray");

        final GroupOperation groupStage = Aggregation.group().push("tokenArray.v").as("tokensList");

        final ProjectionOperation projectTokensStage = Aggregation.project()
                .andExclude("_id")
                .andInclude("tokensList");

        final Aggregation aggregation = Aggregation.newAggregation(
                matchStage,
                projectStage,
                unwindStage,
                groupStage,
                projectTokensStage
        );

        final AggregationResults<NotificationTokenDto> aggregateResults = mongoOperations.aggregate(
                aggregation, "notificationtokens", NotificationTokenDto.class);

        final List<NotificationTokenDto> resultList = aggregateResults.getMappedResults();
        if (resultList.isEmpty()) {
            return Collections.emptyList();
        }

        return resultList.get(0).tokensList();
    }
}
