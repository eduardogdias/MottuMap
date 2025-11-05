package br.com.fiap.mottumap.model.dto.patio;

import br.com.fiap.mottumap.model.entity.Patio;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatioResponseDTO { //usado nas responses do GET, POST e PUT
    private Integer id;
    private String nome;
    private String endereco;

    public PatioResponseDTO(Patio patio) {
        this.id = patio.getId();
        this.nome = patio.getNome();
        this.endereco = patio.getEndereco();
    }
}