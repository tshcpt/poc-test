package com.sample.rest.webservices.ledger.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown= true)
public class CriteriaData {
    public String peCode;
    public String poId;
    public String serviceCode;
    public String eodId;
    public String mopId;
}
