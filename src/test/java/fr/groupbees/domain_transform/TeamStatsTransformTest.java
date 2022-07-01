package fr.groupbees.domain_transform;

import fr.groupbees.domain.TeamStats;
import fr.groupbees.domain.TeamStatsRaw;
import fr.groupbees.infrastructure.io.inmemory.TeamStatsInMemoryIOAdapter;
import org.apache.beam.sdk.testing.TestPipeline;
import org.apache.beam.sdk.testing.ValidatesRunner;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.values.PCollection;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.Serializable;

import static org.apache.beam.sdk.values.TypeDescriptor.of;

/**
 * Tests of WordCount.
 */
@RunWith(JUnit4.class)
public class TeamStatsTransformTest implements Serializable {

    @Rule
    public transient TestPipeline p = TestPipeline.create();

    @Test
    @Category(ValidatesRunner.class)
    public void testCountWords() {
        PCollection<TeamStatsRaw> input = p.apply("Read team stats Raw", new TeamStatsInMemoryIOAdapter().read());

        PCollection<TeamStats> output = input
                .apply("Transform to team stats", new TeamStatsTransform())
                .apply("Log team stats", MapElements.into(of(TeamStats.class)).via(this::logTeamStats));

//        PAssert.that(output).containsInAnyOrder(COUNTS_ARRAY);
        p.run().waitUntilFinish();
    }

    private TeamStats logTeamStats(final TeamStats teamStats) {
        System.out.println(teamStats);
        return teamStats;
    }
}
