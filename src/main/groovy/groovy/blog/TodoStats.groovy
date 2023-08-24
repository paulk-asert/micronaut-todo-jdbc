package groovy.blog

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

@JsonCreator
record TodoStats(@JsonProperty('total') long total,
                 @JsonProperty('completed') long completed,
                 @JsonProperty('totalScheduled') long totalScheduled,
                 @JsonProperty('completedOnScheduled') long completedOnSchedule) {}
