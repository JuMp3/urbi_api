package it.jump3.urbi.service;

import it.jump3.urbi.enumz.RegexTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class RegexService implements IRegex {

    @Override
    public String getRegexFromString(List<String> words) {

        StringBuilder sb = new StringBuilder();
        List<RegexOutPojo> regexOutPojos = new ArrayList<>();

        words.forEach(word -> updateResult(regexOutPojos, processWord(word)));

        regexOutPojos.forEach(regexOutPojo -> {
            sb.append(regexOutPojo.getRegexTypeEnum().regex());
            if (regexOutPojo.getMin() != regexOutPojo.getMax()) {
                sb.append("{");
                sb.append(regexOutPojo.getMin()).append(",").append(regexOutPojo.getMax());
                sb.append("}");
            } else if (regexOutPojo.getMin() > 1) {
                sb.append("{");
                sb.append(regexOutPojo.getMin());
                sb.append("}");
            }
        });

        String regex = Pattern.compile(sb.toString()).toString();

        return "\"".concat(regex).concat("\"");
    }

    private void updateResult(List<RegexOutPojo> regexOutPojos, List<RegexPojo> regexPojos) {

        if (CollectionUtils.isEmpty(regexOutPojos)) {
            // first time
            regexPojos.forEach(regexPojo -> {
                RegexOutPojo regexOutPojo = new RegexOutPojo();
                regexOutPojo.setRegexTypeEnum(regexPojo.getRegexTypeEnum());
                regexOutPojo.setMax(regexPojo.getCount());
                regexOutPojo.setMin(regexPojo.getCount());
                regexOutPojos.add(regexOutPojo);
            });
        } else {

            for (int i = 0; i < regexPojos.size(); i++) {
                RegexPojo regexPojo = regexPojos.get(i);
                RegexOutPojo regexOutPojo = regexOutPojos.get(i);

                if (regexPojo.getCount() != regexOutPojo.getMin()) {
                    regexOutPojo.setMax(Math.max(regexOutPojo.getMax(), regexPojo.getCount()));
                    regexOutPojo.setMin(Math.min(regexOutPojo.getMin(), regexPojo.getCount()));
                }
            }
        }
    }

    private List<RegexPojo> processWord(String word) {

        List<RegexPojo> regexPojoList = new ArrayList<>();
        RegexPojo firstRegex = new RegexPojo(RegexTypeEnum.STRING, 0);
        regexPojoList.add(firstRegex);

        for (int i = 0; i < word.length(); i++) {

            char c = word.charAt(i);
            RegexTypeEnum regexTypeEnum = StringUtils.isNumeric(String.valueOf(c)) ? RegexTypeEnum.NUMBER : RegexTypeEnum.STRING;

            RegexPojo lastItem = regexPojoList.get(regexPojoList.size() - 1);
            if (lastItem.getRegexTypeEnum().equals(regexTypeEnum)) {
                lastItem.setCount(lastItem.getCount() + 1);
            } else {
                regexPojoList.add(new RegexPojo(regexTypeEnum, 1));
            }
        }

        return regexPojoList;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private class RegexPojo {
        private RegexTypeEnum regexTypeEnum;
        private Integer count;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private class RegexOutPojo {
        private RegexTypeEnum regexTypeEnum;
        private Integer min;
        private Integer max;
    }
}
