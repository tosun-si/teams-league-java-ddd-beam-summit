# teams-league-java-ddd-beam-summit

## Run job with Dataflow runner :

### Batch

```
mvn compile exec:java \
  -Dexec.mainClass=fr.groupbees.application.TeamLeagueApp \
  -Dexec.args=" \
  --project=emea-c1-dwh-dev \
  --runner=DataflowRunner \
  --jobName=team-league-java-job-$(date +'%Y-%m-%d-%H-%M-%S') \
  --inputJsonFile=gs://mazlum_dev/team_league/input/json/input_teams_stats_raw.json \
  --region=europe-west1 \
  --streaming=false \
  --zone=europe-west1-d \
  --tempLocation=gs://mazlum_dev/dataflow/temp \
  --gcpTempLocation=gs://mazlum_dev/dataflow/temp \
  --stagingLocation=gs://mazlum_dev/dataflow/staging \
  --serviceAccount=922164338802-compute@developer.gserviceaccount.com \
  --teamLeagueDataset=mazlum_test \
  --teamStatsTable=team_stat \
  --bqWriteMethod=FILE_LOADS \
  " \
  -Pdataflow-runner
```

### Streaming

```
mvn compile exec:java \
  -Dexec.mainClass=fr.groupbees.application.TeamLeagueApp \
  -Dexec.args=" \
  --project=emea-c1-dwh-dev \
  --runner=DataflowRunner \
  --jobName=team-league-java-job-$(date +'%Y-%m-%d-%H-%M-%S') \
  --inputJsonFile=gs://mazlum_dev/team_league/input/json/input_teams_stats_raw.json \
  --inputSubscription=projects/emea-c1-dwh-dev/subscriptions/team_league \
  --region=europe-west1 \
  --streaming=true \
  --zone=europe-west1-d \
  --tempLocation=gs://mazlum_dev/dataflow/temp \
  --gcpTempLocation=gs://mazlum_dev/dataflow/temp \
  --stagingLocation=gs://mazlum_dev/dataflow/staging \
  --serviceAccount=922164338802-compute@developer.gserviceaccount.com \
  --teamLeagueDataset=mazlum_test \
  --teamStatsTable=team_stat \
  --bqWriteMethod=STREAMING_INSERTS \
  " \
  -Pdataflow-runner
```