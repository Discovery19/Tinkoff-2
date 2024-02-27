package edu.java.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

/**
 * ApiErrorResponse
 */
@Validated
@Getter
@Setter
public class ApiErrorResponse   {
    @JsonProperty("description")
    private String description = null;

    @JsonProperty("code")
    private String code = null;

    @JsonProperty("exceptionName")
    private String exceptionName = null;

    @JsonProperty("exceptionMessage")
    private String exceptionMessage = null;


    public ApiErrorResponse description(String description) {
        this.description = description;
        return this;
    }

    /**
     * Get description
     * @return description
     **/
    @Schema(description = "")

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ApiErrorResponse code(String code) {
        this.code = code;
        return this;
    }

    /**
     * Get code
     * @return code
     **/
    @Schema(description = "")

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ApiErrorResponse exceptionName(String exceptionName) {
        this.exceptionName = exceptionName;
        return this;
    }

    /**
     * Get exceptionName
     * @return exceptionName
     **/
    @Schema(description = "")

    public String getExceptionName() {
        return exceptionName;
    }

    public void setExceptionName(String exceptionName) {
        this.exceptionName = exceptionName;
    }

    public ApiErrorResponse exceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
        return this;
    }

    /**
     * Get exceptionMessage
     * @return exceptionMessage
     **/
    @Schema(description = "")

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }


}
