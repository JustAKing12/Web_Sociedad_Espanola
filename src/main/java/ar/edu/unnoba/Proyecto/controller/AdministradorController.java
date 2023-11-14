package ar.edu.unnoba.Proyecto.controller;

import ar.edu.unnoba.Proyecto.model.Novedad;
import ar.edu.unnoba.Proyecto.model.Usuario;
import ar.edu.unnoba.Proyecto.service.NovedadService;
import ar.edu.unnoba.Proyecto.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
@RequestMapping("/administrador")
public class AdministradorController {

    @Autowired
    private NovedadService novedadService;

    @Autowired
    private UsuarioService usuarioService;

    public Usuario obtenerUsuarioActual() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return (Usuario) usuarioService.loadUserByUsername(currentPrincipalName);
    }//Recuperar instancia del usuario en sesion

    @GetMapping("/main")
    public String adminPage(Model model) {
        Usuario usuarioActual = obtenerUsuarioActual();

        String username = usuarioActual.getUsername();
        model.addAttribute("username", username);//para que username? para diseñar un rectangulo, donde el medio tenga el nombre y al apretarlo pueda seleccionar logout.
        model.addAttribute("novedades", novedadService.getAll());
        return "administradores/main";
    }//La idea es tener un principal, donde por ejemplo tener rectangulos cuando si los apretas vas a la acción especifica contra una novedad.

    @RequestMapping(value = "/main/novedad/new", method = {RequestMethod.GET, RequestMethod.POST})
    public String createNovedad(@ModelAttribute("novedad") Novedad novedad, Model model) { //crear y actualizar
        Usuario usuarioActual = obtenerUsuarioActual();

        if (novedad.getTitulo() != null) { //guardar la novedad si el título tiene algún valor
            novedadService.save(novedad); //novedadService ya tiene los datos para modificar
            return "redirect:/main"; }
        else { //mostrar el formulario
            String username = usuarioActual.getUsername();
            model.addAttribute("username", username);
            model.addAttribute("novedad", new Novedad());
            return "administradores/create-and-update"; } //luego accede por segunda vez al mismo metodo para guardar la novedad
    }

    @GetMapping("/main/novedad/delete/{id}")
    public String deleteNovedad(@PathVariable Long id) {
        Usuario usuarioActual = obtenerUsuarioActual();

        novedadService.delete(id);
        return "redirect:/main";
    }//FUNCIONALIDAD: eliminar un usuario (no es necesario un formulario (PostMapping))
}