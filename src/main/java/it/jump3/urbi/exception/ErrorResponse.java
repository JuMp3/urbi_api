package it.jump3.urbi.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import it.jump3.urbi.utils.DateUtil;
import it.jump3.urbi.utils.JsonLocalDateTimeDeserializer;
import it.jump3.urbi.utils.JsonLocalDateTimeSerializer;
import it.jump3.urbi.utils.Utility;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Data
@EqualsAndHashCode
public class ErrorResponse implements Serializable {

    @ApiModelProperty(required = true, value = "Correlation ID del flusso andato in errore")
    private String correlationId;

    @JsonProperty("timestamp")
    @JsonSerialize(using = JsonLocalDateTimeSerializer.class)
    @JsonDeserialize(using = JsonLocalDateTimeDeserializer.class)
    @ApiModelProperty(required = true, value = "Orario dell'errore")
    private LocalDateTime localDateTime = DateUtil.nowLocalDateTime("CET");

    @JsonProperty("code")
    @ApiModelProperty(required = true, value = "Codice dell'errore")
    private int code;

    @JsonProperty("messages")
    @ApiModelProperty(required = true, value = "Messaggi dell'errore")
    private List<String> messages;

    @JsonProperty("errorSourceSystem")
    @ApiModelProperty(required = true, value = "Sistema andato in errore. Valorizzato dal microservizio.")
    private String errorSourceSystem;

    @JsonProperty("exceptionType")
    @ApiModelProperty(required = true, value = "Tipo di eccezione.")
    private String exceptionType;

    @JsonProperty("errorCode")
    @ApiModelProperty(required = true, value = "Codice errore.")
    private String errorCode;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ErrorResponse {\n");
        sb.append("    correlationId: ").append(this.correlationId).append("\n");
        sb.append("    localDateTime: ").append(this.toIndentedString(this.localDateTime != null ? DateUtil.getFromLocalDateTimeDefault(this.localDateTime) : null)).append("\n");
        sb.append("    code: ").append(this.toIndentedString(this.code)).append("\n");
        sb.append("    messages: ").append(this.toIndentedString(Utility.convertListInString(this.messages, "{", "}"))).append("\n");
        sb.append("    errorSourceSystem: ").append(toIndentedString(this.errorSourceSystem)).append("\n");
        sb.append("    errorCode: ").append(toIndentedString(this.errorCode)).append("\n");
        sb.append("    exceptionType: ").append(toIndentedString(this.exceptionType)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    @JsonIgnore
    protected String toIndentedString(Object o) {
        return o == null ? "null" : o.toString().replace("\n", "\n    ");
    }

    @JsonIgnore
    public String getMessage() {
        return !CollectionUtils.isEmpty(getMessages()) ? Utility.convertListInString(getMessages()) : null;
    }

    @JsonIgnore
    public void setMessage(String message) {
        if (StringUtils.isNotBlank(message)) {
            this.messages = new LinkedList<>();
            this.messages.add(message);
        }
    }

    @JsonIgnore
    public void addMessage(String message) {
        if (StringUtils.isNotBlank(message)) {
            if (this.messages == null) {
                this.messages = new LinkedList<>();
            }
            this.messages.add(message);
        }
    }
}
