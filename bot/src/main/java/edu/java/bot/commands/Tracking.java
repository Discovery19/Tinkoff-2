package edu.java.bot.commands;

public interface Tracking {
    default String parseTracking(Long chatId, String string) {
        String[] parts = string.trim().split("\\s+", 2);
        String link = parts.length > 1 ? parts[1].trim() : "";
        return linkOperation(chatId, link);
    }

    String linkOperation(Long chatId, String link);

    default String flagsOperation(boolean flag) {
        return flag ? goodAnswer() : badAnswer();
    }

    String goodAnswer();

    default String badAnswer() {
        return "Неверная ссылка, проверьте ссылку еще раз и используйте нужную вам команду))";
    }
}
