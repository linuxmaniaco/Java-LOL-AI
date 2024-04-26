package me.dio.sdw24.adapters.out;


import feign.RequestInterceptor;
import me.dio.sdw24.domain.ports.GenerativeAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "openAiChatApi", url = "${openai.base-url}", configuration = OpenAiChatService.Config.class)
public interface OpenAiChatService extends GenerativeAiService {

    @PostMapping("/v1/chat/completions")
    OpenAiChatCompletionResp chatCompletion(OpenAiChatCompletionReq req);

    @Override
    default String generateContent(String objective, String context){

        String model = "gpt-3.5-turbo";
        List<Message> messages = List.of(
                new Message("system", objective),
                new Message("user", context)

        );
        OpenAiChatCompletionReq req = new OpenAiChatCompletionReq(model, messages);
        OpenAiChatCompletionResp resp = chatCompletion(req);

        // Verificar se a resposta não está vazia e retornar a primeira escolha
        if (!resp.choices().isEmpty()) {
            Message message = resp.choices().get(0).message();
            return message.content();
        } else {
            // Retorna uma mensagem padrão ou lança uma exceção, dependendo da lógica do seu aplicativo
            return "Não houve resposta do servidor OpenAI.";
        }

    }

    record OpenAiChatCompletionReq(String model, List<Message> messages){ }
    record Message(String role, String content){ }


    record OpenAiChatCompletionResp(List<Choice> choices){ }
    record Choice(Message message){ }

    class Config {
        @Bean
        public RequestInterceptor apiKeyRequestInterceptor(@Value("${openai.api-key}") String apiKey) {
            return requestTemplate -> requestTemplate.header(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(apiKey));
        }
    }
}
