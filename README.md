# teams-league-java-ddd-beam-summit

## Run job with Dataflow runner :

```
mvn compile exec:java \
  -Dexec.mainClass=fr.groupbees.application.TeamLeagueApp \
  -Dexec.args=" \
  --project=emea-c1-dwh-dev \
  --runner=DataflowRunner \
  --jobName=team-league-job-$(date +'%Y-%m-%d-%H-%M-%S') \
  --region=europe-west1 \
  --streaming=false \
  --zone=europe-west1-d \
  --tempLocation=gs://mazlum_dev/dataflow/temp \
  --gcpTempLocation=gs://mazlum_dev/dataflow/temp \
  --stagingLocation=gs://mazlum_dev/dataflow/staging \
  --serviceAccount=922164338802-compute@developer.gserviceaccount.com \
  " \
  -Pdataflow-runner
```