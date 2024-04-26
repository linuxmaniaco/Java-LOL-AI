package me.dio.sdw24.adapters.in;

import io.swagger.v3.oas.annotations.tags.Tag;
import me.dio.sdw24.application.AskChampionsUseCase;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Campeão", description = "Endpoint de dominio campeão do LOL.")
@RestController
@RequestMapping("/champions")
public record AskChampionRestController(AskChampionsUseCase useCase) {

    @PostMapping("/{championId}/ask")
    public AskChampionResponse askChampion(@PathVariable Long championId, @RequestBody AskChampionRequest request){
        String answer = useCase.askChampion(championId, request.question());
        return new AskChampionResponse(answer);
    }

    public record AskChampionRequest(String question){}
    public record AskChampionResponse(String answer){}
}
