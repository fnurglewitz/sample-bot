package it.pwned.telegram.samplebot.imgur.type;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class ImgurResponse<T> {

    private final static String JSON_FIELD_DATA = "data";
    private final static String JSON_FIELD_SUCCESS = "success";
    private final static String JSON_FIELD_STATUS = "status";

    @JsonProperty(JSON_FIELD_DATA)
    public final T[] data;

    @JsonProperty(JSON_FIELD_SUCCESS)
    public final Boolean success;

    @JsonProperty(JSON_FIELD_STATUS)
    public final Integer status;

    public ImgurResponse(
            @JsonProperty(JSON_FIELD_DATA) T[] data,
            @JsonProperty(JSON_FIELD_SUCCESS) Boolean success,
            @JsonProperty(JSON_FIELD_STATUS) Integer status
    ) {

        this.data = data;
        this.success = success;
        this.status = status;

    }
}
