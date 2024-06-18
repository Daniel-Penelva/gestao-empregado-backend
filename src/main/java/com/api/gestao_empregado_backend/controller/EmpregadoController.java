package com.api.gestao_empregado_backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.gestao_empregado_backend.exception.ResourceNotFoundException;
import com.api.gestao_empregado_backend.model.Empregado;
import com.api.gestao_empregado_backend.repository.EmpregadoRepository;

@RestController
@RequestMapping("/api/v1/")
public class EmpregadoController {

    @Autowired
    private EmpregadoRepository empregadoRepository;

    // http://localhost:8080/api/v1/empregados
    @GetMapping("/empregados")
    public List<Empregado> listarTodosEmpregados() {
        return empregadoRepository.findAll();
    }

    // http://localhost:8080/api/v1/empregados
    @PostMapping("/empregados")
    public Empregado criarEmpregado(@RequestBody Empregado empregado) {
        return empregadoRepository.save(empregado);
    }

    // http://localhost:8080/api/v1/empregados/{id}
    @GetMapping("/empregados/{id}")
    public ResponseEntity<Empregado> buscarEmpregadoPorId(@PathVariable Long id) {

        Empregado empregado = empregadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Não existe empregado com esse Id: " + id));

        return ResponseEntity.ok(empregado);
    }

    // http://localhost:8080/api/v1/empregados/{id}
    @PutMapping("/empregados/{id}")
    public ResponseEntity<Empregado> atualizarEmpregado(@PathVariable Long id, @RequestBody Empregado novoEmpregado) {

        Empregado empregado = empregadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Não existe empregado com esse Id: " + id));

        empregado.setNome(novoEmpregado.getNome());
        empregado.setSobrenome(novoEmpregado.getSobrenome());
        empregado.setEmail(novoEmpregado.getEmail());

        return ResponseEntity.ok(empregadoRepository.save(empregado));
    }

    // http://localhost:8080/api/v1/empregados/{id}
    @DeleteMapping("/empregados/{id}")
    public ResponseEntity<Map<String, Boolean>> deletarEmpregado(@PathVariable Long id) {

        Empregado empregado = empregadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Não existe empregado com esse Id: " + id));

        empregadoRepository.delete(empregado);

        Map<String, Boolean> resposta = new HashMap<>();
        resposta.put("eliminar", Boolean.TRUE);
        return ResponseEntity.ok(resposta);
    }

}
