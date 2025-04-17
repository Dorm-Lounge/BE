package dorm.lounge.domain.auth.service;

import org.springframework.stereotype.Component;

@Component
public class NicknameGenerator {

    private static final String[] ADJECTIVES = {"멋진", "귀여운", "똑똑한", "엉뚱한", "용감한"};
    private static final String[] ANIMALS = {"토끼", "사자", "곰", "너구리", "수달"};

    public String generate() {
        int adjIdx = (int) (Math.random() * ADJECTIVES.length);
        int aniIdx = (int) (Math.random() * ANIMALS.length);
        int number = (int) (Math.random() * 900 + 100); // 100~999

        return ADJECTIVES[adjIdx] + ANIMALS[aniIdx] + "#" + number;
    }
}
