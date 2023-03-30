package com.cosmos.back.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class NounModelTest {

    @Test
    public void toStringTest() throws Exception{
        //given
        Noun noun = Noun.builder().id(1L).contents("test").build();

        //when
        String result = noun.toString();

        //then
        Assertions.assertThat(result).isEqualTo("1 test");


    }
}
