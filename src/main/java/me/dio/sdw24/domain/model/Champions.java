package me.dio.sdw24.domain.model;

public record Champions(
        Long id,
        String name,
        String role,
        String lore,
        String imageUrl
) {
    public String generateContextByQuestion(String question){
     return """
        Pergunta: %s
        Name do Campeão: %s
        Função: %s
        Lore (Historia): %s 
     """.formatted(question, this.name, this.role, this.lore);
    }
}
