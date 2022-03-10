package com.teste.barbearia.provider;

import com.teste.barbearia.model.entity.Endereco;
import com.teste.barbearia.provider.dto.EnderecoViaCepDTO;
import com.teste.barbearia.provider.dto.interfaces.EnderecoDTO;

public interface ApiEndereco {
    public Endereco request(String url);
}
