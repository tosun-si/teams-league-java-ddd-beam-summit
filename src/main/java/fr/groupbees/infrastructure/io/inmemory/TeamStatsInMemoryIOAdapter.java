package fr.groupbees.infrastructure.io.inmemory;

import fr.groupbees.domain.*;
import fr.groupbees.domain_transform.TeamStatsInMemoryIOConnector;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.values.PBegin;
import org.apache.beam.sdk.values.PCollection;

import javax.inject.Inject;

public class TeamStatsInMemoryIOAdapter implements TeamStatsInMemoryIOConnector {

    @Inject
    public TeamStatsInMemoryIOAdapter() {
    }

    @Override
    public PTransform<PBegin, PCollection<TeamStatsRaw>> read() {
        return new TeamStatsInMemoryReadTransform();
    }
}
