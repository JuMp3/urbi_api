package it.jump3.urbi.service.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class RegexRequest implements Serializable {

    @NotNull(message = "{NotNull.regexRequest.words}")
    private List<String> words;
}
