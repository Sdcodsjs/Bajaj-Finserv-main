package com.bajaj.webhook.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SubmissionRequest {
    @JsonProperty("finalQuery")
    private String finalQuery;

    public SubmissionRequest() {}

    public SubmissionRequest(String finalQuery) {
        this.finalQuery = finalQuery;
    }

    public String getFinalQuery() {
        return finalQuery;
    }

    public void setFinalQuery(String finalQuery) {
        this.finalQuery = finalQuery;
    }

    @Override
    public String toString() {
        return "SubmissionRequest{finalQuery='" + finalQuery + "'}";
    }
}
