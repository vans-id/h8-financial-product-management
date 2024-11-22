package id.co.cimbniaga.financialproductmanagement.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MessageRequestDTO {
    private String detail;
    @JsonProperty("activity_type")
    private String activityType;

}
