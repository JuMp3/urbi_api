package it.jump3.urbi.enumz;

public enum RegexTypeEnum {

    STRING("[A-Z]"),
    NUMBER("\\d");

    private String regex;

    RegexTypeEnum(String regex) {
        this.regex = regex;
    }

    public String regex() {
        return this.regex;
    }
}
