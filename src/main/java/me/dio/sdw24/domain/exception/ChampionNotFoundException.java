package me.dio.sdw24.domain.exception;

public class ChampionNotFoundException extends RuntimeException {
    public ChampionNotFoundException(long championId) {
        super("Champions %d not found.".formatted(championId));
    }
}
