package com.conference.spring.test.service;

import com.conference.spring.test.common.utils.WordsUtil;
import com.conference.spring.test.domain.Question;
import com.conference.spring.test.domain.QuestionType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author tolkv
 * @version 23/02/2017
 */
@Service
public class TextBasedQuestionTypeResolver implements QuestionTypeResolver {
  @Value("${tokens.jbaruch}")
  String jbaruchTokensString;

  @Value("${tokens.yegor256}")
  String yegor256TokensString;

  @Override
  public QuestionType resolveType(Question question) {
    long jbaruchScore = WordsUtil.getWords(question.getBody().toLowerCase()).stream()
        .filter(s -> jbaruchTokensString.contains(s))
        .count();
    long yegor256Score = WordsUtil.getWords(question.getBody().toLowerCase()).stream()
        .filter(s -> yegor256TokensString.contains(s))
        .count();

    if(jbaruchScore > yegor256Score) {
      return QuestionType.JBARUCH;
    }

    return QuestionType.YEGOR256;
  }
}
