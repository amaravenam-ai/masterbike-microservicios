package com.masterbikes.usuarios.controllers;
import com.masterbikes.usuarios.services.UsuarioService;
import com.masterbikes.usuarios.config.ApiExceptionHandler;
import org.junit.jupiter.api.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
class UsuarioControllerTest {
    private UsuarioService service;
    private MockMvc mvc;
    @BeforeEach void setup() { service=mock(UsuarioService.class); mvc=MockMvcBuilders.standaloneSetup(new UsuarioController(service)).setControllerAdvice(new ApiExceptionHandler()).build(); }
    @Test void emptyList() throws Exception { when(service.obtenerTodos()).thenReturn(List.of()); mvc.perform(get("/api/v1/usuarios")).andExpect(status().isOk()).andExpect(content().json("[]")); }
    @Test void missingItem() throws Exception { when(service.obtenerPorId(999L)).thenReturn(Optional.empty()); mvc.perform(get("/api/v1/usuarios/999")).andExpect(status().isNotFound()); }
    @Test void invalidBodyRejectedBeforePersistence() throws Exception { mvc.perform(post("/api/v1/usuarios").contentType("application/json").content("{}")).andExpect(status().isBadRequest()); verifyNoInteractions(service); }
    @Test void createValid() throws Exception { when(service.guardar(any())).thenAnswer(i -> i.getArgument(0)); mvc.perform(post("/api/v1/usuarios").contentType("application/json").content("{\"nombre\":\"Usuario Demo\",\"correo\":\"demo@example.invalid\",\"rol\":\"TECNICO\"}")).andExpect(status().isOk()); verify(service).guardar(any()); }
}
