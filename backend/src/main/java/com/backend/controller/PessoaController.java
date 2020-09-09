/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.backend.controller;

import com.backend.dto.PessoaDto;
import com.backend.dto.Response;
import com.backend.dto.RetornoCadastro;
import com.backend.entity.Pessoa;
import com.backend.enums.EnumSexo;
import com.backend.services.PessoaService;
import com.backend.services.util.HelperUtil;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author hudson.magalhaes
 */
@RestController
@RequestMapping("/api/pessoa")
@CrossOrigin(origins = "*")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    public static final Logger logger = LoggerFactory.getLogger(PessoaController.class);

    @GetMapping()
    public ResponseEntity<Response<List<PessoaDto>>> listarTodos() {
        Response<List<PessoaDto>> response = new Response<>();
        List<Pessoa> lancamentos = this.pessoaService.findAll();

        List<PessoaDto> pessoas = new ArrayList<>();
        for (Pessoa lancamento : lancamentos) {
            pessoas.add(this.converterCadastroPessoaDto(lancamento));
        }

        response.setDados(pessoas);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Response<PessoaDto>> listarPorId(@PathVariable("id") Long id) {
        logger.debug("Buscando lançamento por ID: {}", id);
        Response<PessoaDto> response = new Response<>();
        Optional<Pessoa> lancamento = this.pessoaService.find(id);

        if (!lancamento.isPresent()) {
            response.getErrors().add("Pessoa não encontrada para o id " + id);
            return ResponseEntity.badRequest().body(response);
        }

        response.setDados(this.converterCadastroPessoaDto(lancamento.get()));
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Response<RetornoCadastro>> cadastrar(@Valid @RequestBody PessoaDto dto,
            BindingResult result) throws NoSuchAlgorithmException {

        logger.debug("Cadastrando PF: {}", dto.toString());
        Response<RetornoCadastro> response = new Response<>();

        validadarDados(dto, result);

        if (result.hasErrors()) {
            logger.error("Erro validando dados de cadastro PF: {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        Pessoa pessoa = this.converterDtoParaPessoa(dto);
        pessoa = this.pessoaService.save(pessoa);

        response.setDados(new RetornoCadastro("Operação realizada com sucesso!", pessoa.getId()));
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Response<RetornoCadastro>> atualizar(@PathVariable("id") Long id,
            @Valid @RequestBody PessoaDto dto, BindingResult result) throws ParseException {

        Response<RetornoCadastro> response = new Response<>();
        validadarDados(dto, result);

        if (dto.getId() != id) {
            response.getErrors().add("Referência do objeto diferente do parâmetro de atualização");
            return ResponseEntity.badRequest().body(response);
        }

        if (result.hasErrors()) {
            logger.error("Erro validando dados de cadastro PF: {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        Optional<Pessoa> p1 = this.pessoaService.find(id);
        if (!p1.isPresent()) {
            response.getErrors().add("Nenhum registro encontrado para o Id=" + id);
            return ResponseEntity.badRequest().body(response);
        }

        Pessoa pessoa = this.converterDtoParaPessoa(dto);
        pessoa = this.pessoaService.update(pessoa);

        response.setDados(new RetornoCadastro("Operação realizada com sucesso!", pessoa.getId()));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Response<String>> remover(@PathVariable("id") Long id) {
        Response<String> response = new Response<String>();
        Optional<Pessoa> p1 = this.pessoaService.find(id);

        if (!p1.isPresent()) {

            response.getErrors().add("Erro ao remover pessoa. Registro não encontrado para o id " + id);
            return ResponseEntity.badRequest().body(response);
        }

        this.pessoaService.delete(p1.get());
        response.setDados("Operação realizada com sucesso!");
        return ResponseEntity.ok(response);
    }

    private void validadarDados(PessoaDto dto, BindingResult result) {
        if (dto.getDataNascimento() == null) {
            result.addError(new ObjectError("Pessoa", "Data de nascimento inválida"));
        }

        if (dto.getCpf() == null || dto.getCpf().isEmpty() || !HelperUtil.ValidarCpfCnpj(HelperUtil.limparPontos(dto.getCpf()))) {
            result.addError(new ObjectError("Pessoa", "CPF inválido"));
        } else {
            if (dto.getId() == 0) {
                Optional<Pessoa> p1 = this.pessoaService.findByCpf(dto.getCpf());
                if (p1.isPresent()) {
                    result.addError(new ObjectError("Pessoa", "CPF já cadastrado"));
                }
            }
        }
    }

    private Pessoa converterDtoParaPessoa(PessoaDto dto) {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(dto.getNome());
        pessoa.setEmail(dto.getEmail());
        pessoa.setCpf(HelperUtil.limparPontos(dto.getCpf()));
        pessoa.setDataNascimento(dto.getDataNascimento());
        pessoa.setId(dto.getId());
        pessoa.setNacionalidade(dto.getNacionalidade());
        pessoa.setNaturalidade(dto.getNaturalidade());
        pessoa.setSexo(dto.getSexo().equalsIgnoreCase("MASCULINO") ? EnumSexo.ENSexo.MASCULINO : EnumSexo.ENSexo.FEMININO);

        return pessoa;
    }

    private PessoaDto converterCadastroPessoaDto(Pessoa pessoa) {
        PessoaDto dto = new PessoaDto();
        dto.setId(pessoa.getId());
        dto.setNome(pessoa.getNome());
        dto.setEmail(pessoa.getEmail());
        dto.setCpf(pessoa.getCpf());
        dto.setDataNascimento(pessoa.getDataNascimento());
        dto.setNacionalidade(pessoa.getNacionalidade());
        dto.setNaturalidade(pessoa.getNaturalidade());
        dto.setSexo(pessoa.getSexo().toString());

        return dto;
    }

}
