package com.microService.repositorios;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.microService.entidades.Usuario;


public interface UsuarioRepository extends PagingAndSortingRepository  <Usuario, Integer> {


}
