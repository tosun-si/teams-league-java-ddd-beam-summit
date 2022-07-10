package fr.groupbees.domain_transform;

import com.fasterxml.jackson.core.type.TypeReference;
import fr.groupbees.domain.TeamStats;
import fr.groupbees.domain.TeamStatsRaw;
import fr.groupbees.domain.exception.TeamStatsRawValidatorException;
import fr.groupbees.infrastructure.io.jsonfile.JsonUtil;
import lombok.val;
import org.apache.beam.sdk.testing.PAssert;
import org.apache.beam.sdk.testing.TestPipeline;
import org.apache.beam.sdk.testing.ValidatesRunner;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.values.PCollection;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.Serializable;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.apache.beam.sdk.values.TypeDescriptors.strings;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(JUnit4.class)
public class TeamStatsTransformTest implements Serializable {

    @Rule
    public transient TestPipeline p = TestPipeline.create();

    @Test
    @Category(ValidatesRunner.class)
    public void givenInputTeamsStatsRawWithoutErrorWhenTransformToStatsDomainThenExpectedOutputInResult() {
        // Given.
        val referenceTeamStatRaw = new TypeReference<List<TeamStatsRaw>>() {
        };
        final List<TeamStatsRaw> inputTeamsStatsRaw = JsonUtil.deserializeFromResourcePath(
                "files/input/domain/transform/input_teams_stats_raw_without_error.json",
                referenceTeamStatRaw
        );

        PCollection<TeamStatsRaw> input = p.apply("Read team stats Raw", Create.of(inputTeamsStatsRaw));

        // When.
        PCollection<String> output = input
                .apply("Transform to team stats", new TeamStatsTransform())
                .apply("Map to Json String", MapElements.into(strings()).via(JsonUtil::serialize))
                .apply("Log Output team stats", MapElements.into(strings()).via(this::logStringElement));

        val referenceTeamStat = new TypeReference<List<TeamStats>>() {
        };
        final List<String> expectedTeamsStats = JsonUtil.deserializeFromResourcePath(
                        "files/expected/domain/transform/expected_teams_stats_without_error.json",
                        referenceTeamStat)
                .stream()
                .map(JsonUtil::serialize)
                .collect(toList());

        // Then.
        PAssert.that(output).containsInAnyOrder(expectedTeamsStats);

        p.run().waitUntilFinish();
    }

    @Test
    @Category(ValidatesRunner.class)
    public void givenInputTeamsStatsRawWithOneErrorAndOneGoodInputWhenTransformToStatsDomainThenValidatorExceptionIsThrownWithExpectedMessage() {
        // Given.
        val referenceTeamStatRaw = new TypeReference<List<TeamStatsRaw>>() {
        };
        final List<TeamStatsRaw> inputTeamsStatsRaw = JsonUtil.deserializeFromResourcePath(
                "files/input/domain/transform/input_teams_stats_raw_with_one_error_one_good_output.json",
                referenceTeamStatRaw
        );

        // When.
        p.apply("Read team stats Raw", Create.of(inputTeamsStatsRaw))
                .apply("Transform to team stats", new TeamStatsTransform())
                .apply("Map to Json String", MapElements.into(strings()).via(JsonUtil::serialize))
                .apply("Log Output team stats", MapElements.into(strings()).via(this::logStringElement));

        // When.
        final ThrowingCallable launchPipeline = () -> p.run().waitUntilFinish();

        assertThatThrownBy(launchPipeline)
                .hasCauseInstanceOf(TeamStatsRawValidatorException.class)
                .hasMessageContaining(TeamStatsRaw.TEAM_EMPTY_ERROR_MESSAGE);
    }

    private String logStringElement(final String element) {
        System.out.println(element);
        return element;
    }
}
