package groovy.blog

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

@JsonCreator
record TodoStats(@JsonProperty('total') int total,
                 @JsonProperty('completed') int completed,
                 @JsonProperty('totalScheduled') int totalScheduled,
                 @JsonProperty('completedOnScheduled') int completedOnSchedule) {}
