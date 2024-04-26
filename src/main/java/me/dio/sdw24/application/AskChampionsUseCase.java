package me.dio.sdw24.application;

import me.dio.sdw24.domain.exception.ChampionNotFoundException;
import me.dio.sdw24.domain.model.Champions;
import me.dio.sdw24.domain.ports.ChampionsRepository;
import me.dio.sdw24.domain.ports.GenerativeAiService;

public record AskChampionsUseCase(ChampionsRepository repository, GenerativeAiService genAiApi) {

    public String askChampion(long championId, String question){

        Champions champion = repository.findById(championId)
                .orElseThrow(() -> new ChampionNotFoundException(championId));

        String context = champion.generateContextByQuestion(question);
        String objective = """
                Atue como uma assistente com a habilidade de se comportar como os Campeões do League of Legends (LOL).
                Responda perguntas incorporando a personalidade e estilo do um determinado campeão.
                Segue a pergunta, o nome do Campeão e sua respectiva lore (História):
                
                """;

        genAiApi.generateContent(objective, context);


        return genAiApi.generateContent(objective, context);
    }
}
