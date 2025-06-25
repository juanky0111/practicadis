package com.ufv.dis.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ufv.dis.model.Usuario;
import com.ufv.dis.utils.PdfGenerator;
import org.springframework.stereotype.Service;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

@Service
public class UsuarioService {
    private final String filePath = "src/main/resources/usuarios.json";
    private final Gson gson = new Gson();

    public List<Usuario> getAllUsuarios() {
        try (Reader reader = new FileReader(filePath)) {
            Type listType = new TypeToken<List<Usuario>>(){}.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Usuario getUsuarioById(String id) {
        return getAllUsuarios().stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void addUsuario(Usuario nuevo) {
        List<Usuario> usuarios = getAllUsuarios();
        usuarios.add(nuevo);
        saveUsuarios(usuarios);
    }

    public void updateUsuario(String id, Usuario actualizado) {
        List<Usuario> usuarios = getAllUsuarios();
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getId().equals(id)) {
                usuarios.set(i, actualizado);
                break;
            }
        }
        saveUsuarios(usuarios);
    }

    public void generarPdf() {
        List<Usuario> usuarios = getAllUsuarios();
        PdfGenerator.generarPdf(usuarios);
    }

    private void saveUsuarios(List<Usuario> usuarios) {
        try (Writer writer = new FileWriter(filePath)) {
            gson.toJson(usuarios, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
