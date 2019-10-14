package com.example.android.uchu;

public class SkillConverter {

    public static String convertToShortSkill(String fullSkill) {
        if (fullSkill.equals("Английский язык")) return "en";
        if (fullSkill.equals("Немецкий язык")) return "ge";
        if (fullSkill.equals("Французский язык")) return "fr";
        if (fullSkill.equals("Испанский язык")) return "sp";
        if (fullSkill.equals("Итальянский язык")) return "it";
        if (fullSkill.equals("Китайский язык")) return "ch";
        if (fullSkill.equals("Японский язык")) return "jp";
        return "default skill";
    }

    public static String convertToFullSkill(String shortSkill) {
        if (shortSkill.equals("en")) return "Английский язык";
        if (shortSkill.equals("ge")) return "Немецкий язык";
        if (shortSkill.equals("fr")) return "Французский язык";
        if (shortSkill.equals("sp")) return "Испанский язык";
        if (shortSkill.equals("it")) return "Итальянский язык";
        if (shortSkill.equals("ch")) return "Китайский язык";
        if (shortSkill.equals("jp")) return "Японский язык";
        return "default skill";
    }

    public static int convertToIndex(String shortSkill) {
        if (shortSkill.equals("en")) return 1;
        if (shortSkill.equals("ge")) return 2;
        if (shortSkill.equals("fr")) return 3;
        if (shortSkill.equals("sp")) return 4;
        if (shortSkill.equals("it")) return 5;
        if (shortSkill.equals("ch")) return 6;
        if (shortSkill.equals("jp")) return 7;
        return 0;
    }

}
