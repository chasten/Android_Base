package com.android.base.androidbaseproject.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Base data structure
 * Created by secretqi on 2018/3/1.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseData {

    private String message;

    public String getMessage() {
        return message;
    }

    @JsonProperty("message")
    public void setMessage(String message) {
        this.message = message;
    }
}
