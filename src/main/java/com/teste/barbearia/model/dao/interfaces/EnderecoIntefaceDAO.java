package com.teste.barbearia.model.dao.interfaces;

import com.teste.barbearia.model.entity.Endereco;
import com.teste.barbearia.provider.dto.interfaces.EnderecoDTO;

public interface EnderecoIntefaceDAO {
  public Long save(Endereco endereco);
  public Endereco getById(Long id);
  public void update(Long id, Endereco endereco);
  public void delete(Long id);
}
